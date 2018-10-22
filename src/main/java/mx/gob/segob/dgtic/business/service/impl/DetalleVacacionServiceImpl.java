package mx.gob.segob.dgtic.business.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.DetalleVacacionRules;
import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.business.service.VacacionPeriodoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionesAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

@Service
public class DetalleVacacionServiceImpl implements DetalleVacacionService {

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
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		detalleVacacionRules.modificaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public void agregaDetalleVacacion (VacacionesAux detalleVacacionDto) {
		
		DetalleVacacionDto vacacion= new DetalleVacacionDto();
		EstatusDto estatusDto = new EstatusDto();
		estatusDto.setIdEstatus(1);
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setIdUsuario(detalleVacacionDto.getIdUsuario());
		VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
		vacacionPeriodoDto.setIdVacacion(detalleVacacionDto.getIdVacacion());
		vacacion.setIdUsuario(usuarioDto);
		vacacion.setIdVacacion(vacacionPeriodoDto);
		vacacion.setIdEstatus(estatusDto);
		System.out.println("idResponsable "+detalleVacacionDto.getIdResponsable());
		vacacion.setIdResponsable(detalleVacacionDto.getIdResponsable());
		vacacion.setDias(detalleVacacionDto.getDias());
		Date fechaInicial = new Date();
    	Date fechaFinal = new Date();
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	try {
    		fechaInicial = df.parse(detalleVacacionDto.getFechaInicio());
    		fechaFinal=df.parse(detalleVacacionDto.getFechaFin());
			System.out.println("fechaInicio "+fechaInicial);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	vacacion.setFechaInicio(fechaInicial);
    	vacacion.setFechaFin(fechaFinal);
    	
		detalleVacacionRules.agregaDetalleVacacion(vacacion);
		
	}

	@Override
	public void eliminaDetalleVacacion(Integer idDetalle) {
		
		detalleVacacionRules.eliminaDetalleVacacion(idDetalle);	
	}

	@Override
	public void aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		detalleVacacionRules.aceptaORechazaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno, Integer idUnidad, Integer idEstatus) {
		return detalleVacacionRules.obtenerVacacionesPorFiltros(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idUnidad, idEstatus);
	}

	@Override
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, Integer idPeriodo,
			Integer idEstatus, String pfechaInicio, String pfechaFin) {
		return detalleVacacionRules.consultaVacacionesPropiasPorFiltros(claveUsuario, idPeriodo, idEstatus, pfechaInicio, pfechaFin);
	}
	
	@Override
	public reporte generaReporteVacaciones(GeneraReporteArchivo generaReporteArchivo ) {
		reporte repo = new reporte();
		byte[] output= null;
		VacacionPeriodoDto vacacion= new VacacionPeriodoDto();
		try {
			System.out.println("idVacacion para el archivo "+generaReporteArchivo.getIdVacacion());
			if(generaReporteArchivo.getIdVacacion()!=null && !generaReporteArchivo.getIdVacacion().toString().isEmpty()){
			Integer idVacacion=Integer.parseInt(generaReporteArchivo.getIdVacacion());
			vacacion=periodoService.buscaVacacionPeriodo(idVacacion);
			}else{
				vacacion=null;
			}
			URL fileLocation = getClass().getClassLoader().getResource("vacacion");
			System.out.println("ruta obtenida "+fileLocation.toString());
//			String relativeWebPath = "/images";
//			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("Datos "+generaReporteArchivo.getNombre());
			InputStream inputstream = getClass().getClassLoader().getResourceAsStream("/resources/jasper/vacacion");
			System.out.println("archivo "+inputstream);
			JasperReport jasperReport=JasperCompileManager.compileReport(getClass().getClassLoader().getResourceAsStream("/resources/jasper/vacacion/Vacaciones.jrxml"));
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("apellidoPaterno", generaReporteArchivo.getApellidoPaterno());
			parametros.put("apellidoMaterno", generaReporteArchivo.getApellidoMaterno());
			parametros.put("rfc", generaReporteArchivo.getRfc());
			parametros.put("idSolicitud", generaReporteArchivo.getIdsolicitud());
			parametros.put("idEstatus", generaReporteArchivo.getIdEstatus());
			parametros.put("puesto", generaReporteArchivo.getIdPuesto());
			parametros.put("unidadAdministrativa", generaReporteArchivo.getUnidadAdministrativa());
			parametros.put("numeroEmpleado", generaReporteArchivo.getNumeroEmpleado());
			Date fechaInicio = null;
			Date fechaFin = null;
			Date fechaIngreso=null;
			String fechaInicial = null;
			String fechaFinal= null;
			String fechaIngresal=null;
			try {
				fechaInicio= df.parse(generaReporteArchivo.getFechaInicio());
				fechaFin= df.parse(generaReporteArchivo.getFechaFin());
				fechaIngreso= df.parse(generaReporteArchivo.getFechaIngreso());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fechaInicial=df.format(fechaInicio);
			fechaFinal=df.format(fechaFin);
			fechaIngresal=df.format(fechaIngreso);
			parametros.put("fechaIngreso", fechaIngresal);
			parametros.put("fechaInicio", fechaInicial);
			parametros.put("fechaFin", fechaFinal);
			parametros.put("responsable", generaReporteArchivo.getResponsable());
			parametros.put("numeroEmpleado", generaReporteArchivo.getNumeroEmpleado());
			parametros.put("responsable", generaReporteArchivo.getResponsable());
			parametros.put("numeroEmpleado", generaReporteArchivo.getNumeroEmpleado());
			parametros.put("diasVacaciones", generaReporteArchivo.getDias());
			Date fecha = new Date();
			String fechaActual= null;
			Integer diasRestantes=0;
			if(vacacion.getDias()!=null){
				diasRestantes=vacacion.getDias();
				parametros.put("diasRestantes", vacacion.getDias());
			}
			parametros.put("periodo", vacacion.getIdPeriodo().getDescripcion());
			fechaActual=df.format(fecha);
			parametros.put("fechaActual",fechaActual);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			output = JasperExportManager.exportReportToPdf (jasperPrint); 
			
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
//			//JasperViewer archivo= JasperViewer(jasperPrint,false);
//			JasperExportManager.exportReportToPdfFile(jasperPrint,"");
//			 output = JasperExportManager.exportReportToPdf (jasperPrint);
			 repo.setNombre(output);
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repo;
	}
}
