/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.persistence.repository;

import mx.gob.segob.dgtic.comun.transport.dto.auditoria.AuditoriaBitacoraDto;

/**
 * Definici&oacute;n de los m&eacute;todos para la persistencia de informaci&oacute;n en el proceso de auditor&iacute;a de un recurso REST, identificada por la anotaci&oacute;n {@link mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable}
 */
public interface AuditoriaRepository {

	/**
	 * Registra la informaci&oacute;n que se audita del recurso.
	 *
	 * @param auditoriaBitacora Informaci&oacute;n de la auditoria a persistir
	 */
	void guardarAuditoriaBitacora(AuditoriaBitacoraDto auditoriaBitacora);

}
