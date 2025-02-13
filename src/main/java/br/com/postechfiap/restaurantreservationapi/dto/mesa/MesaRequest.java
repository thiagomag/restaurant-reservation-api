package br.com.postechfiap.restaurantreservationapi.dto.mesa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MesaRequest {
    private Long restauranteId;
    private Integer quantidadeMesas; // Quantidade de mesas a serem criadas
}
