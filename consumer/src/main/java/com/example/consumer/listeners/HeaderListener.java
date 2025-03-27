package com.example.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class HeaderListener {

    private Logger logger = LoggerFactory.getLogger(HeaderListener.class);

    @KafkaListener(topics = "app-topic", groupId = "spring3")
    public void headerListener(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_KEY) String key,
                               @Header(KafkaHeaders.OFFSET) int offset,
                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timeRecieved){

       String reponse = "Reception du message à : "
               + Instant.ofEpochMilli(Long.parseLong(timeRecieved))
               + " message : "+ message
               + " offset : "+ offset
               + " with key : "+ key;
       logger.info("Header listener ; "+ reponse);
    }
}
