import com.google.gson.*;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class ConverteMoeda {
    Locale localBrasil = new Locale("pt", "BR");
    Locale localEUA = new Locale("en", "US");
    Locale localCroacia = new Locale("hr", "HR");
    Locale localUK = new Locale("en", "GB");
    Locale localPortugal = new Locale("pt", "PT");

    private Map<String, Locale> LOCALES = Map.of(
            "BRL", localBrasil,
            "USD", localEUA,
            "HRK", localCroacia,
            "GBP", localUK,
            "EUR", localPortugal
    );

    private double valor;

    public ConverteMoeda(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public String converteMoeda(String json) {

        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("JSON fornecido está vazio ou nulo.");
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(json);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            if (!jsonObject.has("target_code") || !jsonObject.has("conversion_rate")) {
                throw new IllegalArgumentException("JSON inválido: campos necessários ausentes.");
            }

            JsonElement tc = jsonObject.get("target_code");
            JsonElement cr = jsonObject.get("conversion_rate");

            Locale aux = LOCALES.getOrDefault(tc.getAsString(), Locale.ROOT);

            return NumberFormat.getCurrencyInstance(aux).format(cr.getAsDouble() * getValor());
        }
        return "";
    }
}
