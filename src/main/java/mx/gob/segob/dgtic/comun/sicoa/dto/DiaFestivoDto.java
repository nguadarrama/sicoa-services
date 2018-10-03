/*
* ****************************************************
* * Aplicación Base
* * Versi&oacute;n 1.0.0
* * Secretaria de Gobernación - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;


import java.util.Date;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class DiaFestivoDto {

	/**
     * El id día Festivo.
     */
    @MapeaColumna(columna = "id_dia_festivo") private Integer idDiaFestivo;
    
    /**
     * Descripcion del día festivo .
     */
    @MapeaColumna(columna = "nombre") private String nombre;
    
    /**
     * fecha festiva.
     */
    @MapeaColumna(columna = "fecha") private Date fecha;
    
    /**
     * dia activo.
     */
    @MapeaColumna(columna = "activo") private Boolean activo;

	/**
	 * @return the idDiaFestivo
	 */
	public Integer getIdDiaFestivo() {
		return idDiaFestivo;
	}

	/**
	 * @param idDiaFestivo the idDiaFestivo to set
	 */
	public void setIdDiaFestivo(Integer idDiaFestivo) {
		this.idDiaFestivo = idDiaFestivo;
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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
    
	
}
