/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.demo;

import mx.gob.segob.dgtic.comun.transport.constants.DecisionEnum;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

/**
 * The Class MapeoComplejoHijoDTO.
 */
public class MapeoComplejoHijoDTO {
	
	/**
	 * The clave perfil.
	 */
	@MapeaColumna(columna = "cve_c_perfil") 		private String clavePerfil;
	
	/**
	 * The descripcion perfil.
	 */
	@MapeaColumna(columna = "DESCRIPCION_PERFIL") 	private String descripcionPerfil;
	
	/**
	 * The estatus.
	 */
	@MapeaColumna(columna = "ESTATUS") 				private DecisionEnum estatus;
	
	
	/**
	 * Gets the clave perfil.
	 *
	 * @return the clave perfil
	 */
	public String getClavePerfil() {
		return clavePerfil;
	}
	
	/**
	 * Sets the clave perfil.
	 *
	 * @param clavePerfil the new clave perfil
	 */
	public void setClavePerfil(String clavePerfil) {
		this.clavePerfil = clavePerfil;
	}
	
	/**
	 * Gets the descripcion perfil.
	 *
	 * @return the descripcion perfil
	 */
	public String getDescripcionPerfil() {
		return descripcionPerfil;
	}
	
	/**
	 * Sets the descripcion perfil.
	 *
	 * @param descripcionPerfil the new descripcion perfil
	 */
	public void setDescripcionPerfil(String descripcionPerfil) {
		this.descripcionPerfil = descripcionPerfil;
	}
	
	/**
	 * Gets the estatus.
	 *
	 * @return the estatus
	 */
	public DecisionEnum getEstatus() {
		return estatus;
	}
	
	/**
	 * Sets the estatus.
	 *
	 * @param estatus the new estatus
	 */
	public void setEstatus(DecisionEnum estatus) {
		this.estatus = estatus;
	}
	
	
	
}
