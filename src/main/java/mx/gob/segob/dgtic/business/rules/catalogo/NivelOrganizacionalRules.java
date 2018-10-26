package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.NivelOrganizacionalRepository;

@Component
public class NivelOrganizacionalRules {
	
	@Autowired
	private NivelOrganizacionalRepository nivelOrganizacionalRepository;
	
	public List<NivelOrganizacionalDto> obtenerNiveles(){
		return nivelOrganizacionalRepository.obtenerListaNiveles();
	}
	
	public NivelOrganizacionalDto buscaNivel(Integer idNivel) {
		return nivelOrganizacionalRepository.buscaNivel(idNivel);
	}
	
	public NivelOrganizacionalDto modificaNivel (NivelOrganizacionalDto nivelDto) {
		return nivelOrganizacionalRepository.modificaNivel(nivelDto);
	}
	
	public NivelOrganizacionalDto agregaNivel (NivelOrganizacionalDto nivelDto) {
		return nivelOrganizacionalRepository.agregaNivel(nivelDto);
	}
	
	public NivelOrganizacionalDto eliminaNivel (Integer idNivel) {
		return nivelOrganizacionalRepository.eliminaNivel(idNivel);
	}
	
	public boolean existeNivel (Integer idHorario) {
		return nivelOrganizacionalRepository.existeNivel(idHorario);
	}

	public List<UsuarioDto> nivelesEmpleado(){
		return nivelOrganizacionalRepository.nivelesEmpleado();
	}
	
	public List<UsuarioDto> empleadosNivel (String nivel){
		return nivelOrganizacionalRepository.empleadosNivel(nivel);
	}
	
	public Integer actualizaHorarioEmpleado(Integer idHorario, String nivel) {
		Integer exitoso = nivelOrganizacionalRepository.actualizaHorarioEmpleado(idHorario, nivel);
		return exitoso;
	}
	
	public Horario obtenerHorario(Integer idHorario) {
		Horario hr = nivelOrganizacionalRepository.buscaHorario(idHorario);
		return hr;
	}
}
