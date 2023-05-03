package com.adjadev.stock;

import com.adjadev.stock.dto.UtilisateurDto;
import com.adjadev.stock.dto.auth.RegisterRequest;
import com.adjadev.stock.model.Utilisateur;
import com.adjadev.stock.services.auth.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.adjadev.stock.model.Role.ADMIN;
import static com.adjadev.stock.model.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing
public class StockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.prenom("Admin")
					.nom("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.prenom("Admin")
					.nom("Admin")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}

}
