/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

import java.util.Date;
/**
 * Clase que transporta la informaci&oacute;n del usuario 
 */
public class UsuarioDto {
	
	/**
     * El id usuario.
     */
    @MapeaColumna(columna = "id_usuario") private Integer idUsuario;
	
	/**
     * La clave usuario.
     */
    @MapeaColumna(columna = "cve_m_usuario") private String claveUsuario;
	
	/**
	 * El id del &aacute;rea.
	 */
    @MapeaColumna(columna = "id_area") private Integer idArea;

    /**
     * la clave del perfil.
     */
    //@MapeaColumnasInternas(columnas={"cve_c_perfil", "cve_c_perfil"}) private PerfilDto clavePerfil;
    @MapeaColumna(columna = "cve_c_perfil") private String clavePerfil;
    
    /**
	 * El id del horario.
	 */
    //@MapeaColumnasInternas(columnas={"id_horario", "id_horario"}) private HorarioDto idHorario;
    @MapeaColumna(columna = "id_horario") private String idHorario;
    
    /**
	 * El id del puesto.
	 */
    @MapeaColumna(columna = "id_puesto") private String idPuesto;
    
    /**
	 * El nombre usuario.
	 */
    @MapeaColumna(columna = "nombre") private String nombre;
    
    /**
	 * El apellido paterno.
	 */
    @MapeaColumna(columna = "apellido_paterno") private String apellidoPaterno;
    
    /**
	 * El apellido materno.
	 */
    @MapeaColumna(columna = "apellido_materno") private String apellidoMaterno;
    
    /**
	 * La fecha de ingreso.
	 */
    @MapeaColumna(columna = "fecha_ingreso") private Date fechaIngreso;
    
    /**
	 * La contraseña.
	 */
    @MapeaColumna(columna = "password") private String password;
    
    /**
	 * La activaci&oacute;n.
	 */
    @MapeaColumna(columna = "activo") private Boolean activo;
    
    /**
	 * Estado de nuevo.
	 */
    @MapeaColumna(columna = "nuevo") private Boolean nuevo;
    
    /**
	 * En sesi&oacute;n.
	 */
    @MapeaColumna(columna = "en_sesion") private String enSesion;
    
    /**
	 * &Uacute;ltimo acceso.
	 */
    @MapeaColumna(columna = "ultimo_acceso") private Date ultimoAcceso;
    
    /**
	 * N&uacute;mero de intentos.
	 */
    @MapeaColumna(columna = "numero_intentos") private Integer numeroIntentos;
    
    /**
	 * Usuario bloqueado.
	 */
    @MapeaColumna(columna = "bloqueado") private String bloqueado;
    
    /**
	 * Fecha de bloqueo.
	 */
    @MapeaColumna(columna = "fecha_bloqueo") private Date fechaBloqueo;
    
    /**
	 * Primer ingreso.
	 */
    @MapeaColumna(columna = "primera_vez") private String primeraVez;
    
    /**
	 * Estatus de usuario.
	 */
    @MapeaColumna(columna = "estatus") private String estatus;
    
    
    /**
	 * Obtener el id usuario.
	 *
	 * @return el id usuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * Pasar el id usuario.
	 *
	 * @param idUsuario nuevo valor id usuario
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * Obtener el id &aacute;rea.
	 *
	 * @return the id &aacute;rea.
	 */
	public Integer getIdArea() {
		return idArea;
	}
	
	/**
	 * Pasar el id &aacute;rea.
	 *
	 * @param idArea el nuevo id &aacute;rea.
	 */
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	
	/**
	 * Obtener la clave del perfil.
	 *
	 * @return clavePerfil
	 */
//	public PerfilDto getClavePerfil() {
//		return clavePerfil;
//	}
//	
//	/**
//	 * pasar la clave del perfil.
//	 *
//	 * @param clavePerfil la clave del perfil.
//	 */
//	public void setClavePerfil(PerfilDto clavePerfil) {
//		this.clavePerfil = clavePerfil;
//	}
//    
//	/**
//	 * Obtener el id horario.
//	 *
//	 * @return idHorario
//	 */
//	public Horario getIdHorario() {
//		return idHorario;
//	}
//	
//	/**
//	 * pasar el id horario.
//	 *
//	 * @param idHorario el id horario.
//	 */
//	public void setIdHorario(Horario idHorario) {
//		this.idHorario = idHorario;
//	}
	
	/**
	 * Obtener el id puesto.
	 *
	 * @return el id puesto
	 */
	public String getIdPuesto() {
		return idPuesto;
	}
	
	/**
	 * Pasar el id puesto.
	 *
	 * @param idPuesto el nuevo id puesto
	 */
	public void setIdPuesto (String idPuesto) {
		this.idPuesto = idPuesto;
	}
	
