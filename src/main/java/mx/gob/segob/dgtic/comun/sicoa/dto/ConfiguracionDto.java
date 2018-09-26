package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.sql.Timestamp;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class ConfiguracionDto {
	@MapeaColumna(columna = "id_configuracion") 
	private Integer idConfiguracion;
	
	@MapeaColumna(columna = "nombre") 
	private String nombre;
	
	@MapeaColumna(columna = "fecha") 
	private Timestamp fecha;

	public Integer getIdConfiguracion() {
		return idConfiguracion;
	}

	public void setIdConfiguracion(Integer idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	
}
