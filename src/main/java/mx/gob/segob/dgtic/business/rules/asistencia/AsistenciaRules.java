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
			String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, String cveCoordinador) {
		
		//se obtiene la unidad administrativa del coordinador
		UsuarioDto coordinador = usuarioRepository.buscaUsuario(cveCoordinador);
		
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRangoCoordinador(cve_m_usuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa, coordinador.getIdUnidad());
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa) {
		
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRangoDireccion(cve_m_usuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa);
		
		return listaAsistencia;
	}
	
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		return asistenciaRepository.buscaAsistenciaPorId(id);
	}
	
	public Integer creaIncidencia(IncidenciaDto incidencia) {

		//si la justificación NO existe entonces la crea
		if (!asistenciaRepository.existeIncidencia(incidencia.getIdAsistencia().getIdAsistencia())) { 
			return asistenciaRepository.creaIncidencia(incidencia);
		} else {//si la justificación SÍ existe la edita
			return asistenciaRepository.editaIncidencia(incidencia);
		}
	}
	
	public Integer creaDescuento(IncidenciaDto incidencia) {

		//si el descuento NO existe entonces la crea
		if (!asistenciaRepository.existeDescuento(incidencia.getIdAsistencia().getIdAsistencia())) { 
			return asistenciaRepository.creaDescuento(incidencia);
		} else {//si la justificación SÍ existe la edita
			return asistenciaRepository.editaDescuento(incidencia);
		}
	}
	
	public Integer dictaminaIncidencia(IncidenciaDto incidencia) {
		return asistenciaRepository.dictaminaIncidencia(incidencia);
	}
	
	public Integer aplicaDescuento(IncidenciaDto incidencia) {
		return asistenciaRepository.aplicaDescuento(incidencia);
	}
	
	public List<AsistenciaDto> reporteDireccion(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, String permisos) {
		
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.reporteDireccion(cve_m_usuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa, permisos);
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> reporteCoordinador(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, String cveCoordinador, String permisos) {
		
		//se obtiene la unidad administrativa del coordinador
		UsuarioDto coordinador = usuarioRepository.buscaUsuario(cveCoordinador);
		
		List<AsistenciaDto> listaAsistencia = asistenciaRepository.reporteCoordinador(cve_m_usuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa, coordinador.getIdUnidad(), permisos);
		
		return listaAsistencia;
	}
	
}
