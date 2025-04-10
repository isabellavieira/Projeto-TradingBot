package cloud.tradingbotisafabbia.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RespostaPreenchimentoPedido {
    private double price;
    private BigDecimal quantity;
}
