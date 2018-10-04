package mx.gob.segob.dgtic.business.service;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

public interface AsistenciaService {
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String fechaInicio, String fechaFin);
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
}
