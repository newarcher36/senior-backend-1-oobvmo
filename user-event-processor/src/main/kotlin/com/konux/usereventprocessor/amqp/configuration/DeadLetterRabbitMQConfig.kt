package com.konux.usereventprocessor.amqp.configuration

import org.springframework.amqp.core.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeadLetterRabbitMQConfig {

    @Value("\${amqp.dead-letter.queue}")
    private val deadLetterQueue: String? = null

    @Value("\${amqp.dead-letter.exchange}")
    private val deadLetterExchange: String? = null

    @Bean
    fun deadLetterExchange(): FanoutExchange {
        return FanoutExchange(deadLetterExchange)
    }

    @Bean
    fun deadLetterQueue(): Queue {
        return QueueBuilder.durable(deadLetterQueue).build()
    }

    @Bean
    fun deadLetterBinding(): Binding {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange())
    }
}