package mx.gob.segob.dgtic.business.rules.catalogo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.ArchivoRepository;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.DetalleVacacionRepository;
import mx.gob.segob.dgtic.persistence.repository.VacacionPeriodoRepository;
@Component
public class DetalleVacacionRules {
	
	@Autowired
	private DetalleVacacionRepository detalleVacacionRepository;
	
	@Autowired
	VacacionPeriodoRepository vacacionPeriodoRepository;
	
	@Autowired
	AsistenciaRepository asistenciaRepository;
	
	@Autowired 
	ArchivoRepository archivoRepository;
	
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones() {
			return detalleVacacionRepository.obtenerListaDetalleVacaciones();
	}
	
	public DetalleVacacionDto buscaDetalleVacacion (Integer idDetalle){
		return detalleVacacionRepository.buscaDetalleVacacion(idDetalle);
	}
	
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		detalleVacacionRepository.modificaDetalleVacacion(detalleVacacionDto);
	}
	
	public void agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		detalleVacacionRepository.agregaDetalleVacacion(detalleVacacionDto);
		
	    ArchivoDto archivoDto = new ArchivoDto();
	    
	    Integer idArchivo=archivoRepository.agregaArchivo(archivoDto);
	    archivoDto.setIdArchivo(idArchivo);
	    detalleVacacionDto.setIdArchivo(archivoDto);
	    //
	    VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
	    //se obtienen los datos de la tabla vacacion_periodo
	    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
	    //Resta de los dias disponibles menos los dias que se han pedido
	    Integer resta=vacacionPeriodoDto.getDias()-detalleVacacionDto.getDias();
	    //setea el nueo numero de dias disponibles
	    vacacionPeriodoDto.setDias(resta);
	    //modificamos el numero de vacaciones en la tabla vacacion_periodo
	    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
	    //insertamos en la tabla detalle_vacacion
	    detalleVacacionRepository.agregaDetalleVacacion(detalleVacacionDto);
	    
	    
	}
	
	public void eliminaDetalleVacacion(Integer idDetalle){
		detalleVacacionRepository.eliminaDetalleVacacion(idDetalle);
	}
	
	public void aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		if(detalleVacacionDto.getIdEstatus().getIdEstatus()==1){
			Date fechaInicio=detalleVacacionDto.getFechaInicio();
			Date fechaFin=detalleVacacionDto.getFechaFin();
			Calendar c1 = Calendar.getInstance();
		    c1.setTime(fechaInicio);
		    Calendar c2 = Calendar.getInstance();
		    c2.setTime(fechaFin);
		    List<Date> listaFechas = new ArrayList<Date>();
		    while (!c1.after(c2)) {
		        listaFechas.add(c1.getTime());
		        c1.add(Calendar.DAY_OF_MONTH, 1);
		    }
		    EstatusDto estatusDto = new EstatusDto();
		    estatusDto.setIdEstatus(1);
		    TipoDiaDto tipoDiaDto = new TipoDiaDto();
		    tipoDiaDto.setIdTipoDia(5);
		    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
		        Date date = it.next();
		        AsistenciaDto asistenciaDto = new AsistenciaDto(); 
		        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
		        asistenciaDto.setSalida(new Timestamp(date.getTime()));
		        asistenciaDto.setUsuarioDto(detalleVacacionDto.getIdUsuario());
		        asistenciaDto.setIdEstatus(estatusDto);
		        asistenciaDto.setIdTipoDia(tipoDiaDto);
		        asistenciaRepository.agregaAsistencia(asistenciaDto);
		        System.out.println("Fecha "+date+" registro insertado");  
		    }
		    detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto); 
		}else if(detalleVacacionDto.getIdEstatus().getIdEstatus()==2){
			//detalleVacacionRepository.eliminaDetalleVacacion(detalleVacacionDto.getIdDetalle());
			detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
			VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
		    //se obtienen los datos de la tabla vacacion_periodo
		    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
		    //Resta de los dias disponibles menos los dias que se han pedido
		    Integer resta=vacacionPeriodoDto.getDias()+detalleVacacionDto.getDias();
		    //setea el nueo numero de dias disponibles
		    vacacionPeriodoDto.setDias(resta);
		    //modificamos el numero de vacaciones en la tabla vacacion_periodo
		    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
		}
	}

}
