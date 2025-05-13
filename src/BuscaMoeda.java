import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BuscaMoeda {
    private String moedaBase;
    private String moedaAlvo;

    public BuscaMoeda(TipoMoeda moedaBase, TipoMoeda moedaAlvo) {
        this.moedaBase = moedaBase.getMoeda();
        this.moedaAlvo = moedaAlvo.getMoeda();
    }

    public String buscaMoedaJson() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + lerChave("key.txt") + "/pair/" +
                moedaBase +
                "/" +
                moedaAlvo;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String lerChave(String caminhoArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return null;
        }
    }


}
