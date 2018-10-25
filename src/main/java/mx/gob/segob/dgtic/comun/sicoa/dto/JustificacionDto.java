/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import javax.persistence.Transient;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class JustificacionDto {

	/**
     * El id justificacion.
     */
    @MapeaColumna(columna = "id_justificacion") 
    private Integer idJustificacion;

    /**
     * El nombre de la justificacion.
     */
    @MapeaColumna(columna = "clave") 
    private String clave;
   
    
    /**
     * El nombre de la justificacion.
     */
    @MapeaColumna(columna = "justificacion") 
    private String justificacion;
    
    /**
     * Sí la justificación esta vigente.
     */
    @MapeaColumna(columna = "activo") 
    private Boolean activo;
    
    @Transient 
    private String mensaje;

	/**
	 * @return the idJustificacion
	 */
	public Integer getIdJustificacion() {
		return idJustificacion;
	}

	/**
	 * @param idJustificacion the idJustificacion to set
	 */
	public void setIdJustificacion(Integer idJustificacion) {
		this.idJustificacion = idJustificacion;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}


	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	/**
	 * @return the activo
	 */
	public Boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
   
}
