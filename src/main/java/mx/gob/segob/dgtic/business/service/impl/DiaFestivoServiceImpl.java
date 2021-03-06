package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.segob.dgtic.business.rules.catalogo.DiaFestivoRules;
import mx.gob.segob.dgtic.business.service.DiaFestivoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;

@Service
public class DiaFestivoServiceImpl implements DiaFestivoService {
	
	@Autowired
	private DiaFestivoRules diaFestivoRules;
	

	public List<DiaFestivoDto> obtenerListaDiasFestivos(){
		
		return diaFestivoRules.obtenerListaDiasFestivos();
		
	}
	
	public List<DiaFestivoDto> obtenerListaDiasFestivosCatalogo(){
		
		return diaFestivoRules.obtenerListaDiasCatalogo();
		
	}

	public DiaFestivoDto buscaDiaFestivo(Integer idFestivo){
		
		return diaFestivoRules.buscaDiaFestivo(idFestivo);
		
	}

	public DiaFestivoDto modificaDiaFestivo(DiaFestivoDto diaFestivo){
		
		return diaFestivoRules.modificaDiaFestivo(diaFestivo);
		
	}

	public DiaFestivoDto agregaDiaFestivo(DiaFestivoDto diaFestivo){
		
		return diaFestivoRules.agregaDiaFestivo(diaFestivo);
		
	}

	public void eliminaDiaFestivo(Integer idDia){
		
		diaFestivoRules.eliminaDiaFestivo(idDia);
		
	}

	@Override
	public List<DiaFestivoDto> obtenerDiasFestivosActivos() {
		return diaFestivoRules.obtenerDiasFestivosActivos();
	}
}
