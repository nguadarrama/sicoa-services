/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.webservices.recursos.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.segob.dgtic.business.service.base.ServiceBase;

/**
 * Clase base para los componentes API Rest de la capa de webservice
 */
public abstract class RecursoBase {
    
    /**
     * Intancia para realizar log.
     */
    protected final Logger logger = LoggerFactory.getLogger(ServiceBase.class);
    
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public void generaPeriodoVacacional(String inicio, String descripcion, boolean activo) {
		// TODO Auto-generated method stub
		
	}
}
