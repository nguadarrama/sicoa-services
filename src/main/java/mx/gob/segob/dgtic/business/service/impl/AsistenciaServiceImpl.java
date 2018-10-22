package mx.gob.segob.dgtic.business.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.asistencia.AsistenciaRules;
import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@Service
public class AsistenciaServiceImpl extends RecursoBase implements AsistenciaService {
	
	@Autowired
	private AsistenciaRules asistenciaRules;
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado) {
		
		return asistenciaRules.buscaAsistenciaEmpleadoMes(claveEmpleado);
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String inicio, String fin) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
    	
    	try {
			Date parsedInicio = formatter.parse(inicio);
			Date parsedFin = formatter.parse(fin);
			
			java.sql.Date fechaInicio = new java.sql.Date(parsedInicio.getTime());
			java.sql.Date fechaFin = new java.sql.Date(parsedFin.getTime());
			
			//se suma un día a la fecha fin para incluirla en la búsqueda
			Calendar c = Calendar.getInstance();
			
			c.setTime(fechaFin);
			c.add(Calendar.DAY_OF_MONTH, 1);  
			fechaFin.setTime(c.getTimeInMillis());
			
			return asistenciaRules.buscaAsistenciaEmpleadoRango(claveEmpleado, fechaInicio, fechaFin);
		} catch (ParseException e) {
			logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, String tipo, String estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa, String cveCoordinador) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
    	
    	try {
			Date parsedInicio = formatter.parse(fechaInicial);
			Date parsedFin = formatter.parse(fechaFinal);
			
			java.sql.Date fechaInicio = new java.sql.Date(parsedInicio.getTime());
			java.sql.Date fechaFin = new java.sql.Date(parsedFin.getTime());
			
			//se suma un día a la fecha fin para incluirla en la búsqueda
			Calendar c = Calendar.getInstance();
			
			c.setTime(fechaFin);
			c.add(Calendar.DAY_OF_MONTH, 1);  
			fechaFin.setTime(c.getTimeInMillis());
			
			return asistenciaRules.buscaAsistenciaEmpleadoRangoCoordinador(cve_m_usuario, nombre, 
					paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa, cveCoordinador);
		} catch (ParseException e) {
			logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, String tipo, String estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
    	
    	try {
			Date parsedInicio = formatter.parse(fechaInicial);
			Date parsedFin = formatter.parse(fechaFinal);
			
			java.sql.Date fechaInicio = new java.sql.Date(parsedInicio.getTime());
			java.sql.Date fechaFin = new java.sql.Date(parsedFin.getTime());
			
			//se suma un día a la fecha fin para incluirla en la búsqueda
			Calendar c = Calendar.getInstance();
			
			c.setTime(fechaFin);
			c.add(Calendar.DAY_OF_MONTH, 1);  
			fechaFin.setTime(c.getTimeInMillis());
			
			return asistenciaRules.buscaAsistenciaEmpleadoRangoDireccion(cve_m_usuario, nombre, 
					paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa);
		} catch (ParseException e) {
			logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
		}
		
		return null;
	}

	@Override
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		return asistenciaRules.buscaAsistenciaPorId(id);
	}
	
	@Override
	public void creaIncidencia(IncidenciaDto incidencia) {
		asistenciaRules.creaIncidencia(incidencia);
	}
	
	@Override
	public void creaDescuento(IncidenciaDto incidencia) {
		asistenciaRules.creaDescuento(incidencia);
	}
	
	@Override
	public void dictaminaIncidencia(IncidenciaDto incidencia) {
		asistenciaRules.dictaminaIncidencia(incidencia);
	}
	
	@Override
	public void aplicaDescuento(IncidenciaDto incidencia) {
		asistenciaRules.aplicaDescuento(incidencia);
	}

	@Override
	public reporte generaFormatoJustificacion(FormatoIncidencia generaReporteArchivo) {
		reporte repo = new reporte();
		byte[] output= null;

		try {
			JasperReport jasperReport=JasperCompileManager.compileReport("C:\\asistencia\\justificacion\\justificacion_incidencias.jrxml");
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("unidad", generaReporteArchivo.getUnidadAdministrativa());
			parametros.put("fechaActual", generaReporteArchivo.getFechaActual());
			parametros.put("codigoIncidencia", generaReporteArchivo.getCodigoIncidencia());
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
	
	@Override
	public reporte generaFormatoDescuento(FormatoIncidencia generaReporteArchivo) {
		reporte repo = new reporte();
		byte[] output= null;

		try {
			JasperReport jasperReport=JasperCompileManager.compileReport("C:\\asistencia\\descuento\\descuento_incidencias.jrxml");
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("fechaActual", generaReporteArchivo.getFechaActual());
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
