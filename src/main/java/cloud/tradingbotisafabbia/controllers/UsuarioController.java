package cloud.tradingbotisafabbia.controllers;

import cloud.tradingbotisafabbia.objetosmodelo.Usuario;
import cloud.tradingbotisafabbia.objetosmodelo.ConfiguracaoUsuario;
import cloud.tradingbotisafabbia.objetosmodelo.AcompanhamentoTickerUsuario;
import cloud.tradingbotisafabbia.repositorios.UsuarioRepository;
import cloud.tradingbotisafabbia.repositorios.ConfiguracaoUsuarioRepository;
import cloud.tradingbotisafabbia.repositorios.AcompanhamentoTickerUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConfiguracaoUsuarioRepository configuracaoUsuarioRepository;

    @Autowired
    private AcompanhamentoTickerUsuarioRepository acompanhamentoTickerUsuarioRepository;

    // Cria um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    // Busca usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterPorId(@PathVariable("id") Integer id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        return optUsuario.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Associa uma configuração ao usuário
    @PostMapping("/{id}/configuracao")
    public ResponseEntity<Usuario> associarConfiguracao(@PathVariable("id") Integer id, @RequestBody ConfiguracaoUsuario configuracao) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Associa o usuário à configuração antes de salvar
        Usuario usuario = optUsuario.get();
        configuracao.setUsuario(usuario); // A associação correta

        // Salva a configuração no banco de dados
        configuracaoUsuarioRepository.save(configuracao);

        // Associa a configuração ao usuário
        usuario.getConfiguracoes().add(configuracao);
        usuarioRepository.save(usuario);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }


    // Associa um ticker de acompanhamento ao usuário
    @PostMapping("/{id}/acompanhamento-ticker")
    public ResponseEntity<Usuario> associarTicker(@PathVariable("id") Integer id, @RequestBody AcompanhamentoTickerUsuario ticker) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Salva o ticker no banco de dados
        acompanhamentoTickerUsuarioRepository.save(ticker);

        // Associa o ticker ao usuário
        Usuario usuario = optUsuario.get();
        usuario.getAcompanhamentoTickers().add(ticker);
        usuarioRepository.save(usuario);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}
