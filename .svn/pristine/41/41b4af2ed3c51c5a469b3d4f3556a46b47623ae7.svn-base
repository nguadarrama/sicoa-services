/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.provider.interceptor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInputImpl;
import org.jboss.resteasy.plugins.server.servlet.HttpServletInputMessage;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import mx.gob.segob.dgtic.business.service.AuditoriaService;
import mx.gob.segob.dgtic.comun.transport.dto.auditoria.AuditoriaBitacoraDto;
import mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenBuilder;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.TokenConstants;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.InformacionAdicionalDTO;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.TokenDto;
import mx.gob.segob.dgtic.comun.util.spring.SpringBeanFactoryUtil;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable;

import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

/**
 * Proveedor que identifica si un recurso solicita se audite mediante la 
 * presencia de la anotaci&oacute;n {@link mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable}
 * <p>
 * Si existe la anotaci&oacute;n se realiza un registro de la bit&aacute;cora. Los valores que se bitacorizan son:
 * <ul>
 * 	<li>Fecha : fecha en que se solicit&oacute; el recurso</li>
 *  <li>idUsuario : Si es un recurso restringido se guarda el usuario que lo solicita</li>
 *  <li>uri : URI que se est&aacute; consumiendo</li>
 *  <li>modulo : modulo que identifica ese recurso</li>
 * 	<li>parametros : par&aacute;metros de entrada (opcional)</li>
 * 	<li>estatusRespuesta : c&oacute;digo del response</li>
 * 	<li>contentTypeRespuesta : tipo del response a enviar</li>
 * 	<li>respuesta : par&aacute;metros de respuesta (opcional)</li>
 * </ul> 
 * <p>
 * Ejemplo de un recurso a bitacorizar
 * 	<pre class="code">
 *     {@code @GET}
 *     {@code @Produces(MediaType.APPLICATION_JSON)}
 *     {@code @Path("path")}
 *     {@code @Auditable}     
 *     public Response recursoBitacorizado() {
 *     	...
 *     	return response;
 *     }	      
 * </pre> 
 *  
 * 
 * 
 * @see org.jboss.resteasy.spi.interception.PreProcessInterceptor
 * @see org.jboss.resteasy.spi.interception.PostProcessInterceptor
 */
@Provider
@ServerInterceptor
public class AuditoriaInterceptor implements PreProcessInterceptor,  PostProcessInterceptor {

	/**
	 * Intancia para realizar log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AuditoriaInterceptor.class);
	
	/**
	 *  Constante que representa el atributo ATTRIBUTE_BITACORA, identificador que almacenara la informaci&oacute;n a 
	 *  bitacorizar en el request en lo que se concluye el consumo del recurso.
	 */
	private static final String ATTRIBUTE_BITACORA = "bitacora";
	
    /**
     * Constante que mantendra el request de manera ThreadSafe
     */
    private static final ThreadLocal<HttpRequest> request = new ThreadLocal<>();
    
    /**
     * Proveedores registrados
     */
    @Context Providers workers;
    

