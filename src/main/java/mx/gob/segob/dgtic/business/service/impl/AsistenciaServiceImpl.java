package mx.gob.segob.dgtic.business.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.asistencia.AsistenciaRules;
import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
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
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
		if (!inicio.isEmpty() && !fin.isEmpty()) {
	    	try {
				Date parsedInicio = formatter.parse(inicio);
				Date parsedFin = formatter.parse(fin);
				
				fechaInicio = new java.sql.Date(parsedInicio.getTime());
				fechaFin = new java.sql.Date(parsedFin.getTime());
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(fechaFin);
				c.add(Calendar.DAY_OF_MONTH, 1);  
				fechaFin.setTime(c.getTimeInMillis());
				
				
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
			}
		}
		
    	return asistenciaRules.buscaAsistenciaEmpleadoRango(claveEmpleado, fechaInicio, fechaFin);
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa, String cveCoordinador) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
		if (!fechaInicial.isEmpty() && !fechaFinal.isEmpty()) {
	    	try {
				Date parsedInicio = formatter.parse(fechaInicial);
				Date parsedFin = formatter.parse(fechaFinal);
				
				fechaInicio = new java.sql.Date(parsedInicio.getTime());
				fechaFin = new java.sql.Date(parsedFin.getTime());
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(fechaFin);
				c.add(Calendar.DAY_OF_MONTH, 1);  
				fechaFin.setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
			}
		}
		
		return asistenciaRules.buscaAsistenciaEmpleadoRangoCoordinador(cve_m_usuario, nombre,
				paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa, cveCoordinador);
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
		if (!fechaInicial.isEmpty() && !fechaFinal.isEmpty()) {
	    	try {
				Date parsedInicio = formatter.parse(fechaInicial);
				Date parsedFin = formatter.parse(fechaFinal);
				
				fechaInicio = new java.sql.Date(parsedInicio.getTime());
				fechaFin = new java.sql.Date(parsedFin.getTime());
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(fechaFin);
				c.add(Calendar.DAY_OF_MONTH, 1);  
				fechaFin.setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
			}
		}
		
	    	return asistenciaRules.buscaAsistenciaEmpleadoRangoDireccion(cve_m_usuario, nombre, 
					paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa);
	}

	@Override
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		return asistenciaRules.buscaAsistenciaPorId(id);
	}
	
	@Override
	public Integer creaIncidencia(IncidenciaDto incidencia) {
		return asistenciaRules.creaIncidencia(incidencia);
	}
	
	@Override
	public Integer creaDescuento(IncidenciaDto incidencia) {
		return asistenciaRules.creaDescuento(incidencia);
	}
	
	@Override
	public Integer dictaminaIncidencia(IncidenciaDto incidencia) {
		return asistenciaRules.dictaminaIncidencia(incidencia);
	}
	
	@Override
	public Integer aplicaDescuento(IncidenciaDto incidencia) {
		return asistenciaRules.aplicaDescuento(incidencia);
	}

	@Override
	public reporte generaFormatoJustificacion(FormatoIncidencia generaReporteArchivo) {
		reporte repo = new reporte();
		byte[] output= null;
		try {

			InputStream template = null;
			try {
				File file = new File("/documentos/sicoa/jasper/asistencia/justificacion/justificacion_incidencias.jrxml");
				template = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JasperReport jasperReport=JasperCompileManager.compileReport(template);
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("unidad", generaReporteArchivo.getUnidadAdministrativa());
			parametros.put("fechaActual", generaReporteArchivo.getFechaActual());
			parametros.put("codigoIncidencia", generaReporteArchivo.getCodigoIncidencia());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			output = JasperExportManager.exportReportToPdf (jasperPrint); 
			repo.setNombre(output);
			
		} catch (JRException e) {
			logger.warn("Errora al generar formato justificación -> " + this.getClass());
			e.printStackTrace();
		}
		return repo;
	}
	
	@Override
	public reporte generaFormatoDescuento(FormatoIncidencia generaReporteArchivo) {
		reporte repo = new reporte();
		byte[] output= null;
		try {
			InputStream template = null;
			try {
				File file = new File("/documentos/sicoa/jasper/asistencia/descuento/descuento_incidencias.jrxml");
				template = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JasperReport jasperReport=JasperCompileManager.compileReport(template);
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<String, Object>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("fechaActual", generaReporteArchivo.getFechaActual());
			parametros.put("cve_m_usuario", generaReporteArchivo.getCve_m_usuario());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			output = JasperExportManager.exportReportToPdf (jasperPrint); 
			repo.setNombre(output);
			
		} catch (JRException e) {
			logger.warn("Errora al generar formato descuento -> " + this.getClass());
			e.printStackTrace();
		}
		return repo;
	}

	@Override
	public List<AsistenciaDto> reporteDireccion(String cve_m_usuario, String nombre, String paterno, String materno,
			String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa, String permisos) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
		if (!fechaInicial.isEmpty() && !fechaFinal.isEmpty()) {
	    	try {
				Date parsedInicio = formatter.parse(fechaInicial);
				Date parsedFin = formatter.parse(fechaFinal);
				
				fechaInicio = new java.sql.Date(parsedInicio.getTime());
				fechaFin = new java.sql.Date(parsedFin.getTime());
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(fechaFin);
				c.add(Calendar.DAY_OF_MONTH, 1);  
				fechaFin.setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
			}
		}
		
    	return asistenciaRules.reporteDireccion(cve_m_usuario, nombre, 
					paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa, permisos);
	}

	@Override
	public List<AsistenciaDto> reporteCoordinador(String cve_m_usuario, String nombre, String paterno, String materno,
			String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa, String cveCoordinador, String permisos) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
		if (!fechaInicial.isEmpty() && !fechaFinal.isEmpty()) {
	    	try {
				Date parsedInicio = formatter.parse(fechaInicial);
				Date parsedFin = formatter.parse(fechaFinal);
				
				fechaInicio = new java.sql.Date(parsedInicio.getTime());
				fechaFin = new java.sql.Date(parsedFin.getTime());
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(fechaFin);
				c.add(Calendar.DAY_OF_MONTH, 1);  
				fechaFin.setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
			}
		}
				
		return asistenciaRules.reporteCoordinador(cve_m_usuario, nombre,
				paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa, cveCoordinador, permisos);
	}
	
}
