package mx.gob.segob.dgtic.business.service.impl;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.lucene.store.IOContext.Context;
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
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		 return detalleVacacionRules.modificaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public DetalleVacacionDto agregaDetalleVacacion (VacacionesAux detalleVacacionDto) {
		
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
    	
		return  detalleVacacionRules.agregaDetalleVacacion(vacacion);
		
	}

	@Override
	public void eliminaDetalleVacacion(Integer idDetalle) {
		
		detalleVacacionRules.eliminaDetalleVacacion(idDetalle);	
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
	public reporte generaReporteVacaciones(GeneraReporteArchivo generaReporteArchivo ) {
		reporte repo = new reporte();
		byte[] output= null;
		URI uri = null;
		VacacionPeriodoDto vacacion= new VacacionPeriodoDto();
		try {
			
			if(generaReporteArchivo.getIdVacacion()!=null && !generaReporteArchivo.getIdVacacion().toString().isEmpty()){
			Integer idVacacion=Integer.parseInt(generaReporteArchivo.getIdVacacion());
			vacacion=periodoService.buscaVacacionPeriodo(idVacacion);
			}else{
				vacacion=null;
			}
			try {
				uri = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			URL fileLocation = getClass().getClassLoader().getResource("vacacion");
			System.out.println("**************ruta obtenida "+uri.toString());
//			String relativeWebPath = "/images";
//			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
			System.out.println("Datos "+generaReporteArchivo.getNombre());
			String url = uri.toString().replace("vfs:/", ".");
			System.out.println("URL -------------" + url);
			JasperReport jasperReport=JasperCompileManager.compileReport(url +"/jasper/vacacion/Vacaciones.jrxml");
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
			String fechaInicial = "";
			String fechaFinal= "";
			String fechaIngresal="";
			try {
				if(generaReporteArchivo.getFechaInicio()!= null){
					fechaInicio= df.parse(generaReporteArchivo.getFechaInicio());
					fechaInicial=df.format(fechaInicio);
				}
				if(generaReporteArchivo.getFechaFin()!= null){
					fechaFin= df.parse(generaReporteArchivo.getFechaFin());
					fechaFinal=df.format(fechaFin);
				}
				if(generaReporteArchivo.getFechaIngreso()!= null){
					fechaIngreso= df.parse(generaReporteArchivo.getFechaIngreso());
					fechaIngresal=df.format(fechaIngreso);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fechaFinal= generaReporteArchivo.getFechaFin().substring(0, 12);
			fechaInicial=generaReporteArchivo.getFechaInicio().substring(0, 12);
			parametros.put("fechaIngreso", generaReporteArchivo.getFechaIngreso());
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
				diasRestantes=vacacion.getDias()-Integer.parseInt(generaReporteArchivo.getDias());
				parametros.put("diasRestantes",""+diasRestantes);
			}
			System.out.println("idVacacion para el archivo "+generaReporteArchivo.getIdVacacion()+" fechaInicio "+generaReporteArchivo.getFechaInicio()+ 
					" fechaFin "+generaReporteArchivo.getFechaFin()+" periodo "+vacacion.getIdPeriodo().getDescripcion());
			parametros.put("periodo", vacacion.getIdPeriodo().getDescripcion());
			fechaActual=df.format(fecha);
			parametros.put("fechaActual",fechaActual);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			output = JasperExportManager.exportReportToPdf (jasperPrint); 
			 repo.setNombre(output);
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repo;
	}
}
