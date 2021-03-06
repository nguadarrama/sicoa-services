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
	
	public List<DiaFestivoDto> obtenerListaDiasCatalogo(){
		
		return diaFestivoRepository.obtenerListaDiasCatalogo();
		
	}

	public DiaFestivoDto buscaDiaFestivo(Integer idFestivo){
		
		return diaFestivoRepository.buscaDiaFestivo(idFestivo);
		
	}

	public DiaFestivoDto modificaDiaFestivo(DiaFestivoDto diaFestivo){
		
		return diaFestivoRepository.modificaDiaFestivo(diaFestivo);
		
	}

	public DiaFestivoDto agregaDiaFestivo(DiaFestivoDto diaFestivo){
		
		return diaFestivoRepository.agregaDiaFestivo(diaFestivo);
		
	}

	public void eliminaDiaFestivo(Integer idDia){
		
		diaFestivoRepository.eliminaDiaFestivo(idDia);
		
	}
	
	public List<DiaFestivoDto> obtenerDiasFestivosActivos(){
		return diaFestivoRepository.obtenerDiasFestivosActivos();
	}
	
}
