package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

public interface IncidenciaService {

	public List<IncidenciaDto> obtenerListaIncidencias();
	public IncidenciaDto buscaIncidencia(Integer idIncidencia);
	public void modificaIncidencia(IncidenciaDto incidenciaDto);
	public void agregaIncidencia(IncidenciaDto incidenciaDto);
	public void eliminaIncidencia(Integer idIncidencia);
}
