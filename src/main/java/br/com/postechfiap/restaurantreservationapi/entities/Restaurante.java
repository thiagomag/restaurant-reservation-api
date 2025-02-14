package br.com.postechfiap.restaurantreservationapi.entities;

import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table(name = Restaurante.TABLE_NAME)
@Entity
public class Restaurante extends BaseEntity<Long> {

    public static final String TABLE_NAME = "restaurante";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "restaurante_id_seq", allocationSize = 1)
    private Long id;
    private String nome;
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @Enumerated(EnumType.STRING)
    private TiposCozinhaEnum tipoCozinha;
    private String horarioFuncionamento;
    private String capacidade;
}
