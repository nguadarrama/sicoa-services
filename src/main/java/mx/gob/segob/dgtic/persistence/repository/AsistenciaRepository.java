package mx.gob.segob.dgtic.persistence.repository;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

public interface AsistenciaRepository {

	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin);
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public void creaIncidencia(IncidenciaDto incidencia);
	public boolean existeIncidencia(Integer idAsistencia);
	public void editaIncidencia(IncidenciaDto incidencia);
}
