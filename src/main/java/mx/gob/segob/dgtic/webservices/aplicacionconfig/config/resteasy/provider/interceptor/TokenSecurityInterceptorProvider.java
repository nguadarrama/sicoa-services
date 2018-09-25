/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
	package mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.provider.interceptor;

import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.util.config.AplicacionPropertiesUtil;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.SubjectTokenEnum;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.TokenConstants;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.TokenDto;
import mx.gob.segob.dgtic.comun.util.resteasy.token.validator.TokenValidator;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.interception.Precedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Provedor que evalua si un recurso es publico o restringido, si es restringido evalua que el token cumpla con los criterios que se establecen.
 * 
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
 *     public Response recursoPublico() {
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
 *     public Response recursoProhibido() {
 *     	...
 *     	return response;
 *     }
 *   </pre>
 * 	</li> 		
 * </ul>
 * 
 * @see org.jboss.resteasy.spi.interception.PreProcessInterceptor
 */
@Provider
@ServerInterceptor
@Precedence(value="SECURITY")
public class TokenSecurityInterceptorProvider implements PreProcessInterceptor {

	/**
	 * Intancia para realizar log
	 */
	private static Logger logger = LoggerFactory.getLogger(TokenSecurityInterceptorProvider.class);
	
	/**
	 * The Constant METODO_LOGIN.
	 */
	private static final String METODO_LOGIN = "LOGIN";

	/**
	 * Se eval&uacute;a el tipo de recurso que se solicito, y se restringe el acceso.
	 * <p>
	 * <ul>
	 * 	<li>PermitAll : Recurso publico</li>
	 * 	<li>DenyAll : Recurso prohibido</li>
	 * 	<li> Recurso restringido, Token requerido en el header</li>
	 * </ul>
	 * 
	 * Se puede deshabilitar la seguridad de los recursos cambiando un parametro de configuracion en 
	 * propiedades de la aplicaci&oacute;n ({@code aplicacion.properties} : {@code token.security.enabled})
	 * 
	 * @see org.jboss.resteasy.spi.interception.PreProcessInterceptor#preProcess(org.jboss.resteasy.spi.HttpRequest, org.jboss.resteasy.core.ResourceMethod)
	 */
	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod methodInvoked)
			throws WebApplicationException {
		
		logger.trace("Evaluando token de seguridad ");
		
		//Evalua si esta activo la restriccion de seguridad 
		String enabledSecurity = AplicacionPropertiesUtil.getPropiedades().obtenerPropiedad(TokenConstants.TOKEN_SECURITY_ENABLED_PROPERTY_NAME);
		if(!Boolean.parseBoolean(enabledSecurity)){
			return null;
		}
		
		ServerResponse serverResponse = null;
		Method method = methodInvoked.getMethod();		

		//Evalua el tipo de recurso se solicito
		if(method.isAnnotationPresent(PermitAll.class)) {
			logger.trace("Servicio publico");
        	serverResponse = null;        	
        } else if(method.isAnnotationPresent(DenyAll.class)) {
        	logger.trace("Rechazo completo");
        	serverResponse = getServerResponse("El acceso a este recurso est\u00e1 prohibido", 
        			StatusResponse.FORBIDDEN);        	
        } else {
        	logger.trace("Evaluacion de token de seguridad");
        	serverResponse = evaluaTokenSeguridad(request, methodInvoked);        	
        }		
		
		return serverResponse;		
	}
		
	


	/**
	 * Evalua token seguridad.
	 *
	 * @param request La petici&oacute;n del recurso
	 * @param methodInvoked El proceso que se invoco y que corresponde al endpoint ejecutado
	 * @return null, si el token es correcto, de lo contrario envia una respuesta de error
	 */
	public ServerResponse evaluaTokenSeguridad(HttpRequest request, ResourceMethod methodInvoked){
			
		   ServerResponse response = null;
		   
		   String tokenHeader = getAuthorizationHeaderValue(request);
	       if(StringUtils.isEmpty(tokenHeader)) {
	            response =  getServerResponse("El acceso a este recurso est\u00e1 restringido", 
	            		StatusResponse.UNAUTHORIZED);
	       } else if(!tokenHeader.contains(TokenConstants.AUTHENTICATION_SCHEME_NAME)){
			   response =  getServerResponse("El token de autorizacion debe ser bajo el esquema "+TokenConstants.AUTHENTICATION_SCHEME_NAME, 
					   StatusResponse.UNAUTHORIZED);
		   } else {
			   TokenValidator tokenValidator = new TokenValidator(tokenHeader);
			   
			   if (!tokenValidator.esTokenVigente()) {
					response = getServerResponse("El token ha expirado", 
							StatusResponse.CONFLICT);					
			   } else if(!tokenValidator.esTokenValido()){
				   response = getServerResponse("El token no es valido", 
						   StatusResponse.CONFLICT);
				   
			   } else if(!tokenValidator.esEmisorValido() ){
					response = getServerResponse("El emisor del token no corresponde a este emisor ",
							StatusResponse.UNAUTHORIZED);
					
				} else  if(!tokenValidator.tieneIdentificadorValido()) {
					response = getServerResponse("El identificador del token no corresponde al token generado", 
							StatusResponse.UNAUTHORIZED);
					
				} else if(!tokenValidator.tieneAsuntoValido()) {
				   response = getServerResponse("No tiene un asunto permitido",
						   StatusResponse.UNAUTHORIZED);

			    } else {
				   response = evaluaCriterioAdicional(tokenValidator, methodInvoked);
			   }

		   }
	       return response;		
	}
	
	
	

	/**
	 * Obtiene el token de Autorizaci&oacute;n/Acceso de los header's de la solicitud
	 * 
	 * @param request La petici&oacute;n del recurso
	 * @return El "Token Bearer" de autorizaci&oacute;n.
	 */
	private String getAuthorizationHeaderValue(HttpRequest request){
		 String authorizationValue = "";
		 List<String> authorizationList = new ArrayList<>(0);		
		 
		 HttpHeaders headers = request.getHttpHeaders();
		 for(String headerName : headers.getRequestHeaders().keySet()){
			 if(TokenConstants.AUTHORIZATION_HEADER_NAME.equalsIgnoreCase(headerName)){
				 authorizationList = headers.getRequestHeader(headerName);
				 break;
			 }
		 }

		 if(CollectionUtils.isNotEmpty(authorizationList)) {
			 authorizationValue = authorizationList.get(0);
		 }
		 return authorizationValue;
	}
	

	/**
	 * Genera una respuesta en formato JSON generico de un mensaje 
	 *
	 * @param mensaje El mensaje a responder en el formato del JSON
	 * @param status El status a responder e integrar en el JSON
	 * @return La respuesta en formato JSON.
	 */
	private ServerResponse getServerResponse(String mensaje, StatusResponse status) {
		Headers<Object> headers = new Headers<>();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		
		List<String> errores = Arrays.asList(mensaje);		
		Response response = ResponseJSONGenericoUtil.getRespuestaError(status, errores, null);		
		return new ServerResponse(response.getEntity(), status.getStatusCode(), headers);
	}
	
	/**
	 * Eval&uacute;a el tipo de recurso para identificar si requiere un token de autorizaci&oacute;n o un token de acceso
	 *
	 * @param tokenValidator La informaci&oacute;n del token descompuesto para acceder a sus componentes
	 * @param methodInvoked El metodo que corresponde al endpoint solicitado
	 * @return null si el token es correcto, de lo contrario envia errores.
	 */
	private ServerResponse evaluaCriterioAdicional(TokenValidator tokenValidator, ResourceMethod methodInvoked){
		ServerResponse serverResponse = null;		
		
		TokenDto tokenDto = tokenValidator.getTokenDto();
		SubjectTokenEnum asunto = tokenDto.getAsunto();
		
		//Evalua si es una peticion de login
		boolean esMetodoLogin = methodInvoked.getMethod().getName().equalsIgnoreCase(METODO_LOGIN);
		
		//Evalua cual es el tipo de token generado
		if(asunto.equals(SubjectTokenEnum.AUTENTICACION) && !esMetodoLogin){				
			serverResponse =  getServerResponse("El token de AUTORIZACI\u00d3N solo se permite en proceso de login",
					StatusResponse.UNAUTHORIZED);			 
		} else if(asunto.equals(SubjectTokenEnum.ACCESO) && esMetodoLogin){
			serverResponse =  getServerResponse("Token no autorizado para el proceso de login",
					StatusResponse.UNAUTHORIZED);
		}
					
		return serverResponse;
		
	}
	
}
