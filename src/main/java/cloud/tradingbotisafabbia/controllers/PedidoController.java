package cloud.tradingbotisafabbia.controllers;

import cloud.tradingbotisafabbia.objetosmodelo.Usuario;
import cloud.tradingbotisafabbia.objetosmodelo.RelatorioPedidoUsuario;
import cloud.tradingbotisafabbia.repositorios.RelatorioPedidoUsuarioRepository;
import cloud.tradingbotisafabbia.repositorios.UsuarioRepository;
import cloud.tradingbotisafabbia.resquest.PedidoRequest;
import cloud.tradingbotisafabbia.response.RespostaPedido;
import cloud.tradingbotisafabbia.service.IntegracaoBinance;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/{id}/pedidos")
public class PedidoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RelatorioPedidoUsuarioRepository relatorioPedidoUsuarioRepository;

    @Autowired
    private IntegracaoBinance integracaoBinance;


    @PostMapping
    public ResponseEntity<RespostaPedido> criarPedido(@PathVariable("id") int id, @RequestBody PedidoRequest request) {

        Optional<Usuario> optUsuario = this.usuarioRepository.findById(id);

        if (optUsuario.isEmpty())
            return new ResponseEntity<RespostaPedido>(HttpStatus.NOT_FOUND);

        // Pego os dados do usuário no banco
        Usuario usuario = optUsuario.get();

        // Configurando a chave de acesso para Binance
        integracaoBinance.setChaveApi(usuario.getChaveApiBinance());
        integracaoBinance.setChaveSecreta(usuario.getChaveSecretaBinance());

        // Enviando a ordem
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String result = this.integracaoBinance.criarOrdemMercado(request.getSymbol(),request.getQuantity(),request.getSide());
            RespostaPedido resposta = objectMapper.readValue(result, RespostaPedido.class);


            // Grava na tabela a nova ordem de compra
            if ("BUY".equals(request.getSide())) {
                RelatorioPedidoUsuario relatorio = new RelatorioPedidoUsuario();
                relatorio.setSymbol(request.getSymbol());
                relatorio.setQuantity(request.getQuantity());
                relatorio.setPrecoCompra(resposta.getFills().get(0).getPrice());
                relatorio.setDataHoraOperacao(LocalDateTime.now());

                // Grava na base a operação
                this.relatorioPedidoUsuarioRepository.save(relatorio);

                // Grava a operação para o usuário
                usuario.getRelatoriosPedidos().add(relatorio);
                this.usuarioRepository.save(usuario);
            }

            // Grava o preço de venda
            if ("SELL".equals(request.getSide())) {
                RelatorioPedidoUsuario relatorio = null;
                for (RelatorioPedidoUsuario item : usuario.getRelatoriosPedidos()) {
                    // Achei a operação de compra anterior
                    if (item.getSymbol().equals(request.getSymbol()) && item.getPrecoVenda() == 0) {
                        relatorio = item;
                        break;
                    }
                }

                //Grava o preço de saida da operação
                if (relatorio != null) {
                    relatorio.setPrecoVenda(resposta.getFills().get(0).getPrice());

                    //Grava na base a operação
                    this.relatorioPedidoUsuarioRepository.save(relatorio);
                } else {
                    // Não encontrou operação de compra correspondente
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

            return new ResponseEntity<>(resposta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
