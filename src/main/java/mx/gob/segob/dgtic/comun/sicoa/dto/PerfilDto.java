/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class PerfilDto {

	/**
     * El id perfil.
     */
    @MapeaColumna(columna = "cve_c_perfil") private String clavePerfil;
    
    /**
     * La descripci&oacute;n de perfil.
     */
    @MapeaColumna(columna = "descripcion") private String descripcion;
    
    /**
     * El estatus perfil.
     */
    @MapeaColumna(columna = "estatus") private String estatus;
    
    /**
	 * Obtener el id perfil.
	 *
	 * @return el id perfil
	 */
	public String getClavePerfil() {
		return clavePerfil;
	}
	
	/**
	 * Pasar el id perfil.
	 *
	 * @param idPerfil el nuevo id perfil
	 */
	public void setClavePerfil(String clavePerfil) {
		this.clavePerfil = clavePerfil;
	}
	
	/**
	 * Obtener la descripci&oacute;n..
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Pasar el id perfil.
	 *
	 * @param descripcion la descripcion de perfil
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Obtener el estatus.
	 *
	 * @return estatus el estatus de perfil
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * Pasar el estatus.
	 *
	 * @param estatus el estatus de perfil
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}
