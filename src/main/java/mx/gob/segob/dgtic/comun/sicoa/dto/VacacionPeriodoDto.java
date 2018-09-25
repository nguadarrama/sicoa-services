package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.util.Date;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class VacacionPeriodoDto {
	
		/**
	    * El id vacaci&oacute;n.
	    */
	   @MapeaColumna(columna = "id_vacacion") private Integer idVacacion;
	   
	   /**
	    * El id usuario.
	    */
	   @MapeaColumnasInternas(columnas={"id_usuario", "id_usuario"}) private UsuarioDto idUsuario;
	   
	   /**
	    * El id periodo.
	    */
	   @MapeaColumnasInternas(columnas={"id_periodo", "id_periodo"}) private PeriodoDto idPeriodo;
	   
	   /**
	    * El id estatus.
	    */
	   @MapeaColumnasInternas(columnas={"id_estatus", "id_estatus"}) private EstatusDto idEstatus;
	   
	   /**
	    * La fecha inicio.
	    */
	   @MapeaColumna(columna = "fecha_inicio") private Date fechaInicio;
	   
	   /**
	    * El n&uacute;mero de di&aacute;s.
	    */
	   @MapeaColumna(columna = "dias") private Integer dias;
	   
	   /**
	    * El dato activo.
	    */
	   @MapeaColumna(columna = "activo") private Boolean activo;
	   
	   /**
	    * El id vacaci&oacute;n.
	    * @return idVacacion
	    */
		public Integer getIdVacacion() {
			return idVacacion;
		}
		
		/**
		 * El id vacaci&oacute;n.
		 * @param idVacacion
		 * @return idVacacion
		 */
		public void setIdVacacion(Integer idVacacion) {
			this.idVacacion = idVacacion;
		}
		
		/**
		    * El id usuario.
		    * @return idUsuario
		    */
		public UsuarioDto getIdUsuario() {
			return idUsuario;
		}
		
		/**
		    * El id usuario.
		    * @param idUsuario
		    * @return idUsuario
		    */
		public void setIdUsuario(UsuarioDto idUsuario) {
			this.idUsuario = idUsuario;
		}
		
		/**
		    * El id periodo.
		    * @return idPeriodo
		    */
		public PeriodoDto getIdPeriodo() {
			return idPeriodo;
		}
		
		/**
		    * El id periodo.
		    * @param idPeriodo
		    * @return idPeriodo
		    */
		public void setIdPeriodo(PeriodoDto idPeriodo) {
			this.idPeriodo = idPeriodo;
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
		    * @return idEstatus
		    */
		public void setIdEstatus(EstatusDto idEstatus) {
			this.idEstatus = idEstatus;
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
		    * El n&uacute;mero de di&aacute;s.
		    * @return dias
		    */
		public Integer getDias() {
			return dias;
		}
		
		/**
		    * El n&uacute;mero de di&aacute;s.
		    * @param dias
		    * @return dias
		    */
		public void setDias(Integer dias) {
			this.dias = dias;
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
