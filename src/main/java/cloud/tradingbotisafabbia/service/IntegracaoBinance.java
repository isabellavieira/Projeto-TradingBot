package cloud.tradingbotisafabbia.service;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Data
public class IntegracaoBinance {

    private String urlBase = "https://testnet.binance.vision";
    private String chaveApi;
    private String chaveSecreta;

    // Método privado para criar e retornar o cliente da Binance
    private SpotClient obterCliente() {
        return new SpotClientImpl(chaveApi, chaveSecreta, urlBase);
    }

    // Método para obter os tickers (preços) dos símbolos informados
    public String obterTickers(ArrayList<String> simbolos) {
        // Verifica se a lista de símbolos não está vazia
        if (simbolos == null || simbolos.isEmpty()) {
            return "[]"; // Retorna um array vazio (JSON) se nenhum símbolo for informado
        }
        SpotClient cliente = obterCliente();
        Map<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("symbols", simbolos);
        return cliente.createMarket().ticker(parametros);
    }

    // Método para criar uma ordem de mercado
    public String criarOrdemMercado(String simbolo, double quantidade, String lado) {
        // Valida os parâmetros básicos antes de criar a ordem
        if (simbolo == null || simbolo.isEmpty() || quantidade <= 0 || lado == null || lado.isEmpty()) {
            return "{\"erro\":\"Parâmetros inválidos\"}";
        }
        SpotClient cliente = obterCliente();
        Map<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("symbol", simbolo);
        parametros.put("side", lado);
        parametros.put("type", "MARKET");
        parametros.put("quantity", quantidade);
        return cliente.createTrade().newOrder(parametros);
    }
}
