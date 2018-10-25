package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class UnidadAdministrativaDto {

	/**
	    * El id unidad.
	    */
	   @MapeaColumna(columna = "id_unidad") private Integer idUnidad;
	   
	   /**
	    * El nombre.
	    */
	   @MapeaColumna(columna = "nombre") private String nombre;
	   
	   

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
