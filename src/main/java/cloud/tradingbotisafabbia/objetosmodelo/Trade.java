package objetosmodelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trade {
    // Atributos do trade
    private int id;
    private String symbol;
    private String direction;    // Exemplos: "buy" ou "sell" | "long" ou "short"
    private double quantity;
    private double entryPrice;
    private double exitPrice;
    private double fees;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private double profitLoss;

    /**
     * Construtor para criação de um novo Trade.
     *
     * @param id         Identificador único do trade.
     * @param symbol     Símbolo do ativo.
     * @param direction  Direção da operação ("buy"/"long" para compra, "sell"/"short" para venda).
     * @param quantity   Quantidade negociada.
     * @param entryPrice Preço de entrada.
     * @param fees       Taxas ou comissões iniciais da operação.
     */
    public Trade(int id, String symbol, String direction, double quantity, double entryPrice, double fees) {
        this.id = id;
        this.symbol = symbol;
        this.direction = direction;
        this.quantity = quantity;
        this.entryPrice = entryPrice;
        this.fees = fees;
        this.openTime = LocalDateTime.now();
        this.exitPrice = 0.0;
        this.closeTime = null;
        this.profitLoss = 0.0;
    }

    /**
     * Método para fechar o trade.
     * Define o preço de saída, registra a data/hora de fechamento, adiciona taxas adicionais
     * e calcula o lucro ou prejuízo baseado na direção da operação.
     *
     * @param exitPrice      Preço de saída.
     * @param additionalFees Taxas ou comissões adicionais no fechamento do trade.
     */
    public void closeTrade(double exitPrice, double additionalFees) {
        this.exitPrice = exitPrice;
        this.closeTime = LocalDateTime.now();
        this.fees += additionalFees;

        if (direction.equalsIgnoreCase("buy") || direction.equalsIgnoreCase("long")) {
            // Lucro = (preço de saída - preço de entrada) * quantidade - taxas
            this.profitLoss = (this.exitPrice - this.entryPrice) * this.quantity - this.fees;
        } else if (direction.equalsIgnoreCase("sell") || direction.equalsIgnoreCase("short")) {
            // Lucro = (preço de entrada - preço de saída) * quantidade - taxas
            this.profitLoss = (this.entryPrice - this.exitPrice) * this.quantity - this.fees;
        } else {
            throw new IllegalArgumentException("Direção do trade inválida: " + direction);
        }
    }

    /**
     * Gera uma linha formatada em CSV com as informações do trade.
     *
     * @return String no formato CSV.
     */
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String openTimeStr = openTime.format(formatter);
        String closeTimeStr = (closeTime != null) ? closeTime.format(formatter) : "";
        return id + "," +
                symbol + "," +
                direction + "," +
                quantity + "," +
                entryPrice + "," +
                exitPrice + "," +
                fees + "," +
                openTimeStr + "," +
                closeTimeStr + "," +
                profitLoss + "\n";
    }

    // Getters (opcionais) para uso na exibição de resumos ou outros fins
    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDirection() {
        return direction;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    /*
    Exemplo de uso:

    // Suponha que você tenha uma lógica onde inicia um trade:
    Trade trade = new Trade(tradeId, "BTCUSD", "buy", quantidade, precoEntrada, taxaInicial);
    // Continue com a execução do trade...

    // Quando o trade for fechado:
    trade.closeTrade(precoSaida, taxaAdicional);
    // Registra o trade no TradeManager:
    tradeManager.addTrade(trade);
    */
}
