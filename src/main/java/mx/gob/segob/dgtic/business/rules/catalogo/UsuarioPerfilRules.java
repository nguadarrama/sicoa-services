package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.persistence.repository.UsuarioPerfilRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class UsuarioPerfilRules  extends ServiceBase{
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;
	
	@Autowired UsuarioRepository usuarioRepository;

	public void agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto){
		usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfilDto);
	}
	public Integer insertaEliminaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto){
		Integer r = null;
		List<UsuarioPerfilDto> listaUsuarioPerfil;
		Integer[] arreglo = usuarioPerfilDto.getIdsPerfil();
		System.out.println("Arreglo " + arreglo[0] == null ? " 0 " : arreglo[0]);
		listaUsuarioPerfil=usuarioPerfilRepository.consultaUsuarioPerfil(usuarioPerfilDto.getClaveUsuario().getClaveUsuario());
		for(UsuarioPerfilDto usuarioPer: listaUsuarioPerfil){
			System.out.println("ENTRA");
			usuarioPerfilRepository.eliminarUsuarioPerfil(usuarioPer.getIdUsuarioPerfil());
		}
		for(int i=0; i<arreglo.length;i++){
			PerfilDto perfilDto = new PerfilDto();
			if(arreglo[i]!=null){
					perfilDto.setClavePerfil(""+arreglo[i]);
					logger.info("inserción del perfil; {} " ,arreglo[i]);
					UsuarioPerfilDto usuarioPerfil = new UsuarioPerfilDto();
					usuarioPerfil.setClavePerfil(perfilDto);
					usuarioPerfil.setClaveUsuario(usuarioPerfilDto.getClaveUsuario());
					r = usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfil);
			}else{
				logger.info("no se puede insertar el perfil: {} ");
			}
		}
		return r;
		}
	
	public List<UsuarioPerfilDto> consultaPerfilesPorUsuario(String claveUsuario){
		return usuarioPerfilRepository.consultaUsuarioPerfil(claveUsuario);
	}
}
