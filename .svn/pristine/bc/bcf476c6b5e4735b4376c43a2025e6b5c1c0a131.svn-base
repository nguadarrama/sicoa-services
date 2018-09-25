/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Definici&oacute;n para la anotaci&oacute;n utilizada para el proceso de bitacorizar la invocaci&oacute;n de recursos
 * 
 * <p>
 * La anotaci&oacute;n es a nivel metodo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {
	
	/**
	 * Modulo bitacorizado
	 * <p>
	 * Valor que identificar el modulo que identifica el recurso que sera bitacorizado
	 * 
	 * @return the string
	 */
	String modulo();
	
	/**
	 * Guardar parametros entrada.
	 * <p>
	 * Bandera que indicara si se deben guardar los par&aacute;metros con que se realiz&oacute; la petici&oacute;n del recurso.
	 *
	 * @return true, si se debe bitacorizar los par&aacute;metros de entrada.
	 */
	boolean guardarParametrosEntrada() default false;
	
	/**
	 * Guardar salida.
	 * <p>
	 * Bandera que indicara si se deben guardar los par&aacute;metros de respuesta.
	 *
	 * @return true, si se debe bitacorizar los par&aacute;metros de salida.
	 */
	boolean guardarSalida() default false;
}
