package br.com.postechfiap.restaurantreservationapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table(name = "reserva")
@Entity
public class Reserva extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "reserva_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToMany
    @JoinTable(
            name = "reserva_mesa",  // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "reserva_id"), // FK para Reserva
            inverseJoinColumns = @JoinColumn(name = "mesa_id") // FK para Mesa
    )
    private List<Mesa> mesas = new ArrayList<>();

    private LocalDateTime dataHoraReserva;

    // Quantidade de pessoas para a reserva
    private int numeroDePessoas;



}