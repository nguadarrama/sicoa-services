package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;
import mx.gob.segob.dgtic.persistence.repository.UnidadAdministrativaRepository;

@Component
public class UnidadAdministrativaRules {
	
	@Autowired
	private UnidadAdministrativaRepository unidadAdministrativaRepository;
	
	public List<UsuarioUnidadAdministrativaDto> obtenerListaUnidadAdministrativa() {
		
		return unidadAdministrativaRepository.obtenerListaUnidadAdministrativa();
	}
	
	public void consultaRegistraUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto){
		UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaAuxDto = null;
		usuarioUnidadAdministrativaAuxDto=unidadAdministrativaRepository.buscaUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto.getClaveUsuario().getClaveUsuario());
		if(usuarioUnidadAdministrativaAuxDto!=null){
			unidadAdministrativaRepository.actualizaUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		}else{
			unidadAdministrativaRepository.guardaUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		}
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
}
