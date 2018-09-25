/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.recursos;

import mx.gob.segob.dgtic.business.service.DemoService;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.demo.DemoAnotacionesMapperDTO;
import mx.gob.segob.dgtic.comun.transport.dto.demo.TablaDemo;
import mx.gob.segob.dgtic.comun.transport.dto.webservice.PaginationDto;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;


/**
 * Recurso que expone los servicios REST de muestra.
 *
 * @see mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase
 */
@Path("demo")
@Component
public class DemoRecurso extends RecursoBase {
	
	/** Constante de mensaje  MENSAJE_SALUDO. */
	private static final String MENSAJE_SALUDO = "Hola este un mensaje ";

	/** El servicio de negocio de muestra. */
	@Autowired
	private DemoService demoServiceAutowired;
	
    /**
     * Recurso GET <em>"{contextoAplicacion}/demo/mensaje"</em>, genera un mensaje como cuerpo de la respuesta 
	 * <p>
	 * Recurso publico (anotacion {@code javax.annotation.security.PermitAll} presente)
	 * Respuesta.
	 * {
     *		"metadata": {
     * 			"response": "EXITO"
     * 		},
     *  	"data": "Hola este un mensaje "
	 * }
	 *
	 * @return Response que contiene un objeto JSON con saludo como respuesta
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("mensaje")
    @PermitAll
    public Response mensaje() {

        Response response = null;
        try {
        	response = ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, MENSAJE_SALUDO);            
        } catch (Exception e) {        	
        	List<String> errores = Arrays.asList(e.getMessage());        	
        	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
        			errores, 
        			null);            
        }
        return response;
    }
    
    /**
     * Recurso POST <em>"{contextoAplicacion}/demo/mensaje/POST"</em>, genera un mensaje con un nombre especifico
	 * <p>
	 * Recurso publico (anotacion {@code javax.annotation.security.PermitAll} presente)
	 * Parametro.
	 * {
	 *	   "idUsuario": "ADMIN"
	 * }
	 * 
	 * Respuesta.
	 * {
     * 		"metadata": {
     * 			"response": "EXITO"
     *		},
     *		"data": "Hola este un mensaje ADMIN"
	 * }
	 *
	 *
     * @param dto Objeto que recibe el json para su asignacion a las variables
     * @return  Response que contiene un objeto JSON con saludo al id solicitado 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("mensaje/POST")
    @PermitAll
    public Response mensajePOST(DemoAnotacionesMapperDTO dto) {

        Response response = null;
        try {
        	response = ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, MENSAJE_SALUDO+" "+dto.getNombre());            
        } catch (Exception e) {        	
        	List<String> errores = Arrays.asList(e.getMessage());        	
        	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
        			errores, 
        			null);            
        }
        return response;
    }
    
    

    
    /**
     * Recurso GET <em>"{contextoAplicacion}/demo/mensaje/springautowired"</em>, genera un mensaje al usuario ADMIN despues de acceder a el servicio de negocio asociado
	 * <p>
	 * Recurso publico (anotacion {@code javax.annotation.security.PermitAll} presente)
	 
	 * 
	 * Respuesta.
	 * {
     * 		"metadata": {
     * 			"response": "EXITO"
     *		},
     *		"data": "Hola este un mensaje ADMIN"
	 * }
	 *
	 *
     * 
     * @return  Response que contiene un objeto JSON con saludo al id solicitado si existe en la BD
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)    
    @Path("mensaje/springautowired")
    @PermitAll
    public Response mensajeSpringautowired() {

        Response response = null;
        try {
        	DemoAnotacionesMapperDTO dto = demoServiceAutowired.obtenerUsuarioByAnotaciones("ADMIN");
        	response = ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, MENSAJE_SALUDO+dto.getIdUsuario());      
        } catch (Exception e) {
        	List<String> errores = Arrays.asList(e.getMessage());        	
        	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
        			errores, 
        			null);            
        }
        return response;
    }
    

    /**
     * Recurso GET <em>"{contextoAplicacion}/demo/mensaje/restringido"</em>, genera un mensaje al usuario ADMIN despues de acceder a el servicio de negocio asociado,
     * se requiere el token de autorizacion en el header.
	 * <p>
	 * Recurso restringido (requiere token de acceso valido)	 
	 * 
	 * Respuesta error (sin token incluido).
	 *  {
     * 		"metadata": {
     *   		"errors": [
     *       		"El token no es valido"
     *   		],
     *  		"response": "ERROR"
     * 		}
	 *	}
	 * 
	 * Respuesta exito (incluye token la solicitud).
	 * {
     * 		"metadata": {
     * 			"response": "EXITO"
     *		},
     *		"data": "Hola este un mensaje ADMIN"
	 * }
	 *
	 *
     * 
     * @return  Response que contiene un objeto JSON con saludo al id solicitado si existe en la BD
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("mensaje/restringido")    
    public Response mensajespringautowiredrestringido() {

        Response response = null;      
        try {
        	DemoAnotacionesMapperDTO dto = demoServiceAutowired.obtenerUsuarioByAnotaciones("ADMIN");
        	response = ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, MENSAJE_SALUDO+dto.getIdUsuario());            
        } catch (Exception e) {
        	List<String> errores = Arrays.asList(e.getMessage());        	
        	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
        			errores, 
        			null);
        }
        return response;
    }
    
    
    /**
     * Recurso @POST <em>"{contextoAplicacion}/demo/mensaje/springautowired/POST"</em>, genera un mensaje al usuario solicitado despues de acceder a el servicio de negocio asociado,
	 * <p>
	 * Recurso publico  (anotacion {@code javax.annotation.security.PermitAll} presente)
	 * Parametro:
	 * {
     * 	"idUsuario": "ADMIN"
	 * }
	 * 
	 * Respuesta
	 * {
     * 		"metadata": {
     * 			"response": "EXITO"
     *		},
     *		"data": "Hola este un mensaje ADMIN"
	 * }
	 *
     * 
     * @return  Response que contiene un objeto JSON con saludo al id solicitado si existe en la BD
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("mensaje/springautowired/POST")
    @PermitAll
    public Response mensajeSpringautowired(DemoAnotacionesMapperDTO dto) {

        Response response = null;
      
        try {
        	DemoAnotacionesMapperDTO dtoMapper = demoServiceAutowired.obtenerUsuarioByAnotaciones(dto.getIdUsuario());
        	response = ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, MENSAJE_SALUDO+dtoMapper.getIdUsuario());            
        } catch (Exception e) {
        	List<String> errores = Arrays.asList(e.getMessage());        	
        	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
        			errores, 
        			null);            
        }
        return response;
    }
    
    
    /**
     * Recurso @GET <em>"{contextoAplicacion}/demo/datatable/restringido"</em>, genera un set de datos para consumo paginado, restringidos
     * <p>
	 * Recurso restringido (requiere token de acceso valido)
     * 
     * 
	 * Respuesta error (sin token incluido).
	 *  {
     * 		"metadata": {
     *   		"errors": [
     *       		"El token no es valido"
     *   		],
     *  		"response": "ERROR"
     * 		}
	 *	}
	 * 
	 * Respuesta exito (incluye token la solicitud).
	* {
	*     "metadata": {
	*         "response": "EXITO"
	*     },
	*     "data": [
	*         {
	*             "usuario": "usuario 1",
	*             "departamento": "Economía"
	*         },
	*         ...
	*         {
	*             "usuario": "usuario 10",
	*             "departamento": "Sistemas"
	*         }
	*     ],
	*     "pagination": {
	*         "total": 12,
	*         "page": 1,
	*         "numberElementsPage": 10
	*     }
	* }
     *
     * @param registroInicial el registro con que inicia la busqueda
     * @param numeroRegistros el numero de registros que mostrara del total
     * @return El set de datos (numero de registros solicitados por length ) con la informaci&oacute;n del paginado
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("datatable/restringido")    
    public Response datatable(  @QueryParam("start") Integer registroInicial,
					            @QueryParam("length") Integer numeroRegistros) {
        
        int totalRegistros = demoServiceAutowired.obtenertotalregistrosPaginar();
        int pagina = (registroInicial / numeroRegistros)+1;
        
        List<TablaDemo> datos = demoServiceAutowired.obtenerDatosDemoPagination(registroInicial, numeroRegistros);

        PaginationDto dto = new PaginationDto();
        dto.setNumberElementsPage(datos.size());
        dto.setTotal(totalRegistros);
        dto.setPage(pagina);
        
        Response response;
        try {
        	response = ResponseJSONGenericoUtil.getRespuestaExitoPaginado(StatusResponse.OK, datos, dto); 
        } catch (RuntimeException e) {
        	List<String> errores = Arrays.asList(e.getMessage());        	
        	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
        			errores, 
        			null);            
        }
        return response;
    }

    /**
     * Recurso @GET <em>"{contextoAplicacion}/demo/datatable/publico"</em>, genera un set de datos para consumo paginado
     * <p>
	 * Recurso publico  (anotacion {@code javax.annotation.security.PermitAll} presente) 
     * 
	 * Respuesta 
	* {
	*     "metadata": {
	*         "response": "EXITO"
	*     },
	*     "data": [
	*         {
	*             "usuario": "usuario 1",
	*             "departamento": "Economía"
	*         },
	*         ...
	*         {
	*             "usuario": "usuario 10",
	*             "departamento": "Sistemas"
	*         }
	*     ],
	*     "pagination": {
	*         "total": 12,
	*         "page": 1,
	*         "numberElementsPage": 10
	*     }
	* }
     *
     * @param registroInicial el registro con que inicia la busqueda
     * @param numeroRegistros el numero de registros que mostrara del total
     * @return El set de datos (numero de registros solicitados por length ) con la informaci&oacute;n del paginado
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("datatable/publico")    
    @PermitAll
    public Response datatablePublico(  @QueryParam("start") Integer registroInicial,
					            @QueryParam("length") Integer numeroRegistros) {
    	  int totalRegistrosDisponibles = demoServiceAutowired.obtenertotalregistrosPaginar();
          int paginaConsultada = (registroInicial / numeroRegistros)+1;
          
          List<TablaDemo> registrosPaginados = demoServiceAutowired.obtenerDatosDemoPagination(registroInicial, numeroRegistros);

          PaginationDto dto = new PaginationDto();
          dto.setNumberElementsPage(registrosPaginados.size());
          dto.setTotal(totalRegistrosDisponibles);
          dto.setPage(paginaConsultada);
          
          Response response;
          try {
          	response = ResponseJSONGenericoUtil.getRespuestaExitoPaginado(StatusResponse.OK, registrosPaginados, dto); 
          } catch (IndexOutOfBoundsException e) {
          	List<String> errores = Arrays.asList(e.getMessage());        	
          	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
          			errores, 
          			null);            
          }
          return response;
    }
    
    
    /**
     * Recurso @GET <em>"{contextoAplicacion}/demo/datatable/publico/form"</em>, genera un set de datos para consumo paginado, con parametros adicionales
     * <p>
	 * Recurso publico  (anotacion {@code javax.annotation.security.PermitAll} presente) 
     * 
	 * Respuesta 
	 * {
	 *     "metadata": {
	 *         "response": "EXITO"
	 *     },
	 *     "data": [
	 *         {
	 *             "usuario": "usuario 1",
	 *             "departamento": "Economía",
	 *             "parametroUno": "pppp",
	 *             "parametroDos": "xxxxx"
	 *         },
	 *         ....
	 *         {
	 *             "usuario": "usuario 10",
	 *             "departamento": "Contaduría",
	 *             "parametroUno": "pppp",
	 *             "parametroDos": "xxxxx"
	 *         }
	 *     ],
	 *     "pagination": {
	 *         "total": 12,
	 *         "page": 1,
	 *         "numberElementsPage": 10
	 *     }
	 * }
     *
     * @param registroInicial el registro con que inicia la busqueda
     * @param numeroRegistros el numero de registros que mostrara del total
     * @param parametroUno parametro uno enviado en la peticion
     * @param parametroDos parametro dos enviado en la peticion
     * @return El set de datos (numero de registros solicitados por length ) con la informaci&oacute;n del paginado
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("datatable/publico/form")    
    @PermitAll
    public Response datatablePublicoForm(  @QueryParam("start") Integer registroInicial,
					            @QueryParam("length") Integer numeroRegistros, 
					            @QueryParam("parametroUno") String parametroUno,  
    							@QueryParam("parametroDos") String parametroDos ) {
    	
    	  int totalRegistrosForm = demoServiceAutowired.obtenertotalregistrosPaginar();
          int paginaConsultadaForm = (registroInicial / numeroRegistros)+1;
          
          List<TablaDemo> registrosPaginadosForm = demoServiceAutowired.obtenerDatosDemoPagination(registroInicial, numeroRegistros);
          
          if(StringUtils.isNotBlank(parametroUno) || StringUtils.isNotBlank(parametroDos)){
        	  for(TablaDemo demo:registrosPaginadosForm){
        		  demo.setParametroUno(parametroUno);
        		  demo.setParametroDos(parametroDos);
        	  }
          }
          
          
          PaginationDto dto = new PaginationDto();
          dto.setNumberElementsPage(registrosPaginadosForm.size());
          dto.setTotal(totalRegistrosForm);
          dto.setPage(paginaConsultadaForm);
          
          Response response;
          try {
          	response = ResponseJSONGenericoUtil.getRespuestaExitoPaginado(StatusResponse.OK, registrosPaginadosForm, dto); 
          } catch (IndexOutOfBoundsException e) {
          	List<String> errores = Arrays.asList(e.getMessage());        	
          	response = ResponseJSONGenericoUtil.getRespuestaError(StatusResponse.INTERNAL_SERVER_ERROR, 
          			errores, 
          			null);            
          }
          return response;
    }   
    
    
}
