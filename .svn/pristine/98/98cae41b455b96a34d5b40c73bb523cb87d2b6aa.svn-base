/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.comun;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa mensajes a responder para generar una respuesta de un recurso RESTApi
 */
public class MensajeValidacionDTO implements Serializable {

	/**
	 * La constante serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista de errores.
	 */
	private List<String> errores;
	
	/**
	 * Crea nueva instancia de mensajes de validaci&oacute;n.
	 */
	public MensajeValidacionDTO() {
		errores = new ArrayList<>(0);		
	}



	/**
	 * Obtiene la lista de errores.
	 *
	 * @return la lista de errores.
	 */
	public List<String> getErrores() {
		return errores;
	}

	/**
	 * Asigna la lista de errores.
	 *
	 * @param errores la lista de errores
	 */
	public void setErrores(List<String> errores) {
		this.errores = errores;
	}
}
