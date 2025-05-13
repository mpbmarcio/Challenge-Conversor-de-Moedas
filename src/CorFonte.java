public enum CorFonte {
    RESET("\u001B[0m"),
    PRETO("\u001B[30m"),
    VERMELHO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARELO("\u001B[33m"),
    AZUL("\u001B[34m"),
    ROXO("\u001B[35m"),
    CIANO("\u001B[36m"),
    BRANCO("\u001B[37m");

    private final String codigo;

    CorFonte(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return codigo;
    }
}
