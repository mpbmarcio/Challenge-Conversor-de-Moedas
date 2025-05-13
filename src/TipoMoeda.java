public enum TipoMoeda {
    BRL("BRL"),
    USD("USD"),
    HRK("HRK"),
    GBP("GBP"),
    EUR("EUR");

    public String moeda;

    TipoMoeda(String m) {
        moeda = m;
    }

    public String getMoeda() {
        return moeda;
    }
}
