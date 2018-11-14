/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;



/**
 * Clase de apoyo para acceso a la fabrica de spring fuera de su contexto, esta clase se encuentra bajo el patron SINGLETON
 * 
 * <p>
 * Ejemplo
 * <pre class="code">
 * 
 * 		ClaseService obj = SpringBeanFactoryUtil.getBeanFactory().getBean(ClaseService.class);
 * 	
 * </pre>
 */
public class SpringBeanFactoryUtil {

	/**
	 * La instancia estatica para el patron SINGLETON
	 */
	private static SpringBeanFactoryUtil factory = null;	
	
	/**
	 * Fabrica de bean de SPRING que se registra al inicializar el aplicativo
	 */
	private ApplicationContext beanFactory;

	/**
	 * Asigna el contexto de spring por inyecci&oacute;n de dependencia
	 *
	 * @param beanFactory El contexto de spring
	 * @throws BeansException Una excepcion al asignar la fabrica de beans
	 */
	@Autowired
	public synchronized void setApplicationContext(ApplicationContext beanFactory)  {
		//Se obtiene una instancia de la clase de apoyo para instanciar la clase estatica para acceso singleton
		if(factory == null){
			factory = beanFactory.getBean(SpringBeanFactoryUtil.class);
		}		
		// se asocia la fabrica de beans.
		this.beanFactory = beanFactory;		
	}
	
	
	/**
	 * Obtiene desde un contexto estatico la clase de utileria, para poder acceder a la fabrica de beans.
	 *
	 * @return La fabrica de beans
	 */
	public static SpringBeanFactoryUtil getBeanFactory(){
		return factory;
	}
	
	/**
	 * Obtiene un bean del contexto de spring de un tipo especifico
	 *
	 * @param <T> El tipo de bean a buscar
	 * @param clazz La clase buscada del tipo T
	 * @return El bean que existe en el contexto de spring
	 */
	public <T> T getBean(Class<T> clazz) {
		return beanFactory.getBean(clazz);
	}
	
	/**
	 * Obtiene un bean del contexto de spring de un tipo especifico e identificado por un nombre
	 *
	 * @param <T> El tipo de bean a buscar
	 * @param beanName El nombre del bean con que se identifica en el contexto de SPRING
	 * @param clazz La clase buscada del tipo T
	 * @return El bean que existe en el contexto de spring
	 */
	public <T> T getBean(String beanName, Class<T> clazz) {
		return beanFactory.getBean(beanName, clazz);
	}
	
	
	/**
	 * Obtiene un bean del contexto de spring identificado por un nombre
	 *
	 * @param beanName El nombre del bean con que se identifica en el contexto de SPRING
	 * @return El bean que existe en el contexto de spring
	 */
	public Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}
}
