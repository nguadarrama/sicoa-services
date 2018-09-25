/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.demo;

import java.util.Date;

import mx.gob.segob.dgtic.comun.transport.constants.DecisionEnum;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

/**
 * Created by Hp6460b on 05/10/2017.
 */
public class MapeoComplejoPadreDTO {


	/**
	 * The id usuario.
	 */
	@MapeaColumna(columna = "ID_M_USUARIO") 	private String idUsuario;
	
	/**
	 * The usuario nombre.
	 */
	@MapeaColumna(columna = "USUARIO") 			private String usuarioNombre;
	
	/**
	 * The logueado.
	 */
	@MapeaColumna(columna = "EN_SESION") 		private DecisionEnum logueado;
	
	/**
	 * The ultimo acceso.
	 */
	@MapeaColumna(columna = "ULTIMO_ACCESO")	private Date ultimoAcceso;
	
	/**
	 * The child.
	 */
	@MapeaColumnasInternas(columnas={"DESCRIPCION_PERFIL", "ID_C_PERFIL"}) private MapeoComplejoHijoDTO child;
	
	/**
	 * Gets the id usuario.
	 *
	 * @return the id usuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * Sets the id usuario.
	 *
	 * @param idUsuario the new id usuario
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * Gets the usuario nombre.
	 *
	 * @return the usuario nombre
	 */
	public String getUsuarioNombre() {
		return usuarioNombre;
	}
	
	/**
	 * Sets the usuario nombre.
	 *
	 * @param usuarioNombre the new usuario nombre
	 */
	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}
	
	/**
	 * Gets the logueado.
	 *
	 * @return the logueado
	 */
	public DecisionEnum getLogueado() {
		return logueado;
	}
	
	/**
	 * Sets the logueado.
	 *
	 * @param logueado the new logueado
	 */
	public void setLogueado(DecisionEnum logueado) {
		this.logueado = logueado;
	}
	
	/**
	 * Gets the ultimo acceso.
	 *
	 * @return the ultimo acceso
	 */
	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}
	
	/**
	 * Sets the ultimo acceso.
	 *
	 * @param ultimoAcceso the new ultimo acceso
	 */
	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
	
	/**
	 * Gets the child.
	 *
	 * @return the child
	 */
	public MapeoComplejoHijoDTO getChild() {
		return child;
	}
	
	/**
	 * Sets the child.
	 *
	 * @param child the new child
	 */
	public void setChild(MapeoComplejoHijoDTO child) {
		this.child = child;
	}

    
}
