package mx.gob.segob.dgtic.business.rules.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.persistence.repository.UsuarioPerfilRepository;

@Component
public class UsuarioPerfilRules {
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	public void agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto){
		usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfilDto);
	}
}
