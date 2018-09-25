/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.mapper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  Definici&oacute;n de la anotaci&oacute;n utilizada en los atributos de un dto 
 *  para el proceso de obtenci&oacute;n de informaci&oacute;n mediante un rowmapper gen&eacute;rico {@link mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper}.
 *  
 *  <p>
 *  Ejemplo.
 *  Se mapea de al siguiente manera
 *  <pre class="code">
 *  	public class EjemploMapper {
 *  		{@code @MapeaColumna}(columna = "ID_M_USUARIO") private Integer idUsuario;
 *      	{@code @MapeaColumna}(columna = "USUARIO")     private String nombre;
 *  		...
 *  	}   
 *  </pre>
 *  
 *  Se usa el rowmapper de la siguiente manera
 *  <pre class="code">  
 *  	StringBuilder qry = new StringBuilder();
 *      qry.append(" SELECT ID_M_USUARIO, USUARIO FROM M_USUARIO WHERE USUARIO = :usuarioNombre ");
 *      
 *      MapSqlParameterSource parametros = new MapSqlParameterSource();
 *      parametros.addValue("usuarioNombre", nombreUsuario);
 *      
 *      jdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<>(EjemploMapper.class));
 *  </pre>
 *  
 *  @see mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface MapeaColumna {
	
	/**
	 * Columna que se asociara a el atributo de la clase.
	 * <p>
	 * Nombre de la columna en el query que se asociara a el atributo de la clase
	 * <pre class="code">
	 * 		{@code @MapeaColumna}(columna = "ID_M_USUARIO") private Integer idUsuario;
	 *  </pre>
	 *  
	 * @return La columna que se mapea
	 */
	String columna() default "";
}
