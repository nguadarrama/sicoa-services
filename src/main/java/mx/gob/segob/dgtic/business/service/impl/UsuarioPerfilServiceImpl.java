package mx.gob.segob.dgtic.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioPerfilRules;
import mx.gob.segob.dgtic.business.service.UsuarioPerfilService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;

@Service
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService {
	
	@Autowired
	private UsuarioPerfilRules usuarioPerfilRules;
	
	@Override
	public void agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto) {
		
		usuarioPerfilRules.agregaUsuarioPerfil(usuarioPerfilDto);
		
	}
}
