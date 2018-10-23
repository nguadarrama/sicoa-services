package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
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
	public void insertaEliminaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto){
		System.out.println("Llegamos "+usuarioPerfilDto.getIdsPerfil()[0]);
		List<UsuarioPerfilDto> listaUsuarioPerfil= new ArrayList<>();
		Integer[] arreglo=usuarioPerfilDto.getIdsPerfil();
		listaUsuarioPerfil=usuarioPerfilRepository.consultaUsuarioPerfil(usuarioPerfilDto.getClaveUsuario().getClaveUsuario());
		for(UsuarioPerfilDto usuarioPer: listaUsuarioPerfil){
			System.out.println("Elimina "+usuarioPer.getIdUsuarioPerfil());
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
					usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfil);
			}else{
				System.out.println("no se puede insertar el perfil "+arreglo[i]);
			}
		}
		
//		for(int i=0; i<arreglo.length;i++){
//			System.out.println("Llegamos1.1 "+arreglo[i]);
//				boolean banderaInserta=false;
//				if(listaUsuarioPerfil.size()>0){
//					for(UsuarioPerfilDto usuarioPer: listaUsuarioPerfil){
//						
//							/*if(arreglo[i]==Integer.parseInt(usuarioPer.getClavePerfil().getClavePerfil())){
//								System.out.println("Llegamos1 "+arreglo[i] +" "+Integer.parseInt(usuarioPer.getClavePerfil().getClavePerfil()));
//								banderaInserta=false;
//							}else*/ if(arreglo[i]!=Integer.parseInt(usuarioPer.getClavePerfil().getClavePerfil())){
//								System.out.println("archivos diferentes "+arreglo[i] +" "+Integer.parseInt(usuarioPer.getClavePerfil().getClavePerfil()));
//								banderaInserta=true;	
//							}
//						System.out.println("bandera "+banderaInserta);
//						
//					}
//					
//				}else{
//					PerfilDto perfilDto = new PerfilDto();
//					perfilDto.setClavePerfil(""+arreglo[i]);
//					System.out.println("inserción fuera "+arreglo[i]);
//					UsuarioPerfilDto usuarioPerfil = new UsuarioPerfilDto();
//					usuarioPerfil.setClavePerfil(perfilDto);
//					usuarioPerfil.setClaveUsuario(usuarioPerfilDto.getClaveUsuario());
//					usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfil);
//				}
//				if(banderaInserta==true){
//					PerfilDto perfilDto = new PerfilDto();
//					perfilDto.setClavePerfil(""+arreglo[i]);
//					System.out.println("inserción "+arreglo[i]);
//					UsuarioPerfilDto usuarioPerfil = new UsuarioPerfilDto();
//					usuarioPerfil.setClavePerfil(perfilDto);
//					usuarioPerfil.setClaveUsuario(usuarioPerfilDto.getClaveUsuario());
//					usuarioPerfilRepository.agregaUsuarioPerfil(usuarioPerfil);
//					
//				}
//		}
//			for(UsuarioPerfilDto usuarioPer: listaUsuarioPerfil){
//				Boolean banderaElimina=false;
//				if(arreglo.length>0){
//					for(int i=0; i<arreglo.length;i++){
//						if(arreglo[i]==Integer.parseInt(usuarioPer.getClavePerfil().getClavePerfil())){
//							banderaElimina=false;
//						}else if(arreglo[i]!=Integer.parseInt(usuarioPer.getClavePerfil().getClavePerfil())){
//							banderaElimina=true;	
//						}
//					}
//					if(banderaElimina==true){
//						usuarioPerfilRepository.eliminarUsuarioPerfil(usuarioPer.getIdUsuarioPerfil());
//					}
//				}else{
//					usuarioPerfilRepository.eliminarUsuarioPerfil(usuarioPer.getIdUsuarioPerfil());
//				}
//			}
			
		}
	
	public List<UsuarioPerfilDto> consultaPerfilesPorUsuario(String claveUsuario){
		return usuarioPerfilRepository.consultaUsuarioPerfil(claveUsuario);
	}
}
