/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.jwt.utils;

import java.security.Key;

/**
 *
 * @author sundar
 * @since 2017-10-28
 * @modified 2017-10-28
 */
public interface KeyGenerator {

    Key generateKey();
}
