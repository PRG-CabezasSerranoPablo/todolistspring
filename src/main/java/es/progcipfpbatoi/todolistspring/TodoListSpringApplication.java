package es.progcipfpbatoi.todolistspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListSpringApplication {

	// Este es el metodo principal. Desde aqui arranca toda la aplicacion Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(TodoListSpringApplication.class, args);
	}

}
