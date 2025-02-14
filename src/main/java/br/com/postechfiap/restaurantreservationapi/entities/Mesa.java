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
@Table(name = Mesa.TABLE_NAME)
@Entity
public class Mesa extends BaseEntity<String> {

    public static final String TABLE_NAME = "mesa";

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;  // Relacionamento com a entidade Restaurante

    private Integer numeroMesa;

    @Builder.Default
    private Integer capacidade = 2; // Aqui a mesa irá sempre acomodar 2 Pessoas
}

