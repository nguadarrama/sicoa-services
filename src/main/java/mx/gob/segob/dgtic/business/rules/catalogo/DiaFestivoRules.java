package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;

@Component
public class DiaFestivoRules {

	@Autowired
	private DiaFestivoRepository diaFestivoRepository;
	
	public List<DiaFestivoDto> obtenerListaDias() {
		
		return diaFestivoRepository.obtenerListaDiasFestivos();
	}
	
	public DiaFestivoDto buscaDia(int id) {
		
		return diaFestivoRepository.buscaDiaFestivo(id);
	}
	
	public void modificaDiaFestivo(DiaFestivoDto dia) {
		diaFestivoRepository.modificaDiaFestivo(dia);
	}
	
	public void agregaDiaFestivo(DiaFestivoDto dia) {
		diaFestivoRepository.agregaDiaFestivo(dia);
	}
	
	public void eliminaDiaFestivo(Integer id) {
		diaFestivoRepository.eliminaDiaFestivo(id);
	}
}
