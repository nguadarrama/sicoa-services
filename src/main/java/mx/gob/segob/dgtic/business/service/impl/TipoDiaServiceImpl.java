package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.TipoDiaRules;
import mx.gob.segob.dgtic.business.service.TipoDiaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
@Service
public class TipoDiaServiceImpl implements TipoDiaService {

	@Autowired 
	private TipoDiaRules tipoDiaRules;
	
	@Override
	public List<TipoDiaDto> obtenerListaTipoDias(){
		return  tipoDiaRules.obtenerListaTipoDias();
	}
	
	@Override 
	public TipoDiaDto buscaTipoDia (Integer idTipoDia){
		return tipoDiaRules.buscaTipoDia(idTipoDia);
	}
	
	@Override 
	public void modificaTipoDia (TipoDiaDto tipoDiaDto){
		tipoDiaRules.modificaTipoDia(tipoDiaDto);
	}
	
	@Override 
	public void agregaTipoDia (TipoDiaDto tipoDiaDto){
		tipoDiaRules.agregaTipoDia(tipoDiaDto);
	}
	
	@Override
	public void eliminaTipoDia (Integer idTipoDia){
		tipoDiaRules.eliminaTipoDia(idTipoDia);
	}
}
