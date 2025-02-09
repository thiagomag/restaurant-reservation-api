package br.com.postechfiap.restaurantreservationapi.entities;

import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
public class Restaurante extends BaseEntity<Long> {

    public static final String TABLE_NAME = "restaurante";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "restaurante_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String nome;
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    private TiposCozinhaEnum tipoCozinha;
    private String horarioFuncionamento;
    private String capacidade;
}
