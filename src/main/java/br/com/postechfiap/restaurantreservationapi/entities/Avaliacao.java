package br.com.postechfiap.restaurantreservationapi.entities;

import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "avaliacao")
@Entity
@NoArgsConstructor
public class Avaliacao extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "avaliacao_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Enumerated(EnumType.ORDINAL)
    private NotaEnum nota;  // Enum para representar a nota (1 a 5)

    private String comentario = "";

    // Construtor único (única forma possível), simplificando a criação do objeto
    public Avaliacao(Reserva reserva, NotaEnum nota, String comentario) {
        this.reserva = reserva;
        this.nota = nota;
        this.comentario = comentario != null ? comentario : "";  // Se o comentário for nulo, usa uma string vazia

    }
}