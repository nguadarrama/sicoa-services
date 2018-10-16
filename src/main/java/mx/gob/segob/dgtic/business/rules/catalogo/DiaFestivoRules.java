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
	

	public List<DiaFestivoDto> obtenerListaDiasFestivos(){
		
		return diaFestivoRepository.obtenerListaDiasFestivos();
		
	}

	public DiaFestivoDto buscaDiaFestivo(Integer id_festivo){
		
		return diaFestivoRepository.buscaDiaFestivo(id_festivo);
		
	}

	public void modificaDiaFestivo(DiaFestivoDto diaFestivo){
		
		diaFestivoRepository.modificaDiaFestivo(diaFestivo);
		
	}

	public void agregaDiaFestivo(DiaFestivoDto diaFestivo){
		
		diaFestivoRepository.agregaDiaFestivo(diaFestivo);
		
	}

	public void eliminaDiaFestivo(Integer idDia){
		
		diaFestivoRepository.eliminaDiaFestivo(idDia);
		
	}
	
}
