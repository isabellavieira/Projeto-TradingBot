package cloud.tradingbotisafabbia.repositorios;

import cloud.tradingbotisafabbia.objetosmodelo.ConfiguracaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoUsuarioRepository extends JpaRepository<ConfiguracaoUsuario, Integer> {
}
