/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.webservice;

/**
 * Constante para identificar un tipo de respuesta.
 */
public enum ResponseType {
	
	/** Constante que identifica una respuesta de error. */
	ERROR, 
	/** Constante que identifica una respuesta de &eacute;xito. */
	EXITO, 
	/** Constante que identifica una respuesta de advertencia. */
	WARNING;
}
