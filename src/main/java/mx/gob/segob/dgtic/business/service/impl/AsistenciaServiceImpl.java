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
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class AsistenciaServiceImpl extends ServiceBase implements AsistenciaService {
	
	@Autowired
	private AsistenciaRules asistenciaRules;
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado) {
		
		return asistenciaRules.buscaAsistenciaEmpleadoMes(claveEmpleado);
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, String inicio, String fin) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
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
				logger.info(" Error al convertir la fecha en búsqueda de asistencia: {} ", e.getMessage());
			}
		}
		
    	return asistenciaRules.buscaAsistenciaEmpleadoRango(claveEmpleado, fechaInicio, fechaFin);
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		
		if (!asistenciaBusquedaUtil.getFechaInicial().isEmpty() && !asistenciaBusquedaUtil.getFechaFinal().isEmpty()) {
	    	try {
	    		Date parsedInicio = formatter.parse(asistenciaBusquedaUtil.getFechaInicial());
				Date parsedFin = formatter.parse(asistenciaBusquedaUtil.getFechaFinal());
				
				asistenciaBusquedaUtil.setFechaInicialDate(new java.sql.Date(parsedInicio.getTime()));
				asistenciaBusquedaUtil.setFechaFinalDate(new java.sql.Date(parsedFin.getTime()));
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(asistenciaBusquedaUtil.getFechaFinalDate());
				c.add(Calendar.DAY_OF_MONTH, 1);  
				asistenciaBusquedaUtil.getFechaFinalDate().setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: {} ", e.getMessage());
			}
		}
		
		return asistenciaRules.buscaAsistenciaEmpleadoRangoCoordinador(asistenciaBusquedaUtil);
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		
		if (!asistenciaBusquedaUtil.getFechaInicial().isEmpty() && !asistenciaBusquedaUtil.getFechaFinal().isEmpty()) {
	    	try {
				Date parsedInicio = formatter.parse(asistenciaBusquedaUtil.getFechaInicial());
				Date parsedFin = formatter.parse(asistenciaBusquedaUtil.getFechaFinal());
				
				asistenciaBusquedaUtil.setFechaInicialDate(new java.sql.Date(parsedInicio.getTime()));
				asistenciaBusquedaUtil.setFechaFinalDate(new java.sql.Date(parsedFin.getTime()));
				
				//se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(asistenciaBusquedaUtil.getFechaFinalDate());
				c.add(Calendar.DAY_OF_MONTH, 1);  
				asistenciaBusquedaUtil.getFechaFinalDate().setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: {} ", e.getMessage());
			}
		}
		
	    	return asistenciaRules.buscaAsistenciaEmpleadoRangoDireccion(asistenciaBusquedaUtil);
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
	public reporte generaFormatoJustificacion(FormatoIncidencia generaReporteArchivo) throws FileNotFoundException {
		reporte repo = new reporte();
		byte[] output= null;
		String f = "/documentos/sicoa/jasper/asistencia/justificacion/justificacion_incidencias.jrxml";
		try {

			InputStream template = null;

				File file = new File(f);
				template = new FileInputStream(file);

			JasperReport jasperReport=JasperCompileManager.compileReport(template);
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("unidad", generaReporteArchivo.getUnidadAdministrativa());
			parametros.put("fechaActual", generaReporteArchivo.getFechaActual());
			parametros.put("codigoIncidencia", generaReporteArchivo.getCodigoIncidencia());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			output = JasperExportManager.exportReportToPdf (jasperPrint); 
			repo.setNombre(output);
			
		} catch (JRException e) {
			logger.warn("Errora al generar formato justificación -> {} ",this.getClass());
			logger.error(" Error: {} ",e);
		}
		return repo;
	}
	
	@Override
	public reporte generaFormatoDescuento(FormatoIncidencia generaReporteArchivo) throws FileNotFoundException {
		reporte repo = new reporte();
		byte[] output= null;
		String f = "/documentos/sicoa/jasper/asistencia/descuento/descuento_incidencias.jrxml";
		try {
			InputStream template = null;
		
				File file = new File(f);
				template = new FileInputStream(file);
		
			JasperReport jasperReport=JasperCompileManager.compileReport(template);
			JRDataSource dataSource= new JREmptyDataSource();
			Map<String,Object> parametros = new HashMap<>();
			parametros.put("nombre", generaReporteArchivo.getNombre());
			parametros.put("fechaActual", generaReporteArchivo.getFechaActual());
			parametros.put("cve_m_usuario", generaReporteArchivo.getCve_m_usuario());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			output = JasperExportManager.exportReportToPdf (jasperPrint); 
			repo.setNombre(output);
			
		} catch (JRException e) {
			logger.warn("Errora al generar formato descuento ->: {} ",this.getClass());
			logger.error("Error: {}", e);
		}
		return repo;
	}

	@Override
	public List<AsistenciaDto> reporteDireccion(String cveMusuario, String nombre, String paterno, String materno,
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
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: {}", e.getMessage());
			}
		}
		
    	return asistenciaRules.reporteDireccion(cveMusuario, nombre, 
					paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa, permisos);
	}

	@Override
	public List<AsistenciaDto> reporteCoordinador(String cveMusuario, String nombre, String paterno, String materno,
			String nivel, Integer tipo, Integer estado, String fechaInicial, String fechaFinal,
			String unidadAdministrativa, String cveCoordinador, String permisos) {

		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
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
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: {}", e.getMessage());
			}
		}
				
		return asistenciaRules.reporteCoordinador(cveMusuario, nombre,
				paterno, materno, nivel, tipo, estado, fechaInicio, fechaFin, unidadAdministrativa, cveCoordinador, permisos);
	}
	
}
