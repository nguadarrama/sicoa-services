package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.PermisoDto;
import mx.gob.segob.dgtic.persistence.repository.PermisoRepository;

@Component
public class PermisoRules {

	@Autowired
	private PermisoRepository permisoRepository;
	
	public List<PermisoDto> obtenerListaPerfiles() {
			
			return permisoRepository.obtenerListaPermisos();
	}
	
	public PermisoDto buscaPermiso (String idPermiso){
		return permisoRepository.buscaPermiso(idPermiso);
	}
	
	public void modificaPermiso(PermisoDto permisoDto){
		permisoRepository.modificaPermiso(permisoDto);
	}
	
	public void agregaPermiso(PermisoDto permisoDto){
		permisoRepository.agregaPermiso(permisoDto);
	}
	
	public void eliminaPermiso(String idPermiso){
		permisoRepository.eliminaPermiso(idPermiso);
	}
}
