package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

public interface AsistenciaService {
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String fechaInicio, String fechaFin);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String claveEmpleado, String fechaInicio, String fechaFin, String cveCoordinador);
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public void creaIncidencia(IncidenciaDto incidencia);
	public void dictaminaIncidencia(IncidenciaDto incidencia);
}
