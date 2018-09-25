package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

public interface IncidenciaRepository {

	public List<IncidenciaDto> obtenerListaIncidencias();
	public IncidenciaDto buscaIncidencia(Integer idIncidencia);
	public void modificaIncidencia(IncidenciaDto incidenciaDto);
	public void agregaIncidencia(IncidenciaDto incidenciaDto);
	public void eliminaIncidencia(Integer idIncidencia);
}
