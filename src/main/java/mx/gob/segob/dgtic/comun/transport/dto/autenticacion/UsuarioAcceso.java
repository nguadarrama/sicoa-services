/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.autenticacion;

import java.util.Date;
import java.util.Set;

import mx.gob.segob.dgtic.comun.transport.constants.EstatusEnum;
import mx.gob.segob.dgtic.comun.transport.constants.DecisionEnum;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

/**
 * La clase que transporta el estado del usuario.
 */
public class UsuarioAcceso {
	
	/**
	 * La clave usuario. Mapea resultado a una columna con alias "CVE_M_USUARIO"
	 */
	@MapeaColumna(columna = "CVE_M_USUARIO") 	private String claveUsuario;
	
	/**
	 * Bandera de logueado. Mapea resultado a una columna con alias "EN_SESION"
	 */
	@MapeaColumna(columna = "EN_SESION") 		private DecisionEnum logueado;
	
	/**
	 * Fecha de ultimo acceso. Mapea resultado a una columna con alias "ULTIMO_ACCESO"
	 */
	@MapeaColumna(columna = "ULTIMO_ACCESO")	private Date ultimoAcceso;
	
	/**
	 * El numero intentos. Mapea resultado a una columna con alias "NUMERO_INTENTOS"
	 */
	@MapeaColumna(columna = "NUMERO_INTENTOS")	private int numeroIntentos;
	
	/**
	 * Bandera de bloqueado. Mapea resultado a una columna con alias "BLOQUEADO"
	 */
	@MapeaColumna(columna = "BLOQUEADO")		private DecisionEnum bloqueado;
	
	/**
	 * La fecha de bloqueo. Mapea resultado a una columna con alias "FECHA_BLOQUEO"
	 */
	@MapeaColumna(columna = "FECHA_BLOQUEO")	private Date fechaBloqueado;
	
	/**
	 * Bandera de primera vez. Mapea resultado a una columna con alias "PRIMERA_VEZ"
	 */
	@MapeaColumna(columna = "PRIMERA_VEZ")		private DecisionEnum primeraVez;
	
	/**
	 * El estatus. Mapea resultado a una columna con alias "ESTATUS"
	 */
	@MapeaColumna(columna = "ESTATUS")			private EstatusEnum estatus;
	
	
	/**
	 * Colecci&oacute;n de perfiles asignados al usuario.
	 */
	private Set<String> perfiles;
	
	/**
	 * Colecci&oacute;n de permisos asignados al usuario.
	 */
	private Set<String> permisos;
	
	
	/**
	 * Obtiene la clave usuario.
	 *
	 * @return la clave usuario
	 */
	public String getClaveUsuario() {
		return claveUsuario;
	}
	
	/**
	 * Asigna la clave usuario.
	 *
	 * @param claveUsuario la clave usuario
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}
	/**
	 * Obtiene la bandera de logueado.
	 *
	 * @return bandera de logueado.
	 */
	public DecisionEnum getLogueado() {
		return logueado;
	}
	
	/**
	 * Asigna bandera de logueado.
	 *
	 * @param logueado bandera de logueado.
	 */
	public void setLogueado(DecisionEnum logueado) {
		this.logueado = logueado;
	}
	
	/**
	 * Obtiene la fecha bloqueado.
	 *
	 * @return La fecha bloqueado
	 */
	public Date getFechaBloqueado() {
		return fechaBloqueado;
	}
	
	/**
	 * Asigna la fecha bloqueado.
	 *
	 * @param fechaBloqueado la fecha bloqueado.
	 */
	public void setFechaBloqueado(Date fechaBloqueado) {
		this.fechaBloqueado = fechaBloqueado;
	}
	
	/**
	 * Obtiene bandera de primera vez.
	 *
	 * @return bandera de primera vez.
	 */
	public DecisionEnum getPrimeraVez() {
		return primeraVez;
	}
	
	/**
	 * Asigna bandera de primera vez.
	 *
	 * @param primeraVez bandera de primera vez.
	 */
	public void setPrimeraVez(DecisionEnum primeraVez) {
		this.primeraVez = primeraVez;
	}
	
	/**
	 * Obtiene el estatus.
	 *
	 * @return el estatus
	 */
	public EstatusEnum getEstatus() {
		return estatus;
	}
	
	/**
	 * Asigna el estatus
	 *
	 * @param estatus el estatus
	 */
	public void setEstatus(EstatusEnum estatus) {
		this.estatus = estatus;
	}
	
	/**
	 * Obtiene la fecha del ultimo ultimo acceso.
	 *
	 * @return la fecha del ultimo ultimo acceso.
	 */
	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}
	
	/**
	 * Asigna la fecha del ultimo ultimo acceso.
	 *
	 * @param ultimoAcceso la fecha del ultimo ultimo acceso.
	 */
	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
	
	/**
	 * Obtiene el numero intentos.
	 *
	 * @return el numero intentos
	 */
	public int getNumeroIntentos() {
		return numeroIntentos;
	}
	
	/**
	 * Asigna el numero intentos.
	 *
	 * @param numeroIntentos el numero intentos.
	 */
	public void setNumeroIntentos(int numeroIntentos) {
		this.numeroIntentos = numeroIntentos;
	}
	
	/**
	 * Obtiene la bandera de bloqueado.
	 *
	 * @return la bandera de bloqueado.
	 */
	public DecisionEnum getBloqueado() {
		return bloqueado;
	}
	
	/**
	 * Asigna la bandera de bloqueado.
	 *
	 * @param bloqueado la bandera de bloqueado.
	 */
	public void setBloqueado(DecisionEnum bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	/**
	 * Obtiene la colecci&oacute;n de perfiles
	 *
	 * @return la colecci&oacute;n de perfiles
	 */
	public Set<String> getPerfiles() {
		return perfiles;
	}
	
	/**
	 * Asigna la colecci&oacute;n de perfiles
	 *
	 * @param perfiles la colecci&oacute;n de perfiles
	 */
	public void setPerfiles(Set<String> perfiles) {
		this.perfiles = perfiles;
	}
	
	/**
	 * Obtiene la colecci&oacute;n de permisos.
	 *
	 * @return la colecci&oacute;n de permisos.
	 */
	public Set<String> getPermisos() {
		return permisos;
	}
	
	/**
	 * Asigna la colecci&oacute;n de permisos.
	 *
	 * @param permisos la colecci&oacute;n de permisos.
	 */
	public void setPermisos(Set<String> permisos) {
		this.permisos = permisos;
	}	
}
