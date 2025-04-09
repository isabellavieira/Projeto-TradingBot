package cloud.tradingbotisafabbia.resquest;

import lombok.Data;

@Data
public class PedidoRequest {
    private String simbolo;
    private String lado;
    private double quantidade;
    private double preco;
}
