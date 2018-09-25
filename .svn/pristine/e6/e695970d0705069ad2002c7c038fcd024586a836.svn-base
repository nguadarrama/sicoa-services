/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.dgtic.business.service.AuditoriaService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.transport.dto.auditoria.AuditoriaBitacoraDto;
import mx.gob.segob.dgtic.persistence.repository.AuditoriaRepository;

/**
 * Implementaci&oacute;n de los m&eacute;todos para la l&oacute;gica de negocio en el proceso de auditor&iacute;a de un recurso REST, identificada por la anotaci&oacute;n {@link mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable}
 * 
 * @see mx.gob.segob.dgtic.business.service.AuditoriaService
 * @see mx.gob.segob.dgtic.business.service.base.ServiceBase
 * @see mx.gob.segob.dgtic.webservices.aplicacionconfig.config.resteasy.annotations.Auditable
 */
@Service
public class AuditoriaServiceImpl extends ServiceBase implements AuditoriaService {

	/**
	 * Repositorio de datos para bitacorizar la auditoria.
	 */
	@Autowired
	private AuditoriaRepository auditoriaRepository;
	
	/**
	 * Registra la informaci&oacute;n que se audita del recurso.
	 *
	 * @param auditoriaBitacora Informaci&oacute;n de la auditoria a persistir
	 */
	@Override
	@Transactional
	public void guardarAuditoriaBitacora(AuditoriaBitacoraDto auditoriaBitacora){
		auditoriaRepository.guardarAuditoriaBitacora(auditoriaBitacora);
	}
}
