/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.dto;

import java.util.UUID;

/**
 * Clase que contien la informacion adicional al usuario
 */
public class InformacionAdicionalDTO {
	
	/** El id informacion adicional. */
	private String idInformacion;	
	
	/** La clave del usuario. */
	private String claveUsuario;
	
	
	/**
	 * Se crea una instancia del la informacion adicional
	 */
	public InformacionAdicionalDTO() {
		//Se obtiene un identificador unico para esta informacion
		idInformacion = UUID.randomUUID().toString();
	}
	
	/**
	 * Obtiene el id de informacion adicional.
	 *
	 * @return el id informacion
	 */
	public String getIdInformacion() {
		return idInformacion;
	}

	/**
	 * Obtiene la clave usuario.
	 *
	 * @return la clave usuario
	 */
	public String getClaveUsuario() {
		return claveUsuario;
	}

	/**
	 * Asigna la clave usuario.
	 *
	 * @param claveUsuario la clave de usuario
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}
	
}
