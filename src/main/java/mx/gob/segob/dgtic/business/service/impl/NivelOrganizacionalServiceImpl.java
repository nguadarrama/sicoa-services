package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mx.gob.segob.dgtic.business.rules.catalogo.NivelOrganizacionalRules;
import mx.gob.segob.dgtic.business.service.NivelOrganizacionalService;
import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;

@Service
public class NivelOrganizacionalServiceImpl implements NivelOrganizacionalService{

	
	@Autowired
	private NivelOrganizacionalRules nivelOrganizacionalRules;
	
	@Transactional
	@Override
	public NivelOrganizacionalDto creaNivel(NivelOrganizacionalDto nivelDto) {
		NivelOrganizacionalDto nivel = new NivelOrganizacionalDto();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		/********************************************************************************
		 * Verificando que no exista el horario en el nivel organizacional a dar de alta
		 ********************************************************************************/
		boolean existe = false;
		existe = nivelOrganizacionalRules.existeNivel(nivelDto.getIdHorario());
		Horario horario = new Horario();
		horario = buscaHorario(nivelDto.getIdHorario());
		System.out.println("horario devuelto: Hora Entrada "+horario.getHoraEntrada().toString()+" -  Hora Salida "+horario.getHoraSalida().toString());
		if(existe) {
			System.out.println("El horario ya existe en el nivel");
			nivel.setMensaje("El registro ya existe en el sistema, favor de validar. ");
		}
		else {
			nivelDto.setNivel(nivelDto.getNivel());
			nivelDto.setIdHorario(nivelDto.getIdHorario());
			nivelDto.setHorario("Hora Entrada "+horario.getHoraEntrada().toString()+" -  Hora Salida "+horario.getHoraSalida().toString());
			
		nivel = nivelOrganizacionalRules.agregaNivel(nivelDto);
		System.out.println("NivelCreado--"+gson.toJson(nivel));
		/**********************************************************************
		 * Aqui se actualiza el id_horario a todos los empleados que forman
		 * parte del nivel seleccionado
		 **********************************************************************/
		actualizaHorarioEmpleado(nivelDto.getIdHorario(), nivelDto.getNivel());
		} // fin de else	
		System.out.println("reutn periodoServiceImpl-- method--agregaPeriodo: "+nivel);
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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Horario horario = new Horario();
		horario = buscaHorario(nivelDto.getIdHorario());
		nv.setIdNivel(nivelDto.getIdNivel());
		nv.setIdHorario(nivelDto.getIdHorario());
		nv.setHorario("Hora Entrada "+horario.getHoraEntrada().toString()+" -  Hora Salida "+horario.getHoraSalida().toString());
		
		System.out.println("NivelOrgServiceImpl--method--modificaNivel: "+gson.toJson(nv));
		nv =  nivelOrganizacionalRules.modificaNivel(nv);
		/********************************************
		 * Actualizando la tabla m_usuario.idHorario
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
