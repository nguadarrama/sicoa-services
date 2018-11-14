package mx.gob.segob.dgtic.business.rules.catalogo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.crypto.HashUtils;
import mx.gob.segob.dgtic.persistence.repository.CargaInicialRepository;

@Component
public class CargaInicialRules {
	
	@Autowired 
	private CargaInicialRepository cargaInicialRepository;
	
	public List<UsuarioDto>  recuperarUsuariosCargaInicial(){
		List<UsuarioDto> listaUsuariosRecuperados;
		
		
		listaUsuariosRecuperados= cargaInicialRepository.recuperarUsuariosCargaInicial();
		for(UsuarioDto usuarioDto: listaUsuariosRecuperados){
			usuarioDto.setPassword(HashUtils.md5(usuarioDto.getPassword()));
		}
		return listaUsuariosRecuperados;
	}
}
