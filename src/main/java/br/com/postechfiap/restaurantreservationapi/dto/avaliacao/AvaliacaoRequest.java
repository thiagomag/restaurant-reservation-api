package br.com.postechfiap.restaurantreservationapi.dto.avaliacao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Objeto de solicitação para avaliação de uma reserva")
public class AvaliacaoRequest {

    @Schema(description = "ID da reserva relacionada à avaliação", example = "123")
    private Long reservaId;  // ID da reserva relacionada à avaliação

    @Schema(description = "Nota da avaliação (de 1 a 5)", example = "4")
    private Integer nota;    // Nota de 1 a 5

    @Schema(description = "Comentário sobre a experiência", example = "A comida estava excelente!")
    private String comentario;  // Comentário sobre a experiência
}