package br.com.postechfiap.restaurantreservationapi.dto.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestauranteResponse {

    @Schema(description = "ID do restaurante", example = "1")
    private Long id;

    @Schema(description = "Nome do restaurante", example = "Restaurante do Chef")
    private String nome;

    @Schema(description = "Endereço do restaurante")
    private EnderecoResponse endereco;

    @Schema(description = "Tipo de cozinha do restaurante", example = "ITALIANA")
    private TiposCozinhaEnum tipoCozinha;

    @Schema(description = "Horário de funcionamento do restaurante", example = "10:00 - 22:00")
    private String horarioFuncionamento;

    @Schema(description = "Capacidade total de pessoas do restaurante", example = "100")
    private int capacidade;

    // Método de conversão
    public static RestauranteResponse toDTO(Restaurante restaurante) {
        return RestauranteResponse.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .endereco(EnderecoResponse.toDTO(restaurante.getEndereco()))
                .tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .capacidade(restaurante.getCapacidade())
                .build();
    }
}
