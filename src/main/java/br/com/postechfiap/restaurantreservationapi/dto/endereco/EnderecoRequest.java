package br.com.postechfiap.restaurantreservationapi.dto.endereco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class EnderecoRequest {

    @NotNull
    @Schema(description = "CEP do endereço", example = "01000-000")
    private String cep;

    @NotNull
    @Schema(description = "Estado do endereço", example = "SP")
    private String estado;

    @NotNull
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @NotNull
    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @NotNull
    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @NotNull
    @Schema(description = "Número do endereço", example = "123")
    private Integer numero;

}
