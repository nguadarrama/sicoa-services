/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.provider.exception;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.MethodNotAllowedException;
import org.jboss.resteasy.spi.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;


/**
 * Proveedor encargado de generar una respuesta JSON en formato gen&eacute;rico para los error ocurrido por los recursos APIRest.
 */
@Provider
public class ErrorRecursoMapperProvider implements ExceptionMapper<Exception> {

	/**
	 * Intancia para realizar log.
	 */
	private static Logger logger = LoggerFactory.getLogger(ErrorRecursoMapperProvider.class); 
	
	
    /**
     * Encargado de convertir una excepci&oacute;n lanzada en un recurso a una respuesta en formato JSON. 
     * <p>
	 * Ejemplo
	 * <pre class="code">
	 *   {  "metadata":{
	 *   		"errors":[ "El token no es valido" ],
	 *   		"response": "ERROR"
	 *   	}
	 *   }
	 * </pre> 
     * 
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(Exception exception) {

    	logger.error(exception.getMessage(), exception);

        Response response = null;

        StatusResponse status = null;
        if(exception instanceof NotFoundException ){
            status = StatusResponse.NOT_FOUND;           
        }  else if(exception instanceof BadRequestException) {
            status = StatusResponse.BAD_REQUEST;            
        }  else if (exception instanceof MethodNotAllowedException){
        	status = StatusResponse.METHOD_NOT_ALLOWED;            
        } else {
            status = StatusResponse.INTERNAL_SERVER_ERROR;
        }
        
        List<String> errores = obtenerMensajesError(exception);
        
        response = ResponseJSONGenericoUtil.getRespuestaError(status, 
        														errores, 
        														null);
        
        return response;
    }
    
    
    /**
     * Genera una lista de errores de la excepci&oacute;n que fue lanzada
     * 
     * @param exception Excepci&oacute;n que se genero dentro del recurso
     * @return lista de errores de la excepci&oacute;n
     */
    public List<String> obtenerMensajesError(Exception exception){
    	 List<String> errores = new ArrayList<>(0);
    	
    	 String classException = exception.getClass().getName();
         String mensaje = exception.getMessage();
         if(StringUtils.isEmpty(mensaje) && exception.getCause() != null){
         	mensaje = exception.getCause().getMessage();	
         } else if( StringUtils.isEmpty(mensaje)) {
         	mensaje = exception.toString();
         }
         
         String error = classException.concat(":").concat(mensaje); 
         errores.add(error);
         
         return errores;
    }
}
