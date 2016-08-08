package py.gov.ande.control.gateway.util;



/**
 * Clase para encapsular un resultado de la BBDD
 * de tal manera a agrupar las excepciones segun conveniencia
 *
 */
public class DatabaseOperationResult {
    private ErrorType errorType = null;
    private final int recordsAffected;
    private final RuntimeException exception;

    /**
     * Constructor
     * @param errorType el tipo de error, si lo hubo
     * @param recordsAffected la cantidad de registros afectados
     * @param exception la excepcion en la cual resulto el error
     */
    public DatabaseOperationResult(ErrorType errorType, int recordsAffected, RuntimeException exception) {
        this.errorType = errorType;
        this.recordsAffected = recordsAffected;
        this.exception = exception;
    }

    /**
     * @return el tipo de error
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @return la cantidad de registros afectados
     */
    public int getRecordsAffected() {
        return recordsAffected;
    }

    /**
     * @return la excepcion causante
     */
    public RuntimeException getException() {
        return exception;
    }


    /**
     * @return verdadero si hubo algun tipo de error
     */
    public boolean wasError() {
        return errorType != null;
    }

    /**
     * Enumeracion de los diferentes tipos de errores producidos en la BBDD
     */
    public static enum ErrorType {
        CONSTRAINT_VIOLATION,
        OTHER;
    }
}
