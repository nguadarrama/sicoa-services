package mx.gob.segob.dgtic.persistence.repository;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

public interface AsistenciaRepository {

	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa, Integer idUnidadCoordinador);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa);
	
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

	public List<AsistenciaDto> reporteDireccion(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa, String permisos);
	
	public List<AsistenciaDto> reporteCoordinador(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa, Integer idUnidadCoordinador, String Permisos);
	
	public void eliminaAsistencia(Integer idAsistencia);
}
