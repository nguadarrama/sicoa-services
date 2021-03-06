package mx.gob.segob.dgtic.business.rules.asistencia;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.business.service.TipoDiaService;
import mx.gob.segob.dgtic.business.service.UsuarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.crypto.HashUtils;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.CargaAsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.CargaInicialRepository;
import mx.gob.segob.dgtic.persistence.repository.ConfiguracionRepository;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Component
public class CargaAsistenciaRules extends RecursoBase {
	
	@Autowired
	private CargaAsistenciaRepository cargaAsistenciaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TipoDiaService tipoDiaService;
	
	@Autowired
	private ConfiguracionRepository configuracionRepository;
	
	@Autowired
	private AsistenciaRepository asistenciaRepository;
	
	@Autowired
	private DiaFestivoRepository diaFestivoRepository;
	
	@Autowired
	private CargaInicialRepository cargaInicialRepository;
	
	private List<UsuarioDto> listaUsuarios;
	private List<String> listaClaveUsuariosEnAsistencia;
	Set<String> listaIdusuariosAsistencia;
	
	public void procesaAsistencia() {

		logger.info("***** PROCESANDO ASISTENCIA *****");
		
		//Filtra asistencia, sólo se procesa aquella asistencia en la que el empleado se encuentra en nómina 
		List<AsistenciaDto> listaAsistenciaFiltrada = filtraAsistencia();
		
		if (!listaAsistenciaFiltrada.isEmpty()) {
		
			//Se calculan las entradas y salidas
			List<AsistenciaDto> listaAsistenciaEntradaSalida = calculaEntradasSalidas(listaAsistenciaFiltrada);
			
			//Se calculan las incidencias con base a las entradas y salidas
			List<AsistenciaDto> listaAsistenciaCalculada = calculaIncidencias(listaAsistenciaEntradaSalida);
		
			//la asistencia se guarda
			cargaAsistenciaRepository.guardaAsistencia(listaAsistenciaCalculada);
			
			//actualiza la nómina de sicoa
			actualizaNomina();
			
			//actualiza la última fecha de carga de asistencias
			configuracionRepository.actualizaUltimaFechaCargaAsistencia();
		} else {
			logger.info("No se generó ninguna asistencia");
		}
			
		logger.info("***** TERMINA PROCESO ASISTENCIA *****");
	}
	
