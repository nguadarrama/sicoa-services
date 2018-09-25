/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *  Clase de apoyo para poder leer los atributos de variables de entorno configuradas en el servidor de aplicaciones.
 *  <p>
 *  Se podr&aacute;n acceder a variables de entorno configuradas en el archivo "standalone.xml", por medio de JNDI's globales.
 *  <p>
 * Ejemplo
 * <pre class="code">
 * 
 *   String valorConfigurado = JndiResourceUtil.lookupResourceServer("java:global/web.jndi.path-file-btxt");
 * 
 * </pre>
 */
public class JndiResourceUtil {

	/**
	 * Constructor de utiler&iacute;a para acceso completamente est&aacute;tico
	 */
	private JndiResourceUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Inspecciona el contexto del servidor de aplicaciones en busca de la variable de entorno buscada.
	 *
	 * @param resource El recurso (variable de entorno) a buscar en el servidor de aplicaciones.
	 * @return El valor configurado
	 */
	public static String lookupResourceServer(String resource) {
		 String valorRecurso = null; 
		 try {
		        Context ctx = new InitialContext();
		        valorRecurso = (String) ctx.lookup(resource);
		    } catch(NamingException ne) {
		        valorRecurso = null;
		    }
		 return valorRecurso;
	}
}
