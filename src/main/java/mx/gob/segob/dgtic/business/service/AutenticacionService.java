/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.service;

import mx.gob.segob.dgtic.comun.exception.ReglaNegocioException;
import mx.gob.segob.dgtic.comun.transport.dto.autenticacion.UsuarioAcceso;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;

/**
 * Definici&oacute;n de los m&eacute;todos para las siguientes tareas.
 * <ul>
 * 	 <li>La l&oacute;gica de negocio en el proceso de autenticaci&oacute;n</li>
 * 	 <li>Generaci&oacute;n de token's de autorizaci&oacute;n y de acceso para el consumo de recursos REST no publicos.</li>
 * </ul>
 */
public interface AutenticacionService {
	
	/**
	 * Genera token de autorizaci&oacute;n para poder realizar el proceso de autenticaci&oacute;n.
	 * 
	 * @param solicitante Identificador del solicitante del token.
	 * @return El token de autorizaci&oacute;n
	 * @throws TokenBuilderException Excepci&oacute;n al momento de construir el token.
	 */
	String generarTokenAutorizacionAutenticacion(String solicitante) throws TokenBuilderException;

	/**
	 * Genera token de acceso para poder consumir recursos restringidos.
	 *
	 * @param nombreUsuario nombre de usuario
	 * @param contrasenia la clave secreta del usuario
	 * @param tokenAutorizacion token de autorizaci&oacute;n previamente generado
	 * @return El token de acceso para consumo de recursos restringidos.
	 * @throws TokenBuilderException Excepci&oacute;n al momento de construir el token.
	 * @throws ReglaNegocioException Excepci&oacute;n de reglas de negocio para el usuario a autenticar
	 */
	String generarTokenAcceso(	String nombreUsuario, 
								String contrasenia, 
								String tokenAutorizacion)
			throws TokenBuilderException, ReglaNegocioException;

	/**
	 * Obtiene la informaci&oacute;n de usuario al que se le genera el token de acceso.
	 *
	 * @param tokenHeaderAcceso Token de acceso
	 * @return La informaci&oacute;n del usuario autenticado.
	 */
	UsuarioAcceso obtenerInformacionUsuario(String tokenHeaderAcceso);

	/**
	 * Realiza la cancelaci&oacute;n de la sesi&oacute;n del usuario autenticado
	 *
	 * @param tokenHeaderAcceso Token de acceso
	 */
	void logout(String tokenHeaderAcceso);

	/**
	 * Anular la sesion de todos los usuarios.
	 */
	void anularSessionUsuarios();
	
	Integer cambiarPassword(String password, String claveUsuario);
}
