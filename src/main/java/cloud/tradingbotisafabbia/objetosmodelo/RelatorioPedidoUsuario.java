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

    @Column
    private String simbolo;

    @Column(nullable = false)
    private double quantidade;

    @Column
    private double precoCompra;

    @Column
    private double precoVenda;

    @Column
    private LocalDateTime dataHoraOperacao;
}
