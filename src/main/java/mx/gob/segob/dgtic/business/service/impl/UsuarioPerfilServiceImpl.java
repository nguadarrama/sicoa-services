package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

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

	@Override
	public Integer insertaEliminaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto) {
		return usuarioPerfilRules.insertaEliminaUsuarioPerfil(usuarioPerfilDto);
	}

	@Override
	public List<UsuarioPerfilDto> consultaPerfilesPorUsuario(String claveUsuario) {
		return usuarioPerfilRules.consultaPerfilesPorUsuario(claveUsuario);
	}
}