	private List<AsistenciaDto> filtraAsistencia() {
		//obtiene los usuarios
		listaUsuarios = new ArrayList<>();
		listaUsuarios = usuarioService.obtenerListaUsuarios();
		listaIdusuariosAsistencia = new HashSet<>();
		//obtiene asistencia del sistema de asistencias (biométricos - ASISTENCIA)
		List<AsistenciaDto> listaAsistenciaCompleta = cargaAsistenciaRepository.obtieneAsistencia(configuracionRepository.obtieneUltimaFechaCargaAsistencia());
		
		if (!listaAsistenciaCompleta.isEmpty()) {
			List<AsistenciaDto> listaAsistenciaFiltrada = new ArrayList<>();
			
			for (AsistenciaDto a : listaAsistenciaCompleta) {
				//se obtienen los id de usuario (cve_m_usuario) de la lista de asistencia, sin repetidos
				listaIdusuariosAsistencia.add(a.getUsuarioDto().getClaveUsuario());
				
				for (UsuarioDto u : listaUsuarios) {
					
					//id nómina de asistencias ==  id nómina de usuarios: se guarda asistencia en la lista
					if (a.getUsuarioDto().getClaveUsuario().equals(u.getClaveUsuario())) { 
						listaAsistenciaFiltrada.add(a);
					}
					
				}
			}
			
			if (!listaAsistenciaFiltrada.isEmpty()) {
				logger.info("asistencias de empleados coincidentes: {} ",listaAsistenciaFiltrada.size() );
				return listaAsistenciaFiltrada;
				
			} else {
				logger.info("Entre asistencia y nómina NO se encontraron empleados coincidentes");
				return new ArrayList<>();
			}
		} else {
			logger.info("No se encontró asistencia en el sistema de Asistencias");
			return new ArrayList<>();
		}

	}
	
	
	private List<AsistenciaDto> calculaEntradasSalidas(List<AsistenciaDto> listaAsistencia) {
		Set<String> hashSetIdUsuarios = new HashSet<>();
		List<UsuarioChecada> listaUsuarioChecadas = new ArrayList<>();
		List<AsistenciaDto> listaAsistenciaCalculada = new ArrayList<>();
		listaClaveUsuariosEnAsistencia = new ArrayList<>();
		
		//recupera de la asistencia los id's de usuarios: sin repetidos y les agrupa sus asistencias a cada uno
		ligaAsistenciasAUsuario(listaAsistencia, hashSetIdUsuarios, listaUsuarioChecadas);
		
		//recorre usuarios
		for (UsuarioChecada usuarioChecada : listaUsuarioChecadas) {		
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setClaveUsuario(usuarioChecada.getIdUsuario());
			List<Timestamp> listaEventosMultiples = new ArrayList<>(); //Lista para los eventos multiples en un día
			
			//recorre asistencia del usuario
			for (int i = 0; i < usuarioChecada.getListaChecadas().size(); i++) {			
				AsistenciaDto asistenciaCalculada = new AsistenciaDto();
				asistenciaCalculada.setUsuarioDto(usuarioDto);
				
				Timestamp fechaEvento = usuarioChecada.getListaChecadas().get(i);
				Timestamp fechaEventoSiguiente = null;
				
				
				//¿la búsqueda hacia adelante del evento siguiente está dentro del tamaño de la lista?
				if (i < usuarioChecada.getListaChecadas().size() - 1) {
					fechaEventoSiguiente = usuarioChecada.getListaChecadas().get(i + 1);
				}
				
				//sólo se calcula evento anterior a partir del segundo elemento de la lista
				if (i > 0) {
					 usuarioChecada.getListaChecadas().get(i - 1);
				}
				
				//Calcula entradas y salidas. Toma el primer evento como la fecha de entrada y el úlitmo como la fecha de salida
				calculaEntradaSalida(fechaEvento, fechaEventoSiguiente, listaEventosMultiples, asistenciaCalculada, listaAsistenciaCalculada, usuarioChecada, i);
			}
		}
		
		logger.info("se les generó entrada y salida: {} ",listaAsistenciaCalculada.size() );
		
		return listaAsistenciaCalculada;
	}
	
	private List<AsistenciaDto> calculaIncidencias(List<AsistenciaDto> listaAsistencia) {
		
		//calcula incidencias y setea el tipo de incidencia a la lista de asistencias
		logger.info("calculando incidencias: {} ",listaAsistencia.size());
		calculoIncidencias(listaAsistencia);
		
		//calculo de inasistencias. son aquellas usurios que no tienen registro en el sistema de asistencias y no están de permiso
		logger.info("calculando y creando inasistencias: {} ",listaAsistencia.size());
		calculoInasistencia(listaAsistencia);
		
		logger.info("asistencias procesadas. Termina cálculo de incidencias: {} ",listaAsistencia.size());
		
		return listaAsistencia;
	}
	
	private TipoDiaDto obtieneTipoDia(int idTipoDia) {
		
		for (TipoDiaDto t : tipoDiaService.obtenerListaTipoDias()) {
			if (idTipoDia == t.getIdTipoDia()) {
				return t;
			}
		}
		
		return new TipoDiaDto();
	}
	
