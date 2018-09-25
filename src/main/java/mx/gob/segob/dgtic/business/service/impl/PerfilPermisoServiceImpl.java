package mx.gob.segob.dgtic.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.PerfilPermisoRules;
import mx.gob.segob.dgtic.business.service.PerfilPermisoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilPermisoDto;

@Service
public class PerfilPermisoServiceImpl implements PerfilPermisoService {

	@Autowired
	private PerfilPermisoRules perfilPermisoRules;

	@Override
	public void agregaPerfilPermiso (PerfilPermisoDto perfilPermisoDto) {
		
		perfilPermisoRules.agregaUsuarioPerfil(perfilPermisoDto);
	}
	
	
}
