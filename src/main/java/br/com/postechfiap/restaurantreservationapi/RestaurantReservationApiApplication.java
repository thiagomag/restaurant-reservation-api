package br.com.postechfiap.restaurantreservationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestaurantReservationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantReservationApiApplication.class, args);
    }

}
