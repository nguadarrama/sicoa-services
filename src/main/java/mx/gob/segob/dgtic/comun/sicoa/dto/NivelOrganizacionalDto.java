package mx.gob.segob.dgtic.comun.sicoa.dto;

import javax.persistence.Transient;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class NivelOrganizacionalDto {

	@MapeaColumna(columna="id_nivel")
	private Integer idNivel;
	@MapeaColumna (columna = "nivel")
	private String nivel;
	@MapeaColumna (columna = "id_horario")
	private Integer idHorario;
	@MapeaColumna (columna = "horario")
	private String horario;
	@Transient
	private String mensaje;
	
	
	public Integer getIdNivel() {
		return idNivel;
	}
	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Integer getIdHorario() {
		return idHorario;
	}
	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
