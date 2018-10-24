package mx.gob.segob.dgtic.business.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.gob.segob.dgtic.business.rules.catalogo.PeriodoRules;
import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioRules;
import mx.gob.segob.dgtic.business.rules.catalogo.VacacionPeriodoRules;
import mx.gob.segob.dgtic.business.service.PeriodoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Service
public class PeriodoServiceImpl extends RecursoBase implements PeriodoService {

	@Autowired
	private PeriodoRules periodoRules;
	@Autowired
	private VacacionPeriodoRules vacacionRules;
	@Autowired
	private UsuarioRules usuarioRules;

	@Transactional
	@Override
	public List<PeriodoDto> obtenerListaPeriodos() {
		List<PeriodoDto> listaP;
		System.out.println("PeriodoServiceImpl");
		listaP = periodoRules.obtenerListaPeriodos();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.info(" listaObtenida: "+gson.toJson(listaP.size()));
		return listaP;
	}

	@Override
	public PeriodoDto buscaPeriodo(Integer idPeriodo) {
		// PeriodoDto periodoDto=null;
		// periodoDto= periodoRules.buscaPeriodo(idPeriodo);
		return periodoRules.buscaPeriodo(idPeriodo);
		// System.out.println("periodo "+periodoDto.getIdPerfil());

		// return perfilDto;
	}

	@Override
	public void modificaPeriodo(PeriodoDto periodoDto) {
		periodoRules.modificaPeriodo(periodoDto);
	}

	@Transactional
	@Override
	public PeriodoDto agregaPeriodo(PeriodoDto periodoDto) {
		PeriodoDto periodo = new PeriodoDto();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String fin = formatter.format(periodoDto.getFechaInicio());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Date parsedInicio = periodoDto.getFechaInicio();
			Date parsedFin = formatter.parse(fin);

			java.sql.Date fechaInicio = new java.sql.Date(parsedInicio.getTime());
			java.sql.Date fechaFin = new java.sql.Date(parsedFin.getTime());

			// se suman 1.5 años a la fecha fin para incluirla en la fecha de termino de vacaciones
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();

			c.setTime(fechaFin);
			c.add(Calendar.MONTH, 18);
			fechaFin.setTime(c.getTimeInMillis());
			logger.info("fechaFin= "+fechaFin);
			
			/**
			 * Verificando que no exista el periodo a dar de alta
			 */
			boolean existe = false;
			existe = periodoRules.existePeriodo(sdf.format(fechaInicio));
			if(existe) {
				System.out.println("El periodo ya existe");
				periodo.setMensaje("El periodo ya existe, verifique la información. ");
			}
			else {
				periodoDto.setFechaInicio(fechaInicio);
				periodoDto.setFechaFin(fechaFin);
				periodoDto.setDescripcion(periodoDto.getDescripcion());
				periodoDto.setActivo(periodoDto.getActivo());
				
			periodo = periodoRules.agregaPeriodo(periodoDto);
			System.out.println("PeriodoCreado--"+gson.toJson(periodo));
			logger.info("periodo: "+periodo);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(periodoDto.getFechaInicio());
				c2.add(Calendar.MONTH, -6); 
				parsedInicio =  c2.getTime();
				System.out.println("fecha -6meses: "+parsedInicio);
				String fecha = sdf.format(parsedInicio);
				logger.info("periodo cs = "+c2);
				int estatusPeriodo = 1; 
				/************************************************************
				* este estatus correspondera al que se obtenga al dar de alta
				* el periodo de vacaciones en la tabla estatus
				* ***********************************************************/
				List<UsuarioDto> usuarios = usuarioRules.obtenerListaUsuariosActivos(fecha);
				
				System.out.println("usuarios devueltos PeriodoService: "+gson.toJson(usuarios));
				List<PeriodoDto> periodos = periodoRules.topPeriodo();
				for(UsuarioDto user :usuarios) {
					/*************************************************************
					 * Aqui se agregan los periodos a todos los usuarios devueltos
					 *************************************************************/
					int idUltimo = periodos.get(0).getIdPeriodo();
					System.out.println("ultimoIdPeriodo: "+idUltimo);
					vacacionRules.generarVacacionesTodos(user.getIdUsuario(), idUltimo , estatusPeriodo, sdf.format(fechaInicio), 10, periodoDto.getActivo());
				}
			} // fin de else
		} catch (ParseException e) {
			logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
		}	
		System.out.println("return periodoServiceImpl-- method--agregaPeriodo: "+gson.toJson(periodo));
		return periodo;
	}

	@Override
	public void eliminaPeriodo(Integer idPeriodo) {
		periodoRules.eliminaPeriodo(idPeriodo);
	}

	@Override
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario) {
		return periodoRules.buscaPeriodoPorClaveUsuario(claveUsuario);
	}

	@Override
	public void  generaPeriodoVacacional(String inicio, String descripcion, boolean activo) {
		
	}
	
	@Transactional
	@Override
	public PeriodoDto cambiaEstatusPeriodo(PeriodoDto periodo) {
		
		periodo = periodoRules.cambioEstatusPeriodo(periodo);
		periodoRules.modificaEstatustPeridoEmpleados(periodo.getIdPeriodo(), periodo.getActivo());
		return periodo;
	}
}
