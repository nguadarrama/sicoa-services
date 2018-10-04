package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.persistence.repository.TipoDiaRepository;

@Component
public class TipoDiaRules {
	
	@Autowired 
	private TipoDiaRepository tipoDiaRepository; 
	
	public List<TipoDiaDto> obtenerListaTipoDias (){
		return tipoDiaRepository.obtenerListaTipoDias();
	}
	
	
	public TipoDiaDto buscaTipoDia (Integer idTipoDia){
		return tipoDiaRepository.buscaTipoDia(idTipoDia);
	}
	
	
	public void modificaTipoDia (TipoDiaDto tipoDiaDto){
		tipoDiaRepository.modificaTipoDia(tipoDiaDto);
	}
	
	
	public void agregaTipoDia (TipoDiaDto tipoDiaDto){
		tipoDiaRepository.agregaTipoDia(tipoDiaDto);
	}
	
	
	public void eliminaTipoDia (Integer idTipoDia){
		tipoDiaRepository.eliminaTipoDia(idTipoDia);
	}

} 
