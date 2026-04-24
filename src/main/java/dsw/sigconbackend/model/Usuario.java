package dsw.sigconbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_usuario",nullable=false)
    private Long id;

    @Column(name="user_name",nullable=false)
    private String email;

    @Column(name="password",nullable=false)
    private String passwordHash;

    @OneToOne
    @JoinColumn(name="id_persona")
    private Persona persona;
}
