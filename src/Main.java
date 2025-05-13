import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        int opcao = 0; double valor = 0;

        do {
            BuscaMoeda busca = null;
            menu.exibirMenu();

            System.out.println("Digite uma opção:");
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida, tente novamente.");
                scanner.next();
            }
            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                System.out.println("Digite o valor: ");

                while (!scanner.hasNextDouble()) {
                    System.out.println("Entrada inválida. Digite um valor válido. (Ex: 500,00 ou 500.00)");
                    scanner.next();
                }
                valor = menu.corrigeFormato(scanner.next());

                busca = switch (opcao) {
                    case 1 -> new BuscaMoeda(TipoMoeda.USD, TipoMoeda.BRL);
                    case 2 -> new BuscaMoeda(TipoMoeda.BRL, TipoMoeda.USD);
                    case 3 -> new BuscaMoeda(TipoMoeda.BRL, TipoMoeda.HRK);
                    case 4 -> new BuscaMoeda(TipoMoeda.BRL, TipoMoeda.GBP);
                    case 5 -> new BuscaMoeda(TipoMoeda.EUR, TipoMoeda.BRL);
                    case 6 -> new BuscaMoeda(TipoMoeda.BRL, TipoMoeda.EUR);
                    default -> busca;
                };

                String json = busca.buscaMoedaJson();
                ConverteMoeda cm = new ConverteMoeda(valor);
                menu.desenhaLinha();
                System.out.println("Valor convertido é de: " + CorFonte.VERMELHO +
                                                               cm.converteMoeda(json) +
                                                               CorFonte.RESET);
                menu.desenhaLinha();
            } else if (opcao != 7){
                System.out.println("Opção inexistente, tente novamente.");
            }
        } while (opcao != 7);

        System.out.println("Gostaria de experimentar o modo gráfico? (1 - Sim, Qualquer outro caracter - Não)");
        String sair = scanner.next();
        if (sair.equals("1")) {
            GUI gui = new GUI();
            System.out.println("Modo Pixel será aberto em seguida.");
        }

        System.out.println("Desativando Modo Caverna, Programa finalizado!");
        scanner.close();
    }
}