/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

import java.util.Date;

public class PeriodoDto {
	
		/**
	    * El id periodo.
	    */
	   @MapeaColumna(columna = "id_periodo") private Integer idPeriodo;
	   
	   /**
	    * La fecha inicio.
	    */
	   @MapeaColumna(columna = "fecha_inicio") private Date fechaInicio;
	   
	   /**
	    * La fecha fin.
	    */
	   @MapeaColumna(columna = "fecha_fin") private Date fechaFin;
	   
	   /**
	    * El dato activo.
	    */
	   @MapeaColumna(columna = "activo") private Boolean activo;
	   
	   /**
	    * El id periodo.
	    * @return idPeriodo
	    */
		public Integer getIdPeriodo() {
			return idPeriodo;
		}
		
		/**
		* El id periodo.
		* @param idPeriodo
		* @return idPeriodo
		*/
		public void setIdPeriodo(Integer idPeriodo) {
			this.idPeriodo = idPeriodo;
		}
		
		/**
		* La fecha inicio.
		* @return fechaInicio
		*/
		public Date getFechaInicio() {
			return fechaInicio;
		}
		
		/**
		* La fecha inicio.
		* @param fechaInicio
		* @return fechaInicio
		*/
		public void setFechaInicio(Date fechaInicio) {
			this.fechaInicio = fechaInicio;
		}
		
		/**
		* La fecha fin.
		* @return fechaFin
		*/
		public Date getFechaFin() {
			return fechaFin;
		}
		
		/**
		* La fecha fin.
		* @param fechaFin
		* @return fechaFin
		*/
		public void setFechaFin(Date fechaFin) {
			this.fechaFin = fechaFin;
		}
		
		/**
		* El dato activo.
		* @return activo
		*/
		public Boolean getActivo() {
			return activo;
		}
		
		/**
		* El dato activo.
		* @param activo
		* @return activo
		*/
		public void setActivo(Boolean activo) {
			this.activo = activo;
		}

}
