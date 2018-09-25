/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.test.webservice.autenticacion;

import mx.gob.segob.dgtic.business.service.AutenticacionService;
import mx.gob.segob.dgtic.comun.exception.ReglaNegocioException;
import mx.gob.segob.dgtic.comun.transport.dto.autenticacion.UsuarioAcceso;
import mx.gob.segob.dgtic.comun.util.config.AplicacionPropertiesUtil;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.TokenConstants;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;
import mx.gob.segob.dgtic.webservices.aplicacionconfig.exception.ConfiguracionException;

import javax.naming.NamingException;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;



/**
 * Pruebas unitarias para el proceso de autenticaci&oacute;n.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestAppConfig.class})
@Transactional*/
public class AutenticacionTest {
	
		/**
		 * The Constant logger.
		 */
		private static final Logger logger = LoggerFactory.getLogger(AutenticacionTest.class); 

		/**
		 * The Constant JNDI_WEB_SERVICE_JNDI_PROPERTY_NAME.
		 */
		private static final String JNDI_WEB_SERVICE_JNDI_PROPERTY_NAME =  "java:global/web.jndi.enviroment-host";
		
		/**
		 * The Constant TIEMPO_SEGUNDOS_VIDA_TOKEN_AUTENTICACION.
		 */
		private static final String TIEMPO_SEGUNDOS_VIDA_TOKEN_AUTENTICACION =  "60";
		
		/**
		 * The Constant TIEMPO_SEGUNDOS_VIDA_TOKEN_ACCESO.
		 */
		private static final String TIEMPO_SEGUNDOS_VIDA_TOKEN_ACCESO =  "120";		
		
		/**
		 * The Constant PALABRA_CLAVE_ENCRIPTACION.
		 */
		private static final String PALABRA_CLAVE_ENCRIPTACION =  "s3cr3t";
		
		/**
		 * The Constant AUTENTICACION_MINUTOS_BLOQUEADO.
		 */
		private static final String AUTENTICACION_MINUTOS_BLOQUEADO = "10";
		
		/**
		 * The Constant AUT_INTENTOS_ACCESO_PERMITIDO.
		 */
		private static final String AUT_INTENTOS_ACCESO_PERMITIDO = "3";
		
		/**
		 * The Constant AUTENTICACION_MULTISESION.
		 */
		private static final String AUTENTICACION_MULTISESION = "true";
	
		/**
		 * The service.
		 */
		@Autowired
		private AutenticacionService service;
	
		/**
		 * Inicializa los atributos de aplicativo.
		 * <p>
		 * Se asignan los par&aacute;metros de configuraci&oacute;n requeridos para la ejecuci&oacute;n de las pruebas unitarias donde se requiere la generaci&oacute;n del token de seguridad.
		 * - Par&aacute;metros de configuraci&oacute;n que se cargan del application.properties
		 * - Variables de contexto que se cargan del servidor de aplicaciones
		 */
		@Before
		public void init() {
			//Agrego propiedades de configuracion para configuracion del token
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad(TokenConstants.JNDI_HOST_WS_PROPERTY_NAME, JNDI_WEB_SERVICE_JNDI_PROPERTY_NAME);
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad(TokenConstants.TOKEN_AUTENTICACION_EXPIRA_PROPERTY_NAME, TIEMPO_SEGUNDOS_VIDA_TOKEN_AUTENTICACION);
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad(TokenConstants.TOKEN_KEY_SECRET_PROPERTY_NAME, PALABRA_CLAVE_ENCRIPTACION);
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad(TokenConstants.TOKEN_ACCESO_EXPIRA_PROPERTY_NAME, TIEMPO_SEGUNDOS_VIDA_TOKEN_ACCESO);
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad("autenticacion.minutos_bloqueo", AUTENTICACION_MINUTOS_BLOQUEADO);
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad("usario.numero_intentos_bloque", AUT_INTENTOS_ACCESO_PERMITIDO);
			AplicacionPropertiesUtil.getPropiedades().agregarPropiedad("autenticacion.multiple_sesion", AUTENTICACION_MULTISESION);
			
			//Integro variables de entorno del servidor para configuracion del token
			SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
			builder.bind("java:global/web.jndi.enviroment-host", "http://localhost:8080/");
			try {
				builder.activate();
			} catch (IllegalStateException | NamingException e) {
				throw new ConfiguracionException(e.getMessage(), e);
			}
		}
		
		/**
		 * Prueba de autenticaci&oacute;n correcta usuario.
		 */
		//@Test
	    public void autenticacionCorrectaUsuario() {
			String usuario = "ADMIN";
			String contrasenia = "password";
			try {
				String headerTokenAcceso =getTokenAcceso(usuario, contrasenia);
				
				UsuarioAcceso usuarioAcceso = service.obtenerInformacionUsuario(headerTokenAcceso);
				logger.info(usuarioAcceso.getClaveUsuario());
			} catch (ReglaNegocioException exception){
				logger.info(" !!!!!!! errores de Regla de negocio ");				
				for(String error : exception.getErrores()){
					logger.info(" "+error);	
				}
			} catch (Exception exception){
				logger.error(exception.getMessage(),exception);
			} 			
		} 
		
		
		/**
		 * Prueba de cierre de sesion.
		 */
		//@Test
		public void logout(){
			
			String usuarioLogout = "ADMIN";
			String contraseniaLogout = "password";
			try {
				String headerTokenAcceso =getTokenAcceso(usuarioLogout, contraseniaLogout);
				
				service.logout(headerTokenAcceso);
				logger.info("Cierre de sesion ");	
			} catch (ReglaNegocioException exception){
				logger.info(" !!!!!!! errores de Regla de negocio ");				
				for(String error : exception.getErrores()){
					logger.info(" "+error);	
				}
			} catch (Exception exception){
				logger.error(exception.getMessage(),exception);				
			} 	
		}
		
		
		/**
		 * Prueba en la generacion de un token  token de acceso.
		 * <p>
		 * Se sigue el ciclo de vida
		 * <ul>
		 * 		<li>1. Se solicita token de autorizaci&oacute;n</li>
		 * 		<li>2. Se envian credenciales y token de autorizaci&oacute;n</li>
		 * 		<li>3. Se obtienen el token de acceso.</li>
		 * </ul>
		 *
		 * @param usuario the usuario
		 * @param contrasenia the contrasenia
		 * @return the token acceso
		 * @throws TokenBuilderException the token builder exception
		 * @throws ReglaNegocioException the regla negocio exception
		 */
		private String getTokenAcceso(String usuario, String contrasenia) throws TokenBuilderException, ReglaNegocioException{
			String tokenAutorizacion = service.generarTokenAutorizacionAutenticacion("hola");
			String headerTokenAutorizacion =TokenConstants.AUTHENTICATION_SCHEME_NAME+" "+tokenAutorizacion; 
			
			logger.info("Autorizacion temporal "+headerTokenAutorizacion);
			String tokenAcceso = service.generarTokenAcceso(usuario, contrasenia, headerTokenAutorizacion);
			
			String headerTokenAcceso =TokenConstants.AUTHENTICATION_SCHEME_NAME+" "+tokenAcceso;
			logger.info("Acceso "+headerTokenAcceso);
			
			return headerTokenAcceso; 
		}
		
}
