package service;

import objetosmodelo.Trade;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class TradeManager {
    // Lista que armazena todos os trades registrados
    private List<Trade> trades;

    // Construtor que inicializa a lista de trades
    public TradeManager() {
        trades = new ArrayList<>();
    }

    /**
     * Adiciona um trade à lista.
     * @param trade Objeto Trade que será adicionado.
     */
    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    /**
     * Gera um relatório em formato CSV contendo todos os trades registrados.
     * @param filename Nome do arquivo CSV a ser gerado.
     */
    public void generateReport(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Cabeçalho do CSV
            writer.write("ID,Symbol,Direction,Quantity,EntryPrice,ExitPrice,Fees,OpenTime,CloseTime,ProfitLoss\n");

            // Escreve cada trade no arquivo
            for (Trade t : trades) {
                writer.write(t.toCSV());
            }
            System.out.println("Relatório gerado com sucesso: " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o relatório:");
            e.printStackTrace();
        }
    }

    /**
     * Imprime um resumo dos trades no console, exibindo os dados relevantes de cada operação
     * e o resultado acumulado.
     */
    public void printSummary() {
        System.out.println("----- Resumo dos Trades -----");
        double totalProfitLoss = 0.0;
        System.out.println(String.format("%-5s %-10s %-10s %-10s %-12s", "ID", "Symbol", "Direction", "Quantity", "Profit/Loss"));
        for (Trade t : trades) {
            totalProfitLoss += t.getProfitLoss();
            System.out.println(String.format("%-5d %-10s %-10s %-10.2f %-12.2f",
                    t.getId(), t.getSymbol(), t.getDirection(), t.getQuantity(), t.getProfitLoss()));
        }
        System.out.println("-------------------------------");
        System.out.println("Resultado Acumulado: " + totalProfitLoss);
    }
}
