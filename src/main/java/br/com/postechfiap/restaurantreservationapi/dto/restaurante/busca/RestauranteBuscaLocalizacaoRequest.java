package br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca;

import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Objeto utilizado para buscar restaurantes por localização.")
public class RestauranteBuscaLocalizacaoRequest {

    @Schema(description = "CEP do restaurante. Pode ser informado parcialmente.", example = "12345-678")
    private String cep;

    @Schema(description = "Estado do restaurante. Pode ser informado parcialmente.", example = "RJ")
    private String estado;

    @Schema(description = "Cidade do restaurante. Pode ser informada parcialmente.", example = "Rio de Janeiro")
    private String cidade;


    @Schema(description = "Logradouro do restaurante. Pode ser informado parcialmente.", example = "Avenida Brasil")
    private String logradouro;
}