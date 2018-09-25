/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.webservice;

import java.util.List;

/**
 * Contiene la meta informaci&oacute;n que compone la respuesta a una petici&oacute;n para una serializaci&oacute;n en JSON
 */
public class MetadataDto {
	
	/** Lista de errores. */
	private List<String> errors;
	
	/** El tipo de respuesta. */
	private ResponseType response;
	
	/**
	 * Intancia un nuevo objeto de meta informaci&oacute;n .
	 *
	 * @param responseType El tipo de respuesta
	 */
	public MetadataDto(ResponseType responseType){
		this.response = responseType;
	}
	
	/**
	 * Intancia un nuevo objeto de meta informaci&oacute;n .
	 *
	 * @param responseType El tipo de respuesta
	 * @param errores Lista de errores
	 */
	public MetadataDto(ResponseType responseType, List<String> errores){
		this.response = responseType;
		this.setErrors(errores);
	}
	
	/**
	 * Obtiene el tipo de respuesta response
	 *
	 * @return El reponse
	 */
	public ResponseType getResponse() {
		return response;
	}
	
	/**
	 * Asigna el tipo de respuesta
	 *
	 * @param response el tipo de respuesta
	 */
	public void setResponse(ResponseType response) {
		this.response = response;
	}

	/**
	 * Obtiene los errores
	 *
	 * @return los errores 
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * Asigna los errores
	 *
	 * @param errors los errores
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
