/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
/**
 * Clase de excepci&oacute;n para reglas de negocio evaluados en la capa de negocio
 */
public class ReglaNegocioException extends Exception{

	
	/**  La constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Lista de errores que generan la regla de negocio. */
	private final List<String> errores;


	/**
	 * Instancia una nueva excepci&oacute;n de regla de negocio.
	 *
	 * @param errores los errores que generaron la regla de negocio.
	 */
	public ReglaNegocioException( List<String> errores) {
		super();	
		this.errores = new ArrayList<>(0);
		if(CollectionUtils.isNotEmpty(errores)){
			this.errores.addAll(errores);
		}
	}

	
	/**
	 * Instancia una nueva excepci&oacute;n de regla de negocio.
	 *
	 * @param errores los errores que generaron la regla de negocio.
	 * @param cause la causa de la excepci&oacute;n
	 */
	public ReglaNegocioException( List<String> errores, Throwable cause) {
		super(cause.getMessage(), cause);
		this.errores = new ArrayList<>(0);
		if(CollectionUtils.isNotEmpty(errores)){
			this.errores.addAll(errores);
		}				
	}


	/**
	 * Obtiene los errores que se originaron en la regla de negocio.
	 *
	 * @return los errores que generaron la regla de negocio.
	 */
	public List<String> getErrores() {
		return errores;
	}

}
