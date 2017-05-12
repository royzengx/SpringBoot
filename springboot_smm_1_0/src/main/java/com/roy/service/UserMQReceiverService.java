/**
 * 
 */
package com.roy.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.roy.domain.User;
import com.roy.mq.RabbitmqQueue;

/**
 * @author Roy
 *
 */
@Component
public class UserMQReceiverService {
	private Logger log = LoggerFactory.getLogger(UserMQReceiverService.class);
	
	@RabbitListener(queues = RabbitmqQueue.CONTRACE_SELF)
	public void receiveUserQueue(User user) {
		log.info(new Date() + " Received User<" + new Gson().toJson(user) + ">");
		// do more logic here.
	}
}
