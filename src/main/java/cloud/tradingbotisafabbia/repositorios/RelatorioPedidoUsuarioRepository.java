package cloud.tradingbotisafabbia.repositorios;

import cloud.tradingbotisafabbia.objetosmodelo.RelatorioPedidoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioPedidoUsuarioRepository extends JpaRepository<RelatorioPedidoUsuario, Integer> {
}
