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
	
	@Override
	public List<DiaFestivoDto> obtenerListaDiasFestivos() {
		return diaFestivoRules.obtenerListaDias();
	}

	@Override
	public DiaFestivoDto buscaDiaFestivo(int id) {		
		return diaFestivoRules.buscaDia(id);
	}

	@Override
	public void modificaDiaFestivo(DiaFestivoDto dia) {		
		diaFestivoRules.modificaDiaFestivo(dia);
	}
	
	@Override
	public void agregaDiaFestivo(DiaFestivoDto dia) {		
		diaFestivoRules.agregaDiaFestivo(dia);
	}

	@Override
	public void eliminaDiaFestivo(Integer id) {
		diaFestivoRules.eliminaDiaFestivo(id);
	}
	
	
}
