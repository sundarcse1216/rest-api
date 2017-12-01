/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.services;

import com.sundar.rest.CrudOperation;
import com.sundar.rest.impl.CrudOperationImpl;
import com.sundar.rest.model.CrudOperationDO;
import com.sundar.rest.utils.EmployeeValidation;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author sundar
 * @since 2017-10-21
 * @modified 2017-10-22
 */
@Path("/crud")
public class CrudOperationService {

    private static final Logger log = Logger.getLogger(CrudOperationService.class);
    private CrudOperation crudOper = null;

    public CrudOperationService() {
        log.info("Inside the CrudOperationService");
        crudOper = new CrudOperationImpl();
    }

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecords() {
        int statusCode = 422;
        Object resultList = null;
        try {
            Map<String, Object> result = crudOper.getAllRecords();
            if (result != null && !result.isEmpty()) {
                resultList = result.get("result");
                statusCode = Integer.valueOf(result.get("status").toString());
            }
        } catch (Exception ex) {
            log.info("Exception occurred while get All Records: " + ex, ex);
        }
        return Response.status(statusCode).entity(resultList).build();
    }

    @GET
    @Path("/employees/{empid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticularRecord(@PathParam("empid") String empID) {
        int statusCode = 422;
        Object resultList = null;
        try {
            Map<String, Object> result = crudOper.getParticularRecord(empID);
            if (result != null && !result.isEmpty()) {
                resultList = result.get("result");
                statusCode = Integer.valueOf(result.get("status").toString());
            }
        } catch (Exception ex) {
            log.info("Exception occurred while get particular Records: " + ex, ex);
        }
        return Response.status(statusCode).entity(resultList).build();
    }

    @POST
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddRecords(CrudOperationDO crudDO) {
        Map<String, Object> resultMap = new HashMap<>();
        int statusCode = 422;
        EmployeeValidation validate = new EmployeeValidation();
        try {
            String validateMessage = validate.ValidateInput(crudDO.getEmpID(), crudDO.getName(), crudDO.getAge(), crudDO.getGender(), crudDO.getSalary(), crudDO.getLocation());
            if (validateMessage.isEmpty()) {
                Map<String, String> result = crudOper.insertRecord(crudDO);
                statusCode = Integer.valueOf(result.get("status"));
                resultMap.put("message", result.get("result"));
            } else {
                statusCode = 422;
                resultMap.put("message", validateMessage);
                resultMap.put("status", statusCode);
            }
        } catch (Exception ex) {
            log.info("Exception occurred while Insert Records: " + ex, ex);
        }

        return Response.status(statusCode).entity(resultMap).build();
    }

    @PUT
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRecords(CrudOperationDO crudDO) {
        Map<String, Object> resultMap = new HashMap<>();
        int statusCode = 422;
        EmployeeValidation validate = new EmployeeValidation();
        try {
            String validateMessage = validate.ValidateInput(crudDO.getEmpID(), crudDO.getName(), crudDO.getAge(), crudDO.getGender(), crudDO.getSalary(), crudDO.getLocation());
            if (validateMessage.isEmpty()) {
                Map<String, String> result = crudOper.updateRecord(crudDO);
                statusCode = Integer.valueOf(result.get("status"));
                resultMap.put("message", result.get("result"));
            } else {
                statusCode = 422;
                resultMap.put("message", validateMessage);
                resultMap.put("status", statusCode);
            }
        } catch (Exception ex) {
            log.info("Exception occurred while Update Records: " + ex, ex);
        }

        return Response.status(statusCode).entity(resultMap).build();
    }

    @DELETE
    @Path("/employees/{empid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeRecords(@PathParam("empid") String empID) {
        log.info("Inside the removeRecords()");
        int statusCode = 422;
        String message = null;
        Map<String, String> resultMap = new HashMap<>();
        try {
            Map<String, String> result = crudOper.deleteRecord(empID);
            if (result != null && !result.isEmpty()) {
                message = result.get("result");
                resultMap.put("message", message);
                statusCode = Integer.valueOf(result.get("status"));
            }
        } catch (Exception ex) {
            log.info("Exception occurred while Delete Records: " + ex, ex);
        }
        return Response.status(statusCode).entity(resultMap).build();
    }
}
