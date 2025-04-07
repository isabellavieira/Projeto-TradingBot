package cloud.tradingbotisafabbia.controllers;

import cloud.tradingbotisafabbia.objetosmodelo.Usuario;
import cloud.tradingbotisafabbia.objetosmodelo.TickerController;
import cloud.tradingbotisafabbia.repositorios.UsuarioRepository;
import cloud.tradingbotisafabbia.response.TickerResponse;
import cloud.tradingbotisafabbia.service.BinanceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/{id}/tickers")
public class TickerController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BinanceService binanceService;

    @GetMapping
    public ResponseEntity<List<TickerResponse>> obterTickers(@PathVariable("id") int id) {
        // Buscar usuário pelo ID
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optUsuario.get();

        // Coletar os símbolos das criptomoedas que o usuário está acompanhando
        List<String> tickers = new ArrayList<>();
        for (AcompanhamentoTickerUsuario item : usuario.getAcompanhamentoTickers()) {
            tickers.add(item.getSimbolo());
        }

        // Configurar as chaves da API da Binance
        binanceService.setApiKey(usuario.getChaveApiBinance());
        binanceService.setApiSecret(usuario.getChaveSecretaBinance());

        // Obter os preços mais recentes dos tickers
        String resultado = binanceService.obterPrecosTickers(tickers);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            // Deserializar o resultado e criar a lista de respostas
            List<TickerResponse> resposta = objectMapper.readValue(resultado,
                    new TypeReference<List<TickerResponse>>() {});
            return new ResponseEntity<>(resposta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
