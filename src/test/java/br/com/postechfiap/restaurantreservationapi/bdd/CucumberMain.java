package br.com.postechfiap.restaurantreservationapi.bdd;

import io.cucumber.core.cli.Main;

public class CucumberMain {
    public static void main(String[] args) {
        Main.main(new String[]{
                "--glue", "br.com.postechfiap.restaurantreservationapi.bdd",
                "src/test/resources/features"
        });
    }
}
