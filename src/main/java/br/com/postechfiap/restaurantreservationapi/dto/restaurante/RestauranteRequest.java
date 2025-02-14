package br.com.postechfiap.restaurantreservationapi.dto.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class RestauranteRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório")
    @Schema(description = "Nome do restaurante", example = "Restaurante do Chef", required = true)
    private String nome;

    @NotNull(message = "O endereço é obrigatório")
    @Schema(description = "Endereço completo do restaurante", example = "{ \"rua\": \"Rua das Flores\", \"numero\": \"123\" }", required = true)
    private EnderecoRequest endereco;

    @NotNull(message = "O tipo de cozinha é obrigatório")
    @Schema(description = "Tipo de cozinha do restaurante", example = "ITALIANA", required = true)
    private TiposCozinhaEnum tipoCozinha;

    @Size(max = 100, message = "O horário de funcionamento deve ter no máximo 100 caracteres")
    @Schema(description = "Horário de funcionamento do restaurante", example = "10:00 - 22:00")
    private String horarioFuncionamento;

    @NotBlank(message = "A capacidade do restaurante é obrigatória")
    @Schema(description = "Capacidade total de pessoas do restaurante", example = "100", required = true)
    private String capacidade;
}