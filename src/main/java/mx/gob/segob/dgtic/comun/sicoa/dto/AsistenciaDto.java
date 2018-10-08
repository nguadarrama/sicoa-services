package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.sql.Timestamp;
import java.util.Date;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class AsistenciaDto {
	
		/**
	    * El id asistencia.
	    */
	   @MapeaColumna(columna = "id_asistencia") private Integer idAsistencia;
	   
	   /**
	    * El id usuario.
	    */
	   @MapeaColumnasInternas(columnas={"id_enrolamiento", "id_enrolamiento"}) private UsuarioDto usuarioDto;
	   
	   /**
	    * El id tipo d&iacute;a.
	    */
	   @MapeaColumnasInternas(columnas={"id_tipo_dia", "id_tipo_dia"}) private TipoDiaDto idTipoDia;
	   
	   /**
	    * El id estatus.
	    */
	   @MapeaColumnasInternas(columnas={"id_estatus", "id_estatus"}) private EstatusDto idEstatus;
	   
	   /**
	    * La hora de entrada.
	    */
	   @MapeaColumna(columna = "entrada") private Timestamp entrada;
	   
	   /**
	    * La hora de salida.
	    */
	   @MapeaColumna(columna = "salida") private Timestamp salida;
	   
	   /**
	    * La incidencia.
	    */
	   @MapeaColumna(columna = "id_incidencia") private IncidenciaDto incidencia;
	   
	   /**
	    * El id asistencia.
	    * @return idAsistencia
	    */
		public Integer getIdAsistencia() {
			return idAsistencia;
		}
		
		/**
		    * El id asistencia.
		    * @param idAsistencia
		    * @return idAsistencia
		    */
		public void setIdAsistencia(Integer idAsistencia) {
			this.idAsistencia = idAsistencia;
		}
		
		/**
		    * El id usuario.
		    * @return idUsuario
		    */
		public UsuarioDto getUsuarioDto() {
			return usuarioDto;
		}
		
		/**
		    * El id usuario.
		    * @param idUsuario
		    * @return idUsuario
		    */
		public void setUsuarioDto(UsuarioDto usuarioDto) {
			this.usuarioDto = usuarioDto;
		}
		
		/**
		    * El id tipo d&iacute;a.
		    * @return idTipoDia
		    */
		public TipoDiaDto getIdTipoDia() {
			return idTipoDia;
		}
		
		/**
		    * El id tipo d&iacute;a.
		    * @param idTipoDia
		    * @return idTipoDia
		    */
		public void setIdTipoDia(TipoDiaDto idTipoDia) {
			this.idTipoDia = idTipoDia;
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
		    * La hora de entrada.
		    * @return entrada
		    */
		public Timestamp getEntrada() {
			return entrada;
		}
		
		/**
		    * La hora de entrada.
		    * @param entrada
		    * @return entrada
		    */
		public void setEntrada(Timestamp entrada) {
			this.entrada = entrada;
		}
		
		/**
		    * La hora de salida.
		    * @return salida
		    */
		public Timestamp getSalida() {
			return salida;
		}
		
		/**
		    * La hora de salida.
		    * @param salida
		    * @return salida
		    */
		public void setSalida(Timestamp salida) {
			this.salida = salida;
		}

		public IncidenciaDto getIncidencia() {
			return incidencia;
		}

		public void setIncidencia(IncidenciaDto incidencia) {
			this.incidencia = incidencia;
		}
		
}
