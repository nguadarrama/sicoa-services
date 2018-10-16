package mx.gob.segob.dgtic.business.rules.asistencia;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import mx.gob.segob.dgtic.comun.exception.ArchivoException;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.jasper.JasperUtil;
import mx.gob.segob.dgtic.persistence.repository.ArchivoRepository;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class AsistenciaRules {

	@Autowired
	private AsistenciaRepository asistenciaRepository;
	
	@Autowired
	private ArchivoRepository archivoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado) {
		
		return asistenciaRepository.buscaAsistenciaEmpleadoMes(claveEmpleado);
		
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin) {
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRango(claveEmpleado, fechaInicio, fechaFin);
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, String cveCoordinador) {
		
		//se obtiene la unidad administrativa del coordinador
		UsuarioDto coordinador = usuarioRepository.buscaUsuario(cveCoordinador);
		
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRangoCoordinador(cve_m_usuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa, coordinador.getIdUnidad());
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa) {
		
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRangoDireccion(cve_m_usuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa);
		
		return listaAsistencia;
	}
	
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		return asistenciaRepository.buscaAsistenciaPorId(id);
	}
	
	public void creaIncidencia(IncidenciaDto incidencia) {

		//si la justificación NO existe entonces la crea
		if (!asistenciaRepository.existeIncidencia(incidencia.getIdAsistencia().getIdAsistencia())) { 
			asistenciaRepository.creaIncidencia(incidencia);
		} else {//si la justificación SÍ existe la edita
			asistenciaRepository.editaIncidencia(incidencia);
		}
	}
	
	public void dictaminaIncidencia(IncidenciaDto incidencia) {
		asistenciaRepository.dictaminaIncidencia(incidencia);
	}
	
}
