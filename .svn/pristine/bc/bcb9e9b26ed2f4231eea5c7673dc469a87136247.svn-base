/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Clase de configuraci&oacute;n para el contexto de spring requerido para la ejecuci&oacute;n de pruebas unitarias.
 * <p>
 * Se cargan la configuraci&oacute;n de las capas de negocio y persistencia.  
 * 
 */
@Configuration
@ComponentScan(basePackages = { "mx.gob.segob.dgtic.business", "mx.gob.segob.dgtic.persistence" })
@PropertySource("classpath:/testConfig/test.properties")
@EnableTransactionManagement
public class TestAppConfig {

    /**
     * Url donde se ubica el motor de BD al cual se conectara
     */
    @Value("${jdbc.test.datasource.url}") private String url;
    
    /**
     * El usuario de BD
     */
    @Value("${jdbc.test.datasource.user}") private String user;
    
    /**
     * La palabra secreta con que identifica el usuario en la bd
     */
    @Value("${jdbc.test.datasource.secreto}") private String secreto;
    
    /**
     * The driver a utilizar
     */
    @Value("${jdbc.test.datasource.driver}") private String driverClassName;



    /**
     * Registra el objeto datasource en el contexto de SPRING por medio de una conexi&oacute;n directa para la ejecuci&oacute;n de la prueba unitaria
     *
     * @return El datasource configurado
     * @throws ClassNotFoundException Excepcion lanzada de no encontrar el classdriver
     * 
     * 
	 * @see org.springframework.context.annotation.Bean
     */
    @SuppressWarnings("unchecked")
	@Bean
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass((Class<Driver>) Class.forName(driverClassName));
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(secreto);
        return ds;
    }

    /**
     * Registra el objeto encargado de gestionar las transacciones durante la persistencia de informaci&oacute;n y 
     * mediante el uso de la anotaci&oacute;n {@link org.springframework.transaction.annotation.Transactional @Transactional} 
     *
     * @return El gestor de transacciones
     * @throws ClassNotFoundException Excepcion lanzada de no encontrar el datasource
     * 
	 * @see org.springframework.context.annotation.Bean
     */
    @Bean("txManager")
    public PlatformTransactionManager txManagerTest() throws ClassNotFoundException {
        return new DataSourceTransactionManager(dataSource());
    }


    /**
     * Bean requerido para la inspecci&oacute;n de una propiedad mediante la anotacion @Value del archivo properties cargado mediante @PropertySource
     *
     * @return PropertySourcesPlaceholderConfigurer
     * 
	 * @see org.springframework.context.annotation.Bean
     */
    @Bean("propertiesPersistence")
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDevTest() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Se registra el Jdbc template simple con el cual se podr&aacute; realizar operaciones hacia la BD desde los repositorios de informaci&oacute;n
     * <p>
     * Este objeto se podr&aacute; acceder desde cualquier repositorio de informaci&oacute;n mediante la inyecci&oacute;n de dependencias
     *
     * @param dataSource El datasource
     * @return El jdbc template simple
     * 
	 * @see org.springframework.context.annotation.Bean
	 * @see org.springframework.jdbc.core.JdbcTemplate
     */
    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplateTest(DataSource dataSource){
        return  new JdbcTemplate(dataSource);
    }

    /**
     * Se registra el Jdbc template de enlace de nombres con el cual se podr&aacute; realizar operaciones hacia la BD desde los repositorios de informaci&oacute;n.
     *
     * <p>
     * El enlace de variables en este template se permite por medio de nombres
     *  
     * <pre class="code">     
     * 		MapSqlParameterSource parametros = new MapSqlParameterSource();
	 * 		parametros.addValue("id", 1);	      
	 * </pre> 
     * 
     * <p>
     * Este objeto se podr&aacute; acceder desde cualquier repositorio de informaci&oacute;n mediante la inyecci&oacute;n de dependencias
     * 
     * 
     *
     * @param dataSource El datasource
     * @return El jdbc template de enlace de nombres
     * 
	 * @see org.springframework.context.annotation.Bean
	 * @see org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
     */
    @Bean("namedJdbcTemplate")
    public NamedParameterJdbcTemplate namedJdbcTemplateTest(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
