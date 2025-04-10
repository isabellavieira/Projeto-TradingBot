package cloud.tradingbotisafabbia.objetosmodelo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String chaveApiBinance;

    @Column(nullable = false)
    private String chaveSecretaBinance;

    @Column
    private Double saldoInicio;

    @OneToMany
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private List<ConfiguracaoUsuario> configuracoes;

    @OneToMany
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private List<AcompanhamentoTickerUsuario> acompanhamentoTickers;

    @OneToMany
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private List<RelatorioPedidoUsuario> relatoriosPedidos;
}