	/**
	 * Proceso que obtiene la informaci&oacute;n del request que identifica el recurso previo a la ejecuci&oacute;n del proceso, 
	 * siempre que contenga la anotaci&oacute;n {@link mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable}
	 * 
	 * <ul>
	 * 	<li>Fecha : fecha en que se solicit&oacute; el recurso</li>
	 *  <li>idUsuario : Si es un recurso restringido se guarda el usuario que lo solicita</li>
	 *  <li>uri : URI que se est&aacute; consumiendo</li>
	 *  <li>modulo : modulo que identifica ese recurso</li>
	 * 	<li>parametros : par&aacute;metros de entrada (opcional)</li>	 * 	
	 * </ul> 
	 * 
	 * @see org.jboss.resteasy.spi.interception.PreProcessInterceptor#preProcess(org.jboss.resteasy.spi.HttpRequest, org.jboss.resteasy.core.ResourceMethod)
	 */
	@Override
	public ServerResponse preProcess(HttpRequest httpRequest, ResourceMethod methodInvoked)
			throws WebApplicationException {
		
		logger.trace("preProcess Preparando informacion a auditar ");
		request.set(httpRequest);
		
		Method method = methodInvoked.getMethod();	
		
		boolean esAuditable = method.isAnnotationPresent(Auditable.class);
		if(esAuditable){
			AuditoriaBitacoraDto bitacora = new AuditoriaBitacoraDto();
			bitacora.setCveUsuario(getClaveUsuario(httpRequest));
			
			Auditable atributosAuditar = method.getAnnotation(Auditable.class);
			bitacora.setModulo(atributosAuditar.modulo());
			bitacora.setFechaAudito(new Date());
			
			bitacora.setUri(httpRequest.getUri().getPath());
			String parametrosEntrada = null;
			boolean almacenarParametrosEntrada = atributosAuditar.guardarParametrosEntrada();
			if(almacenarParametrosEntrada){
				parametrosEntrada = obtenerParametrosEntrada(httpRequest);
			}
			bitacora.setParametros(parametrosEntrada);
			httpRequest.setAttribute(ATTRIBUTE_BITACORA, bitacora);
		}
		return null;
	}
		 
	/**
	 * Proceso que obtiene la informaci&oacute;n del response que identifica el recurso posterior a la ejecuci&oacute;n del proceso, 
	 * siempre que contenga la anotaci&oacute;n {@link mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable}
	 * una vez obtenida la informaci&oacute;n se realiza el registro de la informaci&oacute;n en la bit&aacute;cora
	 * 
	 * <ul>
	 * 	<li>estatusRespuesta : c&oacute;digo del response</li>
	 * 	<li>contentTypeRespuesta : tipo del response a enviar</li>
	 * 	<li>respuesta : par&aacute;metros de respuesta (opcional)</li>
	 * </ul> 
	 * 
	 * @see org.jboss.resteasy.spi.interception.PostProcessInterceptor#postProcess(org.jboss.resteasy.core.ServerResponse)
	 */
	@Override
	public void postProcess(ServerResponse response) {
		logger.trace("postProcess Preparando informacion a auditar ");
		Method method = response.getResourceMethod();	
		boolean esAuditable = method.isAnnotationPresent(Auditable.class);
		if(esAuditable){
			HttpRequest httpRequest = request.get();						
			if(httpRequest != null && httpRequest.getAttribute(ATTRIBUTE_BITACORA) != null){
				Auditable atributosAuditar = method.getAnnotation(Auditable.class);
				AuditoriaBitacoraDto bitacora =	(AuditoriaBitacoraDto)httpRequest.getAttribute(ATTRIBUTE_BITACORA);
				bitacora.setEstatusRespuesta(response.getStatus());
				List<Object> headers = response.getMetadata().get(HttpHeaders.CONTENT_TYPE);
				if(CollectionUtils.isNotEmpty(headers)){
					bitacora.setContentTypeRespuesta(headers.get(0).toString());	
				}
				if(atributosAuditar.guardarSalida() && 
						StringUtils.isNotBlank(bitacora.getContentTypeRespuesta()) ){
					String respuesta = obtenerRespuesta(response.getEntity(), bitacora.getContentTypeRespuesta());
					bitacora.setRespuesta(respuesta);
				}
				
				guardarBitacora(bitacora);				
			}				
		}		
	}
	
