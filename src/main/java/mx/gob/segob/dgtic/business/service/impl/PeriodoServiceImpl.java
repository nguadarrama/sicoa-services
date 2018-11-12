package mx.gob.segob.dgtic.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
		listaP = periodoRules.obtenerListaPeriodos();
		return listaP;
	}

	@Override
	public List<PeriodoDto> obtenerListaPeriodosCatalogo() {
		return periodoRules.obtenerListaPeriodosCatalogo();
	}
	
	@Override
	public PeriodoDto buscaPeriodo(Integer idPeriodo) {
		return periodoRules.buscaPeriodo(idPeriodo);
	}

	@Override
	public void modificaPeriodo(PeriodoDto periodoDto) {
		periodoRules.modificaPeriodo(periodoDto);
	}

	@Transactional
	@Override
	public PeriodoDto agregaPeriodo(PeriodoDto periodoDto) {
		
		PeriodoDto periodo = new PeriodoDto();
		Date parsedInicio = periodoDto.getFechaInicio();
		Date parsedFin =  periodoDto.getFechaFin();

		java.sql.Date fechaInicio = new java.sql.Date(parsedInicio.getTime());
		java.sql.Date fechaFin = new java.sql.Date(parsedFin.getTime());

		/******************************************
		 * SE SUMAN 1.5 AÑOS A LA FECHA FIN PARA
		 * INCLUIRLA EN LA FECHA DE TERMINO
		 *****************************************/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("fechaFin: {}",fechaFin);
			/*****************************************************
			 * VERIFICANDO QUE NO EXISTA EL PERIODO A DAR DE ALTA
			 *****************************************************/
		boolean existe = false;
		existe = periodoRules.existePeriodo(sdf.format(fechaInicio));
		if(existe) {
			periodo.setMensaje("El periodo ya se encuentra registrado, verifique por favor. ");
		}
		else {
			periodoDto.setFechaInicio(fechaInicio);
			periodoDto.setFechaFin(fechaFin);
			periodoDto.setDescripcion(periodoDto.getDescripcion());
			periodoDto.setActivo(periodoDto.getActivo());
			
		periodo = periodoRules.agregaPeriodo(periodoDto);
		logger.info("periodo: [{}]",periodo);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(periodoDto.getFechaInicio());
			c2.add(Calendar.MONTH, -6); 
			/**parsedInicio =  c2.getTime(); **/
			parsedFin = c2.getTime();
			/**logger.info("fecha -6meses: {}",parsedInicio);
			 String fecha = sdf.format(parsedInicio); **/
			logger.info("fecha Fin -6meses: {} ", parsedFin);
			String fecha = sdf.format(parsedFin);
			logger.info("periodo cs: {}",c2);
			int estatusPeriodo = 1; 
			/************************************************************
			* ESTE ESTATUS CORRESPONDE AL QUE SE OBTENGA AL DAR DE ALTA
			* EL PERIODO DE VACACIONES EN LA TABLA ESTATUS
			* ***********************************************************/
			List<UsuarioDto> usuarios = usuarioRules.obtenerListaUsuariosActivos(fecha);
			List<PeriodoDto> periodos = periodoRules.topPeriodo();
			for(UsuarioDto user :usuarios) {
				/*************************************************************
				 * AGREGANDO PERIODOS A LOS USUARIOS ENCONTRADOS
				 *************************************************************/
				int idUltimo = periodos.get(0).getIdPeriodo();
				/** System.out.println("ultimoIdPeriodo: "+idUltimo); **/
				vacacionRules.generarVacacionesTodos(user.getIdUsuario(), idUltimo , estatusPeriodo, sdf.format(fechaInicio), 10, periodoDto.getActivo());
			}
		}
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
		/**
		 *  
		 */
	}
	
	@Transactional
	@Override
	public PeriodoDto cambiaEstatusPeriodo(PeriodoDto periodo) {
		
		periodo = periodoRules.cambioEstatusPeriodo(periodo);
		periodoRules.modificaEstatustPeridoEmpleados(periodo.getIdPeriodo(), periodo.getActivo());
		return periodo;
	}
}