	@SuppressWarnings("deprecation")
	private boolean esPuntualEntrada(Timestamp a, Time usuario) {
		boolean response;
		Date usuarioEntradaChecada = new Date();
		usuarioEntradaChecada.setHours(a.getHours());
		usuarioEntradaChecada.setMinutes(a.getMinutes());
		usuarioEntradaChecada.setSeconds(a.getSeconds());
		
		Date usuarioEntradaHorario = new Date();
		usuarioEntradaHorario.setHours(usuario.getHours()); 
		usuarioEntradaHorario.setMinutes(usuario.getMinutes()); 
		usuarioEntradaHorario.setSeconds(usuario.getSeconds()); 
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(usuarioEntradaHorario);
		cal.add(Calendar.MINUTE, 16); // a su hora de entrada laboral, se le agregan 16 minutos como tolerancia
		 
		Date entradaTolerancia = cal.getTime();
		
		if (usuarioEntradaChecada.before(entradaTolerancia) || usuarioEntradaChecada.equals(entradaTolerancia)) {  
			response =  true;
		} else {
			response =  false;
		}
		return response;
	}
	
	@SuppressWarnings("deprecation")
	private boolean esPuntualSalida(Timestamp a, Time usuario) {
		boolean response ;
		Date usuarioSalidaChecada = new Date();
		usuarioSalidaChecada.setHours(a.getHours());
		usuarioSalidaChecada.setMinutes(a.getMinutes());
		usuarioSalidaChecada.setSeconds(a.getSeconds());
		
		Date usuarioSalidaHorario = new Date();
		usuarioSalidaHorario.setHours(usuario.getHours()); 
		usuarioSalidaHorario.setMinutes(usuario.getMinutes()); 
		usuarioSalidaHorario.setSeconds(usuario.getSeconds()); 
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(usuarioSalidaHorario);
		cal.add(Calendar.MINUTE, -1); // a su hora de salida laboral, se le resta 1 minuto como tolerancia
		 
		Date salidaTolerancia = cal.getTime();
		
		if (usuarioSalidaChecada.after(salidaTolerancia) || usuarioSalidaChecada.equals(salidaTolerancia)) { 
			response =  true;
		} else {
			response =  false;
		}
		return response;
	}
	
	private void actualizaNomina() {
		logger.info("-> Proceso de actualización de nómina <-");
		List<String> listaClaveUsuariosEnSicoa = new ArrayList<>();
		
		//se obtienen las cve de usuario en una lista
		for (UsuarioDto usuarioSicoa : listaUsuarios) {
			listaClaveUsuariosEnSicoa.add(usuarioSicoa.getClaveUsuario());
		}
		
		for (String usuarioAsistencia : listaIdusuariosAsistencia) {
			if (!listaClaveUsuariosEnSicoa.contains(usuarioAsistencia)) {
				logger.info("usuario nuevo detectado en asistencias: {} ", usuarioAsistencia);
				UsuarioDto usuarioSIRNO = cargaInicialRepository.obtieneUsuarioPorCveMusuario(usuarioAsistencia);
				
				if (usuarioSIRNO != null) {
					usuarioSIRNO.setPassword(HashUtils.md5(usuarioSIRNO.getClaveUsuario()));
					usuarioService.agregaUsuario(usuarioSIRNO);
					logger.info("existe en SIRNO, se procede a registrarlo en SICOA: {} ",usuarioAsistencia);
				} else {
					logger.info(" NO existe en SIRNO, NO se procede a registrarlo en SICOA: {}",usuarioAsistencia);
				}
			}
		}
	}
	
