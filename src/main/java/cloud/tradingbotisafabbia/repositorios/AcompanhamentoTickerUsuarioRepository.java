package cloud.tradingbotisafabbia.repositorios;

import cloud.tradingbotisafabbia.objetosmodelo.AcompanhamentoTickerUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcompanhamentoTickerUsuarioRepository extends JpaRepository<AcompanhamentoTickerUsuario, Integer> {
}
