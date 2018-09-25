/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.exception;

/**
 * La excepci&oacute;n de construcci&oacute;n de tokens
 */
public class TokenBuilderException extends Exception{
	
	/** La constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepci&oacute;n de construcci&oacute;n de tokens
	 *
	 * @param message El mensaje que identificara la excepci&oacute;n
	 */
	public TokenBuilderException(String message) {
		super(message);
	}
	
	/**
	 * Crea la excepci&oacute;n de construcci&oacute;n de tokens
	 *
	 * @param message El mensaje que identificara la excepci&oacute;n
	 *            
	 * @param cause La causa de la excepcion
	 */
	public TokenBuilderException(String message, Throwable cause){
		super(message, cause);
	}
}