	private List<UsuarioChecada> ligaAsistenciasAUsuario(List<AsistenciaDto> listaAsistencia, Set<String> hashSetIdUsuarios, List<UsuarioChecada> listaUsuarioChecadas) {
		for(AsistenciaDto a : listaAsistencia) {
			hashSetIdUsuarios.add(a.getUsuarioDto().getClaveUsuario());
		}
		
		//agrupa las asistencias de cada id de usuario
		for (String idUsuario : hashSetIdUsuarios) {
			
			//guarda las claves de los usuarios en la asistencia para otro proceso más adelante
			listaClaveUsuariosEnAsistencia.add(idUsuario);
			
			UsuarioChecada usuarioChecadas = new UsuarioChecada();
			usuarioChecadas.setIdUsuario(idUsuario);
			
			for(AsistenciaDto a : listaAsistencia) {
				if (idUsuario.equals(a.getUsuarioDto().getClaveUsuario())) {
					usuarioChecadas.setListaChecadas(a.getEntrada());
				}
			}

			listaUsuarioChecadas.add(usuarioChecadas);
		}
		
		logger.info("Usuarios coincidentes en ASISTENCIA y SIRNO. Calculando entrada y salida: {} ",listaUsuarioChecadas.size() );
		
		return listaUsuarioChecadas;
	}
	
	@SuppressWarnings("deprecation")
	private void buscaMasEventosDelDia(UsuarioChecada usuarioChecada, Timestamp fechaEvento, Timestamp fechaEventoSiguiente, List<Timestamp> listaEventosMultiples, int i) {
		//se continúa buscando eventos en el mismo día y se guardan en la lista
		for (int y = 2; true ; y++) {
			
			//¿la búsqueda hacia adelante de los eventos está dentro del tamaño de la lista de eventos?
			if (
					((i + y) < usuarioChecada.getListaChecadas().size()) && 
					(fechaEvento.getYear() == fechaEventoSiguiente.getYear()) && 
					(fechaEvento.getMonth() == fechaEventoSiguiente.getMonth()) && 
					(fechaEventoSiguiente.getDate() == usuarioChecada.getListaChecadas().get(i + y).getDate()) 
			) {
				//se guardan en la lista los demás eventos del mismo día
				listaEventosMultiples.add(usuarioChecada.getListaChecadas().get(i + y));
			} else {
				break; //si no encuentra más eventos en el mismo día, rompe for
			}
		}
		
	}
	
	private void calculoIncidencias(List<AsistenciaDto> listaAsistencia) {
			
		for (AsistenciaDto a : listaAsistencia) {
			UsuarioDto usuario = usuarioService.buscaUsuario(a.getUsuarioDto().getClaveUsuario());
			
			//con base a las reglas y setea el tipo de la incidencia
			reglasParaCrearIncidencias(a, usuario);
			
		}
	}
	
	private void reglasParaCrearIncidencias(AsistenciaDto a, UsuarioDto usuario) {
		if (a.getEntrada() != null) {
			if (esPuntualEntrada(a.getEntrada(), usuario.getIdHorario().getHoraEntrada())) {
				if (a.getSalida() != null) { 
					if (esPuntualSalida(a.getSalida(), usuario.getIdHorario().getHoraSalida())) { 
						TipoDiaDto tipoDia = obtieneTipoDia(1); 	//Día normal. Fue puntual en entrada y salida
						a.setIdTipoDia(tipoDia);
					} else {
						TipoDiaDto tipoDia = obtieneTipoDia(3);		//checó salida antes: "Omisión de Salida"
						a.setIdTipoDia(tipoDia);
					}
				} else {																	
					TipoDiaDto tipoDia = obtieneTipoDia(3);			//no checó salida: "Omisión de Salida"
					a.setIdTipoDia(tipoDia);
				}
			} else {																	  
				TipoDiaDto tipoDia = obtieneTipoDia(4);				// "Incidencia por permanencia"
				a.setIdTipoDia(tipoDia);
			}
		} else if (a.getEntrada() == null && a.getSalida() != null) { 											
				TipoDiaDto tipoDia = obtieneTipoDia(2);				//no checó entrada: "Omisión de Entrada"
				a.setIdTipoDia(tipoDia);
		}
	}
	