	/**
	 * Proceso que realiza el registro de la informaci&oacute;n auditada en BD.
	 *
	 * @param bitacora Informaci&oacute;n auditada
	 */
	private void guardarBitacora(AuditoriaBitacoraDto bitacora){
		try{
			AuditoriaService auditoriaService = SpringBeanFactoryUtil.getBeanFactory().getBean(AuditoriaService.class);	
			
			auditoriaService.guardarAuditoriaBitacora(bitacora);	
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Convierte la respuesta del recurso ejecutado en una representaci&oacute;n de JSON, para su registro.
	 *
	 * @param entidadRespuesta Entidad a responder por el recurso REST
	 * @param contentType El tipo de Content-Type que identica la respuesta.
	 * @return La representacion JSON de la respuesta.
	 */
	private String obtenerRespuesta(Object entidadRespuesta, String contentType){
		Gson gsonPostProcess = new GsonBuilder().enableComplexMapKeySerialization()
				.serializeNulls()
				.create();
		
		String respuesta = null;
		if(contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON) && entidadRespuesta != null){
			if(entidadRespuesta instanceof String) {
				respuesta = (String)entidadRespuesta;
			} else {
				respuesta =  gsonPostProcess.toJson(entidadRespuesta);
			}
		} else {
			respuesta = "{\"WARNING\" : \"CONTENIDO NO BITACORIZABLE ( "+contentType+" )\" }";	
		}
		return respuesta;		  
	}

	
	/**
	 * Inspecciona la clave de usuario del token de acceso para los recursos que est&aacute;n restringidos.
	 *
	 * @param httpRequest La petici&oacute;n del recurso RESTApi
	 * @return La clave del usuario que peticiona
	 */
	private String getClaveUsuario(HttpRequest httpRequest){

		String claveUsuario = null;
		
		 List<String> authorizationList = null;		
		 
		 HttpHeaders headers = httpRequest.getHttpHeaders();
		 authorizationList = headers.getRequestHeader(TokenConstants.AUTHORIZATION_HEADER_NAME);
		 if(!CollectionUtils.isEmpty(authorizationList)) {
			 String headerToken = authorizationList.get(0);
			 TokenBuilder tokenBuilder = new TokenBuilder();
			 TokenDto dto = tokenBuilder.buildTokenHeaderToDto(headerToken);
			 InformacionAdicionalDTO informacionAdicional = dto.getInformacionAdicional();
			 if( informacionAdicional != null && 
				 StringUtils.isNotBlank(informacionAdicional.getClaveUsuario())){
				 claveUsuario = informacionAdicional.getClaveUsuario();
			 }			 
		 }
		 return claveUsuario;
	}

	
	/**
	 * Obtiene los par&aacute;metros de entrada que vienen en la solicitud para su bitacorizaci&oacute;n y 
	 * se convierten en una objeto JSON para su registro.
	 *
	 * @param httpRequest La petici&oacute;n del recurso RESTApi
	 * @return La representacion JSON de los parametros de entrada
	 */
	private String obtenerParametrosEntrada(HttpRequest httpRequest){
		
			String parametros = null;
			HttpHeaders headers = httpRequest.getHttpHeaders();
			MediaType mediaType = headers.getMediaType();
			String mediaTypeString  = (mediaType != null)?mediaType.toString().split(";")[0]:"";
			//Se evalua el tipo de media type para identificar como obtener los parametros de entrada 
			switch (mediaTypeString) {
				case MediaType.APPLICATION_JSON:
					parametros = obtenerParametrosJson(httpRequest);
					break;
				case MediaType.MULTIPART_FORM_DATA:
					parametros = obtenerParametrosMultipart(httpRequest);
					break;
				case MediaType.APPLICATION_FORM_URLENCODED:					
				default:
					if(httpRequest.getFormParameters() != null){
						parametros = obtenerParametrosFormData(httpRequest);	
					} else {
						parametros = "{\"WARNING\" : \"CONTENIDO NO BITACORIZABLE ( "+mediaTypeString+" )\" }";	
					}					
					break;			
			}
 		 return parametros;
	}

	
	/**
	 * Convierte los parametros de entrada de un formulario a un objeto JSON. 
	 *
	 * @param httpRequest La petici&oacute;n del recurso RESTApi
	 * @return La representacion JSON de los parametros de entrada
	 */
	private String obtenerParametrosFormData(HttpRequest httpRequest){		
		Gson gsonFormData = new GsonBuilder().enableComplexMapKeySerialization()
					.serializeNulls()
					.create();		
		HttpServletInputMessage webHttpRequest = (HttpServletInputMessage)httpRequest;
		String parametrosFormData = null;
		if(httpRequest.getHttpMethod().equals(HttpMethod.GET)){			
			parametrosFormData = gsonFormData.toJson(webHttpRequest.getUri().getQueryParameters());
		} else {
			parametrosFormData = gsonFormData.toJson(httpRequest.getFormParameters());
		}
		
		return parametrosFormData;
	}
	
	/**
	 * Obtiene el objeto json de la solicitud
	 *
	 * @param httpRequest La petici&oacute;n del recurso RESTApi
	 * @return La representacion JSON de los parametros de entrada
	 */
	private String obtenerParametrosJson(HttpRequest httpRequest){	
		String contenidoParametros = "";
		JsonParser jsonParser = new JsonParser();
		
		try{
			InputStreamReader readerInputStream = new InputStreamReader(httpRequest.getInputStream());
			JsonElement jsonElement = jsonParser.parse(readerInputStream);	
			contenidoParametros = jsonElement.toString();
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
			contenidoParametros = "{\"WARNING\" : \"CONTENIDO NO BITACORIZABLE\", \"ERROR\" : \""+e.getMessage()+"\" }";
		}
		return contenidoParametros;
	}

	
	/**
	 * Convierte los parametros de entrada de un formulario de tipo MULTIPART a un objeto JSON. 
	 *
	 * @param httpRequest La petici&oacute;n del recurso RESTApi
	 * @return La representacion JSON de los parametros de entrada
	 */
	private String obtenerParametrosMultipart(HttpRequest httpRequest){		
		
		Gson gsonMultipartData = new GsonBuilder().enableComplexMapKeySerialization()
					.serializeNulls()
					.create();		
		
		MediaType mediaType = httpRequest.getHttpHeaders().getMediaType();
		String contenidoParametros = "";		
		Map<String, List<String>> formDataSimple = new HashMap<>(0);
		try {
			//Se obtienen los parametros multipart
			MultipartFormDataInputImpl input = new MultipartFormDataInputImpl(mediaType, workers);
	    	input.parse(httpRequest.getInputStream());

			//Se Itera cada parametro para poder generar el mapa de valores a convertir
	    	Map<String, List<InputPart>> formData = input.getFormDataMap();	    	
	    	for(Entry<String, List<InputPart>> entry : formData.entrySet() ){
	    		String parametroName = entry.getKey();
	    		List<String> datos = new ArrayList<>(0);
	    		
	    		List<InputPart> partes = entry.getValue();
	    		for(InputPart parte : partes){	    			
	    			//Si el tipo es de texto plano se obtiene el valor
	    			if(parte.getMediaType().isCompatible(MediaType.TEXT_PLAIN_TYPE)){
	    				String parametro = parte.getBodyAsString();
	    				datos.add(parametro);
	    			} else {
	    				//Si el tipo es diferente de texto plano solo se obtiene el nombre del archivo
	    				String contentDisposion = parte.getHeaders().getFirst("Content-Disposition"); 	    				
	    				String fileName = StringUtils.isNotBlank(contentDisposion)? contentDisposion.replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$", "$1") : "";
	    				datos.add(fileName);
	    			}
	    		}
	    		formDataSimple.put(parametroName, datos);
	    	}
	    	contenidoParametros = gsonMultipartData.toJson(formDataSimple);
		} catch (IOException e) {
			contenidoParametros = "{\"WARNING\" : \"CONTENIDO NO BITACORIZABLE\", \"ERROR\" : \""+e.getMessage()+"\" }";
		}
		
		return contenidoParametros;
	}
}
