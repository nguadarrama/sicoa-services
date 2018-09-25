/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices;


import mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.provider.exception.ErrorRecursoMapperProvider;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.provider.interceptor.AuditoriaInterceptor;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.provider.interceptor.TokenSecurityInterceptorProvider;
import mx.gob.segob.dgtic.webservices.recursos.AutenticacionRecurso;
import mx.gob.segob.dgtic.webservices.recursos.DemoRecurso;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Registro de aplicacion REST
 * <p>
 * Se identifica la raiz de recursos REST-Api a "/".
 *
 * @see javax.ws.rs.core.Application
 */
@ApplicationPath("/")
public class RestWSAplicacion extends Application{
    
	/**
	 * Recursos y Proveedores que identifica RestEasy,
	 * <p>
	 * Los recursos se generan bajo el patr&oacute;n singleton.
	 */
	private Set<Object> singletons = new HashSet<>(0);

    /**
     * Instancia una aplicacion REST
     */
    public RestWSAplicacion() {    	
        registraRecursos();
        registraProviders();
    }
    
    /**
     * Punto se registran los recursos a exponer
     * <p>
     * En este punto se deben integrar todos los recursos que se deban exponer como un api REST en el aplicativo
     */
    private void registraRecursos(){
    	
        singletons.add(new DemoRecurso());        
        singletons.add(new AutenticacionRecurso());
        
        
    }

    
    /**
     * Punto donde se registran las implementaciones de los proveedores a identificar por RestEasy.
     * <p>
     * Este es el encargado de registrar las implementaciones de proveedores personalizados (Seguridad, Manejo errores, Conversi&oacute;n, etc)
     * 
     */
    private void registraProviders(){    	
    	//Interceptor que personaliza la respuesta de errores
    	singletons.add(new ErrorRecursoMapperProvider());
    	//Interceptor de seguridad para prevenir el consumo de recursos restringidos
        singletons.add(new TokenSecurityInterceptorProvider());
    	//Interceptor encargado de registrar el consumo de recursos identificados por la anotacion Auditable
        singletons.add(new AuditoriaInterceptor());
    }   
    
    
    /**
     * Obtiene los recursos API, y los proveedores registrados para el aplicativo. 
     * 
     */
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
