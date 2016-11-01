/**
 * 
 */
package com.roy.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roy.domain.User;
import com.roy.service.UserService;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * A {@link io.vertx.core.Verticle} which handles requests for the REST
 * controller.
 * 
 * @author Roy
 */
@Component
public class UserVerticleAPI {
	private Logger log = LoggerFactory.getLogger(UserVerticleAPI.class);
	
	@Resource
    private UserService userServices;
	@Autowired
    private ObjectMapper mapper;
	@Autowired
    private Vertx vertx;

    /**
     * Set up the Vert.x Web routes and start the HTTP server
     * @throws Exception
     */
    @PostConstruct
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/user/finduser")
                .produces("application/json")
                .blockingHandler(this::getUser);
        router.get("/user/hello")
                .blockingHandler(this::hello);
        router.get("/user/updateuser")
        		//.consumes("application/json")
                .produces("application/json")
                .blockingHandler(this::getUserByName);
        vertx.createHttpServer().requestHandler(router::accept).listen(8090);
    }
    
    /**
     * Say hello to client.
     * @param rc The {@link RoutingContext} for the request
     */
    private void hello(RoutingContext rc) {
        try {
            String body = rc.getBodyAsString();
            log.info("Current body is " + body);
            rc.response().setStatusMessage("Accepted")
            .setStatusCode(202)
            .end(mapper.writeValueAsString("hello"));
        } catch (IOException e) {
            rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
            log.error("Server error", e);
        }
    }


    /**
     * Get a {@link User} from the database as indicated by the {@code name}
     * @param rc The {@link RoutingContext} for the request
     */
    private void getUser(RoutingContext rc) {
        log.info("Request for single user");
        String name = rc.request().getParam("name");
        try {
        	name = "zxy2013";
            User user = userServices.findUser(name);
			if (null == user) {
                rc.response().setStatusMessage("Not Found").setStatusCode(404).end("Not Found");
            } else {
                rc.response().setStatusMessage("OK").setStatusCode(200).end(mapper.writeValueAsString(user));
            }
        } catch (JsonProcessingException jpe) {
            rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
            log.error("Server error", jpe);
        }
    }


    /**
     * Get a {@link User} records in the database
     * @param rc The {@link RoutingContext} for the request
     */
    private void getUserByName(RoutingContext rc) {
        String name = "zxy2013";
        User user = userServices.findUserByName(name);
        try {
            rc.response().setStatusMessage("OK").setStatusCode(200).end(mapper.writeValueAsString(user));
        } catch (JsonProcessingException jpe) {
            rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
            log.error("Server error", jpe);
        }
    }
}
