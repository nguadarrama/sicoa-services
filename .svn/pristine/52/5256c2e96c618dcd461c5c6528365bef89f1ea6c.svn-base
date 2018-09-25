/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.mapper;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Esta clase hace uso de las anotaciones {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna} y 
 * {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas}, para la inspecci&oacute;n de propiedades 
 * mapeadas con un valor obtenido de la ejecuci&oacute;n de la consulta realizada.
 *
 * Clase que implementa la interfaz {@link org.springframework.jdbc.core.RowMapper}, la cual utiliza
 * {@link org.springframework.jdbc.core.JdbcTemplate} para asignar un valor del resultset obtenido ( {@link java.sql.ResultSet} ) en el query ejecutado al atributo de un objeto  
 * 
 *  Ejemplo.
 *  Se mapea de al siguiente manera
 *  <pre class="code"> *  
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
 * @param <T> El tipo de objeto que deber&aacute; devolver una vez que se asocien los valores
 * 
 * @see org.springframework.jdbc.core.RowMapper
 * @see org.springframework.jdbc.core.JdbcTemplate
 * @see java.sql.ResultSet
 * @see mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna
 * @see mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public  class  RowAnnotationBeanMapper<T> implements RowMapper<T> {



   /**
    * Intancia para realizar log.
    */
   private static final Logger logger  = LoggerFactory.getLogger(RowAnnotationBeanMapper.class);

    /**
     * El tipo de objeto a crear con los valores de la consulta
     */
    private Class<T> beanTypeToMap;


    /**
     * Instancia un row mapper nuevo.
     *
     * @param beanTypeToMap El tipo de objeto generado con los valores obtenidos de la consulta
     */
    public RowAnnotationBeanMapper(Class<T> beanTypeToMap) {
        this.beanTypeToMap = beanTypeToMap;
    }
    
	/**
	 * Encargado de realizar la conversi&oacute;n del {@link java.sql.ResultSet} generado por la consulta a la instancia 
	 * del tipo de objeto que se defini&oacute; en el constructor.
	 *  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
    	logger.trace(" ==============> infomapping ");
    	
    	if(rs == null) {
            throw new SQLException("ResultSet no valido rs: "+rs+" rowNum : "+rowNum);
        }
    	logger.trace("Iterando resultado : "+rowNum);

    	//Se crea la instancia del objeto definido como de retorno
    	T beanReturn = org.springframework.beans.BeanUtils.instantiateClass(this.beanTypeToMap);
    	
    	//Se obtiene la definicion de columnas del resultado
        Map<String, Object> columnasRowRs = obtenerColumnasrowData(rs);
        //Se obtienen las propiedades mapeadas a columnas de la instancia definida
        Map<String, String> propiedadesBeanMapeadas = obtenerPropiedadesMapeaColumna(beanTypeToMap);
         
        //Se realiza la iteraccion de resultados para identificar a que atributo corresponde
        for(Entry<String, Object> columnaRowRs : columnasRowRs.entrySet()) {
        	Object valorColumna = columnaRowRs.getValue();
        	String nombreColumna = columnaRowRs.getKey();
        	logger.trace("========> Columna ResultSet "+nombreColumna+" : "+valorColumna);
        	
        	try{
        		//Se estable o asigna el valor de la columna a el atributo si no existe relacion se evalua si se debe verificar a un mapeo interno
        		boolean esColumnaMapeada = setColumnaPropiedadMapeada(propiedadesBeanMapeadas, nombreColumna,valorColumna,beanReturn);
	        	if(!esColumnaMapeada){
	        		//Se inspecciona el mapeo inteno.
	        		inspeccionaColumnaInterna(nombreColumna, valorColumna,  beanReturn);
	        	}        	        	
        	}catch(ReflectiveOperationException e){
        		logger.error(e.getMessage(), e);
        	}
        }       
       
    	return beanReturn;
    }
    
	/**
	 * Inspecciona columna interna.
	 * Encargada de evaluar si existe una definici&oacute;n a evaluar columnas mapeadas en objetos internos. La definici&oacute;n de esta evaluaci&oacute;n se establece
	 * por la presencia de la anotaci&oacute;n {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas}.
	 *
	 * @param nombreColumna El nombre de la columna que se busca el atributo relacionado establecer su valor
	 * @param valorColumna Valor de la columna a establecer en un atributo del objeto
	 * @param bean sobre el cual se evaluara la presencia de la anotacion {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas}.
	 * 
	 * @throws ReflectiveOperationException Excepci&oacute;n de reflexion.
	 * @throws SQLException Error de SQL
	 */
	private void inspeccionaColumnaInterna(String nombreColumna, Object valorColumna, Object bean) throws ReflectiveOperationException, SQLException{
		logger.trace("==Inspeccion de columna interna "+nombreColumna);
		Map<String, List<String>> propiedadesMapeoInterno = obtenerPropiedadesMapeaColumnaInterna(bean.getClass());    	
    	for(Entry<String, List<String>> columnaRowRs : propiedadesMapeoInterno.entrySet()) {
    		List<String> columnasInternas = columnaRowRs.getValue();
    		String nombrePropiedadParent = columnaRowRs.getKey();
    		if(columnasInternas.contains(nombreColumna)){    			
    			
    			Object subbeanProperty = org.apache.commons.beanutils.BeanUtilsBean.getInstance().getPropertyUtils().getProperty(bean, nombrePropiedadParent);
    			if(subbeanProperty == null){
    				Class classType = org.apache.commons.beanutils.BeanUtilsBean.getInstance().getPropertyUtils().getPropertyType(bean, nombrePropiedadParent);
                    subbeanProperty = org.springframework.beans.BeanUtils.instantiateClass(classType);
                }
    			Map<String, String> propiedadesBeanMapeadas = obtenerPropiedadesMapeaColumna(subbeanProperty.getClass());    			
    			boolean columnaMapeada = propiedadesBeanMapeadas.containsKey(nombreColumna);
            	if(columnaMapeada) {
            		setColumnaPropiedadMapeada(propiedadesBeanMapeadas, nombreColumna,valorColumna,subbeanProperty);
            		asignaValorBean(bean, nombrePropiedadParent, subbeanProperty);    		
            	}            			
    		}
    		
    	}
	}
	
	/**
	 * Se realiza la evaluaci&oacute;n de las propiedades con la anotaci&oacute;n {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna} 
	 * para identificar cual propiedad corresponde con el valor de la columna evaluada
	 *
	 * @param propiedadesBeanMapeadas Mapa de propiedades del bean que tienen presencia de la anotaci&oacute;n  {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna} 
	 * @param nombreColumna nombre de la columna que se busca el mapeo asignado
	 * @param valorColumna El valor que corresponde a esa columna
	 * @param beanToSet al cual se asignara el valor de la columna en el atributo (propiedad) relacionado
	 * @return true, Si existe una propiedad mapeada a la cual se establecio el valor de la columna enviada.
	 * 
	 * @throws ReflectiveOperationException Excepci&oacute;n de reflexi&oacute;n
	 */
	private boolean setColumnaPropiedadMapeada(Map<String, String> propiedadesBeanMapeadas, String nombreColumna, Object valorColumna,Object beanToSet) throws ReflectiveOperationException{
		boolean columnaMapeada = propiedadesBeanMapeadas.containsKey(nombreColumna);
		if(columnaMapeada){
			String nombrePropiedadBean = propiedadesBeanMapeadas.get(nombreColumna);
	    	logger.trace("=======> Bean propiedad "+beanToSet.getClass().getSimpleName()+"."+nombrePropiedadBean);
	    	if(StringUtils.isNotBlank(nombrePropiedadBean) && valorColumna != null){	            	
	    		asignaValorBean(beanToSet, nombrePropiedadBean, valorColumna);	    		        		  
	    	}
		}
		return columnaMapeada;
	}
  
	/**
	 * Encargado de establecer el valor de la columna evaluada al atributo/propiedad del objeto inspeccionado.
	 * 
	 * @param bean El objeto al que se establecer&aacute; el valor
	 * @param propiedad la propiedad del objeto que se establecer&aacute; el valor
	 * @param valor Valor obtenido del resultset
	 * @throws ReflectiveOperationException Excepci&oacute;n de reflexi&oacute;n
	 */
	private void asignaValorBean(Object bean, String propiedad, Object valor) 
		throws ReflectiveOperationException	{
    	logger.trace(" Seteando : "+bean.toString()+ "."+propiedad+ " - Valor : "+valor);
    	
    	Class classType;
		classType = org.apache.commons.beanutils.BeanUtilsBean.getInstance().getPropertyUtils().getPropertyType(bean, propiedad);
		if(classType.isEnum() && valor != null) {
      	  org.apache.commons.beanutils.BeanUtilsBean.getInstance().setProperty(bean, propiedad, EnumUtils.getEnum(classType, valor.toString()));	
        } else {
        	org.apache.commons.beanutils.BeanUtilsBean.getInstance().setProperty(bean, propiedad, valor);	
        }	
        
    }
    
    /**
     * Obtener el nombre de las columnas de los metadatos del resultset, para poder inspeccionar a que propiedad asociarlo
     *
     * @param rs El resultset
     * @return El mapa de nombre de columnas y los valores asociada a cada una
     * 
     * @throws SQLException Excepci&oacute;n SQL
     */
    private Map<String, Object> obtenerColumnasrowData(ResultSet rs) throws SQLException {
    	Map<String, Object> rowData = new HashMap<>(0);
    	ResultSetMetaData rsMetadata = rs.getMetaData();
    	for(int index = 1; index <= rsMetadata.getColumnCount(); index++) {
    	    String columnNameResultSet = rsMetadata.getColumnLabel(index).toLowerCase();
    	    Object columnValueResultSet = rs.getObject(index);
    	    
    	    rowData.put(columnNameResultSet, columnValueResultSet);
    	}    	
    	return rowData;
    }
    
    
   
	/**
	 * Obtiene las propiedades que tienen la presencia de la anotaci&oacute;n {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna} y la columna que se le asocia.
	 *
	 * @param clazz La clase de la cual se inspecciona la presencia de la anotaci&oacute;n
	 * @return El mapa que asocia.   columna_mapeadad : propiedad_clase_corresponde 
	 * 
	 * @throws SQLException Excepci&oacute;n SQL
	 */
	private Map<String, String> obtenerPropiedadesMapeaColumna(Class clazz) throws SQLException {
    	Map<String, String> beanProperties = new HashMap<>(0);
    	
    	Field[] propertiesClass = clazz.getDeclaredFields();
    	for(Field propertyClass : propertiesClass) {
    		 boolean existeAnotacionMapeaColumna = propertyClass.isAnnotationPresent(MapeaColumna.class);
    		 if(existeAnotacionMapeaColumna){
                 MapeaColumna mapeaColumnaProperty = propertyClass.getAnnotation(MapeaColumna.class);
                 String columnaMapeada = mapeaColumnaProperty.columna()
                		 									.trim()
                		 									.toLowerCase();
                 String propiedadMapeada = propertyClass.getName();
                 
                 if(StringUtils.isNotBlank(columnaMapeada)){
                	 beanProperties.put(columnaMapeada, propiedadMapeada);
                 }
    		 }
    	}    	
    	return beanProperties;
    }


    
    
	/**
	 * Obtiene las propiedades que tienen la presencia de la anotaci&oacute;n {@link mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas} 
	 * y las columnas internas que se configuraron a inspeccionar
	 *
	 * @param clazz La clase de la cual se inspecciona la presencia de la anotaci&oacute;n
	 * 
	 * @return El mapa que asocia.   propiedad_con_anotacion : columnas_internas_cofiguradas
	 */
	private Map<String, List<String>> obtenerPropiedadesMapeaColumnaInterna(Class clazz) {
    	Map<String, List<String>> beanProperties = new HashMap<>(0);
    	
    	Field[] propertiesClass = clazz.getDeclaredFields();
    	for(Field propertyClass : propertiesClass) {
    		 boolean existeAnotacionMapeaColumnasInternas = propertyClass.isAnnotationPresent(MapeaColumnasInternas.class);
    		 if(existeAnotacionMapeaColumnasInternas){
    			 
                 MapeaColumnasInternas mapeaColumnaInternaProperty = propertyClass.getAnnotation(MapeaColumnasInternas.class);
                 String [] columnaMapeada = mapeaColumnaInternaProperty.columnas();
                 String propiedadMapeada = propertyClass.getName();
                 
                 if(columnaMapeada != null && columnaMapeada.length > 0){                	 
                	 List<String> columnas = convierteMinuscula(columnaMapeada);                	 
                	 beanProperties.put(propiedadMapeada, columnas);
                 }
    		 }
    	}    	
    	return beanProperties;
    }
	
	/**
	 * metodo que convierte un arreglo de cadenas a una lista de cadenas en minuscula.
	 *
	 * @param valores valores a convertir
	 * @return Lista de valores convertidos a minusculas
	 */
	private List<String> convierteMinuscula(String [] valores){
		List<String> valoresMinuscula = new ArrayList<>(0);
		if(valores != null && valores.length > 0){
			for(String valor : valores){
				valoresMinuscula.add(valor.trim().toLowerCase());
			}
		}
		return valoresMinuscula;
	}

}
