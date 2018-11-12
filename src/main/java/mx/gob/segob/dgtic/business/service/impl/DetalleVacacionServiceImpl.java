package mx.gob.segob.dgtic.business.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.DetalleVacacionRules;
import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.business.service.VacacionPeriodoService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionesAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class DetalleVacacionServiceImpl extends ServiceBase implements DetalleVacacionService {

	@Autowired
	private DetalleVacacionRules detalleVacacionRules;
	
	@Autowired 
	private VacacionPeriodoService periodoService;
	
	@Override
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones() {
		
		return detalleVacacionRules.obtenerListaDetalleVacaciones();
	}

	@Override
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle) {
		
		return detalleVacacionRules.buscaDetalleVacacion(idDetalle);
	}

	@Override
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		 return detalleVacacionRules.modificaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public DetalleVacacionDto agregaDetalleVacacion (VacacionesAux detalleVacacionDto) {
		
		DetalleVacacionDto vacacion= new DetalleVacacionDto();
		EstatusDto estatusDto = new EstatusDto();
		estatusDto.setIdEstatus(1);
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setIdUsuario(detalleVacacionDto.getIdUsuario().getIdUsuario());
		VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
		vacacionPeriodoDto.setIdVacacion(detalleVacacionDto.getIdVacacion().getIdVacacion());
		vacacion.setIdUsuario(usuarioDto);
		vacacion.setIdVacacion(vacacionPeriodoDto);
		vacacion.setIdEstatus(estatusDto);
		logger.info("idResponsable: {} ",detalleVacacionDto.getIdResponsable());
		vacacion.setIdResponsable(detalleVacacionDto.getIdResponsable());
		vacacion.setDias(detalleVacacionDto.getDias());
		Date fechaInicial = new Date();
    	Date fechaFinal = new Date();
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	try {
    		fechaInicial = df.parse(detalleVacacionDto.getFechaInicio());
    		fechaFinal=df.parse(detalleVacacionDto.getFechaFin());
			logger.info("fechaInicio: {} ",fechaInicial);
		} catch (ParseException e) {
			logger.error("error: {}", e);
		}
    	vacacion.setFechaInicio(fechaInicial);
    	vacacion.setFechaFin(fechaFinal);
    	
		return  detalleVacacionRules.agregaDetalleVacacion(vacacion);
		
	}

	@Override
	public DetalleVacacionDto eliminaDetalleVacacion(Integer idDetalle) {
		
		return detalleVacacionRules.eliminaDetalleVacacion(idDetalle);	
	}

	@Override
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		 return detalleVacacionRules.aceptaORechazaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno, String idUnidad, String idEstatus) {
		return detalleVacacionRules.obtenerVacacionesPorFiltros(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idUnidad, idEstatus);
	}

	@Override
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, String idPeriodo,
			String idEstatus, String pfechaInicio, String pfechaFin) {
		return detalleVacacionRules.consultaVacacionesPropiasPorFiltros(claveUsuario, idPeriodo, idEstatus, pfechaInicio, pfechaFin);
	}
	
	@Override
	public reporte generaReporteVacaciones(GeneraReporteArchivo generaReporteArchivo ) throws FileNotFoundException, ParseException, JRException {
		reporte repo = new reporte();
		String jasper = "/documentos/sicoa/jasper/vacacion/Vacaciones.jrxml";
		byte[] output = null;
		VacacionPeriodoDto vacacion;

			if(generaReporteArchivo.getIdVacacion() != null && !generaReporteArchivo.getIdVacacion().isEmpty()){
				Integer idVacacion = Integer.parseInt(generaReporteArchivo.getIdVacacion());
				vacacion = periodoService.buscaVacacionPeriodo(idVacacion);
			} else {
				vacacion = null;
			}

			File file = new File(jasper);
			InputStream  template = new FileInputStream(file);

			if( template != null ){
			   JasperReport jasperReport = JasperCompileManager.compileReport(template);
				logger.info("Datos: {} ",generaReporteArchivo.getNombre());
				JRDataSource dataSource= new JREmptyDataSource();
				Map<String,Object> parametros = new HashMap<>();
				parametros.put(ServiceConstants.NOMBRE, generaReporteArchivo.getNombre());
				parametros.put(ServiceConstants.APELLIDO_PATERNO, generaReporteArchivo.getApellidoPaterno());
				parametros.put(ServiceConstants.APELLIDO_MATERNO, generaReporteArchivo.getApellidoMaterno());
				parametros.put(ServiceConstants.RFC, generaReporteArchivo.getRfc());
				parametros.put(ServiceConstants.ID_SOLICITUD, generaReporteArchivo.getIdsolicitud());
				parametros.put(ServiceConstants.ID_ESTATUS, generaReporteArchivo.getIdEstatus());
				parametros.put(ServiceConstants.PUESTO, generaReporteArchivo.getIdPuesto());
				parametros.put(ServiceConstants.UNIDAD_ADMVA, generaReporteArchivo.getUnidadAdministrativa());
				parametros.put(ServiceConstants.NUMERO_EMPLEADO, generaReporteArchivo.getNumeroEmpleado());
/**				Date fechaInicio = null;
//				Date fechaFin = null;
//				Date fechaIngreso = null;
//				String fechaInicial = "";
//				String fechaFinal = "";
//				String fechaIngresal = "";
//
//					if(generaReporteArchivo.getFechaInicio()!= null){
//						fechaInicio = df.parse(generaReporteArchivo.getFechaInicio());
//						fechaInicial = df.format(fechaInicio);
//					}
//					if(generaReporteArchivo.getFechaFin()!= null){
//						fechaFin = df.parse(generaReporteArchivo.getFechaFin());
//						fechaFinal = df.format(fechaFin);
//					}
//					if(generaReporteArchivo.getFechaIngreso()!= null){
//						fechaIngreso = df.parse(generaReporteArchivo.getFechaIngreso());
//						fechaIngresal = df.format(fechaIngreso);
//					} 
				//fechaFinal = generaReporteArchivo.getFechaFin().substring(0, 12);
				//fechaInicial = generaReporteArchivo.getFechaInicio().substring(0, 12); **/
				parametros.put(ServiceConstants.FECHA_INGRESO, generaReporteArchivo.getFechaIngreso());
				parametros.put(ServiceConstants.FECHA_INICIO, generaReporteArchivo.getFechaInicio());
				parametros.put(ServiceConstants.FECHA_FIN, generaReporteArchivo.getFechaFin());
				parametros.put(ServiceConstants.RESPONSABLE, generaReporteArchivo.getResponsable());
				parametros.put(ServiceConstants.NUMERO_EMPLEADO, generaReporteArchivo.getNumeroEmpleado());
				parametros.put(ServiceConstants.RESPONSABLE, generaReporteArchivo.getResponsable());
				parametros.put(ServiceConstants.NUMERO_EMPLEADO, generaReporteArchivo.getNumeroEmpleado());
				parametros.put(ServiceConstants.DIAS_VACACIONES, generaReporteArchivo.getDias());
				Integer diasRestantes = 0;
				if(vacacion != null){
					
					diasRestantes = vacacion.getDias();
					parametros.put("diasRestantes",""+diasRestantes);
				}
				logger.info("idVacacion para el archivo: {} ", generaReporteArchivo.getIdVacacion()+" fechaInicio "+generaReporteArchivo.getFechaInicio()+ 
						" fechaFin "+generaReporteArchivo.getFechaFin()+" periodo "+ vacacion.getIdPeriodo().getDescripcion());
				parametros.put("periodo", vacacion.getIdPeriodo().getDescripcion());
				parametros.put("fechaActual",generaReporteArchivo.getFechaPeticion());
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
				output = JasperExportManager.exportReportToPdf (jasperPrint); 
				 repo.setNombre(output);
			}

		return repo;
	}
	
	@Override
	public DetalleVacacionDto cancelaVacaciones(Integer idDetalle) {
		return detalleVacacionRules.cancelaVacaciones(idDetalle);
	}

}
