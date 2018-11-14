/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.aplicacionconfig.listener;

import mx.gob.segob.dgtic.webservices.aplicacionconfig.exception.ConfiguracionException;
import org.jboss.resteasy.plugins.spring.SpringContextLoader;
import org.jboss.resteasy.plugins.spring.SpringContextLoaderSupport;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Listener encargado de cargar el contexto de SPRING para la realizaci&oacute;n  de inyecci&oacute;n de dependencias
 * en los recursos registrados mediante RESTEasy.
 * 
 */
public class CustomSpringContextLoaderListener  extends ContextLoaderListener{
	
	
	 /**
	  * Crea una nueva instancia de CustomSpringContextLoaderListener
 	 * 
 	 * @param context Contexto de spring 
 	 */
 	public CustomSpringContextLoaderListener(WebApplicationContext context) {
		super(context);
	 }
	
	
	 /**
 	 * Inicializa la integraci&oacute;n de los contextos de Spring y RESTEasy
 	 * 
 	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
 	 */
 	@Override
	   public void contextInitialized(ServletContextEvent event)
	   {
	      boolean scanProviders = false;
	      boolean scanResources = false;

	      String sProviders = event.getServletContext().getInitParameter("resteasy.scan.providers");
	      if (sProviders != null)
	      {
	         scanProviders = Boolean.valueOf(sProviders.trim());
	      }
	      String scanAll = event.getServletContext().getInitParameter("resteasy.scan");
	      if (scanAll != null)
	      {
	         boolean tmp = Boolean.parseBoolean(scanAll.trim());
	         scanProviders = tmp || scanProviders;
	         scanResources = tmp || scanResources;
	      }
	      String sResources = event.getServletContext().getInitParameter("resteasy.scan.resources");
	      if (sResources != null)
	      {
	         scanResources = Boolean.valueOf(sResources.trim());
	      }

	      if (scanProviders || scanResources)
	      {
	         throw new ConfiguracionException("You cannot use resteasy.scan, resteasy.scan.resources, or resteasy.scan.providers with the SpringContextLoaderLister as this may cause serious deployment errors in your application");
	      }


	      super.contextInitialized(event);
	   }

	   /**
   	 * Creates the context loader.
   	 *
   	 * @return the context loader
   	 */
   	// for Spring 2.5.x
	   protected ContextLoader createContextLoader() {
	      return new SpringContextLoader();
	   }

	   /**
   	 * The spring context loader support.
   	 */
   	// for Spring 3.x and later
	   private SpringContextLoaderSupport springContextLoaderSupport = new SpringContextLoaderSupport();

	   /* (non-Javadoc)
   	 * @see org.springframework.web.context.ContextLoader#customizeContext(javax.servlet.ServletContext, org.springframework.web.context.ConfigurableWebApplicationContext)
   	 */
	   @Override
   	protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext configurableWebApplicationContext)
	   {
	      super.customizeContext(servletContext, configurableWebApplicationContext);
	      this.springContextLoaderSupport.customizeContext(servletContext, configurableWebApplicationContext);
	   }
}
