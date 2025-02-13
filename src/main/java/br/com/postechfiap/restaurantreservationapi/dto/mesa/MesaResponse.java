package br.com.postechfiap.restaurantreservationapi.dto.mesa;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaResponse {
    private List<Mesa> mesas;
}