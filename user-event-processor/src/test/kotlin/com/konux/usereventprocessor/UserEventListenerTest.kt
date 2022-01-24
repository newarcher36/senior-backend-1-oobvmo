package com.konux.usereventprocessor

import com.konux.usereventprocessor.amqp.event.UserEvent
import com.konux.usereventprocessor.amqp.listener.UserEventListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.messaging.converter.ProtobufMessageConverter
import java.io.File
import java.io.File.separator
import java.io.InputStream

class UserEventListenerTest {

    private val OUTPUT_TEST_DIR = "."
    private val OUTPUT_TEST_FILE = "output.log"

    @BeforeEach
    fun setup() {
        val outputTestFile = File(OUTPUT_TEST_DIR + separator + OUTPUT_TEST_FILE)
        if (outputTestFile.exists()) {
            outputTestFile.delete()
        }
    }

    @Test
    fun logUserEventToFileWhenMessageReceived() {

        val userEvent = UserEvent.newBuilder()
            .setTimestamp(123)
            .setUserId(1L)
            .setMessage("Test")
            .build()
        val userEventListener = UserEventListener()
        userEventListener.outputDirectory = OUTPUT_TEST_DIR
        userEventListener.outputFile = OUTPUT_TEST_FILE
        userEventListener.receiveMessage(aMessage(userEvent))

        val inputStream: InputStream = File(OUTPUT_TEST_DIR + separator + OUTPUT_TEST_FILE).inputStream()
        val loggedUserEventMessage = inputStream.bufferedReader().use { it.readText() }

        assertThat(loggedUserEventMessage).isEqualTo("timestamp: 123\n" + "userId: 1\n" + "message: \"Test\"\n")
    }

    private fun aMessage(userEvent: UserEvent): Message {
        val bytes: ByteArray = userEvent.toByteArray()
        val messageProperties = MessageProperties()
        messageProperties.contentLength = bytes.size.toLong()
        messageProperties.contentType = ProtobufMessageConverter.PROTOBUF.type
        return Message(bytes, messageProperties)
    }
}
