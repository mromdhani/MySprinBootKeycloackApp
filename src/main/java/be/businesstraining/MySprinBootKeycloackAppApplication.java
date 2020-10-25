package be.businesstraining;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import be.businesstraining.models.Task;
import be.businesstraining.repositories.TaskRepository;

@SpringBootApplication
public class MySprinBootKeycloackAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySprinBootKeycloackAppApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runIt(TaskRepository repo) {
		return args -> {
			// IDs are created automatically by the DB
			repo.save(new Task(null, "Clean the house",new Date(), "Done"));
			repo.save(new Task(null, "Write Code", new Date(), "In progress"));
			repo.save(new Task(null, "Eat something", new Date(), "To Do"));			
			repo.save(new Task(null, "Read the news", new Date(), "To DO"));
		};
	}

}
