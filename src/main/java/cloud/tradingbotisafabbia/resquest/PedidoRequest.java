package cloud.tradingbotisafabbia.resquest;

import lombok.Data;

@Data
public class PedidoRequest {
    private String symbol;
    private String side;
    private double quantity;
    private double price;
}
