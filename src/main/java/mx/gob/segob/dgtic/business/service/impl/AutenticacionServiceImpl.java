/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.dgtic.business.rules.autenticacion.AutenticacionRules;
import mx.gob.segob.dgtic.business.service.AutenticacionService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.exception.ReglaNegocioException;
import mx.gob.segob.dgtic.comun.transport.dto.autenticacion.UsuarioAcceso;
import mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenAccesoBuilder;
import mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenAutenticacionBuilder;
import mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenBuilder;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.TokenDto;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;
import mx.gob.segob.dgtic.persistence.repository.AutenticacionRepository;

/**
 * Implementaci&oacute;n de los m&eacute;todos para las siguientes tareas.
 * <ul>
 * 	<li>La l&oacute;gica de negocio en el proceso de autenticaci&oacute;n</li>
 * 	<li>Generaci&oacute;n de token's de autorizaci&oacute;n y de acceso para el consumo de recursos REST no publicos.</li>
 * </ul>
 * @see mx.gob.segob.dgtic.business.service.AutenticacionService
 * @see mx.gob.segob.dgtic.business.service.base.ServiceBase  
 */
@Service
public class AutenticacionServiceImpl extends ServiceBase implements AutenticacionService   {
	
	/**
	 * Repositorio de datos para la autenticaci&oacute;n de usuario.
	 */
	@Autowired
	private AutenticacionRepository autenticacionRepository;
	
	/**
	 * Reglas de regocio para el proceso de autenticaci&oacute;n.
	 */
	@Autowired
	private AutenticacionRules autenticacionRules;
	
	/**
	 * Genera token de autorizaci&oacute;n para poder realizar el proceso de autenticaci&oacute;n.
	 * <p>
	 * Se toman en cuenta las reglas o criterios de construcci&oacute;n definidos en {@link mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenAutenticacionBuilder}
	 * 
	 * @param solicitante Identificador del solicitante del token.
	 * @return El token de autorizaci&oacute;n
	 * @throws TokenBuilderException Excepci&oacute;n al momento de construir el token.
	 * 
	 * @see mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenAutenticacionBuilder 
	 */
	@Override
	public String generarTokenAutorizacionAutenticacion(String solicitante) throws TokenBuilderException {
		TokenAutenticacionBuilder tokenAutenticacionBuilder = new TokenAutenticacionBuilder(); 
		
		return tokenAutenticacionBuilder.buildAutenticacionToken(solicitante);
	}
	
	
	/**
	 * Genera token de acceso para poder consumir recursos restringidos.
	 * 
	 * <p>
	 * Se toman en cuenta las reglas o criterios de construcci&oacute;n definidos en {@link mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenAccesoBuilder} y en {@link mx.gob.segob.dgtic.business.rules.autenticacion.AutenticacionRules}
	 * <p>
	 * <b><i>Nota: </i></b> 
	 * Un recurso se delimita por el siguiente caso.
	 * <ul>
	 * 	<li>Recurso restringido : ausencia de las anotaciones {@link javax.annotation.security.PermitAll} y {@link javax.annotation.security.DenyAll}
	 *  <pre class="code">
	 *     {@code @GET}
	 *     {@code @Produces(MediaType.APPLICATION_JSON)}
	 *     {@code @Path("path")}
	 *     public Response recursoRestringido() {
	 *     	...
	 *     	return response;
	 *     }	      
	 * </pre>
	 * 	</li>
	 * 	<li>Recurso publico : uso de la anotaci&oacute;n {@link javax.annotation.security.PermitAll}
	 * 	<pre class="code">
	 *     {@code @GET}
	 *     {@code @Produces(MediaType.APPLICATION_JSON)}
	 *     {@code @Path("path")}
	 *     {@code @PermitAll}     
	 *     public Response recursoRestringido() {
	 *     	...
	 *     	return response;
	 *     }	      
	 * </pre> 
	 * 	</li>
	 * 	<li>Recurso bloqueado : uso de la anotaci&oacute;n {@link javax.annotation.security.DenyAll}
	 * 	<pre class="code">
	 *     {@code @GET}
	 *     {@code @Produces(MediaType.APPLICATION_JSON)}
	 *     {@code @Path("path")}
	 *     {@code @DenyAll}
	 *     public Response recursoRestringido() {
	 *     	...
	 *     	return response;
	 *     }
	 *   </pre>
	 * 	</li> 		
	 * </ul>
	 *
	 *
	 * @param nombreUsuario nombre de usuario
	 * @param contrasenia la clave secreta del usuario
	 * @param tokenHeaderAutorizacion token de autorizaci&oacute;n previamente generado
	 * @return El token de acceso para consumo de recursos restringidos.
	 * @throws TokenBuilderException Excepci&oacute;n al momento de construir el token.
	 * @throws ReglaNegocioException Excepci&oacute;n de reglas de negocio para el usuario a autenticar
	 * 
	 * @see mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenAccesoBuilder
	 * @see mx.gob.segob.dgtic.business.rules.autenticacion.AutenticacionRules
	 */
	@Override
	@Transactional
	public String generarTokenAcceso( String nombreUsuario, 
										String contrasenia, 
										String tokenHeaderAutorizacion) 
		throws TokenBuilderException, ReglaNegocioException {
		
		 //Se obtiene el usuario registrado a ese nombre
		 UsuarioAcceso usuario = autenticacionRepository.obtenerUsuarioAccesoByCve(nombreUsuario);
		
		//De identificarse el usuario se obtiene el estado del mismo
		String contraseniaUsuarioBd = null;
		if(usuario != null){
			try {
				contraseniaUsuarioBd = autenticacionRepository.obtenerPalabraClavePorUsuario(usuario.getClaveUsuario());
			} catch (CredentialNotFoundException e) {
				contraseniaUsuarioBd = "";
			}	 
		}	
		
		//Se aplican las reglas de negocio a considerar para la autenticacion
		List<String> errores = autenticacionRules.validaAutenticacionRules(usuario, contraseniaUsuarioBd, contrasenia);
		if(CollectionUtils.isNotEmpty(errores)){
			//De existir un detalle en el estado del usuario se registra un intento no valido para autenticarse y de requerirse se bloquea 
			Integer intentoAcceso = (usuario != null)? usuario.getNumeroIntentos() : 0;			
			intentoAcceso++;
			
			autenticacionRepository.registraIntentoAcceso(nombreUsuario, intentoAcceso);			
			if(autenticacionRules.evaluaBloquearUsuario(intentoAcceso)){
				autenticacionRepository.bloquearUsuarioAcceso(nombreUsuario);
			}			
			throw new ReglaNegocioException(errores);
		}
		

		TokenBuilder tokenBuilder = new TokenBuilder();
		TokenAccesoBuilder tokenAccesoBuilder = new TokenAccesoBuilder();

		//Se obtienen los datos del token que se autorizo para autenticarse
		TokenDto tokenDto = tokenBuilder.buildTokenHeaderToDto(tokenHeaderAutorizacion);
		
		//Se registra el acceso en la BD
		String claveUsuario = usuario != null?usuario.getClaveUsuario() : nombreUsuario;
		autenticacionRepository.registrarAccesoUsuario(claveUsuario);

		//Se construye el token de acceso
		return tokenAccesoBuilder.buildAccesoToken( claveUsuario, 
													tokenDto.getSolicitante());
	}
	
