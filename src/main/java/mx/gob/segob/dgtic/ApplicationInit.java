/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic;


import mx.gob.segob.dgtic.webservices.aplicacionconfig.config.spring.SpringConfiguration;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.filter.AntiSqlFilter;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.filter.CorsFilter;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.filter.EncodingFilter;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.listener.CustomSpringContextLoaderListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


/**
 * Clase encargada de inicializar los WebService's dentro del servidor de aplicaciones.
 * 
 * 	- Levanta contexto de SPRING para su fabrica de beans y la inyecci&oacute;n de dependencias
 * 	- Registra la integraci&oacute;n de las apis REST con SPRING.
 *  - Inicializa los filtros del aplicativo.
 *  
 * Esta clase es la encargada de generar las configuraciones que se realizaban mediante el archivo web.xml
 * 
 * @see org.springframework.web.context.WebApplicationContext
 */
public class ApplicationInit implements WebApplicationInitializer  {

    /**
     * Intancia para realizar log.
     */
	Logger logger = LoggerFactory.getLogger(ApplicationInit.class);
	/**
	 * Configura la aplicaci&oacute;n web con los filtros, listener's, par&aacute;metros de contexto y atributos 
	 * necesarios para su ejecuci&oacute;n.
	 * 
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
			logger.info("==========>>>> Iniciando servicios .....");
		
			WebApplicationContext rootContext = configuraSpring();
			configuraResteasy(servletContext, rootContext);
	        agregaFiltroAntiSql(servletContext);
	        agregaFiltroEncoding(servletContext);	        
	        agregaFiltroCORS(servletContext);
	}
	
	/**
	 * Configura el contexto de SPRING.
	 * <p>
	 * Prepara el contexto de SPRING, por medio del registro de beans de configuraci&oacute;n, inyecci&oacute;n de dependencias y
	 * la inspecci&oacute;n de sus anotaciones. 
	 *
	 * @return El contexto del aplicativo web.
	 */
	private WebApplicationContext configuraSpring(){
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	  	rootContext.register(SpringConfiguration.class);
	  	return rootContext;
	}
	
	/**
	 * Configura resteasy.
	 * <p>
	 * Realiza la configuraci&oacute;n de resteasy, integr&aacute;ndolo con la inyecci&oacute;n de dependencias de SPRING.
	 * <p>
	 * Configura el contexto a la ra&iacute;z <b>/</b>, a partir de este se responder&aacute;n los servicios REST 
	 *  <pre class="code">
	 *  	ServletRegistration.Dynamic servlet = context.addServlet("RESTEasyService", new HttpServletDispatcher());
	 *  	servlet.addMapping("/*");
	 *  </pre>
	 *
	 * @param context El contexto del aplicativo
	 * @param rootContext Contexto de SPRING para integrar la inyecci&oacute;n de dependencias
	 */
	private void configuraResteasy(ServletContext context, WebApplicationContext rootContext){
	  	//Inicia configuracion de spring-resteasy
	    //Levanta la configuracion de resteasy para integrarlo naturalmente a spring
	  	context.addListener(new ResteasyBootstrap());
	  	context.addListener(new CustomSpringContextLoaderListener(rootContext));		
	    
	  	//Registra el contexto de los webservices a la raiz
        ServletRegistration.Dynamic servlet = context.addServlet("RESTEasyService", new HttpServletDispatcher());
        servlet.addMapping("/*");
	}
	
	/**
	 * Agrega filtro CORS.
	 * <p>
	 * Filtro que permite el consumo por medio de los encabezados CORS
	 *
	 * @param context the context
	 */
	//Filtro que habilita el control CORS
	private void agregaFiltroCORS(ServletContext context) {       
        FilterRegistration.Dynamic corsFilter = context.addFilter("CorsFilter", CorsFilter.class);
        corsFilter.setInitParameter("encoding", "UTF-8");
        corsFilter.setInitParameter("forceEncoding", "true");
        corsFilter.addMappingForUrlPatterns(null, false, "/*");
	}
	
	/**
	 * Agrega filtro encoding.
	 * <p>
	 * Filtro que integra el encoding a toda petici&oacute;n a UTF-8 si no se define un encoding
	 *
	 *
	 * @param context the context
	 */
	 private void agregaFiltroEncoding(ServletContext context) {       
        FilterRegistration.Dynamic encodingFilter = context.addFilter("EncodingFilter", EncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.setInitParameter("allowedMethods", "GET,POST,PUT,DELETE,HEAD,OPTIONS");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");        
	}
	
	/**
	 * Agrega filtro anti sql.
	 * <p>
	 * Filtro que previene la inyecci&oacute;n simple de sql.
	 *
	 * @param context the context
	 */
	private void agregaFiltroAntiSql(ServletContext context) {       
        FilterRegistration.Dynamic antiSqlFilter = context.addFilter("AntiSqlFilter", AntiSqlFilter.class);
        antiSqlFilter.addMappingForUrlPatterns(null, false, "/*");        
	}
	
}
