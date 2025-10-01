package RecuperacionOrdinariaPOO_MarcosTorres.demo.Exceptions.peliculas;

public class ExceptionColumnDuplicat extends RuntimeException {
    public String getColumnDuplicate;

    public ExceptionColumnDuplicat(String message) {
        super(message);
    }
}
