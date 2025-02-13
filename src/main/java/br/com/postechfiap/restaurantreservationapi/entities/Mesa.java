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
public class Mesa extends BaseEntity<Long> {

    public static final String TABLE_NAME = "mesa";

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "restaurante_id", nullable = false)
    private Long restauranteId;

    private Integer numeroMesa;
    private Integer capacidade = 2; //Aqui a mesa irá sempre acomodar 2 Pessoas
}

