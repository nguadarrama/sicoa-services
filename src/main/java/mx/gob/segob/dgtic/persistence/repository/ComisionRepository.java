package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;

public interface ComisionRepository {

	public List<ComisionDto> obtenerListaComisiones();
	public ComisionDto buscaComision(Integer idComision);
	public void modificaComision(ComisionDto comisionDto);
	public void agregaComision(ComisionDto comisionDto);
	public void eliminaComision(Integer idComision);
}
