/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author sundar
 * @since 2017-10-22
 * @modified 2017-10-28
 */
public class DataSourceConnection {

    private static final Logger log = Logger.getLogger(DataSourceConnection.class);

    private static Connection conn = null;
    private static String dbAddress = null;
    private static String dbUserName = null;
    private static String dbPassword = null;
    private static String DRIVER_CLASS_NAME = null;

    private Properties dbPros = null;

    public DataSourceConnection() {
        try {
            dbPros = new Properties();
//            dbPros.load(new FileInputStream(CrudConstants.DB_PROPERTIES_FILE_PATH));
            dbPros.load(DataSourceConnection.class.getClassLoader().getResourceAsStream(CrudConstants.DB_PROPERTIES_FILE_PATH));
            dbAddress = dbPros.getProperty("address");
            dbUserName = dbPros.getProperty("userName");
            dbPassword = dbPros.getProperty("password");
            DRIVER_CLASS_NAME = dbPros.getProperty("driverName");
        } catch (IOException ex) {
            log.error("Exception occurred while load DB Properties : " + ex, ex);
        }
    }

    public Connection getInstance() {
        if (conn == null) {
            log.info("Creating **New** Connection...");
            conn = getDataSourceConnection();
            log.info("Created **New** Connection.");
        }
        return conn;
    }

    private static Connection getDataSourceConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(dbAddress, dbUserName, dbPassword);
        } catch (ClassNotFoundException | SQLException ex) {
            log.error("Exception occurred while get Connection : " + ex, ex);
        }
        return conn;
    }
}
