package com.konux.usereventprocessor.amqp.listener

import com.konux.usereventprocessor.amqp.event.UserEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.File.*

@Component
class UserEventListener {
    private val LOGGER = LoggerFactory.getLogger(UserEventListener::class.java)

    @Value("\${log.output-directory}")
    var outputDirectory: String = ""
        set(value) {
            field = value
        }

    @Value("\${log.output-file-name}")
    var outputFile: String = ""
        set(value) {
            field = value
        }

    @RabbitListener(queues = ["user_events"])
    fun receiveMessage(message: Message) {
        LOGGER.info("User event received [{}]", message)
        val userEvent = UserEvent.parseFrom(message.body)
        logMessage(userEvent.toString())
    }

    private fun logMessage(messageContent: String) {
        val file = File(outputDirectory + separator + outputFile)
        if (file.exists()) {
            file.appendText(messageContent)
        } else {
            val createNewFile = file.createNewFile()
            if (createNewFile) {
                LOGGER.info("File [{}] created", outputFile)
                file.writeText(messageContent)
            } else {
                LOGGER.error("Could not create [{}]", outputFile)
            }
        }
    }
}