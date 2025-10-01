package RecuperacionOrdinariaPOO_MarcosTorres.demo.Entities.Peliculas;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@ToString @EqualsAndHashCode
@Table(name = "PELICULAS")
public class PeliculasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_peliculas_id")
    @SequenceGenerator(sequenceName = "seq_peliculas_id", name = "seq_peliculas_id", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DIRECTOR")
    private String director;
    @Column(name = "AÑO")
    private LocalDate año;
    @Column(name = "GENERO")
    private String genero;
    @Column(name = "DURACION_MINUTOS")
    private int duracion;
    @Column(name = "PUNTUACION")
    private int puntuacion;
}
