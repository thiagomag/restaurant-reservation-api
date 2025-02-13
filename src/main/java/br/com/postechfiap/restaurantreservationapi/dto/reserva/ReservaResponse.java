package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import java.time.LocalDateTime;
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
public class ReservaResponse {

    private Long id; // ID da reserva
    private Long usuarioId; // ID do usuário que fez a reserva
    private List<Mesa> mesas; // ID da mesa
    private LocalDateTime dataHoraReserva;
    private int numeroDePessoas;
}