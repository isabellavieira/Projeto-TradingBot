package cloud.tradingbotisafabbia.repositorios;

import cloud.tradingbotisafabbia.objetosmodelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
