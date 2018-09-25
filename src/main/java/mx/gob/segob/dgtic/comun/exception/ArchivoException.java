/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.exception;

/**
 * Clase de excepci&oacute;n para proceso de archivos
 */
public class ArchivoException extends Exception  {
	
	/**
	 * La constante serialVersionUID.
	 */	
	private static final long serialVersionUID = 1714158587023997687L;

	/**
	 * Instancia una nueva excepci&oacute;n de archivos
	 */
	public ArchivoException() {
		super();
	}

	/**
	 * Instancia una nueva excepci&oacute;n de archivos con parametros
	 *
	 * @param mensaje El mensaje a integrar para identificar la excepci&oacute;n
	 * @param cause La excepci&oacute;n causa
	 */
	public ArchivoException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}	

}