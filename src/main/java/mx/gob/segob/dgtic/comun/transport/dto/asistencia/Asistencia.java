package mx.gob.segob.dgtic.comun.transport.dto.asistencia;

import java.sql.Timestamp;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class Asistencia {
	@MapeaColumna(columna = "id_enrolamiento")
	private Integer idEnrolamiento;
	
	@MapeaColumna(columna = "fecha")
	private Timestamp fecha;
	
	public Asistencia() {
		
	}

	public Integer getIdEnrolamiento() {
		return idEnrolamiento;
	}

	public void setIdEnrolamiento(Integer idEnrolamiento) {
		this.idEnrolamiento = idEnrolamiento;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

}
