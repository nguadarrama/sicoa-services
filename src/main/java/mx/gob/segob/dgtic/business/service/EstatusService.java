package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;

public interface EstatusService {

	public List<EstatusDto> obtenerListaEstatus();
	public EstatusDto buscaEstatus(Integer idEstatus);
	public void modificaEstatus(EstatusDto estatusDto);
	public void agregaEstatus(EstatusDto estatusDto);
	public void eliminaEstatus(Integer idEstatus);
}
