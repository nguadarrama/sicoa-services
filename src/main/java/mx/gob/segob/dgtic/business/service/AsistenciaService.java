package mx.gob.segob.dgtic.business.service;

import java.io.FileNotFoundException;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;

public interface AsistenciaService {
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String fechaInicio, String fechaFin);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador (String cveMusuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal, String unidadAdministrativa, String cveCoordinador);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion (String cveMusuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal, String unidadAdministrativa);
	
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public Integer creaIncidencia(IncidenciaDto incidencia);
	public Integer creaDescuento(IncidenciaDto incidencia);
	public Integer dictaminaIncidencia(IncidenciaDto incidencia);
	public Integer aplicaDescuento(IncidenciaDto incidencia);
	public reporte generaFormatoJustificacion(FormatoIncidencia generaReporteArchivo) throws FileNotFoundException;
	public reporte generaFormatoDescuento(FormatoIncidencia generaReporteArchivo) throws FileNotFoundException;
	
	public List<AsistenciaDto> reporteDireccion (String cveMusuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal, String unidadAdministrativa, String permisos);
	
	public List<AsistenciaDto> reporteCoordinador (String cveMusuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal, String unidadAdministrativa, String cveCoordinador, String permisos);
}
