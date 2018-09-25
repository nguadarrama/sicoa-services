/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.webservice;

/**
 * Contiene la informaci&oacute;n para el paginado de resultados
 */
public class PaginationDto {
	
	/** El total de registros. */
	private Integer total;
	
	/** La pagina posicionada. */
	private Integer page;
	
	/** El n&uacute;mero de elementos por pagina. */
	private Integer numberElementsPage;
		
	/**
	 * Obtiene el total de registros
	 *
	 * @return el total de registros
	 */
	public Integer getTotal() {
		return total;
	}
	
	/**
	 * Asigna el total de registros
	 *
	 * @param total el total de registros
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * Obtiene el numero de elementos por pagina
	 *
	 * @return el numero de elementos por pagina
	 */
	public Integer getNumberElementsPage() {
		return numberElementsPage;
	}
	
	/**
	 * Asigna el numero de elementos por pagina
	 *
	 * @param numberElementsPage el numero de elementos por pagina
	 */
	public void setNumberElementsPage(Integer numberElementsPage) {
		this.numberElementsPage = numberElementsPage;
	}
	
	/**
	 * Obtiene la pagina posicionada
	 *
	 * @return la pagina posicionada
	 */
	public Integer getPage() {
		return page;
	}
	
	/**
	 * Asigna la pagina posicionada
	 *
	 * @param page la pagina posicionada
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

}
