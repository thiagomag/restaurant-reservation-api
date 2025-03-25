package br.com.postechfiap.restaurantreservationapi.dto.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UsuarioRequest {

    @Schema(description = "ID do usuário", example = "1")
    @JsonIgnore
    private Long id;
    @Schema(description = "Nome do usuário", example = "João da Silva")
    @NotNull
    @NotBlank
    private String nome;
    @Schema(description = "Email do usuário", example = "email@email.com")
    @NotNull
    @NotBlank
    private String email;
    @Schema(description = "Telefone do usuário", example = "11999999999")
    private String telefone;

}
