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

    private String urlBase = "https://testnet.binance.vision";  // URL da API de teste da Binance
    private String chaveApi;  // Chave de API da Binance
    private String chaveSecreta;  // Chave secreta da Binance

    // Método para criar o cliente Binance com as chaves fornecidas
    private SpotClient obterCliente() {
        return new SpotClientImpl(chaveApi, chaveSecreta, urlBase);
    }

    // Método para obter os preços dos símbolos
    public String obterTickers(ArrayList<String> simbolos) {

        if (simbolos == null || simbolos.isEmpty()) {
            return "[]";  // Retorna um array vazio caso a lista de símbolos seja nula ou vazia
        }
        SpotClient cliente = obterCliente();
        Map<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("symbols", simbolos);  // Adiciona os símbolos aos parâmetros
        return cliente.createMarket().ticker(parametros);  // Faz a requisição para obter os tickers
    }

    // Método para criar uma ordem de mercado
    public String criarOrdemMercado(String simbolo, double quantidade, String lado) {

        if (simbolo == null || simbolo.isEmpty() || quantidade <= 0 || lado == null || lado.isEmpty()) {
            return "{\"erro\":\"Parâmetros inválidos\"}";  // Retorna erro caso algum parâmetro seja inválido
        }
        SpotClient cliente = obterCliente();
        Map<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("symbol", simbolo);
        parametros.put("side", lado);  // Define o lado da ordem (compra ou venda)
        parametros.put("type", "MARKET");  // Tipo de ordem (mercado)
        parametros.put("quantity", quantidade);  // Define a quantidade de ativos para a ordem
        return cliente.createTrade().newOrder(parametros);  // Executa a ordem no mercado
    }
}