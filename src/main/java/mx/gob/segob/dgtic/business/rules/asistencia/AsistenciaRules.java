package mx.gob.segob.dgtic.business.rules.asistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;

@Component
public class AsistenciaRules {

	@Autowired
	private AsistenciaRepository asistenciaRepository;
	
	public List<AsistenciaDto> buscaAsistenciaEmpleado(String claveEmpleado) {
		
		return asistenciaRepository.buscaAsistenciaEmpleado(claveEmpleado);
	}
	
}
