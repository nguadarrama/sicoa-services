/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.aplicacionconfig.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro CORS.
 */
public class CorsFilter implements Filter {

	/**
	 * Intancia para realizar log.
	 */
	private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	/**
	 * Configuraci&oacute;n del filtro
	 */
	@SuppressWarnings("unused")
	private FilterConfig filterConfig;

	/**
	 *  Constante que representa el atributo HEADERS_ALLOW, representa los par&aacute;metros permitidos por cada petici&oacute;n.
	 */
	private static final String HEADERS_ALLOW = "Origin,Access-Control-Allow-Origin,Accept, x-requested-with, X-Auth-Token, Content-Type";
	
	/**
	 * Constante que representa el atributo METHODS_ALLOW, representa los m&eacute;todos permitidos por cada petici&oacute;n.	 * 
	 */
	private static final String METHODS_ALLOW = "GET, POST, PUT, DELETE, OPTIONS";

	/**
	 * Integra los headers necesarios para permitir las peticiones CORS (Intercambio de Recursos de Origen Cruzado) a los servicios REST
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  
	  	HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", METHODS_ALLOW);
		httpResponse.setHeader("Access-Control-Allow-Headers", HEADERS_ALLOW);
		httpResponse.setHeader("Access-Control-Max-Age", "3600");

		logger.debug("Peticion: {} ",((HttpServletRequest) request).getMethod());
		logger.debug("Recurso: {} ",((HttpServletRequest) request).getPathInfo());
		logger.debug("ip: {}",((HttpServletRequest) request).getRemoteHost());

		chain.doFilter(request, response);
	}

	
	/**
	 * Invocado por el contenedor web para indicar a un filtro que ha sido detenido
	 */
	@Override
	public void destroy() {
		logger.info("destroy: {} ",getClass().getCanonicalName());
	}

	/**
	 * Inicializa la configuracion del filtro.
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		logger.info("=========>>>>> Inicializando Filtro CORS: {} ",getClass().getCanonicalName());
		logger.info("Metodos permitidos : "+METHODS_ALLOW);
		logger.info("Headers permitidos : "+HEADERS_ALLOW);
		this.filterConfig = filterConfig;
	}

}
