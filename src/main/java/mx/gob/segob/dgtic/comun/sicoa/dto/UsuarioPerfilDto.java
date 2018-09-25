/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class UsuarioPerfilDto {

	/**
    * La clave perfil.
    */
   @MapeaColumna(columna = "cve_m_usuario") private String claveUsuario;
   
   /**
    * La clave permiso.
    */
   @MapeaColumna(columna = "cve_c_perfil") private String clavePerfil;
   
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
	 * Obtener la clave usuario.
	 *
	 * @return claveUsuario
	 */
	public String getClaveUsuario() {
		return claveUsuario;
	}
	
	/**
	 * Pasar la clave usuario.
	 *
	 * @param claveUsuario
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}
}
