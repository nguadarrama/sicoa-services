package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;

public interface PerfilRepository {
	
	public List<PerfilDto> obtenerListaPerfiles();
	public PerfilDto buscaPerfil(String idPerfil);
	public void modificaPerfil(PerfilDto perfilDto);
	public void agregaPerfil(PerfilDto perfilDto);
	public void eliminaPerfil(String idPerfil);

}
