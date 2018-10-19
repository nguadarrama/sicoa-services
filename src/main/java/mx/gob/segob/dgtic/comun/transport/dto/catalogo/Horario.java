package mx.gob.segob.dgtic.comun.transport.dto.catalogo;

import java.sql.Time;

import javax.persistence.Transient;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class Horario {

	@MapeaColumna(columna = "id_horario")
	private Integer idHorario;
	
	@MapeaColumna(columna = "nombre")
	private String nombre;

	@MapeaColumna(columna = "hora_entrada")
	private Time horaEntrada;

	@MapeaColumna(columna = "hora_salida")
	private Time horaSalida;

	@MapeaColumna(columna = "activo")
	private Boolean activo;
	
	@Transient 
	private String mensaje;

	public Horario() {

	}

	public Integer getIdHorario() {
		return idHorario;
	}

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

	public Time getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Time horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Time getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Time horaSalida) {
		this.horaSalida = horaSalida;
	}

	public boolean getActivo() {
		return activo;
	}

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
