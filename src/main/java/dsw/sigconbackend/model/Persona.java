package dsw.sigconbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="persona")
public class Persona {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_persona",nullable=false)
    private Long id;

    @Column(name="apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name="apellido_materno", nullable = false, length = 100)
    private String apellidoMaterno;

    @Column(nullable = false, length = 60)
    private String nombres;

    @Column(name="fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 120)
    private String direccion;

    @Column(name="ndocumento", nullable = false, unique=true)
    private Long numeroDocumento;

    @Column(name="create_at", nullable = false, updatable=false)
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name="update_at", nullable = true)
    private LocalDateTime updateAt = LocalDateTime.now();
}
