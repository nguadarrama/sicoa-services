package mx.gob.segob.dgtic.business.rules.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilPermisoDto;
import mx.gob.segob.dgtic.persistence.repository.PerfilPermisoRepository;

@Component
public class PerfilPermisoRules {

	@Autowired
	private PerfilPermisoRepository perfilPermisoRepository;

	public void agregaUsuarioPerfil(PerfilPermisoDto perfilPermisoDto){
		perfilPermisoRepository.guardarPermisoRepository(perfilPermisoDto);
	}
}
