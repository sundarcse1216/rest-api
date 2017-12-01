/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.services;

import com.sundar.jwt.KeyGeneratorImpl;
import com.sundar.jwt.utils.KeyGenerator;
import com.sundar.rest.model.UserDO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author sundar
 * @since 2017-10-28
 * @modified 2017-10-28
 */
@Path("/users")
public class UserManagementService {

    private static final Logger log = Logger.getLogger(UserManagementService.class);

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    @POST
    @Path("/login")
    @Consumes(APPLICATION_JSON)
    public Response authenticateUser(UserDO user) {

        String login = null;
        String password = null;
        String token = null;

        try {
            login = user.getUserName();
            password = user.getPassword();

            log.info("login/password : " + login + " / " + password);

            // Authenticate the user using the credentials provided
            boolean isValid = authenticate(login, password);

            // Issue a token for the user
            if (isValid) {
                token = issueToken(login);
            }

            if (token != null) {
                // Return the token on the response
                return Response.ok().header(AUTHORIZATION, "token " + token).build();
            } else {
                return Response.status(UNAUTHORIZED).build();
            }

        } catch (Exception ex) {
            log.error("Exception occurred while authenticate : " + ex, ex);
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String login, String password) throws Exception {
        if ("sundar".equals(login) && "123&S#@".equals(password)) {
            log.info("Valid UserName and Password");
            return true;
        } else {
            throw new SecurityException("Invalid user/password");
        }
    }

    private String issueToken(String login) {
        keyGenerator = new KeyGeneratorImpl();
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        log.info("generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
