package mx.gob.segob.dgtic.persistence.repository;


import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;

public interface UsuarioPerfilRepository {
	
	public Integer agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto);
	public void eliminarUsuarioPerfil(Integer idUsuarioPerfil);
	public List<UsuarioPerfilDto> consultaUsuarioPerfil(String claveUsuario);
	public List<UsuarioPerfilDto> consultaUsuarisePerfiles();
	public void actualizaUsuarioPerfil(Integer idUsuarioPerfil);
	
}
