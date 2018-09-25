/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import mx.gob.segob.dgtic.comun.util.spring.SpringBeanFactoryUtil;




/**
 * Configura y establece la capa de negocio dentro del contexto de SPRING, 
 * delimitando las clases de esta capa al paquete <em>{@code mx.gob.segob.dgtic.business}</em>
 * 
 * <p>La clase de inicializaci&oacute;n {@link mx.gob.segob.dgtic.ApplicationInit} detectara este componente 
 * como una clase de configuraci&oacute;n mediante la anotaci&oacute;n {@link org.springframework.context.annotation.Configuration @Configuration} 
 * y por  medio de la anotaci&oacute;n {@link org.springframework.context.annotation.ComponentScan @ComponentScan} se establece la 
 * ubicaci&oacute;n del paquete  que contendr&aacute; las clases a detectar para la capa de negocio.
 *
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 */

@Configuration
@ComponentScan(basePackages = { "mx.gob.segob.dgtic.business" })
public class BusinessConfig {
	  
	/**
	 * 
	 * Registra la clase de apoyo para poder acceder a la f&aacute;brica de bean's de SPRING, fuera de su contexto.
	 *  
	 * <p>
	 * Ejemplo donde se utiliza el componente estatico para acceder a la fabrica de beans. Para mas referencia consultar {@link mx.gob.segob.dgtic.comun.util.spring.SpringBeanFactoryUtil} 
	 * <pre class="code">	 	
	 *     public MyBean obtenerMyBean() {
	 *         MyBean obj = SpringBeanFactoryUtil.getBeanFactory().getBean(MyBean.class);
	 *         return obj;
	 *     }
	 * </pre>
	 * <p> <b><i>Nota: </i></b> La clase permite acceso a objetos de SPRING fuera de su contexto sin necesidad de realizar 
	 * la inyecci&oacute;n de dependencias. <b>Solo se debe usar fuera del contexto de SPRING</b>
	 * 
	 * 
	 * @return  Utiler&iacute;a que proporciona la f&aacute;brica de bean's de SPRING
	 * 
	 * @see org.springframework.context.annotation.Bean
	 * @see mx.gob.segob.dgtic.comun.util.spring.SpringBeanFactoryUtil
	 */
	@Bean
	public SpringBeanFactoryUtil springBeanFactoryUtil(){
		return new SpringBeanFactoryUtil();
	}
}
