package br.com.postechfiap.restaurantreservationapi.dto.mesa;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaResponseList {
    private List<MesaResponse> mesas;
}