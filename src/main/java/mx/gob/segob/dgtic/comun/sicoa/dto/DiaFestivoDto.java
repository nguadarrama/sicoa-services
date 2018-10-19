/**
 * 
 */
package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Transient;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

/**
 * @author Anzen Digital
 *
 */
public class DiaFestivoDto {
	
	/**
	    * El id estatus.
	    */
	   @MapeaColumna(columna = "id_festivo") 
	   private Integer idDiaFestivo;
	   
	   /**
	    * La descripción de la festividad o motivo del día
	    */
	   @MapeaColumna(columna = "nombre") 
	   private String nombre;
	   
	   /**
	    * Fecha del día inhabíl.
	    */
	   @MapeaColumna(columna = "fecha") 
	   private Date fecha;
	   
	   /**
	    * Estatus del catalogo
	    */
	   @MapeaColumna(columna = "activo") 
	   private Boolean activo;

	   @Transient
	   private String mensaje;

	/**
	 * 
	 */
	public DiaFestivoDto() {
		super();
	}



	/**
	 * @param idDiaFestivo
	 * @param nombre
	 * @param fecha
	 * @param activo
	 */
	public DiaFestivoDto(Integer idDiaFestivo, String nombre, Date fecha, Boolean activo, String mensaje) {
		super();
		this.idDiaFestivo = idDiaFestivo;
		this.nombre = nombre;
		this.fecha = fecha;
		this.activo = activo;
		this.mensaje = mensaje;
	}



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
	 * @throws ParseException 
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
