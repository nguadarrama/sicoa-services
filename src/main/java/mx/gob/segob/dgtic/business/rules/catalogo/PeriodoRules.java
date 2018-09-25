package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;

@Component
public class PeriodoRules {

	@Autowired
	private PeriodoRepository periodoRepository;
	
	public List<PeriodoDto> obtenerListaPeriodos() {
			
			return periodoRepository.obtenerListaPeriodos();
	}
	
	public PeriodoDto buscaPeriodo (Integer idPeriodo){
		return periodoRepository.buscaPeriodo(idPeriodo);
	}
	
	public void modificaPeriodo(PeriodoDto periodoDto){
		periodoRepository.modificaPeriodo(periodoDto);
	}
	
	public void agregaPeriodo(PeriodoDto periodoDto){
		periodoRepository.agregaPeriodo(periodoDto);
	}
	
	public void eliminaPeriodo(Integer idPeriodo){
		periodoRepository.eliminaPeriodo(idPeriodo);
	}
}
