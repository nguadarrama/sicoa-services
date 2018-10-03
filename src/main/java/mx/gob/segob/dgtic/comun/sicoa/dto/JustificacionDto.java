/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class JustificacionDto {

	/**
     * El id justificacion.
     */
    @MapeaColumna(columna = "id_justificacion") private Integer idJustificacion;
    
    /**
     * El nombre de la justificacion.
     */
    @MapeaColumna(columna = "justificacion") private String justificacion;
    
    /**
     * Sí la justificación esta vigente.
     */
    @MapeaColumna(columna = "activo") private Boolean activo;

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
    
   
}
