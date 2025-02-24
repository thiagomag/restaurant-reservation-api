package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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

    @Schema(description = "ID da reserva.", example = "5")
    private Long reservaId;

    @Schema(description = "ID do usuário que fez a reserva.", example = "1")
    private Long usuarioId;

    @Schema(description = "Nome do restaurante onde a reserva foi feita.", example = "Restaurante do Chef")
    private String restauranteName;

    @Schema(description = "Lista de IDs das mesas reservadas.", example = "[\"001-001\", \"001-002\"]")
    private List<String> mesas;

    @Schema(description = "Data e hora em que a reserva foi feita.", example = "2025-02-24T19:00:00")
    private LocalDateTime dataHoraReserva;

    @Schema(description = "Número de pessoas para a reserva.", example = "4")
    private int numeroDePessoas;

    // Converte uma única reserva para DTO, incluindo as mesas associadas
    public static ReservaResponse toDto(Reserva reserva) {
        return ReservaResponse.builder()
                .reservaId(reserva.getId())
                .mesas(reserva.getMesas().stream().map(Mesa::getId).collect(Collectors.toList())) // Lista de IDs de mesas
                .dataHoraReserva(reserva.getDataHoraReserva())
                .numeroDePessoas(reserva.getNumeroDePessoas())
                .build();
    }

    // Converte uma lista de reservas para a lista de DTOs
    public static List<ReservaResponse> toList(List<Reserva> reservas) {
        return reservas.stream().map(ReservaResponse::toDto).collect(Collectors.toList());
    }
}