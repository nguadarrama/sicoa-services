package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;

public interface TipoDiaRepository {
	
	public List<TipoDiaDto> obtenerListaTipoDias();
	public TipoDiaDto buscaTipoDia(Integer idTipoDia);
	public void modificaTipoDia(TipoDiaDto tipoDiaDto);
	public void agregaTipoDia(TipoDiaDto tipoDiaDto);
	public void eliminaTipoDia(Integer idTipoDia);
}
