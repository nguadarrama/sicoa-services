package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;
import mx.gob.segob.dgtic.persistence.repository.UnidadAdministrativaRepository;

@Component
public class UnidadAdministrativaRules {
	
	@Autowired
	private UnidadAdministrativaRepository unidadAdministrativaRepository;
	
	public List<UsuarioUnidadAdministrativaDto> obtenerListaUnidadAdministrativa() {
		
		return unidadAdministrativaRepository.obtenerListaUnidadAdministrativa();
	}
	
	public Integer consultaRegistraUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto){
		UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaAuxDto = null;
		Integer r = null;
		usuarioUnidadAdministrativaAuxDto=unidadAdministrativaRepository.buscaUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto.getClaveUsuario().getClaveUsuario());
		if(usuarioUnidadAdministrativaAuxDto!=null){
			r = unidadAdministrativaRepository.actualizaUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		}else{
			r = unidadAdministrativaRepository.guardaUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		}
		return r;
	}
	
	public List<UsuarioUnidadAdministrativaDto> consultaResponsable(String claveUsuario){
		return unidadAdministrativaRepository.consultaResponsable(claveUsuario);
	}
	
	public List<UsuarioUnidadAdministrativaDto> obtenerUnidadesAdministrativas(){
		return unidadAdministrativaRepository.obtenerUnidadesAdministrativas();
	}

	public List<UsuarioUnidadAdministrativaDto> consultasoloUnidades(){
		return unidadAdministrativaRepository.consultasoloUnidades();
	}
	
	 public List<UnidadAdministrativaDto> obtenerUnidades(){
			return unidadAdministrativaRepository.obtenerUnidades();
		}
}
