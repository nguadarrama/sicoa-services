package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.util.Date;

import javax.persistence.Transient;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class LicenciaMedicaDto {

			/**
		    * El id licencia.
		    */
		   @MapeaColumna(columna = "id_licencia") private Integer idLicencia;
		   
		   /**
		     * El id responsable.
		     */
		    @MapeaColumna(columna = "id_responsable") private Integer idResponsable;
		    
		    /**
		     * El id estatus.
		     */
		    @MapeaColumnasInternas(columnas={"id_estatus", "id_estatus"}) private EstatusDto idEstatus;
		   
		   /**
		     * El id archivo.
		     */
		    @MapeaColumnasInternas(columnas={"id_archivo", "id_archivo"}) private ArchivoDto idArchivo;
		    
		    /**
			  * El id usuario.
			  */
			 @MapeaColumnasInternas(columnas={"id_usuario", "id_usuario"}) private UsuarioDto idUsuario;
			 
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
			   * Padecimiento.
			   */
			  @MapeaColumna(columna = "padecimiento") private String padecimiento;
			
			  @MapeaColumna(columna = "fecha_registro") private Date fechaRegistro;
			  
			  String fechaInicioAux;
			  String fechaFinAux;
			  String totalLicencias;
			  String diasTotales;
			  
			  @Transient
			   private String mensaje;
			  
			  public String getMensaje() {
				return mensaje;
			}

			public void setMensaje(String mensaje) {
				this.mensaje = mensaje;
			}

			public String getDiasTotales() {
				return diasTotales;
			}

			public void setDiasTotales(String diasTotales) {
				this.diasTotales = diasTotales;
			}

			public String getTotalLicencias() {
				return totalLicencias;
			}

			public void setTotalLicencias(String totalLicencias) {
				this.totalLicencias = totalLicencias;
			}

			public String getFechaInicioAux() {
				return fechaInicioAux;
			}

			public void setFechaInicioAux(String fechaInicioAux) {
				this.fechaInicioAux = fechaInicioAux;
			}

			public String getFechaFinAux() {
				return fechaFinAux;
			}

			public void setFechaFinAux(String fechaFinAux) {
				this.fechaFinAux = fechaFinAux;
			}

			public Date getFechaRegistro() {
				return fechaRegistro;
			}

			public void setFechaRegistro(Date fechaRegistro) {
				this.fechaRegistro = fechaRegistro;
			}

			/**
			    * El id licencia.
			    * @return idLicencia
			    */
			public Integer getIdLicencia() {
				return idLicencia;
			}
			
			/**
			    * El id licencia.
			    * @param idLicencia
			    */
			public void setIdLicencia(Integer idLicencia) {
				this.idLicencia = idLicencia;
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
			   * Padecimiento.
			   * @return padecimiento
			   */
			public String getPadecimiento() {
				return padecimiento;
			}
			
			/**
			   * Padecimiento.
			   * @param padecimiento
			   */
			public void setPadecimiento(String padecimiento) {
				this.padecimiento = padecimiento;
			}
}
