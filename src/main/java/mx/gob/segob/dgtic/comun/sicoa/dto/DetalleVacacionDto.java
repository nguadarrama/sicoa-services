/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

import java.util.Date;

import javax.persistence.Transient;

public class DetalleVacacionDto {
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
     * El id detalle vacaci&oacute;n.
     */
    @MapeaColumna(columna = "id_detalle") private Integer idDetalle;
    
    
    /**
     * la clave del usuario.
     */
    @MapeaColumnasInternas(columnas={"cve_m_usuario", "cve_m_usuario"}) private UsuarioDto idUsuario;
    
    /**
     * El id vacaci&oacute;n.
     */
    @MapeaColumnasInternas(columnas={"id_vacacion", "id_vacacion"}) private VacacionPeriodoDto idVacacion;
    
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
    
    @MapeaColumna(columna = "fecha_registro") private Date fechaRegistro;
    
    @Transient
	   private String mensaje;
    
    /**
     * El id detalle vacaci&oacute;n.
     * @return idDetalle
     */
	public Integer getIdDetalle() {
		return idDetalle;
	}
	
	/**
     * El id detalle vacaci&oacute;n.
     * @param idDetalle
     * @return idDetalle
     */
	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
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
     * @return idResponsable
     */
	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}
	
	/**
     * La fecha inicio.
     * @return fechaInicio
     */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	/**
     * La fecha inicio.
     * @param fechaInicio
     * @return fechaInicio
     */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
     * El n&uacute;mero de d&iacute;as.
     * @return dias
     */
	public Integer getDias() {
		return dias;
	}
	
	/**
     * El n&uacute;mero de d&iacute;as.
     * @param dias
     * @return dias
     */
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	/**
     * la clave del usuario.
     * @return idUsuario
     */
	public UsuarioDto getIdUsuario() {
		return idUsuario;
	}
	
	/**
     * la clave del usuario.
     * @param idUsuario
     * @return idUsuario
     */
	public void setIdUsuario(UsuarioDto idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
     * El id vacaci&oacute;n.
     * @return idVacacion
     */
	public VacacionPeriodoDto getIdVacacion() {
		return idVacacion;
	}
	
	/**
     * El id vacaci&oacute;n.
     * @param idVacacion
     * @return idVacacion
     */
	public void setIdVacacion(VacacionPeriodoDto idVacacion) {
		this.idVacacion = idVacacion;
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
     * @return idArchivo
     */
	public void setIdArchivo(ArchivoDto idArchivo) {
		this.idArchivo = idArchivo;
	}
	
	/**
     * El id estatus.
     * @param idEstatus
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
    
	
	

}
