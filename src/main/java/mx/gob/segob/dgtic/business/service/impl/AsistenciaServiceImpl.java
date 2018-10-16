package mx.gob.segob.dgtic.business.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.asistencia.AsistenciaRules;
import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

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
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String claveEmpleado, String inicio, String fin, String cveCoordinador) {
		
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
			
			return asistenciaRules.buscaAsistenciaEmpleadoRangoCoordinador(claveEmpleado, fechaInicio, fechaFin, cveCoordinador);
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
	public void dictaminaIncidencia(IncidenciaDto incidencia) {
		asistenciaRules.dictaminaIncidencia(incidencia);
	}
	
}
