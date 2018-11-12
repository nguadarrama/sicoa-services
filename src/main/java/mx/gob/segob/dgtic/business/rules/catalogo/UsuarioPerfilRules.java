package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.persistence.repository.UsuarioPerfilRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class UsuarioPerfilRules {
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;
	
	@Autowired UsuarioRepository usuarioRepository;

	public void agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto){
		usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfilDto);
	}
	public Integer insertaEliminaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto){
		Integer r = null;
		List<UsuarioPerfilDto> listaUsuarioPerfil= new ArrayList<>();
		Integer[] arreglo=usuarioPerfilDto.getIdsPerfil();
		listaUsuarioPerfil=usuarioPerfilRepository.consultaUsuarioPerfil(usuarioPerfilDto.getClaveUsuario().getClaveUsuario());
		for(UsuarioPerfilDto usuarioPer: listaUsuarioPerfil){
			usuarioPerfilRepository.eliminarUsuarioPerfil(usuarioPer.getIdUsuarioPerfil());
		}
		for(int i=0; i<arreglo.length;i++){
			PerfilDto perfilDto = new PerfilDto();
			if(arreglo[i]!=null){
					perfilDto.setClavePerfil(""+arreglo[i]);
					System.out.println("inserción del perfil "+arreglo[i]);
					UsuarioPerfilDto usuarioPerfil = new UsuarioPerfilDto();
					usuarioPerfil.setClavePerfil(perfilDto);
					usuarioPerfil.setClaveUsuario(usuarioPerfilDto.getClaveUsuario());
					r = usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfil);
			}else{
				System.out.println("no se puede insertar el perfil "+arreglo[i]);
			}
		}
		return r;
		}
	
	public List<UsuarioPerfilDto> consultaPerfilesPorUsuario(String claveUsuario){
		return usuarioPerfilRepository.consultaUsuarioPerfil(claveUsuario);
	}
}
