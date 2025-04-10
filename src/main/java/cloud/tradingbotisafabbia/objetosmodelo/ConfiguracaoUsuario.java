package cloud.tradingbotisafabbia.objetosmodelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
public class ConfiguracaoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double percentualPerda;

    @Column
    private Double percentualLucro;

    @Column
    private Double quantidadePorPedido;

    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonBackReference
    private Usuario usuario;

}
