package mx.gob.segob.dgtic.persistence.repository;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;

public interface AsistenciaRepository {

	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<AsistenciaDto> buscaAsistenciaEmpleado(String claveUsuario, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal);
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public Integer creaIncidencia(IncidenciaDto incidencia);
	public Integer creaDescuento(IncidenciaDto incidencia);
	public boolean existeIncidencia(Integer idAsistencia);
	public boolean existeDescuento(Integer idAsistencia);
	public Integer editaIncidencia(IncidenciaDto incidencia);
	public Integer editaDescuento(IncidenciaDto incidencia);
	public Integer dictaminaIncidencia(IncidenciaDto incidencia);
	public Integer aplicaDescuento(IncidenciaDto incidencia);
	public Integer agregaAsistencia(AsistenciaDto asistenciaDto);
	public List<String> obtieneListaEmpleadosDeVacacionesHoy();
	public List<String> obtieneListaEmpleadosDeComisionHoy();
	public List<String> obtieneListaEmpleadosDeLicenciaHoy();
	public List<AsistenciaDto> reporteDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<AsistenciaDto> reporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public void eliminaAsistencia(Integer idAsistencia);
}
