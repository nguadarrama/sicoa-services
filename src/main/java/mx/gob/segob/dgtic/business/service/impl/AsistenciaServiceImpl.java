package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.asistencia.AsistenciaRules;
import mx.gob.segob.dgtic.business.rules.asistencia.CargaAsistenciaRules;
import mx.gob.segob.dgtic.business.rules.catalogo.HorarioRules;
import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.HorarioRepository;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {
	
	@Autowired
	private AsistenciaRules asistenciaRules;
	
	public List<AsistenciaDto> obtenerListaAsistencia() {

		return asistenciaRules.obtenerListaAsistencia();
	}

	@Override
	public AsistenciaDto buscaAsistencia(int id) {
		
		return asistenciaRules.buscaAsistencia(id);
	}
	
	@Override
	public void eliminaAsistencia(Integer id) {
		asistenciaRules.eliminaAsistencia(id);
		
	}
	
}
