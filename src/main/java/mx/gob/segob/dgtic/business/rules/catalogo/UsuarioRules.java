package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class UsuarioRules {

	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	public List<UsuarioDto> obtenerListaUsuarios (){
		return usuarioRepository.obtenerListaUsuarios();
	}
		
	public UsuarioDto buscaUsuario (String claveUsuario){
		return usuarioRepository.buscaUsuario(claveUsuario);
	}
	
	
	public Integer modificaUsuario (UsuarioDto usuarioDto){
		return usuarioRepository.modificaUsuario(usuarioDto);
	}
	
	public void agregaUsuario (UsuarioDto usuarioDto){
		usuarioRepository.agregaUsuario(usuarioDto);
	}
	
	public void eliminaUsuario (String claveUsuario){
		usuarioRepository.eliminaUsuario(claveUsuario);
	}
	
	public String consultaContrasenia(String claveUsuario){
		return usuarioRepository.consultaContrasenia(claveUsuario);
	}
	
	public List<UsuarioDto> obtenerListaUsuariosActivos (String fecha){
		return usuarioRepository.obtenerListaUsuariosActivos(fecha);
	}
	
	public void reiniciaContrasenia(String claveUsuario){
		usuarioRepository.reiniciaContrasenia(claveUsuario);
	}
	
	public UsuarioDto buscaUsuarioPorId(Integer idUsuario){
		return usuarioRepository.buscaUsuarioPorId(idUsuario);
	}
	
	public List<UsuarioDto> obtenerListajefes() {
		return usuarioRepository.obtenerListaJefes();
	}
	
}
