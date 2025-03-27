package com.example.consumer;

import com.example.consumer.listeners.BasicListener;
import com.example.consumer.listeners.DetailListener;
import com.example.consumer.listeners.FilteredListener;
import com.example.consumer.listeners.HeaderListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(ConsumerApplication.class, args);
		BasicListener bl = context.getBean(BasicListener.class);
		DetailListener dl = context.getBean(DetailListener.class);
		HeaderListener hl = context.getBean(HeaderListener.class);
		FilteredListener fl = context.getBean(FilteredListener.class);
	}

}
