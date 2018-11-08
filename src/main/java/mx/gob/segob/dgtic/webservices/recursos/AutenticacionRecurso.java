/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.recursos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.segob.dgtic.business.service.AutenticacionService;
import mx.gob.segob.dgtic.comun.exception.ReglaNegocioException;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.autenticacion.UsuarioAcceso;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

/**
 * Recurso que expone las tareas del proceso de autenticaci&oacute;n
 * 
 * @see mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase
 */
@Path("sec")
@Component
public class AutenticacionRecurso extends RecursoBase {
	
	
	
	/**
	 * Servicio de negocio del proceso de autenticaci&oacute;n
	 */
	@Autowired
	private AutenticacionService autenticacionService; 
	
	
	/**
	 * Anula session de todos los usuarios. Al reiniciar el proyecto
	 */
	@PostConstruct
	public void anulaSessionUsuarios(){
		autenticacionService.anularSessionUsuarios();
	}
	/**
	 * Recurso GET <em>"{contextoAplicacion}/sec/autorizacionAutenticacion"</em> que genera un token de autorizaci&oacute;n para el proceso de login
	 * <p>
	 * Recurso publico
	 *
	 * @param solicitante Parametro de formulario identificado como "solicitante", corresponde al solicitante a autorizar
	 * @return Response que contiene el token de autorizaci&oacute;n en formato JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("autorizacionAutenticacion")	
	@PermitAll
	@Auditable(modulo="AUTORIZACION_AUTENTICACION", guardarParametrosEntrada=true)
	public Response solicitarAutorizacionAutenticacion(@QueryParam("solicitante") String solicitante){
		
		Response response = null;
		try{
			String tokenAutorizacion = autenticacionService.generarTokenAutorizacionAutenticacion(solicitante);		
			
			response= ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, tokenAutorizacion);
		}catch(TokenBuilderException exception){
			logger.error(exception.getMessage(), exception);			
			
			List<String> errores = Arrays.asList(exception.getMessage());
			response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.BAD_REQUEST, errores, "");		
		}
		return response;
	}
	
	/**
	 * Recurso @POST <em>"{contextoAplicacion}/sec/login"</em> encargado de realizar la autenticaci&oacute;n de usuario mediante sus credenciales
	 * <p>
	 * Es un recurso que est&aacute; restringido a token's de autorizaci&oacute;n.
	 *
	 * @param usuario Par&aacute;metro de formulario identificado como "usuario", corresponde al nombre de usuario a autenticar
	 * @param contrasenia Par&aacute;metro de formulario identificado como "contrasenia", corresponde a la palabra secreta con que se identifica el usuario
	 * @param tokenHeaderAutorizacion Header identificado como "Authorization" el cual contiene la autorizaci&oacute;n mediante el esquema "Bearer TOKEN" con el token de autorizaci&oacute;n previamente solicitado
	 * @return Response que contiene la respuesta en formato JSON en base a la evaluaci&oacute;n de credenciales.
	 *			<ul>
	 *				<li> Correcto : El token de acceso para los recursos restringidos </li>
	 *				<li> Incorrecto : Env&iacute;a mensaje de error o condiciones no cumplidas </li>
	 *			</ul>
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("login")	
	@Auditable(modulo="AUTENTICACION", guardarParametrosEntrada=true, guardarSalida=true)
	public final Response login(
							@FormParam("usuario") String usuario, 
							@FormParam("contrasenia") String contrasenia,
							@HeaderParam("Authorization") String tokenHeaderAutorizacion){
		
		Response response = null;
		try{
			String tokenAcceso = autenticacionService.generarTokenAcceso(usuario, contrasenia, tokenHeaderAutorizacion);			
			response= ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, tokenAcceso);			
		} catch(TokenBuilderException  exception){
			logger.error(exception.getMessage(), exception);
			
			List<String> errores = Arrays.asList(exception.getMessage());
			response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.BAD_REQUEST, errores, "");			
		} catch (ReglaNegocioException exception) {			
			logger.error(exception.getMessage(), exception);
			response = ResponseJSONGenericoUtil.getRespuestaWarning(StatusResponse.BAD_REQUEST, 
																		exception.getErrores(), "");
		}
		return response;
	}
	
	/**
	 * Recurso GET <em>"{contextoAplicacion}/sec/informacion/usuario"</em> que obtiene la informacion del usuario
	 * 
	 * @param tokenHeaderAcceso Header identificado como "Authorization" el cual contiene la autorizaci&oacute;n mediante el esquema "Bearer TOKEN" con el token de acceso previamente solicitado
	 * @return Response que contiene la respuesta en formato JSON de la informacion del usuario identificado con el atributo "datosUsuario" .
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("informacion/usuario")	
	public Response obtenerInformacionUSuario(@HeaderParam("Authorization") String tokenHeaderAcceso){
		
		UsuarioAcceso usuario = autenticacionService.obtenerInformacionUsuario(tokenHeaderAcceso);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, usuario);
	}
	
	/**
	 * Recurso PUT <em>"{contextoAplicacion}/sec/logout"</em> que cierra la sesi&oacute;n de usuario.
	 * 
	 * @param tokenHeaderAcceso Header identificado como "Authorization" el cual contiene la autorizaci&oacute;n mediante el esquema "Bearer TOKEN" con el token de acceso previamente solicitado
	 * @return Response que indica la ejecuci&oacute;n del cierre de sesi&oacute;n correcto
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("logout")	
	public Response logout(@HeaderParam("Authorization") String tokenHeaderAcceso){
		
		Response response = null;
		
		autenticacionService.logout(tokenHeaderAcceso);
		response=ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		
		return response;
	}
	
	/**@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cambiaContrasenia")
	@PermitAll
	public Response cambiaPassword(@QueryParam("usuario") String usuario, 
			@QueryParam("contrasenia") String contrasenia ){
		System.out.println("usuario "+usuario);
		Response response = null;
		
		autenticacionService.cambiarPassword(contrasenia, usuario);
		response=ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		
		return response;
	}**/
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cambiaContrasenia")	
	public Response cambiaContrasenia(@RequestParam String jsonContrasenia) {
		JsonObject jsonObject = new JsonParser().parse(jsonContrasenia).getAsJsonObject();
		List<String> datos = new ArrayList<>();
		
		String datoUsuario="usuario";
		String usuario =""+ jsonObject.get(datoUsuario);
		String contrasenia =""+ jsonObject.get("contrasenia");
		contrasenia=contrasenia.replace("\"", "");
		usuario=usuario.replace("\"", "");
		logger.info("usuario recibido: {} ",usuario);
		logger.info("contrasenia: {}", contrasenia );
		boolean resultado = false;
		resultado = autenticacionService.cambiarPassword(contrasenia,usuario);
		/**
		 * Consultar resultado
		 */
		if(!resultado){
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		}else{
			return ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.NOT_MODIFIED, datos, "Fallo la actualización");
		}
		
	}
}
