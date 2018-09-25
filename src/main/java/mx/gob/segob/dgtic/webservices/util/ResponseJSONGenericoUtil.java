/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.webservice.MetadataDto;
import mx.gob.segob.dgtic.comun.transport.dto.webservice.PaginationDto;
import mx.gob.segob.dgtic.comun.transport.dto.webservice.ResponseJSONDto;
import mx.gob.segob.dgtic.comun.transport.dto.webservice.ResponseType;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utiler&iacute;a encargada de construir un Response Gen&eacute;rico de tipo JSON.
 * <p>
 * Se genera diferentes JSON de respuesta generica con una estructura especifica.
 * Ejemplo
 * <pre class="code">
 * { "metadata":{
 * 			"response": "EXITO",
 * 			"errores" : [] }, 
 *   "data":{},
 *   "pagination":{
 *   		"total":"",
 *   		"page":"",
 *   		"numberElementsPage":""}
 *  }
 * </pre>
 */
public class ResponseJSONGenericoUtil {



	/**
	 * Instancia con constructor privado para prevenir que la clase de utiler&iacute;a sea instancia ya que su acceso es completamente est&aacute;tico.
	 */
	private ResponseJSONGenericoUtil() {
		throw new IllegalStateException("Utility class");
	}

	

	/**
	 * Obtiene respuesta gen&eacute;rica clasificada como EXITO
	 * <p>
	 * Se generara una respuesta JSON clasificada como de EXITO, y con un objeto a serializar en el contenido de informaci&oacute;n.
	 * Ejemplo
	 * Para los par&aacute;metros:
	 * <pre class="code">
	 * 		StatusResponse status = StatusResponse.OK;
	 * 
	 * 		Map<String, Object> resultado = new HashMap<>();
	 * 		resultado.put("identificador", "1")
	 * 
	 * 		Objecto myobjeto = new Objeto();
	 * 		myobjeto.setEtiqueta("hola");
	 * 		resultado.put("myobjeto", myobjeto);
	 * 
	 * 		Response response = getRespuestaExito.getRespuestaExitoPaginado(status, resultado);
	 * </pre">
	 * Se obtendr&aacute; una respuesta del tipo.
	 * <pre class="code">
	 * 	{  "metadata": { "response": "EXITO" }, 
	 *     "data":{ 
	 *     		  	"identificador", "1" ,
	 *   		    "myobjeto":{ "etiqueta":"hola" 
	 *   		  }
	 *     	}
	 *  }   
	 * 
	 * </pre>
	 *
	 * @param <T> Tipo generico
	 * @param status El estatus de la respuesta
	 * @param respuesta  El objeto a serializar dentro del cuerpo del JSON de respuesta, sera identificado por la etiqueta "data"
	 * @return Un response que contiene la estructura del JSON generico.
	 */
	public static <T> Response getRespuestaExito(StatusResponse status,
			T respuesta) {		
		MetadataDto meta = new MetadataDto(ResponseType.EXITO);
		
		return getRespuesta(status, meta, respuesta, null);
	}
	
	
	
	/**
	 * Obtiene respuesta gen&eacute;rica clasificada como EXITO, e integra el componente de informaci&oacute;n de un paginado
	 * <p>
	 * Se generara una respuesta JSON clasificada como de EXITO, con un objeto a serializar en el contenido de informaci&oacute;n y 
	 * con la informaci&oacute;n referente a el paginado de informaci&oacute;n.
	 * 
	 * Ejemplo
	 * Para los par&aacute;metros:
	 * <pre class="code">
	 * 		StatusResponse status = StatusResponse.OK;
	 * 
	 * 		Map<String, Object> resultado = new HashMap<>();
	 * 		resultado.put("identificador", "1")
	 * 
	 * 		Objecto myobjeto = new Objeto();
	 * 		myobjeto.setEtiqueta("hola");
	 * 		resultado.put("myobjeto", myobjeto);
	 * 	
	 * 		PaginationDto paginado = new PaginationDto();
	 * 		paginado.setTotal(100);
	 * 		paginado.setNumberElementsPage(10);
	 * 		paginado.setPage(2);
	 * 
	 * 		Response response = getRespuestaExito.getRespuestaExitoPaginado(StatusResponse.OK, resultado);
	 * </pre">
	 * Se obtendr&aacute; una respuesta del tipo.
	 * <pre class="code">
	 * 	{  "metadata": { "response": "EXITO" }, 
	 *     "data":{ 
	 *     		  	"identificador", "1" ,
	 *   		    "myobjeto":{ "etiqueta":"hola" 
	 *   		  }
	 *     	},
	 *     "pagination":{
	 *   		"total":"100",
	 *   		"page":"2",
	 *   		"numberElementsPage":"10" 
	 *      }  	
	 *  }   
	 * 
	 * </pre>
	 *
	 * @param <T> Tipo generico
	 * @param status El estatus de la respuesta
	 * @param respuesta El objeto a serializar dentro del cuerpo del JSON de respuesta, sera identificado por la etiqueta "data"
	 * @param paginado El objeto que contiene la informaci&oacute;n referente al paginado de los datos devueltos
	 * @return Un response que contiene la estructura del JSON generico.
	 */
	public static <T> Response getRespuestaExitoPaginado(StatusResponse status,
				T respuesta,
				PaginationDto paginado) {		
		MetadataDto meta = new MetadataDto(ResponseType.EXITO);
		
		return getRespuesta(status, meta, respuesta, paginado);
	}
	
	
	
