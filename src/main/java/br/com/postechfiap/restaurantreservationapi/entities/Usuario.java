package br.com.postechfiap.restaurantreservationapi.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table(name = "usuario")
@Entity
public class Usuario extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "usuario_id_seq", allocationSize = 1)
    private Long id;

    private String nome;
    private String email;
    private String telefone;

}
