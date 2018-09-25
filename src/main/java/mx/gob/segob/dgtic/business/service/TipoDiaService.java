package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;

public interface TipoDiaService {
	
	public List<TipoDiaDto> obtenerListaTipoDias();
	public TipoDiaDto buscaTipoDia(Integer idPermiso);
	public void modificaTipoDia(TipoDiaDto tipoDiaDto);
	public void agregaTipoDia(TipoDiaDto tipoDiaDto);
	public void eliminaTipoDia(Integer idTipoDia);
}
