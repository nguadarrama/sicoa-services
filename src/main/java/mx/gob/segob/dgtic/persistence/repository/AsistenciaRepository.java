package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

public interface AsistenciaRepository {

	public List<AsistenciaDto> obtenerListaAsistencia();
	public AsistenciaDto buscaAsistencia(int idHorario);
	public void eliminaAsistencia(Integer id);
	public List<AsistenciaDto> obtieneAsistencia();
	public void guardaAsistencia(List<AsistenciaDto> asistencias);
		
}
