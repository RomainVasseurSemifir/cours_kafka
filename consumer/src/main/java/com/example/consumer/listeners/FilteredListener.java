package com.example.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class FilteredListener {

    private Logger logger = LoggerFactory.getLogger(HeaderListener.class);

    @KafkaListener(groupId = "spring4",
        topicPartitions = @TopicPartition(
                topic = "app-topic",
//                partitions = { "0", "4"}
                partitionOffsets = @PartitionOffset(
                        partition = "4",
                        initialOffset = "2"
                )
        ))
    public void filteredListener(String message){
        logger.info("Filtered listener ; "+ message);
    }
}
