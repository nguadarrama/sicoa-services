package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

public interface UsuarioRepository {
	public List<UsuarioDto> obtenerListaUsuarios();
	public UsuarioDto buscaUsuario(String claveUsuario);
	public void modificaUsuario(UsuarioDto usuarioDto);
	public void agregaUsuario(UsuarioDto usuarioDto);
	public void eliminaUsuario(String claveUsuario);
	public String consultaContrasenia(String claveUsuario);
	public void reiniciaContrasenia(String claveUsuario);
	public UsuarioDto buscaUsuarioPorId(Integer idUsuario);
	public List<UsuarioDto> obtenerListaUsuariosActivos(String fecha);
	public List<UsuarioDto> obtenerListaJefes();
	
}