	private void calculoInasistencia(List<AsistenciaDto> listaAsistencia) {
		//obtiene la fecha de hoy
		Timestamp diaAyer = new Timestamp(new Date().getTime());
		
		/**SimpleDateFormat sdf = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD);**/
		Calendar c = Calendar.getInstance();
		
		c.setTime(diaAyer);
		c.add(Calendar.DAY_OF_MONTH, -1); //se resta un día a la fecha porque el servicio corre al día siguiente 
		c.set(Calendar.HOUR, 0);          //no interesa la hora
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        diaAyer.setTime(c.getTimeInMillis());
		
		//se obtienen los usuarios que están de vacaciones, licencias, comisiones. Hoy
		List<String> listaEmpleadosDeVacacionesHoy = asistenciaRepository.obtieneListaEmpleadosDeVacacionesHoy();
		List<String> listaEmpleadosDeComisionHoy = asistenciaRepository.obtieneListaEmpleadosDeComisionHoy();
		List<String> listaEmpleadosDeLicenciaHoy = asistenciaRepository.obtieneListaEmpleadosDeLicenciaHoy();
		List<String> listaEmpleadosConPermiso = new ArrayList<>();
		
		//los usuarios en vacaciones, comisión y licencia se agrupan en una sola lista
		for (String permiso : listaEmpleadosDeVacacionesHoy) {
			listaEmpleadosConPermiso.add(permiso);
		}
		
		for (String permiso : listaEmpleadosDeComisionHoy) {
			listaEmpleadosConPermiso.add(permiso);
		}
		
		for (String permiso : listaEmpleadosDeLicenciaHoy) {
			listaEmpleadosConPermiso.add(permiso);
		}
		
		//se aplican las reglas para crear inasistencias
		reglasParaCrearInasistencias(listaAsistencia, listaEmpleadosConPermiso, diaAyer);
		
	}
	
	private void reglasParaCrearInasistencias(List<AsistenciaDto> listaAsistencia, List<String> listaEmpleadosConPermiso, Timestamp diaAyer) {
		for (UsuarioDto usuario : listaUsuarios) {
			if (!listaClaveUsuariosEnAsistencia.contains(usuario.getClaveUsuario())) {
				AsistenciaDto asistencia = new AsistenciaDto();
				
				//empleados que faltaron justificadamente
				if (!listaEmpleadosConPermiso.isEmpty()) {

					//si el usuario no está de vacación, comision ó licencia se le genera inasistencia
					if (!listaEmpleadosConPermiso.contains(usuario.getClaveUsuario())) {

						//se revisa si es día inhábil y se crea día inhábil
						esDiaInhabil(asistencia, diaAyer, listaAsistencia, usuario);
						
					} else {
						//cuando el empleado que faltó sí se encuentra de vacaciones no se requiere ninguna acción
						logger.info("el empleado se encuentra con permiso: {} ","* " + usuario.getClaveUsuario() );
					}
					
				} else { //si no hay ningún usuario que esté de vacación, de comisión ó licencia, entonces se revisa si es día inhábil
						
					//se revisa si es día inhábil y se crea día inhábil
					esDiaInhabil(asistencia, diaAyer, listaAsistencia, usuario);
					
				}
			} 
		}
	}
	
	private void esDiaInhabil(AsistenciaDto asistencia, Timestamp ayer, List<AsistenciaDto> listaAsistencia, UsuarioDto usuario) {
		List<DiaFestivoDto> listaDiaFestivo = diaFestivoRepository.obtenerListaDiasFestivos();
		
		//obtiene día de ayer
		Boolean esInhabil = false;
		Date diaAyer = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setLenient(false);
	    c.setTime(diaAyer);
	    c.add(Calendar.DATE, -1);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE,0);
	    c.set(Calendar.SECOND,0);
	    c.set(Calendar.MILLISECOND,0);
	    diaAyer = c.getTime();
		
		//compara los días festivos con el día de ayer		
		for (DiaFestivoDto diaFestivo : listaDiaFestivo) {
			
			//si el día de ayer está en la lista de día inhábil, entonces es día inhábil
			if (diaAyer.compareTo(diaFestivo.getFecha()) == 0) {
				esInhabil = true;
			} 
		}
		
