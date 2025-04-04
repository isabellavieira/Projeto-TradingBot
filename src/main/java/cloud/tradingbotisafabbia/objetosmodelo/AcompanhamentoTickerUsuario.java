package cloud.tradingbotisafabbia.objetosmodelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AcompanhamentoTickerUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String simbolo;
}
