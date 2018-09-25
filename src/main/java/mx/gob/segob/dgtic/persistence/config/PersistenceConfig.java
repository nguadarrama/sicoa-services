/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.persistence.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Configura y establece la capa de persistencia dentro del contexto de SPRING,
 * delimitando las clases de esta capa al paquete <em>{@code mx.gob.segob.dgtic.persistence}</em>
 * 
 * <p>La clase de inicializaci&oacute;n {@link mx.gob.segob.dgtic.ApplicationInit} detectara este componente 
 * como una clase de configuraci&oacute;n mediante la anotaci&oacute;n {@link org.springframework.context.annotation.Configuration @Configuration} 
 * y por  medio de la anotaci&oacute;n {@link org.springframework.context.annotation.ComponentScan @ComponentScan} se establece la 
 * ubicaci&oacute;n del paquete  que contendr&aacute; las clases a detectar para la capa de persistencia.
 * 
 * <p>Se activa el control transaccional para el proceso de persistencia de informaci&oacute;n por medio de SPRING y del uso 
 * de la anotaci&oacute;n {@link org.springframework.transaction.annotation.Transactional @Transactional}
 * <p>
 * El acceso a la base de datos se controla por medio de un pool de conexiones configurado desde el servidor de aplicaciones y 
 * se accede desde un JNDI Datasource. El nombre del JNDI se puede modifica desde la variable 
 * de propiedades de configuraci&oacute;n de la aplicaci&oacute;n ({@code aplicacion.properties} : {@code jndi.datasource})
 * 
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.transaction.annotation.EnableTransactionManagement
 */
@Configuration
@ComponentScan(basePackages = { "mx.gob.segob.dgtic.persistence" })
@PropertySource("classpath:config/properties/aplicacion.properties")
@EnableTransactionManagement
@EnableScheduling
public class PersistenceConfig {

    /**
     * JNDI del pool de conexiones configurado en el servidor de aplicaciones. 
     * El nombre de la variable se puede alterar desde las propiedades de configuraci&oacute;n 
     * de la aplicaci&oacute;n ({@code aplicacion.properties} : {@code jndi.datasource}) 
     */
    @Value("${jndi.datasource}")
    private String jndiDataSource;
    
    @Value("${jndi.datasource.sqlserver}")
    private String jndiDataSourceSqlServer;
    
    @Value("${jndi.datasource.oracle}")           
    private String jndiDataSourceOracle;

    /**
     * Registra el objeto datasource en el contexto de SPRING realizando la inspecci&oacute;n del JNDI en el servidor de aplicaciones
     *
     * @return El datasource configurado
     * @throws NamingException Excepcion lanzada de no encontrar el JNDI registrado
     * 
     * 
	 * @see org.springframework.context.annotation.Bean
     */
    @Bean
    public DataSource dataSource() throws NamingException {
    	final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(jndiDataSource);
    }
    
    @Bean(name = "sqlServer")
    public DataSource dataSourceSqlServer() throws NamingException {
    	final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(jndiDataSourceSqlServer);
    }
    
    @Bean(name = "oracle")
    public DataSource dataSourceOracle() throws NamingException {
    	final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(jndiDataSourceOracle);
    }

    /**
     * Registra el objeto encargado de gestionar las transacciones durante la persistencia de informaci&oacute;n y 
     * mediante el uso de la anotaci&oacute;n {@link org.springframework.transaction.annotation.Transactional @Transactional} 
     *
     * @return El gestor de transacciones
     * @throws NamingException Excepcion lanzada de no encontrar el JNDI registrado del datasource
     * 
	 * @see org.springframework.context.annotation.Bean
     */
    @Bean
    public PlatformTransactionManager txManager() throws NamingException {
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
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
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
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    
    @Bean("jdbcTemplateSqlServer")
    public JdbcTemplate jdbcTemplateSqlServer(@Qualifier("sqlServer") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    
    @Bean("jdbcTemplateOracle")
    public JdbcTemplate jdbcTemplateOracle(@Qualifier("oracle") DataSource dataSource){
        return new JdbcTemplate(dataSource);
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
    public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
