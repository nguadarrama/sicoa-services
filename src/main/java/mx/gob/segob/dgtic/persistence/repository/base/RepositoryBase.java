/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.persistence.repository.base;

import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase base para los componentes repository de la capa de persistencia.
 */
public abstract class RepositoryBase {

    /** 
     * Intancia para realizar log 
     */
    protected final Logger logger = LoggerFactory.getLogger(ServiceBase.class);
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();
}
