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
import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
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
		logger.info("idVacacion en rules: {} ",detalleVacacionDto.getIdVacacion().getIdVacacion());
	    VacacionPeriodoDto vacacionPeriodoDto;
	    //se obtienen los datos de la tabla vacacion_periodo
	    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
	    //Resta de los dias disponibles menos los dias que se han pedido
	   logger.info("Dias del periodo- {} ",vacacionPeriodoDto.getDias());
	   logger.info("dias pedidos - {} ", detalleVacacionDto.getDias());
	    Integer resta=vacacionPeriodoDto.getDias()-detalleVacacionDto.getDias();
	    //setea el nueo numero de dias disponibles
	    vacacionPeriodoDto.setDias(resta);	    
	    //insertamos en la tabla detalle_vacacion
	    detalleVacacionDto = detalleVacacionRepository.agregaDetalleVacacion(detalleVacacionDto);
	    if(detalleVacacionDto.getMensaje().contains("correctamente") || detalleVacacionDto.getMensaje().contains("10"))
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
		logger.info("idDetalle en el rules: {} ",detalleVacacionDto.getIdDetalle());
		DetalleVacacionDto detalleAux;
		detalleAux=detalleVacacionRepository.buscaDetalleVacacion(detalleVacacionDto.getIdDetalle());
		detalleVacacionDto.setFechaInicio(detalleAux.getFechaInicio());
		detalleVacacionDto.setFechaFin(detalleAux.getFechaFin());
		detalleVacacionDto.setIdUsuario(detalleAux.getIdUsuario());
		
		if(detalleVacacionDto.getIdEstatus().getIdEstatus()==2){
			java.sql.Date fechaInicioDate = null;
			java.sql.Date fechaFinDate = null;
			SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
			Date fechaActual= new Date();
			String fechaActualCadena=formatter.format(fechaActual);
			String parsedFin = formatter.format(detalleVacacionDto.getFechaFin());
			Date parsedInicial=null;
			Date parsedFinal=null;
			String parsedInicio = formatter.format(detalleVacacionDto.getFechaInicio());
			try {
				fechaActual= formatter.parse(fechaActualCadena);
				 parsedInicial = formatter.parse(parsedInicio);
			     parsedFinal = formatter.parse(parsedFin);
			} catch (ParseException e) {
				logger.error("Error. {} ",e);
			}
			
			if(parsedInicial!=null){
			fechaInicioDate = new java.sql.Date(parsedInicial.getTime());
			}
			if(parsedFinal!=null){
			fechaFinDate = new java.sql.Date(parsedFinal.getTime());
			}
			UsuarioDto usuarioDto;
		    usuarioDto=usuarioRepository.buscaUsuarioPorId(detalleVacacionDto.getIdUsuario().getIdUsuario());
		    logger.info("FechaInicial: {} ",parsedInicial);
		    logger.info("fechaActual: {} ",fechaActual);
			if((fechaActual.before(parsedInicial) || fechaActual.equals(parsedInicial))){
				aux=aceptaVacacionesFuturas(parsedInicial, detalleVacacionDto, usuarioDto);
			}else{
				aux=aceptaVacacionesPasadas(fechaInicioDate, fechaFinDate, detalleVacacionDto, usuarioDto);
			}
			
		}else if(detalleVacacionDto.getIdEstatus().getIdEstatus()==3){
			aux = detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
			VacacionPeriodoDto vacacionPeriodoDto;
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
	
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(BusquedaDto busquedaDto){
		return detalleVacacionRepository.obtenerVacacionesPorFiltros(busquedaDto);
	}
	
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(BusquedaDto busquedaDto){
		return detalleVacacionRepository.consultaVacacionesPropiasPorFiltros(busquedaDto);
	}
	
	public DetalleVacacionDto cancelaVacaciones(Integer idDetalle){
		DetalleVacacionDto detalleVacacionDto;
		DetalleVacacionDto detalleAux = new DetalleVacacionDto();
		detalleVacacionDto = buscaDetalleVacacion(idDetalle);
		UsuarioDto usuario = usuarioRepository.buscaUsuarioPorId(detalleVacacionDto.getIdUsuario().getIdUsuario());
		
		java.sql.Date fechaInicio = null;
		java.sql.Date fechaFin = null;
		
	    	try {
	    		Date fechaActual= new Date();
	    		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
				
				String fechaActualCadena=formatter.format(fechaActual);
				fechaActual= formatter.parse(fechaActualCadena);
				String parsedFin = formatter.format(detalleVacacionDto.getFechaFin());
				Date parsedFinal = formatter.parse(parsedFin);
				String parsedInicio = formatter.format(detalleVacacionDto.getFechaInicio());
				Date parsedInicial = formatter.parse(parsedInicio);
				fechaInicio = new java.sql.Date(parsedInicial.getTime());
				fechaFin = new java.sql.Date(parsedFinal.getTime());
				if((fechaActual.before(parsedInicial) || fechaActual.equals(parsedInicial))){
					detalleAux=vacacionesFuturas(usuario, fechaInicio, fechaFin, detalleVacacionDto);
				}else{
					detalleAux=vacacionesPasadas(usuario, fechaInicio, fechaFin, detalleVacacionDto);
				}
/**				if(fechaActual.after(parsedInicial) && fechaActual.after(parsedFinal)){
//					Integer contadorDias=0;
////					se suma un día a la fecha fin para incluirla en la búsqueda
//					Calendar c = Calendar.getInstance();
//					c.setTime(fechaFin);
//					c.add(Calendar.DAY_OF_MONTH, 1);  
//					fechaFin.setTime(c.getTimeInMillis());
//					List<AsistenciaDto> listaAsistencia= asistenciaRepository.buscaAsistenciaEmpleado(usuario.getClaveUsuario(), 0, 0, fechaInicio, fechaFin);
//					System.out.println("resultado de consulta asistencia "+listaAsistencia.size()+" filtros claveUsuario "+usuario.getClaveUsuario()
//					+" fechaInicio "+fechaInicio+" fechaFin "+fechaFin);
//					for(AsistenciaDto asistencia: listaAsistencia){
//						System.out.println("idAsistencia "+asistencia.getIdTipoDia().getIdTipoDia());
//						if(asistencia.getIdTipoDia().getIdTipoDia().toString().equals("1")){
//							contadorDias=+1;
//						}
//						if(asistencia.getIdTipoDia().getIdTipoDia().toString().equals("6")){
//							System.out.println("Eliminando "+asistencia.getIdAsistencia()+" y agregando "+contadorDias);
//							asistenciaRepository.eliminaAsistencia(asistencia.getIdAsistencia());
//							TipoDiaDto tipoDia= new TipoDiaDto();
//							tipoDia.setIdTipoDia(5);
//							asistencia.setIdTipoDia(tipoDia);
//							asistenciaRepository.agregaAsistencia(asistencia);
//						}
//							
//					}
//					EstatusDto estatus =new EstatusDto();
//					estatus.setIdEstatus(4);
//					detalleVacacionDto.setIdEstatus(estatus);
//					detalleVacacionDto=detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
//					VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
//				    //se obtienen los datos de la tabla vacacion_periodo
//				    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
//				    //Suma los dias pedidos que ya no se necesitan
//				    Integer resta=vacacionPeriodoDto.getDias()+contadorDias;
//				    //setea el nueo numero de dias disponibles
//				    vacacionPeriodoDto.setDias(resta);
//				    //modificamos el numero de vacaciones en la tabla vacacion_periodo
//				    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
//				} **/
				
			} catch (ParseException e) {
				logger.warn("Error al convertir la fecha en búsqueda de asistencia: {} ", e.getMessage());
			}	
		return detalleAux;
	}
	
	 private List<Date> removerFinesDeSemana(Date fechaInicio, Date fechaFin) {
		    List<Date> listaFechas = new ArrayList<>();
		    List<DiaFestivoDto> listaDiasFestivos = diaFestivoRepository.obtenerDiasFestivosActivos();

		    Calendar c1 = Calendar.getInstance();
		    c1.setTime(fechaInicio);
		    Calendar c2 = Calendar.getInstance();
		    c2.setTime(fechaFin);
		    while (!c1.after(c2)) {
		      if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
		          || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
		        c1.add(Calendar.DAY_OF_MONTH, 1);
		      } else {
		        listaFechas.add(c1.getTime());
		        c1.add(Calendar.DAY_OF_MONTH, 1);
		      }
		    }

		    for (DiaFestivoDto diaFestivo : listaDiasFestivos) {
		      for (Date fecha : listaFechas) {
		        if (diaFestivo.getFecha().equals(fecha)) {
		          listaFechas.remove(fecha);
		        }
		      }
		    }

		    return listaFechas;
		  }
	 
	 private DetalleVacacionDto vacacionesFuturas(UsuarioDto usuario, java.sql.Date fechaInicio, java.sql.Date fechaFin, DetalleVacacionDto detalleVacacionDto){
		 	DetalleVacacionDto detalleAux=null;
		 	Calendar c = Calendar.getInstance();
			c.setTime(fechaFin);
			c.add(Calendar.DAY_OF_MONTH, 1);  
			fechaFin.setTime(c.getTimeInMillis());
			List<AsistenciaDto> listaAsistencia= asistenciaRepository.buscaAsistenciaEmpleado(usuario.getClaveUsuario(), 5, 0, fechaInicio, fechaFin); 
			logger.info("resultado de consulta asistencia - {}",listaAsistencia.size());
			logger.info("filtros claveUsuario- {} ",usuario.getClaveUsuario());
			logger.info("fechaInicio . {}",fechaInicio);
			logger.info("fechaFin.- {} ",fechaFin);
			for(AsistenciaDto asistencia: listaAsistencia){
				logger.info("idAsistencia-- {} ",asistencia.getIdAsistencia());
				asistenciaRepository.eliminaAsistencia(asistencia.getIdAsistencia());
			}
			EstatusDto estatus= new EstatusDto();
			estatus.setIdEstatus(6);
			detalleVacacionDto.setIdEstatus(estatus);
			detalleAux=detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
			VacacionPeriodoDto vacacionPeriodoDto;
		    //se obtienen los datos de la tabla vacacion_periodo
		    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
		    //Suma los dias pedidos que ya no se necesitan
		    Integer resta=vacacionPeriodoDto.getDias()+detalleVacacionDto.getDias();
		    //setea el nueo numero de dias disponibles
		    vacacionPeriodoDto.setDias(resta);
		    //modificamos el numero de vacaciones en la tabla vacacion_periodo
		    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
		    
		    return detalleAux;
	 }
	 
	 private DetalleVacacionDto vacacionesPasadas(UsuarioDto usuario, java.sql.Date fechaInicio, java.sql.Date fechaFin, DetalleVacacionDto detalleVacacionDto){
		 DetalleVacacionDto detalleAux= new DetalleVacacionDto();
		 List<AsistenciaDto> listaAsistencia= asistenciaRepository.buscaAsistenciaEmpleado(usuario.getClaveUsuario(), 0, 0, fechaInicio, fechaFin);
			Boolean bandera=false;
			for(AsistenciaDto asistencia: listaAsistencia){
				logger.info("entrada.- {} ",fechaInicio);
				logger.info("Entrada.- {} ",asistencia.getEntrada());
				logger.info("tipoDia.- {} ",asistencia.getIdTipoDia());
				if(fechaInicio.toString().equals(asistencia.getEntrada().toString().substring(0, 10)) && !asistencia.getIdTipoDia().getIdTipoDia().toString().equals("8")){
					bandera=true;
				}
			}
			if(bandera){
				for(AsistenciaDto asistencia2: listaAsistencia){
					logger.info("idAsistencia -- {}",asistencia2.getIdAsistencia());
					if(asistencia2.getIdTipoDia().getIdTipoDia().toString().equals("5")){
						asistenciaRepository.eliminaAsistencia(asistencia2.getIdAsistencia());
					}
				}
				EstatusDto estatus =new EstatusDto();
				estatus.setIdEstatus(6);
				detalleVacacionDto.setIdEstatus(estatus);
				detalleAux=detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
				VacacionPeriodoDto vacacionPeriodoDto;
			    //se obtienen los datos de la tabla vacacion_periodo
			    vacacionPeriodoDto=vacacionPeriodoRepository.buscaVacacionPeriodo(detalleVacacionDto.getIdVacacion().getIdVacacion());
			    //Suma los dias pedidos que ya no se necesitan
			    Integer resta=vacacionPeriodoDto.getDias()+detalleVacacionDto.getDias();
			    //setea el nueo numero de dias disponibles
			    vacacionPeriodoDto.setDias(resta);
			    //modificamos el numero de vacaciones en la tabla vacacion_periodo
			    vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
				
			}else{
				detalleAux.setMensaje("Error, no se pueden cancelar las vacaciones porque existe una inasistencia");
			}
		return detalleAux;
	 }
	 
	 private DetalleVacacionDto aceptaVacacionesFuturas(Date parsedInicial, DetalleVacacionDto detalleVacacionDto, UsuarioDto usuarioDto){
		 DetalleVacacionDto aux= null;
		 logger.info("FechaInicial. {} ",parsedInicial);
				Date fechaInicio=detalleVacacionDto.getFechaInicio();
				Date fechaFin=detalleVacacionDto.getFechaFin();
				Calendar c1 = Calendar.getInstance();
			    c1.setTime(fechaInicio);
			    Calendar c2 = Calendar.getInstance();
			    c2.setTime(fechaFin);
			    List<Date> listaFechas;
			    listaFechas=removerFinesDeSemana(fechaInicio, fechaFin);
			    EstatusDto estatusDto = new EstatusDto();
			    estatusDto.setIdEstatus(2);
			    TipoDiaDto tipoDiaDto = new TipoDiaDto();
			    tipoDiaDto.setIdTipoDia(5);
			    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
			        	Date date = it.next();
				        AsistenciaDto asistenciaDto = new AsistenciaDto(); 
				        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
				        asistenciaDto.setSalida(new Timestamp(date.getTime()));
				        asistenciaDto.setUsuarioDto(usuarioDto);
				        asistenciaDto.setIdEstatus(estatusDto);
				        asistenciaDto.setIdTipoDia(tipoDiaDto);
				        asistenciaRepository.agregaAsistencia(asistenciaDto);
			    }
			    aux= detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto); 
		 return aux;
	 }
	 
	 private DetalleVacacionDto aceptaVacacionesPasadas(java.sql.Date fechaInicioDate, java.sql.Date fechaFinDate, DetalleVacacionDto detalleVacacionDto,
			 UsuarioDto usuarioDto){
		 DetalleVacacionDto aux= new DetalleVacacionDto();
			List<AsistenciaDto> listaAsistencia= asistenciaRepository.buscaAsistenciaEmpleado(usuarioDto.getClaveUsuario(), 0, 0, fechaInicioDate, fechaFinDate);
			Boolean bandera=false;
			for(AsistenciaDto asistencia: listaAsistencia){
				logger.info("entrada . {} ",fechaInicioDate);
				logger.info("fecha de asistencia.. {} ",asistencia.getEntrada());
				logger.info("tipoDia -- {}",asistencia.getIdTipoDia());
				if(fechaInicioDate.toString().equals(asistencia.getEntrada().toString().substring(0, 10)) && (asistencia.getIdTipoDia().getIdTipoDia().toString().equals("8"))){
					bandera=true;
				}
			}
			if(bandera){
				for(AsistenciaDto asistencia2: listaAsistencia){
					logger.info("idAsistencia: {} ",asistencia2.getIdAsistencia());
					if(asistencia2.getIdTipoDia().getIdTipoDia().toString().equals("8")){
						asistenciaRepository.eliminaAsistencia(asistencia2.getIdAsistencia());
					}
				}
				Date fechaInicio=detalleVacacionDto.getFechaInicio();
				Date fechaFin=detalleVacacionDto.getFechaFin();
				Calendar c1 = Calendar.getInstance();
			    c1.setTime(fechaInicio);
			    Calendar c2 = Calendar.getInstance();
			    c2.setTime(fechaFin);
			    List<Date> listaFechas;
			    listaFechas=removerFinesDeSemana(fechaInicio, fechaFin);
			    EstatusDto estatusDto = new EstatusDto();
			    estatusDto.setIdEstatus(2);
			    TipoDiaDto tipoDiaDto = new TipoDiaDto();
			    tipoDiaDto.setIdTipoDia(5);
			    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
			        	Date date = it.next();
				        AsistenciaDto asistenciaDto = new AsistenciaDto(); 
				        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
				        asistenciaDto.setSalida(new Timestamp(date.getTime()));
				        asistenciaDto.setUsuarioDto(usuarioDto);
				        asistenciaDto.setIdEstatus(estatusDto);
				        asistenciaDto.setIdTipoDia(tipoDiaDto);
				        asistenciaRepository.agregaAsistencia(asistenciaDto);
			    }
				EstatusDto estatus =new EstatusDto();
				estatus.setIdEstatus(2);
				detalleVacacionDto.setIdEstatus(estatus);
				aux=detalleVacacionRepository.aceptaORechazaDetalleVacacion(detalleVacacionDto);
				
			}else{
				aux.setMensaje("Error, no se pueden aceptar las vacaciones porque existe un registro en asistencia");
			}
			return aux;
	 }

}
