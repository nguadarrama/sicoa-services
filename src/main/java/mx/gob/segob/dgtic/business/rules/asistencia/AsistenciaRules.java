package mx.gob.segob.dgtic.business.rules.asistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.HorarioRepository;

@Component
public class AsistenciaRules {

	@Autowired
	private AsistenciaRepository asistenciaRepository;
	
	public List<AsistenciaDto> obtenerListaAsistencia() {
		
		return asistenciaRepository.obtenerListaAsistencia();
	}
	
	public AsistenciaDto buscaAsistencia(int id) {
		
		return asistenciaRepository.buscaAsistencia(id);
	}
	
	public void eliminaAsistencia(Integer id) {
		asistenciaRepository.eliminaAsistencia(id);
	}
}
