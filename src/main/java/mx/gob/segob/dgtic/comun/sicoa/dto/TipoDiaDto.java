/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class TipoDiaDto {

	/**
     * El id tipo d&iacute;a.
     */
    @MapeaColumna(columna = "id_tipo_dia") private Integer idTipoDia;
    
    /**
     * El nombre.
     */
    @MapeaColumna(columna = "nombre") private String nombre;
    
    /**
     * La observaci&oacute;n.
     */
    @MapeaColumna(columna = "observacion") private String observacion;
    
    /**
     * El dato incidencia.
     */
    @MapeaColumna(columna = "incidencia") private boolean incidencia;
    
    /**
	 * Obtener id de tipo d&iacute;a..
	 *
	 * @return idTipoDia
	 */
	public Integer getIdTipoDia() {
		return idTipoDia;
	}
	
	/**
	 * Pasar el id tipo horario d&iacute;a..
	 *
	 * @param idTipoDia el nuevo id tipo d&iacute;a.
	 */
	public void setIdTipoDia(Integer idTipoDia) {
		this.idTipoDia = idTipoDia;
	}
	
	/**
	 * Obtener el nombre de usuario.
	 *
	 * @return el nombre de usuario.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Pasar el nombre de usuario.
	 *
	 * @param nombre el nombre de usuario
	 */
	public void setNombre (String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Obtener observaci&oacute;n.
	 *
	 * @return observacion
	 */
	public String getObservacion() {
		return observacion;
	}
	
	/**
	 * Pasar la observaci&oacute;n.
	 *
	 * @param observacion el valor de observaci&oacute;n
	 */
	public void setObservacion (String observacion) {
		this.observacion = observacion;
	}
	
	/**
	 * Obtener la incidencia.
	 *
	 * @return incidencia
	 */
	public boolean getIncidencia() {
		return incidencia;
	}
	
	/**
	 * Pasar la incidencia.
	 *
	 * @param observacion el valor de la incidencia
	 */
	public void setIncidencia (boolean incidencia) {
		this.incidencia = incidencia;
	}
}
