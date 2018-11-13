package mx.gob.segob.dgtic.business.rules.asistencia;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class AsistenciaRules {

	@Autowired
	private AsistenciaRepository asistenciaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado) {
		
		return asistenciaRepository.buscaAsistenciaEmpleadoMes(claveEmpleado);
		
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin) {
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRango(claveEmpleado, fechaInicio, fechaFin);
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		
		//se obtiene la unidad administrativa del coordinador
		UsuarioDto coordinador = usuarioRepository.buscaUsuario(asistenciaBusquedaUtil.getCveUsuarioLogeado());
		asistenciaBusquedaUtil.setIdUnidadCoordinador(coordinador.getIdUnidad());
		
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRangoCoordinador(asistenciaBusquedaUtil);
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.buscaAsistenciaEmpleadoRangoDireccion(asistenciaBusquedaUtil);
		
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
	
	public List<AsistenciaDto> reporteDireccion(String cveMusuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, String permisos) {
		
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.reporteDireccion(cveMusuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa, permisos);
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> reporteCoordinador(String cveMusuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, String cveCoordinador, String permisos) {
		
		//se obtiene la unidad administrativa del coordinador
		UsuarioDto coordinador = usuarioRepository.buscaUsuario(cveCoordinador);
		
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.reporteCoordinador(cveMusuario, nombre, 
				paterno, materno, nivel, tipo, estado, fechaInicial, fechaFinal, unidadAdministrativa, coordinador.getIdUnidad(), permisos);
		
		return listaAsistencia;
	}
	
	
}
