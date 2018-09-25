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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filtro AntiSql
 */
public class AntiSqlFilter implements Filter {
	
    /**
     * Intancia para realizar log.
     */
	private Logger logger = LoggerFactory.getLogger(AntiSqlFilter.class);
	

	/**
	 * Patron Cross-site Scripting (XSS) a evaluar 
	 */
	private Pattern xsspattern;

	/**
	 * Configuraci&oacute;n del filtro
	 */
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;
	
	/**
	 * Invocado por el contenedor web para indicar a un filtro que ha sido detenido
	 */
	@Override
	public void destroy() {
		logger.info("Destroy "+getClass().getCanonicalName());
		this.filterConfig = null;
	}

	/**
	 * Evalua los par&aacute;metros que componen una petici&oacute;n para identificar si contienen inyecci&oacute;n de SQL.
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		boolean isInjection = false;
		int i;
		request.getHeaderNames();
		for(Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			for (i = 0; i < entry.getValue().length; i++) {
				isInjection = contieneSQL(entry.getValue()[i]);
				if(isInjection) {
					break;
				}
			}
			if(isInjection) {
				break;
			}
		}
		if(isInjection) {
			response.sendError(400);
		} else {
			chain.doFilter(req, res);
		}
	}

	/**
	 * Inicializa el patr&oacute;n a evaluar para evitar la inyecci&oacute;n de SQL.
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String regexBuilder = "ALTER|CREATE|DELETE|DROP|EXEC|INSERT|MERGE|SELECT|UPDATE|GRANT|COLUMN";
		xsspattern = Pattern.compile(regexBuilder);
		this.filterConfig = filterConfig;

		logger.info("Inicializando "+getClass().getCanonicalName());
	}

	/**
	 * Eval&uacute;a si el valor de un par&aacute;metro cumple con el patr&oacute;n que identifica inyecci&oacute;n de SQL.
	 *
	 * @param valor El valor del parametro a evaluar que es contenido en una peticion
	 * @return true, si el valor contiene inyecci&oacute;n de SQL
	 */
	private boolean contieneSQL(String valor) {
		boolean contienSql = false;
		String valorParametro = valor.toUpperCase();
		Matcher match = xsspattern.matcher(valorParametro);
		if(match.find()) {
			contienSql =  true;
			logger.debug("Contiene sql injection");
		}
		return contienSql;
	}
	
}
