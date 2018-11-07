package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.segob.dgtic.business.rules.catalogo.NivelOrganizacionalRules;
import mx.gob.segob.dgtic.business.service.NivelOrganizacionalService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;

@Service
public class NivelOrganizacionalServiceImpl extends ServiceBase implements NivelOrganizacionalService{

	
	@Autowired
	private NivelOrganizacionalRules nivelOrganizacionalRules;
	
	@Transactional
	@Override
	public NivelOrganizacionalDto creaNivel(NivelOrganizacionalDto nivelDto) {
		NivelOrganizacionalDto nivel = new NivelOrganizacionalDto();
		/********************************************************************************
		 * VERIFICANDO QUE NO EXISTA EL HORARIO EN EL NIVEL ORGANIZACIONAL A DAR DE ALTA
		 ********************************************************************************/
		boolean existe = false;
		existe = nivelOrganizacionalRules.existeNivel(nivelDto.getIdHorario());
		Horario horario = buscaHorario(nivelDto.getIdHorario());
		
		logger.info("Hora Entrada: {}", horario.getHoraEntrada());
		logger.info("Hora Salida: {}",horario.getHoraSalida());
		if(existe) {
			nivel.setMensaje("El registro ya existe en el sistema, favor de validar. ");
		}
		else {
			nivelDto.setNivel(nivelDto.getNivel());
			nivelDto.setIdHorario(nivelDto.getIdHorario());
			nivelDto.setHorario("Hora Entrada "+horario.getHoraEntrada().toString()+" -  Hora Salida "+horario.getHoraSalida().toString());
			
		nivel = nivelOrganizacionalRules.agregaNivel(nivelDto);
		logger.info("NivelCreado: {}", "");
		gson.toJson(nivel);
		/**********************************************************************
		 *AQUI SE ACTUALIZA  id_horario A TODOS LOS EMPLEADOS QUE FORMAN
		 *PARTE DEL NIVEL SELECCIONADO
		 **********************************************************************/
		actualizaHorarioEmpleado(nivelDto.getIdHorario(), nivelDto.getNivel());
		} 	
		logger.info("reutn periodoServiceImpl-- method--agregaPeriodo: {}",nivel);
		return nivel;
	}

	@Transactional
	@Override
	public List<NivelOrganizacionalDto> obtenerListaNiveles() {
		return nivelOrganizacionalRules.obtenerNiveles();
	}

	@Transactional
	@Override
	public NivelOrganizacionalDto buscaNivel(Integer idNivel) {
		return nivelOrganizacionalRules.buscaNivel(idNivel);
	}

	@Transactional
	@Override
	public NivelOrganizacionalDto modificaNivel(NivelOrganizacionalDto nivelDto) {
		NivelOrganizacionalDto nv = new NivelOrganizacionalDto();
		Horario horario = buscaHorario(nivelDto.getIdHorario());
		nv.setIdNivel(nivelDto.getIdNivel());
		nv.setIdHorario(nivelDto.getIdHorario());
		nv.setHorario("Hora Entrada "+horario.getHoraEntrada().toString()+" -  Hora Salida "+horario.getHoraSalida().toString());
		logger.info("NivelOrgServiceImpl--method--modificaNivel: {} "," ");
		gson.toJson(nv);
		nv =  nivelOrganizacionalRules.modificaNivel(nv);
		/********************************************
		 *ACTUALIZANDO LA TABLA m_usuario.idHorario
		 ********************************************/
		nivelOrganizacionalRules.actualizaHorarioEmpleado(nivelDto.getIdHorario(), nivelDto.getNivel());
		return nv;
	}

	@Transactional
	@Override
	public NivelOrganizacionalDto eliminaNivel(Integer idNivel) {
		return nivelOrganizacionalRules.eliminaNivel(idNivel);
		
	}

	@Transactional
	@Override
	public List<UsuarioDto> nivelesEmpleado() {
		return nivelOrganizacionalRules.nivelesEmpleado();
	}

	@Override
	public Integer actualizaHorarioEmpleado(Integer idHorario, String nivel) {
		return nivelOrganizacionalRules.actualizaHorarioEmpleado(idHorario, nivel);
	}

	@Override
	public Horario buscaHorario(Integer idHorario) {
		return nivelOrganizacionalRules.obtenerHorario(idHorario);
	}

}
