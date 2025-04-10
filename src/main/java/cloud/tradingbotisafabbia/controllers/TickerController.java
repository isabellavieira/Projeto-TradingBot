package cloud.tradingbotisafabbia.controllers;

import cloud.tradingbotisafabbia.objetosmodelo.Usuario;
import cloud.tradingbotisafabbia.objetosmodelo.AcompanhamentoTickerUsuario;
import cloud.tradingbotisafabbia.repositorios.UsuarioRepository;
import cloud.tradingbotisafabbia.response.RespostaTicker;
import cloud.tradingbotisafabbia.service.IntegracaoBinance;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("{id}/tickers")
public class TickerController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IntegracaoBinance integracaoBinance;

    @GetMapping
    public ResponseEntity<List<RespostaTicker>> obterTickers(@PathVariable("id") int id) {
        Optional<Usuario> optUsuario = this.usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optUsuario.get();
        ArrayList<String> simbolos = new ArrayList<>();

        // Adicionando os símbolos para consulta
        for (AcompanhamentoTickerUsuario item : usuario.getAcompanhamentoTickers()) {
            simbolos.add(item.getSimbolo());
        }

        if (simbolos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Não tem tickers associados
        }

        // Configurações da API Binance
        this.integracaoBinance.setChaveApi(usuario.getChaveApiBinance());
        this.integracaoBinance.setChaveSecreta(usuario.getChaveSecretaBinance());

        String resultado = this.integracaoBinance.obterTickers(simbolos);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<RespostaTicker> resposta = objectMapper.readValue(resultado, new TypeReference<List<RespostaTicker>>() {});
            return new ResponseEntity<>(resposta, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}