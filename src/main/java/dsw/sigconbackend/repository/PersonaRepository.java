package dsw.sigconbackend.repository;

import dsw.sigconbackend.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long>  {
    boolean existsByNumDocumento(String numDocumento);
}
