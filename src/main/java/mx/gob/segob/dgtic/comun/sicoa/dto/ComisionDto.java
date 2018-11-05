package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.util.Date;
import javax.persistence.Transient;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class ComisionDto {
	
	/**
	    * El id comisi&oacute;n.
	    */
	   @MapeaColumna(columna = "id_comision") private Integer idComision;
	   
	   /**
	    * El id usuario.
	    */
	   @MapeaColumnasInternas(columnas={"id_usuario", "id_usuario"}) private UsuarioDto idUsuario;
	   
	   /**
	     * El id responsable.
	     */
	    @MapeaColumna(columna = "id_responsable") private Integer idResponsable;
	    
	    /**
	     * El id archivo.
	     */
	    @MapeaColumnasInternas(columnas={"id_archivo", "id_archivo"}) private ArchivoDto idArchivo;
	    
	    /**
	     * El id estatus.
	     */
	    @MapeaColumnasInternas(columnas={"id_estatus", "id_estatus"}) private EstatusDto idEstatus;
	    
	    /**
	     * La fecha inicio.
	     */
	    @MapeaColumna(columna = "fecha_inicio") private Date fechaInicio;
	    
	    /**
	     * La fecha fin.
	     */
	    @MapeaColumna(columna = "fecha_fin") private Date fechaFin;
	    
	    /**
	     * El n&uacute;mero de d&iacute;as.
	     */
	    @MapeaColumna(columna = "dias") private Integer dias;
	    
	    /**
	     * La descripci&oacute;n de la comisi&oacute;n.
	     */
	    @MapeaColumna(columna = "comision") private String comision;
	    
	    /**
         * La descripci&oacute;n de la comisi&oacute;n.
         */
        @MapeaColumna(columna = "fecha_registro") private Date fechaRegistro;
        
        /**
         * La descripci&oacute;n de la comisi&oacute;n.
         */
        @MapeaColumna(columna = "id_horario") private Horario idHorario;
	    
  @Transient
  private String mensaje;

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }
	    /**
		    * El id comisi&oacute;n.
		    * @return idComision
		    */
		public Integer getIdComision() {
			return idComision;
		}
		
		/**
		    * El id comisi&oacute;n.
		    * @return idComision
		    */
		public void setIdComision(Integer idComision) {
			this.idComision = idComision;
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
		    */
		public void setIdUsuario(UsuarioDto idUsuario) {
			this.idUsuario = idUsuario;
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
	     * El id archivo.
	     * @return idArchido
	     */
		public ArchivoDto getIdArchivo() {
			return idArchivo;
		}
		
		/**
	     * El id archivo.
	     * @param idArchido
	     */
		public void setIdArchivo(ArchivoDto idArchivo) {
			this.idArchivo = idArchivo;
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
	     * La fecha inicio.
	     * @return fechaInicio
	     */
		public Date getFechaInicio() {
			return fechaInicio;
		}
		
		/**
	     * La fecha inicio.
	     * @param fechaInicio
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
	     */
		public void setFechaFin(Date fechaFin) {
			this.fechaFin = fechaFin;
		}
		
		/**
	     * El n&uacute;mero de d&iacute;as.
	     * @return dias
	     */
		public Integer getDias() {
			return dias;
		}
		
		/**
	     * El n&uacute;mero de d&iacute;as.
	     * @param dias
	     */
		public void setDias(Integer dias) {
			this.dias = dias;
		}
		
		/**
	     * La descripci&oacute;n de la comisi&oacute;n.
	     * @return comision
	     */
		public String getComision() {
			return comision;
		}
		
		/**
	     * La descripci&oacute;n de la comisi&oacute;n.
	     * @param comision
	     */
		public void setComision(String comision) {
			this.comision = comision;
		}

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
      return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
      this.fechaRegistro = fechaRegistro;
    }

    public Horario getIdHorario() {
      return idHorario;
    }

    public void setIdHorario(Horario idHorario) {
      this.idHorario = idHorario;
    }
    
}
