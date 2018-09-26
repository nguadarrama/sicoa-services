package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.asistencia.AsistenciaRules;
import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {
	
	@Autowired
	private AsistenciaRules asistenciaRules;
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleado(String claveEmpleado) {
		
		return asistenciaRules.buscaAsistenciaEmpleado(claveEmpleado);
	}
	
}
