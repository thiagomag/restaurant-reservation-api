package br.com.postechfiap.restaurantreservationapi.enuns;

import br.com.postechfiap.restaurantreservationapi.interfaces.EnumSerializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum TiposCozinhaEnum implements EnumSerializable {

    // Cozinhas Internacionais
    ITALIANA("Italiana"),
    FRANCESA("Francesa"),
    JAPONESA("Japonesa"),
    CHINESA("Chinesa"),
    MEXICANA("Mexicana"),
    TAILANDESA("Tailandesa"),
    INDIANA("Indiana"),
    ESPANHOLA("Espanhola"),
    ARABE("Árabe"),
    GREGA("Grega"),
    COREANA("Coreana"),

    // Tipos de Cozinha por Estilo de Preparo
    GOURMET("Gourmet"),
    CONTEMPORANEA("Contemporânea"),
    MOLECULAR("Molecular"),
    FUSION("Fusion"),
    COMFORT_FOOD("Comfort Food"),
    FAST_FOOD("Fast Food"),
    FAST_CASUAL("Fast Casual"),
    STEAKHOUSE("Steakhouse / Churrascaria"),
    VEGETARIANA("Vegetariana"),
    VEGANA("Vegana"),
    ORGANICA("Orgânica"),
    SAUDAVEL_FIT("Saudável / Fit"),

    // Cozinhas Regionais do Brasil
    MINEIRA("Mineira"),
    BAIANA("Baiana"),
    NORDESTINA("Nordestina"),
    PAULISTA("Paulista"),
    GAUCHA("Gaúcha"),
    AMAZONICA("Amazônica");

    private final String descricao;

    public static TiposCozinhaEnum findBy(final String descricao) {
        return Stream.of(values())
                .filter(v -> v.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElse(null);
    }


    @Override
    public String getValue() {
        return this.descricao.trim();
    }
}
