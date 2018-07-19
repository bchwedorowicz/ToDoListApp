package pl.beata.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vaadin.spring.events.annotation.EnableEventBus;

@SpringBootApplication
@EnableEventBus
@EnableTransactionManagement
public class ToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}
}
