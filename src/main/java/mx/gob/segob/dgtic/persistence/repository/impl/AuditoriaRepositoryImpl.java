/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.transport.dto.auditoria.AuditoriaBitacoraDto;
import mx.gob.segob.dgtic.persistence.repository.AuditoriaRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;

/**
 * Implementaci&oacute;n de los m&eacute;todos para la persistencia de informaci&oacute;n en el proceso de auditor&iacute;a de un recurso REST, identificada por la anotaci&oacute;n {@link mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable}
 * 
 * @see mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase
 * @see mx.gob.segob.dgtic.persistence.repository.AuditoriaRepository
 */
@Repository
public class AuditoriaRepositoryImpl extends RepositoryBase implements AuditoriaRepository {
	
    /**
     * Template de acceso a datos por parametros de enlace de nombres 
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	/**
	 * Registra la informaci&oacute;n que se audita del recurso.
	 *
	 * @param auditoriaBitacora Informaci&oacute;n de la auditoria a persistir
	 */
	@Override
	public void guardarAuditoriaBitacora(AuditoriaBitacoraDto auditoriaBitacora){
		
		StringBuilder qry = new StringBuilder();
		qry.append(" INSERT INTO h_bitacora_auditoria (");
		qry.append(" FECHA_HORA, CVE_M_USUARIO, URI, MODULO, PARAMETROS, CODIGO_RESPUESTA, CONTENT_TYPE_RESPUESTA, RESPUESTA ");
		qry.append(" ) VALUES ( " );
		qry.append(" :fechaHora, :cveUsuario, :uri, :modulo, :parametros, :codigoRespuesta, :contentTypeRespuesta, :respuesta ");
		qry.append(" ) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("fechaHora", auditoriaBitacora.getFechaAudito());
		parametros.addValue("cveUsuario", auditoriaBitacora.getCveUsuario());
		parametros.addValue("uri", auditoriaBitacora.getUri());
		parametros.addValue("modulo", auditoriaBitacora.getModulo());
		parametros.addValue("parametros", auditoriaBitacora.getParametros());
		parametros.addValue("codigoRespuesta", auditoriaBitacora.getEstatusRespuesta());
		parametros.addValue("contentTypeRespuesta", auditoriaBitacora.getContentTypeRespuesta());
		parametros.addValue("respuesta", auditoriaBitacora.getRespuesta());
		logger.debug("query- {}",qry);
		 jdbcTemplate.update(qry.toString(), parametros);	
	}
}
