/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.model;

/**
 *
 * @author sundar
 * @since 2017-10-28
 * @modifies 2017-10-28
 */
public class UserDO {

    private String userName;
    private String password;

    public UserDO() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
