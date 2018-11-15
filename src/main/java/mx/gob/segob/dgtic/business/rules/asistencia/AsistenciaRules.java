package mx.gob.segob.dgtic.business.rules.asistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;
import mx.gob.segob.dgtic.persistence.repository.DetalleVacacionRepository;
import mx.gob.segob.dgtic.persistence.repository.LicenciaMedicaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class AsistenciaRules {

	@Autowired
	private AsistenciaRepository asistenciaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DetalleVacacionRepository detalleVacacionRepository;
	
	@Autowired
	private LicenciaMedicaRepository licenciaMedicaRepository;
	
	@Autowired
	private ComisionRepository comisionRepository;
	
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
	
	public List<AsistenciaDto> reporteDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.reporteDireccion(asistenciaBusquedaUtil);
		
		if (!asistenciaBusquedaUtil.getPermisos().isEmpty() || asistenciaBusquedaUtil.getTipo() != null) {
        	String[] arrayPermisos = asistenciaBusquedaUtil.getPermisos().split(",");
            List<String> listaPermisos = new ArrayList<>(Arrays.asList(arrayPermisos));
            
            if (listaPermisos.contains("vacacion") || (asistenciaBusquedaUtil.getTipo() != null && asistenciaBusquedaUtil.getTipo() == 5)) { //vacación
            	//extrae vacaciones
        		List<AsistenciaDto> listaAsistenciaVacaciones = buscaVacacionesReporteDirector(asistenciaBusquedaUtil);
        		
        		for (AsistenciaDto asistenciaVacacion : listaAsistenciaVacaciones) {
        			listaAsistencia.add(asistenciaVacacion);
        		}
        		
            }
            
            if (listaPermisos.contains("comision") || (asistenciaBusquedaUtil.getTipo() != null && asistenciaBusquedaUtil.getTipo() == 7)) { //comisión
            	//extrae comision
            	List<AsistenciaDto> listaAsistenciaComision = buscaComisionesReporteDirector(asistenciaBusquedaUtil);
        		
        		for (AsistenciaDto asistenciaComision : listaAsistenciaComision) {
        			listaAsistencia.add(asistenciaComision);
        		}
            }
            
            if (listaPermisos.contains("licencia") || (asistenciaBusquedaUtil.getTipo() != null && asistenciaBusquedaUtil.getTipo() == 6)) { //licencia
            	//extrae licencia
            	List<AsistenciaDto> listaAsistenciaLicencia = buscaLicenciasReporteDirector(asistenciaBusquedaUtil);
        		
        		for (AsistenciaDto asistenciaLicencia : listaAsistenciaLicencia) {
        			listaAsistencia.add(asistenciaLicencia);
        		}
            }
		}
		
		return listaAsistencia;
	}
	
	public List<AsistenciaDto> reporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		
		//se obtiene la unidad administrativa del coordinador
		UsuarioDto coordinador = usuarioRepository.buscaUsuario(asistenciaBusquedaUtil.getCveUsuarioLogeado());
		asistenciaBusquedaUtil.setIdUnidadCoordinador((coordinador.getIdUnidad()));
		
		//se obtienen las incidencias
		List<AsistenciaDto> listaAsistencia;
		listaAsistencia = asistenciaRepository.reporteCoordinador(asistenciaBusquedaUtil);
		
		//se agregan al reporte vacaciones, licencias y comisiones
		if (!asistenciaBusquedaUtil.getPermisos().isEmpty() || asistenciaBusquedaUtil.getTipo() != null) {
        	String[] arrayPermisos = asistenciaBusquedaUtil.getPermisos().split(",");
            List<String> listaPermisos = new ArrayList<>(Arrays.asList(arrayPermisos));
            
            if (listaPermisos.contains("vacacion") || (asistenciaBusquedaUtil.getTipo() != null && asistenciaBusquedaUtil.getTipo() == 5)) { //vacación
            	//extrae vacaciones
        		List<AsistenciaDto> listaAsistenciaVacaciones = buscaVacacionesReporteCoordinador(asistenciaBusquedaUtil);
        		
        		for (AsistenciaDto asistenciaVacacion : listaAsistenciaVacaciones) {
        			listaAsistencia.add(asistenciaVacacion);
        		}
        		
            }
            
            if (listaPermisos.contains("comision") || (asistenciaBusquedaUtil.getTipo() != null && asistenciaBusquedaUtil.getTipo() == 7)) { //comisión
            	//extrae comision
            	List<AsistenciaDto> listaAsistenciaComision = buscaComisionesReporteCoordinador(asistenciaBusquedaUtil);
        		
        		for (AsistenciaDto asistenciaComision : listaAsistenciaComision) {
        			listaAsistencia.add(asistenciaComision);
        		}
            }
            
            if (listaPermisos.contains("licencia") || (asistenciaBusquedaUtil.getTipo() != null && asistenciaBusquedaUtil.getTipo() == 6)) { //licencia
            	//extrae licencia
            	List<AsistenciaDto> listaAsistenciaLicencia = buscaLicenciasReporteCoordinador(asistenciaBusquedaUtil);
        		
        		for (AsistenciaDto asistenciaLicencia : listaAsistenciaLicencia) {
        			listaAsistencia.add(asistenciaLicencia);
        		}
            }
		}
		
		return listaAsistencia;
	}
	
	private List<AsistenciaDto> buscaVacacionesReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		List<AsistenciaDto> listaAsistenciaVacaciones = new ArrayList<>();
		
		List<DetalleVacacionDto> listaVacaciones = detalleVacacionRepository.buscaDetalleVacacionReporteCoordinador(asistenciaBusquedaUtil);
		
		//se convierte la lista de vacaciones en una lista de asistencia para incorporar al reporte
		for (DetalleVacacionDto vacacion : listaVacaciones) {
			TipoDiaDto tipoDia = new TipoDiaDto();
			tipoDia.setIdTipoDia(5);
			tipoDia.setNombre("Vacación");
			
			IncidenciaDto incidencia = new IncidenciaDto();
			incidencia.setDescuento(false);
			
			AsistenciaDto asistencia = new AsistenciaDto();
			asistencia.setUsuarioDto(vacacion.getIdUsuario());
			asistencia.setIdEstatus(vacacion.getIdEstatus());
			asistencia.setIdTipoDia(tipoDia);
			asistencia.setIncidencia(incidencia);

			listaAsistenciaVacaciones.add(asistencia);
		}
		
		return listaAsistenciaVacaciones;
	}
	
	private List<AsistenciaDto> buscaComisionesReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		List<AsistenciaDto> listaAsistenciaComisiones = new ArrayList<>();
		
		List<ComisionDto> listaComision = comisionRepository.buscaComisionReporteCoordinador(asistenciaBusquedaUtil);
		
		//se convierte la lista de vacaciones en una lista de asistencia para incorporar al reporte
		for (ComisionDto comision : listaComision) {
			TipoDiaDto tipoDia = new TipoDiaDto();
			tipoDia.setIdTipoDia(7);
			tipoDia.setNombre("Comisión");
			
			IncidenciaDto incidencia = new IncidenciaDto();
			incidencia.setDescuento(false);
			
			AsistenciaDto asistencia = new AsistenciaDto();
			asistencia.setUsuarioDto(comision.getIdUsuario());
			asistencia.setIdEstatus(comision.getIdEstatus());
			asistencia.setIdTipoDia(tipoDia);
			asistencia.setIncidencia(incidencia);
			
			listaAsistenciaComisiones.add(asistencia);
		}
		
		return listaAsistenciaComisiones;
	}
	
	private List<AsistenciaDto> buscaLicenciasReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		List<AsistenciaDto> listaAsistenciaLicencias = new ArrayList<>();
		
		List<LicenciaMedicaDto> listaLicencias = licenciaMedicaRepository.buscaLicenciaMedicaReporteCoordinador(asistenciaBusquedaUtil);
		
		//se convierte la lista de vacaciones en una lista de asistencia para incorporar al reporte
		for (LicenciaMedicaDto vacacion : listaLicencias) {
			TipoDiaDto tipoDia = new TipoDiaDto();
			tipoDia.setIdTipoDia(6);
			tipoDia.setNombre("Licencia Médica");
			
			IncidenciaDto incidencia = new IncidenciaDto();
			incidencia.setDescuento(false);
			
			AsistenciaDto asistencia = new AsistenciaDto();
			asistencia.setUsuarioDto(vacacion.getIdUsuario());
			asistencia.setIdEstatus(vacacion.getIdEstatus());
			asistencia.setIdTipoDia(tipoDia);
			asistencia.setIncidencia(incidencia);
			
			listaAsistenciaLicencias.add(asistencia);
		}
		
		return listaAsistenciaLicencias;
	}
	
	private List<AsistenciaDto> buscaVacacionesReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		List<AsistenciaDto> listaAsistenciaVacaciones = new ArrayList<>();
		
		List<DetalleVacacionDto> listaVacaciones = detalleVacacionRepository.buscaDetalleVacacionReporteDirector(asistenciaBusquedaUtil);
		
		//se convierte la lista de vacaciones en una lista de asistencia para incorporar al reporte
		for (DetalleVacacionDto vacacion : listaVacaciones) {
			TipoDiaDto tipoDia = new TipoDiaDto();
			tipoDia.setIdTipoDia(5);
			tipoDia.setNombre("Vacación");
			
			IncidenciaDto incidencia = new IncidenciaDto();
			incidencia.setDescuento(false);
			
			AsistenciaDto asistencia = new AsistenciaDto();
			asistencia.setUsuarioDto(vacacion.getIdUsuario());
			asistencia.setIdEstatus(vacacion.getIdEstatus());
			asistencia.setIdTipoDia(tipoDia);
			asistencia.setIncidencia(incidencia);

			listaAsistenciaVacaciones.add(asistencia);
		}
		
		return listaAsistenciaVacaciones;
	}
	
	private List<AsistenciaDto> buscaComisionesReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		List<AsistenciaDto> listaAsistenciaComisiones = new ArrayList<>();
		
		List<ComisionDto> listaComision = comisionRepository.buscaComisionReporteDirector(asistenciaBusquedaUtil);
		
		//se convierte la lista de vacaciones en una lista de asistencia para incorporar al reporte
		for (ComisionDto comision : listaComision) {
			TipoDiaDto tipoDia = new TipoDiaDto();
			tipoDia.setIdTipoDia(7);
			tipoDia.setNombre("Comisión");
			
			IncidenciaDto incidencia = new IncidenciaDto();
			incidencia.setDescuento(false);
			
			AsistenciaDto asistencia = new AsistenciaDto();
			asistencia.setUsuarioDto(comision.getIdUsuario());
			asistencia.setIdEstatus(comision.getIdEstatus());
			asistencia.setIdTipoDia(tipoDia);
			asistencia.setIncidencia(incidencia);
			
			listaAsistenciaComisiones.add(asistencia);
		}
		
		return listaAsistenciaComisiones;
	}
	
	private List<AsistenciaDto> buscaLicenciasReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		List<AsistenciaDto> listaAsistenciaLicencias = new ArrayList<>();
		
		List<LicenciaMedicaDto> listaLicencias = licenciaMedicaRepository.buscaLicenciaMedicaReporteDirector(asistenciaBusquedaUtil);
		
		//se convierte la lista de vacaciones en una lista de asistencia para incorporar al reporte
		for (LicenciaMedicaDto vacacion : listaLicencias) {
			TipoDiaDto tipoDia = new TipoDiaDto();
			tipoDia.setIdTipoDia(6);
			tipoDia.setNombre("Licencia Médica");
			
			IncidenciaDto incidencia = new IncidenciaDto();
			incidencia.setDescuento(false);
			
			AsistenciaDto asistencia = new AsistenciaDto();
			asistencia.setUsuarioDto(vacacion.getIdUsuario());
			asistencia.setIdEstatus(vacacion.getIdEstatus());
			asistencia.setIdTipoDia(tipoDia);
			asistencia.setIncidencia(incidencia);
			
			listaAsistenciaLicencias.add(asistencia);
		}
		
		return listaAsistenciaLicencias;
	}
	
	
}
