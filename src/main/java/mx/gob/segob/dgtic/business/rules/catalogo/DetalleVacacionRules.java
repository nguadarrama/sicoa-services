package mx.gob.segob.dgtic.business.rules.catalogo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.ArchivoRepository;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.DetalleVacacionRepository;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.persistence.repository.VacacionPeriodoRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Component
public class DetalleVacacionRules extends RecursoBase {
	
	@Autowired
	private DetalleVacacionRepository detalleVacacionRepository;
	
	@Autowired
	VacacionPeriodoRepository vacacionPeriodoRepository;
	
	@Autowired
	AsistenciaRepository asistenciaRepository;
	
	@Autowired 
	ArchivoRepository archivoRepository;
	
	@Autowired UsuarioRepository usuarioRepository;
	
	@Autowired DiaFestivoRepository diaFestivoRepository;
	
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
		UsuarioDto usuario=usuarioRepository.buscaUsuarioPorId(detalleVacacionDto.getIdUsuario().getIdUsuario());
		detalleVacacionDto.setIdUsuario(usuario);
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
	
	public DetalleVacacionDto eliminaDetalleVacacion(Integer idDetalle){
		DetalleVacacionDto detalle= buscaDetalleVacacion(idDetalle);
		VacacionPeriodoDto periodo= vacacionPeriodoRepository.buscaVacacionPeriodo(detalle.getIdVacacion().getIdVacacion());
		periodo.setDias(periodo.getDias()+detalle.getDias());
		vacacionPeriodoRepository.modificaVacacionPeriodo(periodo);
		return detalleVacacionRepository.eliminaDetalleVacacion(idDetalle);
	}
	
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		DetalleVacacionDto aux= new DetalleVacacionDto();
		System.out.println("idDetalle en el rules "+detalleVacacionDto.getIdDetalle());
		DetalleVacacionDto detalleAux= new DetalleVacacionDto();
		detalleAux=detalleVacacionRepository.buscaDetalleVacacion(detalleVacacionDto.getIdDetalle());
		detalleVacacionDto.setFechaInicio(detalleAux.getFechaInicio());
		detalleVacacionDto.setFechaFin(detalleAux.getFechaFin());
		
		if(detalleVacacionDto.getIdEstatus().getIdEstatus()==2){
			List<DiaFestivoDto> listaDiaFestivo = new ArrayList<>();
			listaDiaFestivo=diaFestivoRepository.obtenerDiasFestivosActivos();
			Date fechaInicio=detalleVacacionDto.getFechaInicio();
			Date fechaFin=detalleVacacionDto.getFechaFin();
			
			Calendar c1 = Calendar.getInstance();
			System.out.println("Fechas fecha inicial "+detalleVacacionDto.getFechaInicio()+" fecha final "+detalleVacacionDto.getFechaFin());
		    c1.setTime(fechaInicio);
		    Calendar c2 = Calendar.getInstance();
		    c2.setTime(fechaFin);
		    List<Date> listaFechas = new ArrayList<>();
		    while (!c1.after(c2)) {
		        System.out.println("Fecha para registrar asistencia "+c1.get(Calendar.DAY_OF_WEEK));
		        if((c1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)){
		        	System.out.println("Datos dentro de la comparación ");
		        	
		        	c1.add(Calendar.DAY_OF_MONTH, 1);
		        }else{
		        	listaFechas.add(c1.getTime());
		        	c1.add(Calendar.DAY_OF_MONTH, 1);
		        }
		        
		    }
		  
	        for(DiaFestivoDto diaFestivo : listaDiaFestivo){
	        	for(Date lista: listaFechas){
		        	if(diaFestivo.getFecha().equals(lista)){
		        		listaFechas.remove(lista);
		        	}
	        	}
	        	
	        }
		    EstatusDto estatusDto = new EstatusDto();
		    estatusDto.setIdEstatus(2);
		    TipoDiaDto tipoDiaDto = new TipoDiaDto();
		    tipoDiaDto.setIdTipoDia(5);
		    UsuarioDto usuarioDto= new UsuarioDto();
		    usuarioDto=usuarioRepository.buscaUsuarioPorId(detalleVacacionDto.getIdUsuario().getIdUsuario());
		    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
		        Date date = it.next();
			        AsistenciaDto asistenciaDto = new AsistenciaDto(); 
			        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
			        asistenciaDto.setSalida(new Timestamp(date.getTime()));
			        System.out.println("detalleVacacionDto.getIdUsuario().getIdUsuario() "+detalleVacacionDto.getIdUsuario().getIdUsuario());
			        asistenciaDto.setUsuarioDto(usuarioDto);
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
	
	public DetalleVacacionDto cancelaVacaciones(Integer idDetalle){
		DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
		detalleVacacionDto = buscaDetalleVacacion(idDetalle);
		UsuarioDto usuario = usuarioRepository.buscaUsuarioPorId(detalleVacacionDto.getIdUsuario().getIdUsuario());
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
	    	try {
				String parsedInicio = formatter.format(detalleVacacionDto.getFechaInicio());
				
				String parsedFin = formatter.format(detalleVacacionDto.getFechaFin());
				System.out.println("fechas "+parsedInicio+" "+parsedFin);
				Date parsedInicial = formatter.parse(parsedInicio);
				Date parsedFinal = formatter.parse(parsedFin);
				System.out.println("fechas "+parsedInicial+" "+parsedFinal);
				
				fechaInicio = new java.sql.Date(parsedInicial.getTime());
				fechaFin = new java.sql.Date(parsedFinal.getTime());
				System.out.println("Dato a mostrar "+fechaInicio);
				
//				se suma un día a la fecha fin para incluirla en la búsqueda
				Calendar c = Calendar.getInstance();
				
				c.setTime(fechaFin);
				c.add(Calendar.DAY_OF_MONTH, 1);  
				fechaFin.setTime(c.getTimeInMillis());
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: {} ", e.getMessage());
			}
		
		List<AsistenciaDto> listaAsistencia= asistenciaRepository.buscaAsistenciaEmpleadoRangoDireccion(usuario.getClaveUsuario(), 
				"", "", "", "", 5, 0, fechaInicio, fechaFin, 
				"");
		System.out.println("resultado de consulta asistencia "+listaAsistencia.size()+" filtros claveUsuario "+usuario.getClaveUsuario()
		+" fechaInicio "+fechaInicio+" fechaFin "+fechaFin);
		for(AsistenciaDto asistencia: listaAsistencia){
			System.out.println("idAsistencia "+asistencia.getIdAsistencia());
			asistenciaRepository.eliminaAsistencia(asistencia.getIdAsistencia());
		}
		EstatusDto estatus =new EstatusDto();
		estatus.setIdEstatus(3);
		detalleVacacionDto.setIdEstatus(estatus);
		detalleVacacionDto=aceptaORechazaDetalleVacacion(detalleVacacionDto);
		
		return detalleVacacionDto;
	}

}
