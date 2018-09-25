/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.aplicacionconfig.config.spring;

import mx.gob.segob.dgtic.business.config.BusinessConfig;
import mx.gob.segob.dgtic.persistence.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configura y establece la capa de los recursos (API REST) dentro del contexto de SPRING, 
 * delimitando las clases de esta capa al paquete <em>{@code mx.gob.segob.dgtic.webservices}</em>
 * 
 * <p>La clase de inicializaci&oacute;n {@link mx.gob.segob.dgtic.ApplicationInit} detectara este componente 
 * como una clase de configuraci&oacute;n mediante la anotaci&oacute;n {@link org.springframework.context.annotation.Configuration @Configuration} 
 * y por  medio de la anotaci&oacute;n {@link org.springframework.context.annotation.ComponentScan @ComponentScan} se establece la 
 * ubicaci&oacute;n del paquete  que contendr&aacute; las clases a detectar para la  capa de los recursos.
 *
 *	<p>Se integra la configuraci&oacute;n de las capas de negocio y de persistencia
 *
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @see mx.gob.segob.dgtic.business.config.BusinessConfig
 * @see mx.gob.segob.dgtic.persistence.config.PersistenceConfig
 */
@Configuration
@ComponentScan(basePackages = { "mx.gob.segob.dgtic.webservices" })
@Import({BusinessConfig.class, PersistenceConfig.class})
public class SpringConfiguration {

}
