/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class PerfilPermisoDto {

	/**
     * La clave perfil.
     */
    @MapeaColumna(columna = "cve_c_perfil") private String clavePerfil;
    
    /**
     * La clave permiso.
     */
    @MapeaColumna(columna = "cve_c_permiso") private String clavePermiso;
    
    /**
	 * Obtener la clave perfil.
	 *
	 * @return clavePerfil
	 */
	public String getClavePerfil() {
		return clavePerfil;
	}
	
	/**
	 * Pasar la clave perfil.
	 *
	 * @param clavePerfil
	 */
	public void setClavePerfil(String clavePerfil) {
		this.clavePerfil = clavePerfil;
	}
	
	/**
	 * Obtener la clave permiso.
	 *
	 * @return clavePemiso
	 */
	public String getClavePermiso() {
		return clavePermiso;
	}
	
	/**
	 * Pasar la clave permiso.
	 *
	 * @param clavePermiso
	 */
	public void setClavePermiso(String clavePermiso) {
		this.clavePermiso = clavePermiso;
	}
}
