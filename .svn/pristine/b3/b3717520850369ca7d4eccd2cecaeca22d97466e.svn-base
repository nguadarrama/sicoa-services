/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.mapper.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 *  Definici&oacute;n de la anotaci&oacute;n utilizada en los atributos de un dto para bajar a un nivel de profundida en objetos anidados
 *  y realizar el proceso de obtenci&oacute;n de informaci&oacute;n mediante un rowmapper gen&eacute;rico {@link mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper}.
 *  
 *  <p>
 *  Ejemplo.
 *  Se mapea de al siguiente manera
 *  <pre class="code"> *  
 *  	public class EjemploPadreMapper {
 *  		{@code @MapeaColumnasInternas}(columnas={"DESCRIPCION_PERFIL", "ID_C_PERFIL"}) private EjemploHijoMapper child;      	
 *  		...
 *  	}   
 *  
 *   	public class EjemploHijoMapper {
 *  		{@code @MapeaColumna}(columna = "ID_C_PERFIL") 			private String idPerfil;
 *  		{@code @MapeaColumna}(columna = "DESCRIPCION_PERFIL") 	private String descripcionPerfil;  		
 *  		...
 *  	}   
 *  </pre>
 *  
 *  Se usa el rowmapper de la siguiente manera
 *  <pre class="code">  
 *  	StringBuilder qry = new StringBuilder();
 *      qry.append(" SELECT c.ID_C_PERFIL, c.DESCRIPCION DESCRIPCION_PERFIL ");      
 *      qry.append(" FROM M_USUARIO a ");
 *      qry.append(" INNER JOIN D_USUARIO_PERFIL b ON b.ID_M_USUARIO = a.ID_M_USUARIO  ");
 *      qry.append(" INNER JOIN C_PERFIL c ON c.ID_C_PERFIL = b.ID_C_PERFIL  ");
 *      ...        
 *      
 *      MapSqlParameterSource parametros = new MapSqlParameterSource();
 *      
 *      jdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<>(EjemploPadreMapper.class));
 *  </pre>
 *  
 *  @see mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface MapeaColumnasInternas {
	
	/**
	 * Columnas que me asociaran a el objeto interno para inspeccion.
	 * <p>
	 * Nombre de la columna en el query que se asociara al objeto para su inspecci&oacute;n interna
	 * <pre class="code">
	 * 		{@code @MapeaColumnasInternas}(columnas={"DESCRIPCION_PERFIL", "ID_C_PERFIL"}) private EjemploHijoMapper child; 
	 *  </pre>
	 *  
	 * @return La columnas internas que se configuran
	 */
	String[] columnas() default "";	
}
