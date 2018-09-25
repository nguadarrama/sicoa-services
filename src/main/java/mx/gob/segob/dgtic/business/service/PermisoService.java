package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.PermisoDto;


public interface PermisoService {
	
	public List<PermisoDto> obtenerListaPermisos();
	public PermisoDto buscaPermiso(String idPermiso);
	public void modificaPermiso(PermisoDto permisoDto);
	public void agregaPermiso(PermisoDto permisoDto);
	public void eliminaPermiso(String idPermiso);

}
