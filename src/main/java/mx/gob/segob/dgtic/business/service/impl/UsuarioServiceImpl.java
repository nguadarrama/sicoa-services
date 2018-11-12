package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioRules;
import mx.gob.segob.dgtic.business.service.UsuarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired 
	private UsuarioRules usuarioRules;
	
	@Override
	public List<UsuarioDto> obtenerListaUsuarios() {
		List<UsuarioDto> lista= null;
		lista= usuarioRules.obtenerListaUsuarios();

		return lista;
	}

	@Override
	public UsuarioDto buscaUsuario(String claveUsuario) {
		UsuarioDto usuarioDto= null;
		 usuarioDto = usuarioRules.buscaUsuario(claveUsuario);

		 return usuarioDto;
	}

	@Override
	public Integer modificaUsuario(UsuarioDto usuarioDto) {
		return usuarioRules.modificaUsuario(usuarioDto);
	}

	@Override
	public void agregaUsuario(UsuarioDto usuarioDto) {
		
		usuarioRules.agregaUsuario(usuarioDto);
		
	}

	@Override
	public void eliminaUsuario(String claveUsuario) {
		
		usuarioRules.eliminaUsuario(claveUsuario);
		
	}

	@Override
	public void reiniciaContrasenia(String claveUsuario) {
		usuarioRules.reiniciaContrasenia(claveUsuario);	
	}
	
	@Override
	public UsuarioDto buscaUsuarioPorId(Integer idUsuario) {
		 return usuarioRules.buscaUsuarioPorId(idUsuario);
	}

	@Override
	public List<UsuarioDto> obtenerListaJefes() {
		return usuarioRules.obtenerListajefes();
	}
	
}
