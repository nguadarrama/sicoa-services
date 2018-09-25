package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;

public interface ComisionService {

	public List<ComisionDto> obtenerListacomisiones();
	public ComisionDto buscaComision(Integer idComision);
	public void modificaComision(ComisionDto comisionDto);
	public void agregaComision(ComisionDto comisionDto);
	public void eliminaComision(Integer idComision);
}
