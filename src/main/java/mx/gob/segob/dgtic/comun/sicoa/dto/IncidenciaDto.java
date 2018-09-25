package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class IncidenciaDto {
	
	   /**
	    * El id incidencia.
	    */
	   @MapeaColumna(columna = "id_incidencia") private Integer idIncidencia;
	   
	   /**
	     * El id archivo.
	     */
	    @MapeaColumnasInternas(columnas={"id_asistencia", "id_asistencia"}) private AsistenciaDto idAsistencia;
	    
	    /**
		    * El id tipo incidencia.
		    */
		   @MapeaColumna(columna = "id_tipo_incidencia") private Integer idTipoIncidencia;
	    
	    /**
	     * El id estatus.
	     */
	    @MapeaColumnasInternas(columnas={"id_estatus", "id_estatus"}) private EstatusDto idEstatus;
	   
	   /**
	     * El id archivo.
	     */
	    @MapeaColumnasInternas(columnas={"id_archivo", "id_archivo"}) private ArchivoDto idArchivo;
	    
	    /**
	     * El id responsable.
	     */
	    @MapeaColumna(columna = "id_responsable") private Integer idResponsable;
	    
	    /**
	     * El descuento.
	     */
	    @MapeaColumna(columna = "descuento") private Boolean descuento;
	    
	    /**
	     * Observaciones de la incidencia.
	     */
	    @MapeaColumna(columna = "observaciones") private String observaciones;

	    /**
		    * El id incidencia.
		    * @return idIncidencia
		    */
		public Integer getIdIncidencia() {
			return idIncidencia;
		}
		
		/**
		    * El id incidencia.
		    * @param idIncidencia
		    */
		public void setIdIncidencia(Integer idIncidencia) {
			this.idIncidencia = idIncidencia;
		}
		
		/**
		    * El id asistencia.
		    * @return idAsistencia
		    */
		public AsistenciaDto getIdAsistencia() {
			return idAsistencia;
		}
		
		/**
		    * El id asistencia.
		    * @param idAsistencia
		    */
		public void setIdAsistencia(AsistenciaDto idAsistencia) {
			this.idAsistencia = idAsistencia;
		}
		
		/**
		    * El id tipo incidencia.
		    * @return idTipoIncidencia
		    */
		public Integer getIdTipoIncidencia() {
			return idTipoIncidencia;
		}
		
		/**
		    * El id tipo incidencia.
		    * @param idTipoIncidencia
		    */
		public void setIdTipoIncidencia(Integer idTipoIncidencia) {
			this.idTipoIncidencia = idTipoIncidencia;
		}
		
		/**
	     * El id estatus.
	     * @return idEstatus
	     */
		public EstatusDto getIdEstatus() {
			return idEstatus;
		}
		
		/**
	     * El id estatus.
	     * @param idEstatus
	     */
		public void setIdEstatus(EstatusDto idEstatus) {
			this.idEstatus = idEstatus;
		}
		
		/**
	     * El id archivo.
	     * @return idArchivo
	     */
		public ArchivoDto getIdArchivo() {
			return idArchivo;
		}
		
		/**
	     * El id archivo.
	     * @param idArchivo
	     */
		public void setIdArchivo(ArchivoDto idArchivo) {
			this.idArchivo = idArchivo;
		}
		
		/**
	     * El id responsable.
	     * @return idResponsable
	     */
		public Integer getIdResponsable() {
			return idResponsable;
		}
		
		/**
	     * El id responsable.
	     * @param idResponsable
	     */
		public void setIdResponsable(Integer idResponsable) {
			this.idResponsable = idResponsable;
		}
		
		/**
	     * El descuento.
	     * @return descuento
	     */
		public Boolean getDescuento() {
			return descuento;
		}
		
		/**
	     * El descuento.
	     * @param descuento
	     */
		public void setDescuento(Boolean descuento) {
			this.descuento = descuento;
		}
		
		/**
	     * Observaciones de la incidencia.
	     * @return observaciones
	     */
		public String getObservaciones() {
			return observaciones;
		}
		
		/**
	     * Observaciones de la incidencia.
	     * @param observaciones
	     */
		public void setObservaciones(String observaciones) {
			this.observaciones = observaciones;
		}
}
