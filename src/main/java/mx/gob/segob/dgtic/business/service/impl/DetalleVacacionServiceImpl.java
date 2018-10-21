package mx.gob.segob.dgtic.business.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.DetalleVacacionRules;
import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
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
	public void agregaDetalleVacacion (DetalleVacacionDto detalleVacacionDto) {
		
		detalleVacacionRules.agregaDetalleVacacion(detalleVacacionDto);
		
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
	public reporte generaReporteVacaciones(GeneraReporteArchivo generaReporteArchivo) {
		reporte repo = new reporte();
		byte[] output= null;
		try {
			System.out.println("Datos "+generaReporteArchivo.getNombre());
			JasperReport jasperReport=JasperCompileManager.compileReport("C:\\vacaciones\\Vacaciones.jrxml");
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("apellidoPaterno", generaReporteArchivo.getApellidoPaterno());
			parametros.put("apellidoMaterno", generaReporteArchivo.getApellidoMaterno());
			parametros.put("rfc", generaReporteArchivo.getRfc());
			parametros.put("idSolicitud", generaReporteArchivo.getIdsolicitud());
			parametros.put("idEstatus", generaReporteArchivo.getIdEstatus());
			parametros.put("idPuesto", generaReporteArchivo.getIdPuesto());
			parametros.put("unidadAdministrativa", generaReporteArchivo.getUnidadAdministrativa());
			parametros.put("numeroEmpleado", generaReporteArchivo.getNumeroEmpleado());
			parametros.put("fechaIngreso", generaReporteArchivo.getFechaIngreso());
			parametros.put("fechaInicio", generaReporteArchivo.getFechaInicio());
			parametros.put("fechaFin", generaReporteArchivo.getFechaFin());
			parametros.put("responsable", generaReporteArchivo.getResponsable());
			parametros.put("numeroEmpleado", generaReporteArchivo.getNumeroEmpleado());
			parametros.put("responsable", generaReporteArchivo.getResponsable());
			parametros.put("numeroEmpleado", generaReporteArchivo.getNumeroEmpleado());
			parametros.put("diasVacaciones", generaReporteArchivo.getDias());
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
