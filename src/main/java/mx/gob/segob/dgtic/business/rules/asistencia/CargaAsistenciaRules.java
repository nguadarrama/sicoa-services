package mx.gob.segob.dgtic.business.rules.asistencia;


import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.business.service.TipoDiaService;
import mx.gob.segob.dgtic.business.service.UsuarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.persistence.repository.CargaAsistenciaRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Component
public class CargaAsistenciaRules extends RecursoBase {
	
	@Autowired
	private CargaAsistenciaRepository asistenciaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TipoDiaService tipoDiaService;
	
	public void procesaAsistencia() {

		logger.info("***** PROCESANDO ASISTENCIA *****");
		
		
		//Filtra asistencia, sólo se procesa aquella asistencia en la que el empleado se encuentra en nómina 
		List<AsistenciaDto> listaAsistencia = filtraAsistencia();
		
		//Se calculan las entradas y salidas
		List<AsistenciaDto> listaAsistenciaEntradaSalida = calculaEntradasSalidas(listaAsistencia);
		
		//Se calculan las incidencias con base a las entradas y salidas
		List<AsistenciaDto> listaAsistenciaCalculada = calculaIncidencias(listaAsistenciaEntradaSalida);
		
		//la asistencia se guarda
		asistenciaRepository.guardaAsistencia(listaAsistenciaCalculada);
		
		logger.info("***** TERMINA PROCESO ASISTENCIA *****");
	}
	
