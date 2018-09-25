/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class PermisoDto {

	/**
     * El id permiso.
     */
    @MapeaColumna(columna = "cve_c_permiso") private String idPermiso;
    
    /**
     * La descripci&oacute;n del permiso.
     */
    @MapeaColumna(columna = "descripcion") private String descripcion;
    
    /**
     * El estatus del permiso.
     */
    @MapeaColumna(columna = "estatus") private String estatus;
    
    /**
	 * Obtener el id permiso.
	 *
	 * @return el id permiso
	 */
	public String getIdPermiso() {
		return idPermiso;
	}
	
	/**
	 * Pasar el id permiso.
	 *
	 * @param idPermiso valor de id permiso
	 */
	public void setIdPermiso(String idPermiso) {
		this.idPermiso = idPermiso;
	}
	
	/**
	 * Obtener la descripci&oacute;n.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Pasar la descripci&oacute;n.
	 *
	 * @param descripcion valor de descripci&oacute;n.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Obtener el estatus.
	 *
	 * @return estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * Pasar el estatus.
	 *
	 * @param estatus valor de estatus.
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
    
}
