package br.com.postechfiap.restaurantreservationapi.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class H2DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testEnderecoInsertion() {
        assertEquals(3, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM endereco", Integer.class));
    }

    @Test
    public void testRestauranteInsertion() {
        assertEquals(3, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM restaurante", Integer.class));
    }

    @Test
    public void testMesaInsertion() {
        assertEquals(6, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM mesa", Integer.class));
    }

    @Test
    public void testUsuarioInsertion() {
        assertEquals(3, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuario", Integer.class));
    }

    @Test
    public void testReservaInsertion() {
        assertEquals(3, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reserva", Integer.class));
    }

    @Test
    public void testReservaMesaInsertion() {
        assertEquals(3, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reserva_mesa", Integer.class));
    }

    @Test
    public void testSpecificData() {
        // Verifica dados específicos na tabela endereco
        String cidade = jdbcTemplate.queryForObject("SELECT cidade FROM endereco WHERE id = 1", String.class);
        assertEquals("São Paulo", cidade);

        // Verifica dados específicos na tabela restaurante
        String nomeRestaurante = jdbcTemplate.queryForObject("SELECT nome FROM restaurante WHERE id = 2", String.class);
        assertEquals("Cantina da Esquina", nomeRestaurante);

        // Verifica dados específicos na tabela mesa
        int numeroMesa = jdbcTemplate.queryForObject
                ("SELECT numeroMesa FROM mesa WHERE id = '003-001'", Integer.class);
        assertEquals(1, numeroMesa);

        // Verifica dados específicos na tabela usuario
        String nomeUsuario = jdbcTemplate.queryForObject
                ("SELECT nome FROM usuario WHERE id = 3", String.class);
        assertEquals("Ricardo Mendes", nomeUsuario);

        // Verifica dados específicos na tabela reserva
        int numeroPessoas = jdbcTemplate.queryForObject
                ("SELECT numero_de_pessoas FROM reserva WHERE id = 2", Integer.class);
        assertEquals(2, numeroPessoas);

        // Verifica dados específicos na tabela reserva_mesa
        String mesaId = jdbcTemplate.queryForObject
                ("SELECT mesa_id FROM reserva_mesa WHERE reserva_id = 3", String.class);
        assertEquals("003-001", mesaId);
    }
}