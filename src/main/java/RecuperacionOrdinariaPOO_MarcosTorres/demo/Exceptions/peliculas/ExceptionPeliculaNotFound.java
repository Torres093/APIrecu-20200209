package RecuperacionOrdinariaPOO_MarcosTorres.demo.Exceptions.peliculas;

public class ExceptionPeliculaNotFound extends RuntimeException {
    public ExceptionPeliculaNotFound(String message) {
        super(message);
    }
}
