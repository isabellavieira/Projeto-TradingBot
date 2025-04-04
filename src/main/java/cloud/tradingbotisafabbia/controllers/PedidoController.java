package cloud.tradingbotisafabbia.controllers;

import cloud.tradingbotisafabbia.objetosmodelo.Usuario;
import cloud.tradingbotisafabbia.objetosmodelo.RelatorioPedidoUsuario;
import cloud.tradingbotisafabbia.repositorios.RelatorioPedidoUsuarioRepository;
import cloud.tradingbotisafabbia.repositorios.UsuarioRepository;
import cloud.tradingbotisafabbia.request.PedidoRequest;
import cloud.tradingbotisafabbia.response.PedidoResponse;
import cloud.tradingbotisafabbia.service.BinanceService;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
    
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/{id}/pedidos")
public class PedidoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RelatorioPedidoUsuarioRepository relatorioPedidoUsuarioRepository;

    @Autowired
    private BinanceService binanceService;

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@PathVariable("id") int id, @RequestBody PedidoRequest request) {

        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optUsuario.get();

        // Configuração da chave da API para o usuário
        binanceService.setApiKey(usuario.getChaveApiBinance());
        binanceService.setApiSecret(usuario.getChaveSecretaBinance());

        try {
            // Enviar ordem de compra ou venda para a Binance
            String resultado = binanceService.criarOrdemMercado(request.getSimbolo(), request.getQuantidade(), request.getLado());
            PedidoResponse response = binanceService.processarResultadoOrdem(resultado);

            // Registrando o relatório de pedido no banco
            if ("BUY".equals(request.getLado())) {
                RelatorioPedidoUsuario relatorio = new RelatorioPedidoUsuario();
                relatorio.setSimbolo(request.getSimbolo());
                relatorio.setQuantidade(request.getQuantidade());
                relatorio.setPrecoCompra(response.getPrecoCompra());
                relatorio.setDataHoraOperacao(LocalDateTime.now());

                relatorioPedidoUsuarioRepository.save(relatorio);
                usuario.getRelatoriosPedidos().add(relatorio);
                usuarioRepository.save(usuario);
            }

            if ("SELL".equals(request.getLado())) {
                RelatorioPedidoUsuario relatorio = null;
                for (RelatorioPedidoUsuario item : usuario.getRelatoriosPedidos()) {
                    if (item.getSimbolo().equals(request.getSimbolo()) && item.getPrecoVenda() == 0) {
                        relatorio = item;
                        break;
                    }
                }

                if (relatorio != null) {
                    relatorio.setPrecoVenda(response.getPrecoVenda());
                    relatorioPedidoUsuarioRepository.save(relatorio);
                }
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BinanceConnectorException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
