import java.io.IOException;

public class Menu {
    public void exibirMenu(){
        System.out.println(CorFonte.CIANO +
                           "-------------------------------------------------");
        System.out.println("               CONVERSOR DE MOEDAS               ");
        System.out.println("-------------------------------------------------" +
                           CorFonte.RESET);
        System.out.println(" 1) Dólar Americano      ->      Real Brasileiro");
        System.out.println(" 2) Real Brasileiro      ->      Dólar Americano");
        System.out.println(" 3) Real Brasileiro      ->          Kuna Croata");
        System.out.println(" 4) Real Brasileiro      ->      Libra esterlina");
        System.out.println(" 5) Euro                 ->      Real Brasileiro");
        System.out.println(" 6) Real Brasileiro      ->                 Euro");
        System.out.println(" 7) Sair");
        System.out.println(CorFonte.CIANO +
                           "-------------------------------------------------" +
                           CorFonte.RESET);
    }

    public double corrigeFormato(String valor) {
        valor = valor.replace(",",".");
        return Double.parseDouble(valor);
    }

    public void desenhaLinha() {
        System.out.println(CorFonte.AMARELO +
                "-------------------------------------------------" +
                CorFonte.RESET);
    }


}