	/**
	 * Obtiene respuesta gen&eacute;rica clasificada como WARNING, y la lista de errores que causaron que la solicitud no fuera exitosa
	 * <p>
	 * Se generara una respuesta JSON clasificada como WARNING, con un objeto a serializar en el contenido de informaci&oacute;n y 
	 * con una lista de errores.
	 * 
	 * Ejemplo
	 * Para los par&aacute;metros:
	 * <pre class="code">
	 * 		StatusResponse status = StatusResponse.BAD_REQUEST;
	 * 
	 * 		Map<String, Object> resultado = new HashMap<>();
	 * 		resultado.put("identificador", "1")
	 * 
	 * 		Objecto myobjeto = new Objeto();
	 * 		myobjeto.setEtiqueta("hola");
	 * 		resultado.put("myobjeto", myobjeto);
	 * 	
	 * 		List<String> errores = new ArrayList<>(0);
	 * 		errores.add("Identificador repetido");	  		
	 * 
	 * 		Response response = getRespuestaExito.getRespuestaWarning(status, resultado);
	 * </pre">
	 * Se obtendr&aacute; una respuesta del tipo.
	 * <pre class="code">
	 * 	{  "metadata": { 
	 * 			"response": "WARNING",
	 * 			"errores" : ["Identificador repetido"]
	 * 		}, 
	 *     "data":{ 
	 *     		  	"identificador", "1" ,
	 *   		    "myobjeto":{ "etiqueta":"hola" 
	 *   		  }
	 *     	}  	
	 *  }   
	 * 
	 * </pre>
	 *
	 * @param <T> Tipo generico
	 * @param status El estatus de la respuesta
	 * @param errores Lista de errores que se identifican para generar la respuesta
	 * @param respuesta El objeto a serializar dentro del cuerpo del JSON de respuesta, sera identificado por la etiqueta "data"	
	 * @return Un response que contiene la estructura del JSON generico.
	 */
	public static <T> Response getRespuestaWarning(StatusResponse status,
			List<String> errores,
			T respuesta) {		
		List<String> erroresMeta = new ArrayList<>(0);
		if(CollectionUtils.isNotEmpty(errores)){
			erroresMeta.addAll(errores);
		}
		
		MetadataDto meta = new MetadataDto(ResponseType.WARNING, erroresMeta);
		
		return getRespuesta(status, meta, respuesta, null);
	}
	
	