		if (esInhabil) { //al ser día inhábil crea una asistencia con día inhábil
			TipoDiaDto tipoDiaInhabil = new TipoDiaDto();
			tipoDiaInhabil.setIdTipoDia(9); //día inhábil
			
			asistencia.setEntrada(ayer);
			asistencia.setUsuarioDto(usuario);
			asistencia.setIdTipoDia(tipoDiaInhabil); 	
			
			listaAsistencia.add(asistencia);
		} else { //al ser día hábil crea una inasistencia
			TipoDiaDto tipoDiaInasistencia = obtieneTipoDia(8);
			
			asistencia.setEntrada(ayer);
			asistencia.setUsuarioDto(usuario);
			asistencia.setIdTipoDia(tipoDiaInasistencia);
			
			listaAsistencia.add(asistencia);
		}
	}
	
	private void calculaEntradaSalida(Timestamp fechaEvento, Timestamp fechaEventoSiguiente, List<Timestamp> listaEventosMultiples,
			AsistenciaDto asistenciaCalculada, List<AsistenciaDto> listaAsistenciaCalculada, UsuarioChecada usuarioChecada, int i) {
		
		//null: no hay evento siguiente
		if (fechaEventoSiguiente != null) {
			Calendar calendar = Calendar.getInstance();
			//se configura en ceros la hora de las fechas del evento actual y siguiente
			calendar.setTime(new Date(fechaEvento.getTime()));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			 
			Date fechaEventoSinHora = calendar.getTime();
			
			calendar.setTime(new Date(fechaEventoSiguiente.getTime()));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			Date fechaEventoSiguienteSinHora = calendar.getTime();
			
			/*
			 * definición de entrada y salida
			 */
			
			//¿ocurrieron varios eventos en el mismo día? entonces se puede calcular entrada y salida
			if (fechaEventoSinHora.compareTo(fechaEventoSiguienteSinHora) == 0) {							
				//el primer evento del día se coloca como entrada
				asistenciaCalculada.setEntrada(fechaEvento);
				
				//se guarda en la lista el segundo evento en el mismo día
				listaEventosMultiples.add(fechaEventoSiguiente);
				
				buscaMasEventosDelDia(usuarioChecada, fechaEvento, fechaEventoSiguiente, listaEventosMultiples, i);
				
				//el último evento del día se coloca como salida
				int ultimoEventoDelDia = listaEventosMultiples.size() - 1;
				asistenciaCalculada.setSalida(listaEventosMultiples.get(ultimoEventoDelDia));
				
				if (!listaEventosMultiples.contains(fechaEvento)) { 
					listaAsistenciaCalculada.add(asistenciaCalculada);
				}							
			} else {
				//Evento que sólo tiene entrada
				asistenciaCalculada.setEntrada(fechaEvento);
				asistenciaCalculada.setSalida(null);
				if (!listaEventosMultiples.contains(fechaEvento)) { 
					listaAsistenciaCalculada.add(asistenciaCalculada);
				}	
			}
		} else { //al ser el último evento sólo se coloca la entrada
			asistenciaCalculada.setEntrada(fechaEvento);
			asistenciaCalculada.setSalida(null);
			if (!listaEventosMultiples.contains(fechaEvento)) { 
				listaAsistenciaCalculada.add(asistenciaCalculada);
			}
		}
	}
}


class UsuarioChecada {
	private String idUsuario;
	private List<Timestamp> listaChecadas = new ArrayList<>();
	
	public UsuarioChecada() {
		/**
		 * 
		 */
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public List<Timestamp> getListaChecadas() {
		return listaChecadas;
	}
	public void setListaChecadas(Timestamp checada) {
		this.listaChecadas.add(checada);
	}
		
}
