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
    public String obterTickers(ArrayList<String> symbol) {

        if (symbol == null || symbol.isEmpty()) {
            return "[]";  // Retorna um array vazio caso a lista de símbolos seja nula ou vazia
        }
        SpotClient cliente = obterCliente();
        Map<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("symbols", symbol);  // Adiciona os símbolos aos parâmetros
        return cliente.createMarket().ticker(parametros);  // Faz a requisição para obter os tickers
    }

    // Método para criar uma ordem de mercado
    public String criarOrdemMercado(String symbol, double quantity, String side) {

        if (symbol == null || symbol.isEmpty() || quantity <= 0 || side == null || side.isEmpty()) {
            return "{\"erro\":\"Parâmetros inválidos\"}";  // Retorna erro caso algum parâmetro seja inválido
        }
        SpotClient cliente = obterCliente();
        Map<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("symbol", symbol);  // O símbolo do par de criptomoedas (ex: ETHUSDT)
        parametros.put("side", side);  // Define o lado da ordem (compra ou venda)
        parametros.put("type", "MARKET");  // Tipo de ordem (mercado)
        parametros.put("quantity", quantity);  // A quantidade para a ordem

        try {
            // Executa a ordem no mercado e obtém a resposta
            String resposta = cliente.createTrade().newOrder(parametros);
            // Verifica se a resposta contém erro e retorna a resposta
            if (resposta.contains("erro")) {
                return resposta;  // Caso haja erro, retorna a resposta da API com o erro
            }
            return resposta;  // Caso a ordem seja bem-sucedida, retorna a resposta da Binance
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"erro\":\"Falha na comunicação com a Binance\"}";  // Caso haja erro na comunicação, retorna erro
        }
    }
}
