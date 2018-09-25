package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.PerfilRules;
import mx.gob.segob.dgtic.business.service.PerfilService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;

@Service
public class PerfilServiceImpl implements PerfilService{

	@Autowired
	private PerfilRules perfilRules;
	
	@Override
	public List<PerfilDto> obtenerListaPerfiles() {
		
		return perfilRules.obtenerListaPerfiles();
	}
	
	@Override
	public PerfilDto buscaPerfil (String idPerfil){
		PerfilDto perfilDto=null;
		perfilDto= perfilRules.buscaPerfil(idPerfil);
		
		System.out.println("perfil "+perfilDto.getDescripcion());
		
		return perfilDto;
	}
	
	@Override
	public void modificaPerfil(PerfilDto perfilDto){
		perfilRules.modificaPerfil(perfilDto);
	}
	
	@Override
	public void agregaPerfil(PerfilDto perfilDto){
		perfilRules.agregaPerfil(perfilDto);
	}
	
	@Override
	public void eliminaPerfil(String idPerfil){
		perfilRules.eliminaPerfil(idPerfil);
	}
	
}
