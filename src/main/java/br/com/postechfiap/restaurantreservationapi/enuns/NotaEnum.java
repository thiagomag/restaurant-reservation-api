package br.com.postechfiap.restaurantreservationapi.enuns;


public enum NotaEnum {
    PESSIMO(1, "Péssimo"),
    RUIM(2, "Ruim"),
    RAZOAVEL(3, "Razoável"),
    BOM(4, "Bom"),
    EXCELENTE(5, "Excelente");

    private final int valor;
    private final String descricao;

    NotaEnum(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static NotaEnum fromValor(int valor) {
        for (NotaEnum nota : values()) {
            if (nota.getValor() == valor) {
                return nota;
            }
        }
        throw new IllegalArgumentException("Nota inválida");
    }
}
