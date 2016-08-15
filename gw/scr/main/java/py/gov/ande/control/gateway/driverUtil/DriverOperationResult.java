package py.gov.ande.control.gateway.driverUtil;

import py.gov.ande.control.gateway.util.DatabaseOperationResult.ErrorType;

/**
 * Clase que encapsula los errores relacionados a los drivers de comunicaciones
 * @author Pablo
 * @date 2016-08-13
 * @version 1.0
 *
 */
public class DriverOperationResult {

	private ErrorType errorType;
	private Exception exception;

	public DriverOperationResult(){
		this.errorType = null;
	}
	
	public DriverOperationResult(ErrorType errorType, Exception exception) {
		this.errorType = errorType;
		this.exception = exception;
	}
	
    public ErrorType getErrorType() {
		return errorType;
	}

	public Exception getException() {
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
        OTHER,
        IO_EXCEPTION,
        SERVICE_ERROR;
    }

}
