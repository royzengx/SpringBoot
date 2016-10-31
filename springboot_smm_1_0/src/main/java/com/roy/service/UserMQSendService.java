/**
 * 
 */
package com.roy.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roy.domain.User;
import com.roy.mq.RabbitmqExchange;
import com.roy.mq.RabbitmqQueue;

/**
 * @author Roy
 *
 */
@Service
public class UserMQSendService implements ConfirmCallback {

	private Logger log = LoggerFactory.getLogger(UserMQSendService.class);

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;

	public void sendUserRabbitmqDirect(final User user) {
		this.rabbitMessagingTemplate.convertAndSend(
				RabbitmqExchange.USER_DIRECT, 
				RabbitmqQueue.CONTRACE_SELF, 
				user);
	}

	/**
	 * 回调函数.
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			log.info(new Date() + "Message confirm success:" + cause);
		} else {
			log.info(new Date() + "Message confirm failed:" + cause);
		}
	}
}
