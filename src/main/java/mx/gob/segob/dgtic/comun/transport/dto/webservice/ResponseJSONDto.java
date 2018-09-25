/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.webservice;

/**
 * Objeto encargado de contener la estructura a serializar para un JSON generico
 *
 * @param <T> Tipo generico
 */
public class ResponseJSONDto <T> {
	
	/** El metadata. */
	private MetadataDto metadata;
	
	/** El dato. */
	private T data;
	
	/** La paginacion. */
	private PaginationDto pagination;
	
	/**
	 * Obtiene el dato.
	 *
	 * @return el dato
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * Asigna el dato
	 *
	 * @param data El dato
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Obtiene la paginaci&oacute;n.
	 *
	 * @return la paginaci&oacute;n.
	 */
	public PaginationDto getPagination() {
		return pagination;
	}
	
	/**
	 * Asigna la paginaci&oacute;n.
	 *
	 * @param pagination la paginaci&oacute;n.
	 */
	public void setPagination(PaginationDto pagination) {
		this.pagination = pagination;
	}
	
	/**
	 * Obtiene el metadato
	 *
	 * @return El metadato
	 */
	public MetadataDto getMetadata() {
		return metadata;
	}
	
	/**
	 * Asigna el metadato
	 *
	 * @param metadata El metadato
	 */
	public void setMetadata(MetadataDto metadata) {
		this.metadata = metadata;
	}
	
	
	
}
