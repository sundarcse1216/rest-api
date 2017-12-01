/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.utils;

/**
 *
 * @author sundar
 * @since 2017-10-22
 * @modified 2017-10-28
 */
public interface CrudConstants {

//    public String DB_PROPERTIES_FILE_PATH = "WebService-1.0/WEB-INF/classes/conf/dbProperties.properties";
//    public String QUERY_FILE_PATH = "WebService-1.0/WEB-INF/classes/conf/queries/queries.xml";
    
    public String DB_PROPERTIES_FILE_PATH = "conf/dbProperties.properties";
    public String QUERY_FILE_PATH = "conf/queries/queries.xml";

    public String GET_ALL_RECORDS_QUERY_KEY = "GET_ALL_REGORDS";
    public String GET_SPECIFIC_RECIRD_KEY = "GET_PARTICULAR_RECORD";
    public String INSERT_RECORD_KEY = "ADD_RECORDS";
    public String UPDATE_RECORD_KEY = "UPDATE_RECORD";
    public String DELETE_RECORD_KEY = "DELETE_RECORD";

}
