package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;


import mx.gob.segob.dgtic.comun.sicoa.dto.PermisoDto;

public interface PermisoRepository {
	
	public List<PermisoDto> obtenerListaPermisos();
	public PermisoDto buscaPermiso(String idPermiso);
	public void modificaPermiso(PermisoDto permisoDto);
	public void agregaPermiso(PermisoDto permisoDto);
	public void eliminaPermiso(String idPermmiso);
}
