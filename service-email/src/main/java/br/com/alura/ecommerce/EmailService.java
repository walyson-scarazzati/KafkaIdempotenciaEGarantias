package br.com.alura.ecommerce;

import java.util.concurrent.ExecutionException;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService implements ConsumerService<String> {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		new ServiceRunner(EmailService::new).start(5);
	}

	public String getConsumerGroup() {
		return EmailService.class.getSimpleName();
	}

	public String getTopic() {
		return "ECOMMERCE_SEND_EMAIL";
	}

	public void parse(ConsumerRecord<String, Message<String>> record) {
		System.out.println("-----------------");
		System.out.println("Sending email");
		System.out.println("key: " + record.key());
		System.out.println("value: " + record.value());
		System.out.println("partition: " + record.partition());
		System.out.println("Offset: " + record.offset());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Email sent successfully");
	}

}
