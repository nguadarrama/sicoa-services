package mx.gob.segob.dgtic.business.service;

import java.io.FileNotFoundException;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.Reporte;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;

public interface AsistenciaService {
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String fechaInicio, String fechaFin);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public Integer creaIncidencia(IncidenciaDto incidencia);
	public Integer creaDescuento(IncidenciaDto incidencia);
	public Integer dictaminaIncidencia(IncidenciaDto incidencia);
	public Integer aplicaDescuento(IncidenciaDto incidencia);
	public Reporte generaFormatoJustificacion(FormatoIncidencia generaReporteArchivo) throws FileNotFoundException;
	public Reporte generaFormatoDescuento(FormatoIncidencia generaReporteArchivo) throws FileNotFoundException;
	public List<AsistenciaDto> reporteDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<AsistenciaDto> reporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
}
