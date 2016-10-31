/**
 * 
 */
package com.roy.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.roy.mq.RabbitmqExchange;
import com.roy.mq.RabbitmqQueue;

/**
 * @author Roy
 *
 */
@Configuration
public class UserExchangeConfig {

	/**
	 * 匹配型 默认：, durable = true, autoDelete = false
	 * 
	 * @param rabbitAdmin
	 * @return
	 */
	@Bean
	TopicExchange userTopicExchangeDurable(RabbitAdmin rabbitAdmin) {
		TopicExchange userTopicExchange = new TopicExchange(RabbitmqExchange.USER_TOPIC);
		rabbitAdmin.declareExchange(userTopicExchange);
		return userTopicExchange;
	}

	/**
	 * 直连型
	 * 
	 * @param rabbitAdmin
	 * @return
	 */
	@Bean
	DirectExchange userDirectExchange(RabbitAdmin rabbitAdmin) {
		DirectExchange userDirectExchange = new DirectExchange(RabbitmqExchange.USER_DIRECT);
		rabbitAdmin.declareExchange(userDirectExchange);
		return userDirectExchange;
	}

	@Bean
	Binding bindingExchangeUser(Queue queueUser, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueUser).to(exchange).with(RabbitmqQueue.CONTRACE_SELF);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Binding bindingExchangeUser(Queue queueUser, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueUser).to(exchange).with(RabbitmqQueue.CONTRACE_SELF);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	/**
	 * 所有关于user exchange的queue
	 * 
	 * @param rabbitAdmin
	 * @return
	 */
	@Bean
	Queue queueUser(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitmqQueue.CONTRACE_SELF, true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}	
}
