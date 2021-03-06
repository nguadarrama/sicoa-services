package mx.gob.segob.dgtic.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.business.rules.asistencia.CargaAsistenciaRules;

@Component
public class CargaAsistenciaServiceImpl {

	@Autowired
	private CargaAsistenciaRules asistenciaRules;
	
	@Scheduled(cron = "0 0 8 * * *") //todos los días a las 3 am
//	@Scheduled(fixedDelay = 600000) //5 min
	public void procesaAsistencia() {
		asistenciaRules.procesaAsistencia();
	}
	
	
}
