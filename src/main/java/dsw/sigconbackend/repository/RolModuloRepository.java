package dsw.sigconbackend.repository;
import dsw.sigconbackend.model.RolModulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolModuloRepository extends JpaRepository<RolModulo, Integer> {
}
