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
		@MapeaColumna(columna = "id_tipo_dia") private TipoDiaDto tipoDia;
	    
	    /**
	     * El id estatus.
	     */
	    @MapeaColumnasInternas(columnas={"id_estatus", "id_estatus"}) private EstatusDto estatus;
	   
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
	     * Justificación de la incidencia.
	     */
	    @MapeaColumna(columna = "id_justificacion") private JustificacionDto justificacion;

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
		public TipoDiaDto getTipoDia() {
			return tipoDia;
		}
		
		/**
		    * El id tipo incidencia.
		    * @param idTipoIncidencia
		    */
		public void setTipoDia(TipoDiaDto tipoDia) {
			this.tipoDia = tipoDia;
		}
		
		/**
	     * El id estatus.
	     * @return idEstatus
	     */
		public EstatusDto getEstatus() {
			return estatus;
		}
		
		/**
	     * El id estatus.
	     * @param idEstatus
	     */
		public void setEstatus(EstatusDto estatus) {
			this.estatus = estatus;
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

		public JustificacionDto getJustificacion() {
			return justificacion;
		}

		public void setJustificacion(JustificacionDto justificacion) {
			this.justificacion = justificacion;
		}
		
		
}