	/**
	 * Obtiene la informaci&oacute;n de usuario al que se le genera el token de acceso.
	 *
	 * @param tokenHeaderAcceso Token de acceso
	 * @return La informaci&oacute;n del usuario autenticado.
	 */
	@Override
	public UsuarioAcceso obtenerInformacionUsuario(String tokenHeaderAcceso){
		TokenBuilder tokenBuilder = new TokenBuilder();
		TokenDto tokenDto = tokenBuilder.buildTokenHeaderToDto(tokenHeaderAcceso);		
		
		UsuarioAcceso usuario = autenticacionRepository.obtenerUsuarioAccesoByCve(tokenDto.getInformacionAdicional().getClaveUsuario());
		List<String> perfiles = autenticacionRepository.obtenerPerfilesUsuario(usuario.getClaveUsuario());
		List<String> permisos = autenticacionRepository.obtenerPermisosUsuario(usuario.getClaveUsuario());
		
		usuario.setPerfiles(CollectionUtils.isNotEmpty(perfiles) ? new HashSet<>(perfiles) : new HashSet<String>(0));
		usuario.setPermisos(CollectionUtils.isNotEmpty(permisos) ? new HashSet<>(permisos) : new HashSet<String>(0));
		
		return usuario;
	}
	
	/**
	 * Realiza la cancelaci&oacute;n de la sesi&oacute;n del usuario autenticado
	 *
	 * @param tokenHeaderAcceso Token de acceso
	 */
	@Override
	public void logout(String tokenHeaderAcceso){
		TokenBuilder tokenBuilder = new TokenBuilder();
		TokenDto tokenDto = tokenBuilder.buildTokenHeaderToDto(tokenHeaderAcceso);		
		autenticacionRepository.cerrarSession(tokenDto.getInformacionAdicional().getClaveUsuario());
	}
	

	/**
	 * Anular la sesion de todos los usuarios.
	 */
	@Override
	public void anularSessionUsuarios() {
		autenticacionRepository.anularSessionUsuarios();
	}


	@Override
	public Integer cambiarPassword(String password, String claveUsuario) {
		return   autenticacionRules.cambiarPassword(claveUsuario, password);
	}
}
