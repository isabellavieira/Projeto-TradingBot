package cloud.tradingbotisafabbia.objetosmodelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ConfiguracaoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double percentualPerda;

    @Column(nullable = false)
    private Double percentualLucro;

    @Column(nullable = false)
    private Double quantidadePorPedido;
}
