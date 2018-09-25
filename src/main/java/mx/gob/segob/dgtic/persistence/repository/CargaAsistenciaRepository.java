package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

public interface CargaAsistenciaRepository {
	public void guardaAsistencia(List<AsistenciaDto> asistencias);
	public List<AsistenciaDto> obtieneAsistencia();
}
