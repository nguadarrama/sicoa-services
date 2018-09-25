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
 * Constante para las banderas de estatus 
 */
public enum EstatusEnum {
	
			/**
			 * Bandera para identificar el valor constante ACTIVO.
			 */
			A("ACTIVO"), 
			 /**
			  * Bandera para identificar el valor constante INACTIVO.
			  */
			 I("INACTIVO");
	
	/**
	 * Descripci&oacute;n asociadas a la bandera que se representa
	 */
	private String descripcion;
	
	/**
	 * Se crea una instancia de la constante de estatus, asignando los valores que lo representan
	 *
	 * @param descripcion La descripci&oacute;n a asignar a la constante
	 */
	private EstatusEnum(String descripcion){
		this.descripcion = descripcion;		
	}
	
	/**
	 *Obtiene el valor de la descripci&oacute;n asignada a la constante de estatus.
	 *
	 * @return La descripci&oacute;n asignada 
	 */
	public String getDescripcion(){
		return this.descripcion;
	}
	
	
	/**
	 * Encargado de buscar la constante que corresponda a la descripcion solicitida.
	 *
	 * @param descripcion La descripci&oacute;n a buscar para encontrar la constante que empate
	 * @return  La constante de estatus que corresponde, si no existe devuelve NULL
	 */
	public static EstatusEnum findByDescripcion(String descripcion){
		EstatusEnum estatusEnum = null;
		if(StringUtils.isNotBlank(descripcion)){
			for(EstatusEnum estatusIterado : EstatusEnum.values()){
				if(estatusIterado.getDescripcion().equals(descripcion)){
					estatusEnum = estatusIterado;
					break;
				}
			}	
		}		
		return estatusEnum;
	}
}
