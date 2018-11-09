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
import mx.gob.segob.dgtic.webservices.recursos.ArchivoRecurso;
import mx.gob.segob.dgtic.webservices.recursos.AsistenciaRecurso;
import mx.gob.segob.dgtic.webservices.recursos.AutenticacionRecurso;
import mx.gob.segob.dgtic.webservices.recursos.ComisionRecurso;
import mx.gob.segob.dgtic.webservices.recursos.DashBoardRecurso;
import mx.gob.segob.dgtic.webservices.recursos.DiaFestivoRecurso;
import mx.gob.segob.dgtic.webservices.recursos.EstatusRecurso;
import mx.gob.segob.dgtic.webservices.recursos.HorarioRecurso;
import mx.gob.segob.dgtic.webservices.recursos.IncidenciaRecurso;
import mx.gob.segob.dgtic.webservices.recursos.JustificacionRecurso;
import mx.gob.segob.dgtic.webservices.recursos.LicenciaMedicaRecurso;
import mx.gob.segob.dgtic.webservices.recursos.NivelOrganizacionalRecurso;
import mx.gob.segob.dgtic.webservices.recursos.PerfilRecurso;
import mx.gob.segob.dgtic.webservices.recursos.PeriodoRecurso;
import mx.gob.segob.dgtic.webservices.recursos.PermisoRecurso;
import mx.gob.segob.dgtic.webservices.recursos.TipoDiaRecurso;
import mx.gob.segob.dgtic.webservices.recursos.UnidadAdministrativaRecurso;
import mx.gob.segob.dgtic.webservices.recursos.UsuarioPerfilRecurso;
import mx.gob.segob.dgtic.webservices.recursos.UsuarioRecurso;
import mx.gob.segob.dgtic.webservices.recursos.VacacionPeriodoRecurso;

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
        singletons.add(new AutenticacionRecurso());
        singletons.add(new ArchivoRecurso());
        singletons.add(new AsistenciaRecurso());
        singletons.add(new ComisionRecurso());
        singletons.add(new DashBoardRecurso());
        singletons.add(new DiaFestivoRecurso());
        singletons.add(new EstatusRecurso());
        singletons.add(new HorarioRecurso());
        singletons.add(new IncidenciaRecurso());
        singletons.add(new JustificacionRecurso());
        singletons.add(new LicenciaMedicaRecurso());
        singletons.add(new NivelOrganizacionalRecurso());
        singletons.add(new PerfilRecurso());
        singletons.add(new PeriodoRecurso());
        singletons.add(new PermisoRecurso());
        singletons.add(new TipoDiaRecurso());
        singletons.add(new UnidadAdministrativaRecurso());
        singletons.add(new UsuarioPerfilRecurso());
        singletons.add(new UsuarioRecurso());
        singletons.add(new VacacionPeriodoRecurso());
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
