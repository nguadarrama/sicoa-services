package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;

public interface UsuarioPerfilService {

	public void agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto);
	public void insertaEliminaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto);
	public List<UsuarioPerfilDto> consultaPerfilesPorUsuario(String claveUsuario);
}
