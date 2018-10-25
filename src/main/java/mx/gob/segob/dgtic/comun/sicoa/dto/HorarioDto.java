/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.sql.Time;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class HorarioDto {

	/**
     * El id horario.
     */
    @MapeaColumna(columna = "id_horario") private Integer idHorario;

    
    /**
     * horario activo.
     */
    @MapeaColumna(columna = "nombre") private String nombre;
    
    /**
     * horario de entrada.
     */
    @MapeaColumna(columna = "hora_entrada") private Time horaEntrada;
    
    /**
     * horario de entrada.
     */
    @MapeaColumna(columna = "hora_salida") private Time horaSalida;
    
    /**
     * horario activo.
     */
    @MapeaColumna(columna = "activo") private String activo;
    
	
	/**
	 * Obtener id de horario.
	 *
	 * @return el id horario
	 */
	public Integer getIdHorario() {
		return idHorario;
	}
	
	/**
	 * Pasar el id horario.
	 *
	 * @param idHorario el nuevo id horario
	 */
	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtener hora de entrada.
	 *
	 * @return hora de entrada
	 */
	public Time getHoraEntrada() {
		return horaEntrada;
	}
	
	/**
	 * Pasar hora de entrada.
	 *
	 * @param horaEntrada Hora de entrada
	 */
	public void setHoraEntrada(Time horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	
	/**
	 * Obtener hora de salida.
	 *
	 * @return hora de salida
	 */
	public Time getHoraSalida() {
		return horaSalida;
	}
	
	/**
	 * Pasar hora de salida.
	 *
	 * @param horaSalida Hora de salida
	 */
	public void setHoraSalida(Time horaSalida) {
		this.horaSalida = horaSalida;
	}
}
