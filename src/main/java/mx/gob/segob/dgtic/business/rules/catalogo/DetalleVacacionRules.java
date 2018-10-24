package mx.gob.segob.dgtic.business.rules.catalogo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		return detalleVacacionRepository.modificaDetalleVacacion(detalleVacacionDto);
	}
	
	public DetalleVacacionDto agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		//detalleVacacionRepository.agregaDetalleVacacion(detalleVacacionDto);
		System.out.println("idVacacion en rules "+detalleVacacionDto.getIdVacacion().getIdVacacion());
	    //ArchivoDto archivoDto = new ArchivoDto();
	    
	    //Integer idArchivo=archivoRepository.agregaArchivo(archivoDto);
	    //archivoDto.setIdArchivo(idArchivo);
	    //detalleVacacionDto.setIdArchivo(archivoDto);
	    //
	    VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
	    //se obtienen los datos de la tabla vacacion_periodo
	    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
	    //Resta de los dias disponibles menos los dias que se han pedido
	    System.out.println("Dias del periodo "+vacacionPeriodoDto.getDias()+" dias pedidos "+ detalleVacacionDto.getDias());
	    Integer resta=vacacionPeriodoDto.getDias()-detalleVacacionDto.getDias();
	    //setea el nueo numero de dias disponibles
	    vacacionPeriodoDto.setDias(resta);	    
	    //insertamos en la tabla detalle_vacacion
	    detalleVacacionDto = detalleVacacionRepository.agregaDetalleVacacion(detalleVacacionDto);
	    if(detalleVacacionDto.getMensaje().contains("correctamente"))
	    	//modificamos el numero de vacaciones en la tabla vacacion_periodo
		    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
	    return detalleVacacionDto;
	    
	    
	}
	
	public void eliminaDetalleVacacion(Integer idDetalle){
		detalleVacacionRepository.eliminaDetalleVacacion(idDetalle);
	}
	
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		DetalleVacacionDto aux= new DetalleVacacionDto();
		System.out.println("idDetalle en el rules "+detalleVacacionDto.getIdDetalle());
		DetalleVacacionDto detalleAux= new DetalleVacacionDto();
		detalleAux=detalleVacacionRepository.buscaDetalleVacacion(detalleVacacionDto.getIdDetalle());
		detalleVacacionDto.setFechaInicio(detalleAux.getFechaInicio());
		detalleVacacionDto.setFechaFin(detalleAux.getFechaFin());
		if(detalleVacacionDto.getIdEstatus().getIdEstatus()==2){
			Date fechaInicio=detalleVacacionDto.getFechaInicio();
			Date fechaFin=detalleVacacionDto.getFechaFin();
			
			Calendar c1 = Calendar.getInstance();
			System.out.println("Fechas fecha inicial "+detalleVacacionDto.getFechaInicio()+" fecha final "+detalleVacacionDto.getFechaFin());
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
		        System.out.println("detalleVacacionDto.getIdUsuario().getIdUsuario() "+detalleVacacionDto.getIdUsuario().getIdUsuario());
		        asistenciaDto.setUsuarioDto(detalleVacacionDto.getIdUsuario());
		        asistenciaDto.setIdEstatus(estatusDto);
		        asistenciaDto.setIdTipoDia(tipoDiaDto);
		        asistenciaRepository.agregaAsistencia(asistenciaDto);
		        System.out.println("Fecha "+date+" registro insertado");  
		    }
		    aux= detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto); 
		}else if(detalleVacacionDto.getIdEstatus().getIdEstatus()==3){
			//detalleVacacionRepository.eliminaDetalleVacacion(detalleVacacionDto.getIdDetalle());
			aux = detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
			VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
		    //se obtienen los datos de la tabla vacacion_periodo
		    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
		    //Suma los dias pedidos que ya no se necesitan
		    Integer resta=vacacionPeriodoDto.getDias()+detalleVacacionDto.getDias();
		    //setea el nueo numero de dias disponibles
		    vacacionPeriodoDto.setDias(resta);
		    //modificamos el numero de vacaciones en la tabla vacacion_periodo
		    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
		}
		return aux;
	}
	
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String idUnidad, String idEstatus){
		return detalleVacacionRepository.obtenerVacacionesPorFiltros(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idUnidad, idEstatus);
	}
	
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, String idPeriodo, String idEstatus, String pfechaInicio, String pfechaFin ){
		return detalleVacacionRepository.consultaVacacionesPropiasPorFiltros(claveUsuario, idPeriodo, idEstatus, pfechaInicio, pfechaFin);
	}

}