	/**
	 * Obtener el nombre de usuario.
	 *
	 * @return el nombre de usuario.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Pasar el nombre de usuario.
	 *
	 * @param nombre el nombre de usuario
	 */
	public void setNombre (String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Obtener el apellido paterno.
	 *
	 * @return apellidoPaterno.
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
	/**
	 * Pasar el el apellido paterno.
	 *
	 * @param apellidoPaterno el apellido paterno.
	 */
	public void setApellidoPaterno (String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	/**
	 * Obtener el apellido materno.
	 *
	 * @return apellidoMaterno.
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
	/**
	 * Pasar el el apellido materno.
	 *
	 * @param apellidoMaterno el apellido materno.
	 */
	public void setApellidoMaterno (String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	/**
	 * Obtener la fecha de ingreso.
	 *
	 * @return fechaIngreso.
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	
	/**
	 * Pasar la fecha de ingreso.
	 *
	 * @param fechaIngreso la nueva fecha de ingreso.
	 */
	public void setFechaIngreso (Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	/**
	 * Obtener la contraseña.
	 *
	 * @return password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Pasar la contraseña.
	 *
	 * @param password La nueva contraseña.
	 */
	public void setPassword (String password) {
		this.password = password;
	}
	
	/**
	 * Obtener activo.
	 *
	 * @return activo.
	 */
	public Boolean getActivo() {
		return activo;
	}
	
	/**
	 * Pasar el activo.
	 *
	 * @param password el nuevo activo.
	 */
	public void setActivo (Boolean activo) {
		this.activo = activo;
	}
	
	/**
	 * Obtener activo.
	 *
	 * @return activo.
	 */
	public Boolean getNuevo() {
		return nuevo;
	}
	
	/**
	 * Pasar el nuevo.
	 *
	 * @param nuevo el nuevo dato.
	 */
	public void setNuevo (Boolean nuevo) {
		this.nuevo = nuevo;
	}
	
	/**
	 * Obtener el dato en sesion.
	 *
	 * @return enSesion.
	 */
	public String getEnSesion() {
		return enSesion;
	}
	
	/**
	 * Pasar el dato en sesion.
	 *
	 * @param enSesion el nuevo dato en sesion.
	 */
	public void setEnSesion (String enSesion) {
		this.enSesion = enSesion;
	}
	
	/**
	 * Obtener el ultimo acceso.
	 *
	 * @return ultimoAcceso.
	 */
	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}
	
	/**
	 * Pasar el ultimo acceso.
	 *
	 * @param ultimoAcceso el nuevo ultimo acceso.
	 */
	public void setUltimoAcceso (Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
	/**
	 * Obtener el &uacute; de intentos.
	 *
	 * @return numeroIntentos.
	 */
	public Integer getNumeroIntentos() {
		return numeroIntentos;
	}
	
	/**
	 * Pasar el n&uacute;mero de intentos.
	 *
	 * @param numeroIntentos el nuevo &uacute; de intentos.
	 */
	public void setNumeroIntentos (Integer numeroIntentos) {
		this.numeroIntentos = numeroIntentos;
	}
	
	/**
	 * Obtener el campo bloqueado.
	 *
	 * @return bloqueado.
	 */
	public String getBloqueado() {
		return bloqueado;
	}
	
	/**
	 * Pasar el campo bloqueado.
	 *
	 * @param bloqueado el nuevo valor de bloqueado.
	 */
	public void setBloqueado (String bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	/**
	 * Obtener el campo bloqueado.
	 *
	 * @return fechaBloqueo.
	 */
	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}
	
	/**
	 * Pasar  fecha de bloqueo.
	 *
	 * @param fechaBloqueo el nuevo valor de fecha de bloqueo.
	 */
	public void setFechaBloqueo (Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}
	
	/**
	 * Obtener el campo primera vez.
	 *
	 * @return primeraVez.
	 */
	public String getPrimeraVez() {
		return primeraVez;
	}
	
	/**
	 * Pasar  fecha de primera vez.
	 *
	 * @param primeraVez el nuevo valor de primera vez.
	 */
	public void setPrimeraVez (String primeraVez) {
		this.primeraVez = primeraVez;
	}
	
	/**
	 * Obtener el valor de estatus.
	 *
	 * @return estatus.
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * Pasar  valor de estatus.
	 *
	 * @param estatus el nuevo valor estatus.
	 */
	public void setEstatus (String estatus) {
		this.estatus = estatus;
	}
	
	/**
     * La clave usuario.
     * @return claveUsuario
     */
	public String getClaveUsuario() {
		return claveUsuario;
	}
	
	/**
     * La clave usuario.
     * @param claveUsuario
     */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getClavePerfil() {
		return clavePerfil;
	}

	public void setClavePerfil(String clavePerfil) {
		this.clavePerfil = clavePerfil;
	}

	/*public HorarioDto getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(HorarioDto idHorario) {
		this.idHorario = idHorario;
	}*/
	
	public String getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(String idHorario) {
		this.idHorario = idHorario;
	}

}
