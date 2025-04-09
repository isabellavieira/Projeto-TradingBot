import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.beans.factory.annotation.Autowired;

import cloud.tradingbotisafabbia.objetosmodelo.Trade;
import cloud.tradingbotisafabbia.service.TradeManager;


@SpringBootApplication
public class TradingBotIsafabiaApplication {

    // Injete o TradeManager (como @Service, ele será gerenciado pelo Spring)
    @Autowired
    private TradeManager tradeManager;

    public static void main(String[] args) {
        SpringApplication.run(TradingBotIsafabiaApplication.class, args);
    }

    /**
     * Método executado após o Spring Boot iniciar completamente a aplicação.
     * Usado aqui apenas para simulação e teste da funcionalidade de trades.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        // Simulação de abertura de trade:
        int tradeId = 1;                      // Identificador do trade (pode ser gerado ou sequencial)
        double quantidade = 0.5;
        double precoEntrada = 30000.0;
        double taxaInicial = 15.0;

        // Cria o trade
        Trade trade = new Trade(tradeId, "BTCUSD", "buy", quantidade, precoEntrada, taxaInicial);
        System.out.println("Trade aberto: " + trade.getSymbol());

        // Simulação: Lógica do bot que acompanha o trade...
        // (aguarde ou processe o trade; aqui usamos valores de exemplo)

        // Trade finalizado: fechando o trade
        double precoSaida = 31000.0;
        double taxaAdicional = 5.0;
        trade.closeTrade(precoSaida, taxaAdicional);
        System.out.println("Trade fechado com lucro/prejuízo: " + trade.getProfitLoss());

        // Registra o trade no TradeManager
        tradeManager.addTrade(trade);

        // Gerar relatório e exibir resumo
        tradeManager.generateReport("relatorio_trades.csv");
        tradeManager.printSummary();
    }
}
