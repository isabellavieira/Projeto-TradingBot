package cloud.tradingbotisafabbia.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RespostaPedido {
    private String simbolo;
    private String idPedido;
    private BigDecimal quantidadeExecutada;
    private String tipo;
    private String lado;
    private List<RespostaPreenchimentoPedido> preenchimentos;
}