	private List<AsistenciaDto> filtraAsistencia() {
		//obtiene nómina del sistema de nómina (SIRNO)
		List<UsuarioDto> listaUsuarios = usuarioService.obtenerListaUsuarios();
		
		//obtiene asistencia del sistema de asistencias (biométricos - ASISTENCIA)
		List<AsistenciaDto> listaAsistenciaCompleta = asistenciaRepository.obtieneAsistencia();
		
		if (listaAsistenciaCompleta.size() != 0) {
			List<AsistenciaDto> listaAsistenciaFiltrada = new ArrayList<>();
			
			for (AsistenciaDto a : listaAsistenciaCompleta) {
				for (UsuarioDto u : listaUsuarios) {
					
					//id nómina de asistencias ==  id nómina de usuarios: se guarda asistencia en la lista
					if (a.getUsuarioDto().getClaveUsuario().equals(u.getClaveUsuario())) { 
						listaAsistenciaFiltrada.add(a);
					}
					
				}
			}
			
			if (listaAsistenciaFiltrada.size() != 0) {
				logger.info(listaAsistenciaFiltrada.size() + " asistencias de empleados coincidentes");
				return listaAsistenciaFiltrada;
				
			} else {
				logger.info("Entre asistencia y nómina NO se encontraron empleados coincidentes");
				return null;
			}
		} else {
			logger.info("No se encontró asistencia en el sistema de Asistencias");
			return null;
		}

	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	private List<AsistenciaDto> calculaEntradasSalidas(List<AsistenciaDto> listaAsistencia) {
		Set<String> hashSetIdUsuarios = new HashSet<>();
		List<UsuarioChecada> listaUsuarioChecadas = new ArrayList<>();
		List<AsistenciaDto> listaAsistenciaCalculada = new ArrayList<>();
		
		//recupera de la asistencia los id's de usuarios: sin repetidos
		for(AsistenciaDto a : listaAsistencia) {
			hashSetIdUsuarios.add(a.getUsuarioDto().getClaveUsuario());
		}
		
		//agrupa las asistencias de cada id de usuario
		for (String idUsuario : hashSetIdUsuarios) {
			UsuarioChecada usuarioChecadas = new UsuarioChecada();
			usuarioChecadas.setIdUsuario(idUsuario);
			
			for(AsistenciaDto a : listaAsistencia) {
				if (idUsuario.equals(a.getUsuarioDto().getClaveUsuario())) {
					usuarioChecadas.setListaChecadas(a.getEntrada());
				}
			}

			listaUsuarioChecadas.add(usuarioChecadas);
		}
		
		
		logger.info(listaUsuarioChecadas.size() + " Usuarios coincidentes en ASISTENCIA y SIRNO. Calculando entrada y salida.");
		
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
				Timestamp fechaEventoAnterior = null;
				
				//¿la búsqueda hacia adelante del evento siguiente está dentro del tamaño de la lista?
				if (i < usuarioChecada.getListaChecadas().size() - 1) {
					fechaEventoSiguiente = usuarioChecada.getListaChecadas().get(i + 1);
				}
				
				//sólo se calcula evento anterior a partir del segundo elemento de la lista
				if (i > 0) {
					fechaEventoAnterior = usuarioChecada.getListaChecadas().get(i - 1);
				}
				
				//null: no hay evento siguiente
				if (fechaEventoSiguiente != null) {
					
					/*
					 * definición de entrada y salida
					 */
					
					//¿ocurrieron varios eventos en el mismo día? entonces se puede calcular entrada y salida
					if (fechaEvento.getYear() == fechaEventoSiguiente.getYear()) {
						if (fechaEvento.getMonth() == fechaEventoSiguiente.getMonth()) {
							if (fechaEvento.getDate() == fechaEventoSiguiente.getDate()) {							
								//el primer evento del día se coloca como entrada
								asistenciaCalculada.setEntrada(fechaEvento);
								
								//se guarda en la lista el segundo evento en el mismo día
								listaEventosMultiples.add(fechaEventoSiguiente);
								
								//se continúa buscando eventos en el mismo día y se guardan en la lista
								for (int y = 2; true ; y++) {
									
									//¿la búsqueda hacia adelante de los eventos está dentro del tamaño de la lista de eventos?
									if ((i + y) < usuarioChecada.getListaChecadas().size()) {
										if (fechaEvento.getYear() == fechaEventoSiguiente.getYear()) {
											if (fechaEvento.getMonth() == fechaEventoSiguiente.getMonth()) {
												if (fechaEventoSiguiente.getDate() == usuarioChecada.getListaChecadas().get(i + y).getDate()) {
													//se guardan en la lista los demás eventos del mismo día
													listaEventosMultiples.add(usuarioChecada.getListaChecadas().get(i + y));
												} else {
													break; //si no encuentra más eventos en el mismo día, rompe for
												}
											} else {
												break; //si no encuentra más eventos en el mismo día, rompe for
											}
										} else {
											break; //si no encuentra más eventos en el mismo día, rompe for
										}
									} else {
										break; //si no encuentra más eventos en el mismo día, rompe for
									}
								}
								
								
								
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
						} else {
							//Evento que sólo tiene entrada
							asistenciaCalculada.setEntrada(fechaEvento);
							asistenciaCalculada.setSalida(null);
							if (!listaEventosMultiples.contains(fechaEvento)) { 
								listaAsistenciaCalculada.add(asistenciaCalculada);
							}	
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
		
		logger.info(listaAsistenciaCalculada.size() + " se les generó entrada y salida");
		
		return listaAsistenciaCalculada;
	}
	
	private List<AsistenciaDto> calculaIncidencias(List<AsistenciaDto> listaAsistencia) {
		logger.info(listaAsistencia.size() + " calculando incidencias...");
		
		for (AsistenciaDto a : listaAsistencia) {
			
			UsuarioDto usuario = usuarioService.buscaUsuario(a.getUsuarioDto().getClaveUsuario());
			
//			if (a.getEntrada() != null) {
//				if (esPuntualEntrada(a.getEntrada(), usuario.getIdHorario())) {
//					if (a.getSalida() != null) { 
//						if (esPuntualSalida(a.getSalida(), usuario.getIdHorario())) { //Día normal. Fue puntual en entrada y salida
//							TipoDiaDto tipoDia = obtieneTipoDia("Día Normal");
//							a.setIdTipoDia(tipoDia);
//						} else {
//							TipoDiaDto tipoDia = obtieneTipoDia("Omisión de Salida");			//checó salida antes: "Omisión de Salida"
//							a.setIdTipoDia(tipoDia);
//						}
//					} else {																	//no checó salida: "Omisión de Salida"
//						TipoDiaDto tipoDia = obtieneTipoDia("Omisión de Salida");
//						a.setIdTipoDia(tipoDia);
//					}
//				} else {																	  //Incidencia por permanencia
//					TipoDiaDto tipoDia = obtieneTipoDia("Incidencia por Permanencia");
//					a.setIdTipoDia(tipoDia);
//				}
//			} else if (a.getEntrada() == null) { 											//no checó entrada: "Omisión de Entrada"
//				if (a.getSalida() != null) {
//					TipoDiaDto tipoDia = obtieneTipoDia("Omisión de Entrada");
//					a.setIdTipoDia(tipoDia);
//				}
//			}
		}
		
		logger.info(listaAsistencia.size() + " asistencias procesadas. Termina cálculo de incidencias");
		
		return listaAsistencia;
	}
	
	private TipoDiaDto obtieneTipoDia(String nombreTipoDia) {
		
		for (TipoDiaDto t : tipoDiaService.obtenerListaTipoDias()) {
			if (nombreTipoDia.equals(t.getNombre())) {
				return t;
			}
		}
		
		return new TipoDiaDto();
	}
	
	@SuppressWarnings("deprecation")
	private boolean esPuntualEntrada(Timestamp a, Time usuario) {
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
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean esPuntualSalida(Timestamp a, Time usuario) {
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
			return true;
		} else {
			return false;
		}
	}
}


class UsuarioChecada {
	private String idUsuario;
	private List<Timestamp> listaChecadas = new ArrayList<>();
	
	public UsuarioChecada() {
		
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
