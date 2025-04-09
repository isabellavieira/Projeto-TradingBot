package cloud.tradingbotisafabbia.objetosmodelo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class RelatorioPedidoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String simbolo;

    @Column(nullable = false)
    private double quantidade;

    @Column(nullable = false)
    private double precoCompra;

    @Column(nullable = false)
    private double precoVenda;

    @Column(nullable = false)
    private LocalDateTime dataHoraOperacao;
}
