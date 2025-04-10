package cloud.tradingbotisafabbia.objetosmodelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
public class AcompanhamentoTickerUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String simbolo;

    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonBackReference
    private Usuario usuario;

}
