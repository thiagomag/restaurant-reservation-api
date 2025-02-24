package br.com.postechfiap.restaurantreservationapi.dto.endereco;

import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
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
public class EnderecoResponse {

    @Schema(description = "ID do endereço", example = "1")
    private Long id;

    @Schema(description = "CEP do endereço", example = "01000-000")
    private String cep;

    @Schema(description = "Estado do endereço", example = "SP")
    private String estado;

    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Número do endereço", example = "123")
    private String numero;


    // Método de conversão
    public static EnderecoResponse toDTO(Endereco endereco) {
        return EnderecoResponse.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .cidade(endereco.getCidade())
                .bairro(endereco.getBairro())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .build();
    }
}