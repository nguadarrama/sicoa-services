package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.IncidenciaRules;
import mx.gob.segob.dgtic.business.service.IncidenciaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

	@Autowired
	private IncidenciaRules incidenciaRules;

	@Override
	public List<IncidenciaDto> obtenerListaIncidencias() {
		
		return incidenciaRules.obtenerListaIncidencias();
	}

	@Override
	public IncidenciaDto buscaIncidencia(Integer idIncidencia) {
		
		return incidenciaRules.buscaIncidencia(idIncidencia);
	}

	@Override
	public IncidenciaDto buscaIncidenciaPorIdAsistencia(Integer idAsistencia) {
		
		return incidenciaRules.buscaIncidenciaPorIdAsistencia(idAsistencia);
	}
	
	@Override
	public void modificaIncidencia(IncidenciaDto incidenciaDto) {
		incidenciaRules.modificaIncidencia(incidenciaDto);
		
	}

	@Override
	public void agregaIncidencia(IncidenciaDto incidenciaDto) {
		incidenciaRules.agregaIncidencia(incidenciaDto);
		
	}

	@Override
	public void eliminaIncidencia(Integer idIncidencia) {
		incidenciaRules.eliminaIncidencia(idIncidencia);
		
	}
}
