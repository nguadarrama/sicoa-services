/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * Constante para las banderas de decisi&oacute;n
 */
public enum DecisionEnum {
	
		/**
		 * Bandera para identificar el valor constante SI.
		 */
		S("SI", true),
		/**
		 * Bandera para identificar el valor constante NO.
		 */
		N("NO", false);
	
	
	/**
	 * Descripci&oacute;n asociadas a la bandera que se representa
	 */
	private String descripcion;
	
	/**
	 * Valor boolean que representa a la constante
	 */
	private boolean booleano;
	
	/**
	 * Se crea una instancia de la constante de decisi&oacute;n, asignando los valores que lo representan
	 *
	 * @param descripcion La descripci&oacute;n a asignar a la constante
	 * @param booleano El valor boolean a asignar a la constante
	 */
	private DecisionEnum(String descripcion, boolean booleano){
		this.descripcion = descripcion;
		this.booleano = booleano;
	}
	
	/**
	 * Obtiene el valor de la descripci&oacute;n asignada a la constante de decisi&oacute;n.
	 *
	 * @return La descripci&oacute;n asignada 
	 */
	public String getDescripcion(){
		return this.descripcion;
	}
	
	/**
	 * Obtiene el valor boolean asignado
	 *
	 * @return Valor asignado a la constante
	 */
	public boolean getBooleano(){
		return booleano;
	}
	
	/**
	 * Encargado de buscar la constante que corresponda a la descripcion solicitida
	 * 
	 * @param descripcion La descripci&oacute;n a buscar para encontrar la constante que empate
	 * @return La constante de decisi&oacute;n que corresponde, si no existe devuelve NULL
	 */
	public static DecisionEnum findByDescripcion(String descripcion){
		DecisionEnum sinoEnum = null;
		if(StringUtils.isNotBlank(descripcion)){
			for(DecisionEnum sinoIterado : DecisionEnum.values()){
				if(sinoIterado.getDescripcion().equals(descripcion)){
					sinoEnum = sinoIterado;
					break;
				}
			}	
		}		
		return sinoEnum;
	}
	
	/**
	 * Encargado de buscar la constante que corresponda a una bandera boolean solicitida
	 * 
	 * @param booleano La bandera boolean a identificar como la constante
	 * @return La constante de decisi&oacute;n que corresponde, si no existe devuelve NULL
	 */
	public static DecisionEnum findByBoolean(boolean booleano){
		DecisionEnum sinoEnum = null;
		for(DecisionEnum sinoIterado : DecisionEnum.values()){
			if(sinoIterado.getBooleano() == booleano){
				sinoEnum = sinoIterado;
				break;
			}
		}	
				
		return sinoEnum;
	}
}
