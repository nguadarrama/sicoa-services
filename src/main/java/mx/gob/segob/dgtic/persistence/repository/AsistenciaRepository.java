package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

public interface AsistenciaRepository {

	public List<AsistenciaDto> buscaAsistenciaEmpleado(String claveEmpleado);
		
}
