package br.com.postechfiap.restaurantreservationapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class H2ConsoleRunner implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Conectando ao banco H2...");
        jdbcTemplate.query("SELECT * FROM usuario", (rs, rowNum) -> {
            return rs.getString("nome");
        }).forEach(nome -> System.out.println("Usuário: " + nome));
    }
}

