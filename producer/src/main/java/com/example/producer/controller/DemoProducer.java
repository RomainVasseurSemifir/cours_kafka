package com.example.producer.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class DemoProducer {

    private Logger logger = LoggerFactory.getLogger(DemoProducer.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/") // => GET http://localhost:8090/
    public String index(){
        return "server online";
    }

    @GetMapping("/sync/{message}") // => GET http://localhost:8090/sync/{message}
    public String publishSync(@PathVariable("message") String message){
        String topic = "app-topic";
        String key = "sync";
        String reponse = "";
        // creer un message
        final ProducerRecord<String, String> myRecord =
                new ProducerRecord<>(topic,key, message);

        try {
            kafkaTemplate.send(myRecord).get(3, TimeUnit.SECONDS);
            reponse = "Publish successfully ! ";
        } catch (InterruptedException e) {
            reponse = "InterruptedException";
        } catch (ExecutionException e) {
            reponse = "ExecutionException";
        } catch (TimeoutException e) {
            reponse = "TimeoutException";
        } finally {
            kafkaTemplate.destroy();
        }
        return reponse;
    }

    @GetMapping("/async/{message}") // => GET http://localhost:8090/async/{message}
    public String publishAsync(@PathVariable("message") String message){
        String topic = "app-topic";
        String key = "async";
        // creer un message
        final ProducerRecord<String, String> myRecord =
                new ProducerRecord<>(topic,key, message);

        // code non blockant
        CompletableFuture<SendResult<String, String>> future
                = kafkaTemplate.send(myRecord);

        future.whenComplete( (result, ex) -> {
            // if success
            if (ex == null){
                logger.info("Send message : "+ message
                        + " in partition :" + result.getRecordMetadata().partition()
                        + " and with offset : "+ result.getRecordMetadata().offset()
                );
            }// sinon
            else {
                logger.info(ex.getMessage());
            }
        });
        return "Send Async completed";
    }


}
