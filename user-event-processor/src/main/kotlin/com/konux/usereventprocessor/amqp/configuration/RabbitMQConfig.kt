package com.konux.usereventprocessor.amqp.configuration

import org.springframework.amqp.core.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    private val X_DEAD_LETTER_EXCHANGE: String = "x-dead-letter-exchange"

    @Value("\${amqp.dead-letter.exchange}")
    private val deadLetterExchange: String? = null

    @Value("\${spring.rabbitmq.template.default-receive-queue}")
    private val queueName: String? = null

    @Value("\${spring.rabbitmq.template.exchange}")
    private val exchangeName: String? = null

    @Value("\${spring.rabbitmq.template.routing-key}")
    private val routingKey: String? = null

    @Bean
    fun queue(): Queue {
        return QueueBuilder.durable(queueName)
                .withArgument(X_DEAD_LETTER_EXCHANGE, deadLetterExchange)
                .build()
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(exchangeName)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
    }
}