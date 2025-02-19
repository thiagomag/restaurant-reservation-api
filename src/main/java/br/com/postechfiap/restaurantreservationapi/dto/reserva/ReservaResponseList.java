package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservaResponseList {

    private Long usuarioId;  // ID do usuário
    private Long restauranteId;  // ID do restaurante
    private List<ReservaResponse> reservas;  // Lista das reservas
}