package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.persistence.repository.IncidenciaRepository;

@Component
public class IncidenciaRules {

	@Autowired
	private IncidenciaRepository incidenciaRepository;
	
	public List<IncidenciaDto> obtenerListaIncidencias() {
			return incidenciaRepository.obtenerListaIncidencias();
	}
	
	public IncidenciaDto buscaIncidencia (Integer idIncidencia){
		return incidenciaRepository.buscaIncidencia(idIncidencia);
	}
	
	public void modificaIncidencia(IncidenciaDto incidenciaDto){
		incidenciaRepository.modificaIncidencia(incidenciaDto);
	}
	
	public void agregaIncidencia(IncidenciaDto incidenciaDto){
		incidenciaRepository.agregaIncidencia(incidenciaDto);
	}
	
	public void eliminaIncidencia(Integer idIncidencia){
		incidenciaRepository.eliminaIncidencia(idIncidencia);
	}
}
