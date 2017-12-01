/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.impl;

import com.sundar.rest.CrudOperation;
import com.sundar.rest.model.CrudOperationDO;
import com.sundar.rest.utils.CrudConstants;
import com.sundar.rest.utils.DataSourceConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author sundar
 * @since 2017-10-22
 * @modified 2017-10-22
 */
public class CrudOperationImpl implements CrudOperation {

    private static final Logger log = Logger.getLogger(CrudOperationImpl.class);
    private Properties queryPro = new Properties();
    private DataSourceConnection dataSource = null;
    private Connection conn = null;

    public CrudOperationImpl() {
        try {
            log.info("CrudOperationImpl");
            dataSource = new DataSourceConnection();
            queryPro = new Properties();
            log.info("QUERY_FILE_PATH : " + CrudConstants.QUERY_FILE_PATH);
            log.info(queryPro);
            queryPro.loadFromXML(CrudOperationImpl.class.getClassLoader().getResourceAsStream(CrudConstants.QUERY_FILE_PATH));
            conn = dataSource.getInstance();
        } catch (IOException ex) {
            log.error("Exception occurred while load Query Properties : " + ex, ex);
        }
    }

    @Override
    public Map<String, Object> getAllRecords() {
        String query = queryPro.getProperty(CrudConstants.GET_ALL_RECORDS_QUERY_KEY);
        log.info("Get all Record Query : " + query);
        List<CrudOperationDO> list = null;
        Map<String, Object> resultMap = new HashMap<>();
        try (PreparedStatement pre = conn.prepareStatement(query); ResultSet rs = pre.executeQuery();) {
            list = new ArrayList<>();
            while (rs.next()) {
                CrudOperationDO crudDO = new CrudOperationDO();

                crudDO.setId(rs.getInt("id"));
                crudDO.setEmpID(rs.getString("empid"));
                crudDO.setName(rs.getString("name"));
                crudDO.setAge(rs.getInt("age"));
                crudDO.setGender(rs.getString("gender"));
                crudDO.setSalary(rs.getLong("salary"));
                crudDO.setLocation(rs.getString("location"));
                crudDO.setStatus(rs.getString("status"));

                list.add(crudDO);
            }
            if (list != null && !list.isEmpty()) {
                resultMap.put("status", 200);
                resultMap.put("result", list);
            }
        } catch (Exception ex) {
            log.error("Exception occurred while get all records : " + ex, ex);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getParticularRecord(String empID) {
        String query = queryPro.getProperty(CrudConstants.GET_SPECIFIC_RECIRD_KEY);
        log.info("EmployeesID : " + empID);
        log.info("Get particular Record Query : " + query);
        ResultSet rs = null;
        List<CrudOperationDO> list = null;
        Map<String, Object> resultMap = new HashMap<>();
        try (PreparedStatement pre = conn.prepareStatement(query);) {
            pre.setString(1, empID);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                CrudOperationDO crudDO = new CrudOperationDO();

                crudDO.setId(rs.getInt("id"));
                crudDO.setEmpID(rs.getString("empid"));
                crudDO.setName(rs.getString("name"));
                crudDO.setAge(rs.getInt("age"));
                crudDO.setGender(rs.getString("gender"));
                crudDO.setSalary(rs.getLong("salary"));
                crudDO.setLocation(rs.getString("location"));
                crudDO.setStatus(rs.getString("status"));

                list.add(crudDO);
            }
            if (list != null && !list.isEmpty()) {
                resultMap.put("status", 200);
                resultMap.put("result", list);
            }
        } catch (Exception ex) {
            log.error("Exception occurred while get particular records : " + ex, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    log.info("Exception occurred while close ResultSet : " + ex, ex);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, String> insertRecord(CrudOperationDO crudDO) {
        String query = queryPro.getProperty(CrudConstants.INSERT_RECORD_KEY);
        log.info("Insert Record Query : " + query);
        ResultSet rs = null;
        Map<String, String> resultMap = new HashMap<>();
        try (PreparedStatement pre = conn.prepareStatement(query);) {
            pre.setString(1, crudDO.getEmpID());
            pre.setString(2, crudDO.getName());
            pre.setInt(3, crudDO.getAge());
            pre.setString(4, crudDO.getGender());
            pre.setLong(5, crudDO.getSalary());
            pre.setString(6, crudDO.getLocation());
            pre.setString(7, "A");

            int result = pre.executeUpdate();

            if (result != 0) {
                resultMap.put("status", "200");
                resultMap.put("result", "Record inserted Successfully");
            } else {
                resultMap.put("status", "422");
                resultMap.put("result", "Record not inserted");
            }

        } catch (Exception ex) {
            log.error("Exception occurred while insert records : " + ex, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    log.info("Exception occurred while close ResultSet : " + ex, ex);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, String> updateRecord(CrudOperationDO crudDO) {
        String query = queryPro.getProperty(CrudConstants.UPDATE_RECORD_KEY);
        log.info("Update Record Query : " + query);
        ResultSet rs = null;
        Map<String, String> resultMap = new HashMap<>();
        log.info("EmpID: " + crudDO.getEmpID());
        try (PreparedStatement pre = conn.prepareStatement(query);) {
            pre.setString(1, crudDO.getName());
            pre.setInt(2, crudDO.getAge());
            pre.setString(3, crudDO.getGender());
            pre.setLong(4, crudDO.getSalary());
            pre.setString(5, crudDO.getLocation());
            pre.setString(6, crudDO.getEmpID());

            int result = pre.executeUpdate();

            if (result != 0) {
                resultMap.put("status", "200");
                resultMap.put("result", "Record inserted Successfully");
            } else {
                resultMap.put("status", "422");
                resultMap.put("result", "Record not inserted");
            }

        } catch (Exception ex) {
            log.error("Exception occurred while insert records : " + ex, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    log.info("Exception occurred while close ResultSet : " + ex, ex);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, String> deleteRecord(String empID) {
        String query = queryPro.getProperty(CrudConstants.DELETE_RECORD_KEY);
        log.info("EmployeesID : " + empID);
        log.info("Delete Record Query : " + query);
        ResultSet rs = null;
        Map<String, String> resultMap = new HashMap<>();
        try (PreparedStatement pre = conn.prepareStatement(query);) {
            pre.setString(1, empID);
            int result = pre.executeUpdate();

            if (result != 0) {
                resultMap.put("status", "200");
                resultMap.put("result", "Record detelete Successfully");
            } else {
                resultMap.put("status", "422");
                resultMap.put("result", "No matches Found");
            }

        } catch (Exception ex) {
            log.error("Exception occurred while delete records : " + ex, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    log.info("Exception occurred while close ResultSet : " + ex, ex);
                }
            }
        }
        return resultMap;
    }
}
