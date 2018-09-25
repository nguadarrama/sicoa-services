package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.PermisoRules;
import mx.gob.segob.dgtic.business.service.PermisoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PermisoDto;

@Service
public class PermisoServiceImpl implements PermisoService {
	
	@Autowired
	private PermisoRules permisoRules;
	
	@Override
	public List<PermisoDto> obtenerListaPermisos() {
		
		return permisoRules.obtenerListaPerfiles();
	}
	
	@Override
	public PermisoDto buscaPermiso (String idPermiso){
		return permisoRules.buscaPermiso(idPermiso);
	}
	
	@Override
	public void modificaPermiso(PermisoDto permisoDto){
		permisoRules.modificaPermiso(permisoDto);
	}
	
	@Override
	public void agregaPermiso(PermisoDto permisoDto){
		permisoRules.agregaPermiso(permisoDto);
	}
	
	@Override
	public void eliminaPermiso(String idPermiso){
		permisoRules.eliminaPermiso(idPermiso);
	}
}
