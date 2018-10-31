package mx.gob.segob.dgtic.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;

public interface AsistenciaService {
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String fechaInicio, String fechaFin);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, String estado, String fechaInicial, String fechaFinal, String unidadAdministrativa, String cveCoordinador);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, String estado, String fechaInicial, String fechaFinal, String unidadAdministrativa);
	
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public Integer creaIncidencia(IncidenciaDto incidencia);
	public Integer creaDescuento(IncidenciaDto incidencia);
	public Integer dictaminaIncidencia(IncidenciaDto incidencia);
	public Integer aplicaDescuento(IncidenciaDto incidencia);
	public reporte generaFormatoJustificacion(FormatoIncidencia generaReporteArchivo);
	public reporte generaFormatoDescuento(FormatoIncidencia generaReporteArchivo);
	
	public List<AsistenciaDto> reporteDireccion(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, String estado, String fechaInicial, String fechaFinal, String unidadAdministrativa, String permisos);
	
	public List<AsistenciaDto> reporteCoordinador(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, String estado, String fechaInicial, String fechaFinal, String unidadAdministrativa, String cveCoordinador, String permisos);
}