	/**
	 * Obtiene respuesta gen&eacute;rica clasificada como ERROR, y la lista de errores que causaron que la solicitud no fuera exitosa
	 * <p>
	 * Se generara una respuesta JSON clasificada como ERROR, con un objeto a serializar en el contenido de informaci&oacute;n y 
	 * con una lista de errores.
	 * 
	 * Ejemplo
	 * Para los par&aacute;metros:
	 * <pre class="code">
	 * 		StatusResponse status = StatusResponse.BAD_REQUEST;
	 * 
	 * 		Map<String, Object> resultado = new HashMap<>();
	 * 		resultado.put("identificador", "1")
	 * 
	 * 		Objecto myobjeto = new Objeto();
	 * 		myobjeto.setEtiqueta("hola");
	 * 		resultado.put("myobjeto", myobjeto);
	 * 	
	 * 		List<String> errores = new ArrayList<>(0);
	 * 		errores.add("Identificador repetido");	  		
	 * 
	 * 		Response response = getRespuestaExito.getRespuestaWarning(status, resultado);
	 * </pre">
	 * Se obtendr&aacute; una respuesta del tipo.
	 * <pre class="code">
	 * 	{  "metadata": { 
	 * 			"response": "ERROR",
	 * 			"errores" : ["Identificador repetido"]
	 * 		}, 
	 *     "data":{ 
	 *     		  	"identificador", "1" ,
	 *   		    "myobjeto":{ "etiqueta":"hola" 
	 *   		  }
	 *     	}  	
	 *  }   
	 * 
	 * </pre>
	 *
	 * @param <T> Tipo generico
	 * @param status El estatus de la respuesta
	 * @param errores Lista de errores que se identifican para generar la respuesta
	 * @param respuesta El objeto a serializar dentro del cuerpo del JSON de respuesta, sera identificado por la etiqueta "data"	
	 * @return Un response que contiene la estructura del JSON generico.
	 */
	public static <T> Response getRespuestaError(StatusResponse status,
			List<String> errores,
			T respuesta) {		
		List<String> erroresMeta = new ArrayList<>(0);
		if(CollectionUtils.isNotEmpty(errores)){
			erroresMeta.addAll(errores);
		}
		
		MetadataDto meta = new MetadataDto(ResponseType.ERROR, erroresMeta);		
		return getRespuesta(status, meta, respuesta, null);
	}


	
	

	/**
	 * Obtiene respuesta gen&eacute;rica con los parametros que se establecen
	 * 
	 * Ejemplo
	 * Para los par&aacute;metros:
	 * <pre class="code">
	 * 		StatusResponse status = StatusResponse.OK;
	 * 		 	
	 * 		Map<String, Object> resultado = new HashMap<>();
	 * 		resultado.put("identificador", "1") 
	 * 		Objecto myobjeto = new Objeto();
	 * 		myobjeto.setEtiqueta("hola");
	 * 		resultado.put("myobjeto", myobjeto);
	 * 	
	 * 		List<String> errores = new ArrayList<>(0);
	 * 		errores.add("Identificador repetido");
	 * 		MetadataDto meta = new MetadataDto(ResponseType.ERROR, errores);	  		
	 * 
	 * 		Response response = getRespuestaExito.getRespuesta(status,meta, resultado, null);
	 * </pre">
	 * Se obtendr&aacute; una respuesta del tipo.
	 * <pre class="code">
	 * 	{  "metadata": { 
	 * 			"response": "ERROR",
	 * 			"errores" : ["Identificador repetido"]
	 * 		}, 
	 *     "data":{ 
	 *     		  	"identificador", "1" ,
	 *   		    "myobjeto":{ "etiqueta":"hola" 
	 *   		  }
	 *     	}  	
	 *  }   
	 * 
	 * </pre>
	 *
	 * @param <T> Tipo generico
	 * @param status El estatus de la respuesta
	 * @param metadata Contiene la informaci&oacute;n del metada de la respuesta
	 * @param data El objeto a serializar dentro del cuerpo del JSON de respuesta, sera identificado por la etiqueta "data"
	 * @param pagination El objeto que contiene la informaci&oacute;n referente al paginado de los datos devueltos	
	 * @return Un response que contiene la estructura del JSON generico.
	 */
	public static <T> Response getRespuesta(StatusResponse status, 
										MetadataDto metadata,
										T data,
										PaginationDto pagination)	
	{
		Gson gson = new GsonBuilder()
				.enableComplexMapKeySerialization()
				.create();
		
		ResponseJSONDto<T> responseEntity = new ResponseJSONDto<>();
		responseEntity.setMetadata(metadata);
		responseEntity.setPagination(pagination);
		responseEntity.setData(data);
		
		JsonElement jsonel = gson.toJsonTree(responseEntity);
		
		return  Response.status(status)
						.type(MediaType.APPLICATION_JSON)
						.entity(gson.toJson(jsonel))
						.build();
	}
	
}
