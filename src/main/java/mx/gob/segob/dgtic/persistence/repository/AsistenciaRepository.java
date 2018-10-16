package mx.gob.segob.dgtic.persistence.repository;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;

public interface AsistenciaRepository {

	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado);
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa, Integer idUnidadCoordinador);
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno, 
    		String materno, String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa);
	
	public AsistenciaDto buscaAsistenciaPorId(Integer id);
	public void creaIncidencia(IncidenciaDto incidencia);
	public boolean existeIncidencia(Integer idAsistencia);
	public void editaIncidencia(IncidenciaDto incidencia);
	public void dictaminaIncidencia(IncidenciaDto incidencia);
	public void agregaAsistencia(AsistenciaDto asistenciaDto);
	public List<String> obtieneListaEmpleadosDeVacacionesHoy();
}
