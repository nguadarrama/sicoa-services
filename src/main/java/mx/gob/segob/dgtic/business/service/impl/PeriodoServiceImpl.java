package mx.gob.segob.dgtic.business.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public void agregaPeriodo(PeriodoDto periodoDto) {
		periodoRules.agregaPeriodo(periodoDto);
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
		int periodo = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fin = inicio;
		try {
			Date parsedInicio = formatter.parse(inicio);
			Date parsedFin = formatter.parse(fin);

			java.sql.Date fechaInicio = new java.sql.Date(parsedInicio.getTime());
			java.sql.Date fechaFin = new java.sql.Date(parsedFin.getTime());

			// se suman 1.5 años a la fecha fin para incluirla en la fecha de termino de vacaciones
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-dd");
			Calendar c = Calendar.getInstance();

			c.setTime(fechaFin);
			c.add(Calendar.DAY_OF_MONTH, 548);
			fechaFin.setTime(c.getTimeInMillis());
			logger.info("fechaFin= "+fin);
			periodo = periodoRules.generaPeriodoVacacional(inicio, fin, descripcion, activo);
			logger.info("periodo: "+periodo);
			if(periodo == 1) {
				Calendar c2 = Calendar.getInstance();
				c2.add(Calendar.DAY_OF_MONTH, -183);
//				String fecha = c2;
				String fecha = "";
				logger.info("periodo cs = "+c2);
				int estatusPeriodo = 1; // este estatus correspondera al que se obtenga al dar de alta el periodo de vacaciones en la tabla estatus
				List<UsuarioDto> usuarios = usuarioRules.obtenerListaUsuariosActivos(fecha);
				for(UsuarioDto user :usuarios) {
					logger.info("users: "+user);
					
					vacacionRules.generarVacacionesTodos(user.getIdUsuario(), 1, estatusPeriodo, inicio, 10, activo, fecha);
					break;
				}
				
				/**
				 * Agregar el metodo para insertar el periodo vacacional a todos los empleados con mas de 6 meses de antigüedad
				 * esto en automático
				 */
			}
			
		} catch (ParseException e) {
			logger.warn("Error al convertir la fecha en búsqueda de asistencia: " + e.getMessage());
		}
		
	}
}
