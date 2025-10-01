package RecuperacionOrdinariaPOO_MarcosTorres.demo.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class peliculasDTO {
    @Positive(message = "El valor debe de ser positivo")
    private Long id;
    private String titulo;
    private String director;
    private int añio;
    private String genero;
    private int duracion;
    @Positive(message = "La puntuntuacion deben ser números positivos")
    @Min(value = 1, message = "El valor debe ser mayor o igual a 1")
    @Max(value = 5, message = "El valor debe ser menor o igual a 10")
    private int puntuacion;


}
