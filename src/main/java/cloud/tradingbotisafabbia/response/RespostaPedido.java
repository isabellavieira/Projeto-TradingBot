package cloud.tradingbotisafabbia.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RespostaPedido {
    private String symbol;
    private String orderId;
    private BigDecimal executedQty;
    private String type;
    private String side;
    private List<RespostaPreenchimentoPedido> Fills;
}
