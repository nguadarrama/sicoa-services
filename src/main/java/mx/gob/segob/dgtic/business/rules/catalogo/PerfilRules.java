package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;

import mx.gob.segob.dgtic.persistence.repository.PerfilRepository;

@Component
public class PerfilRules {
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	public List<PerfilDto> obtenerListaPerfiles() {
			
			return perfilRepository.obtenerListaPerfiles();
	}
	
	public PerfilDto buscaPerfil (String idPerfil){
		return perfilRepository.buscaPerfil(idPerfil);
	}
	
	public void modificaPerfil(PerfilDto perfilDto){
		perfilRepository.modificaPerfil(perfilDto);
	}
	
	public void agregaPerfil(PerfilDto perfilDto){
		perfilRepository.agregaPerfil(perfilDto);
	}
	
	public void eliminaPerfil(String idPerfil){
		perfilRepository.eliminaPerfil(idPerfil);
	}

}
