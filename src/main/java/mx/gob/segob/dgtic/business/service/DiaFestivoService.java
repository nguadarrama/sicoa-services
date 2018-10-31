package mx.gob.segob.dgtic.business.service;

import java.util.List;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;

public interface DiaFestivoService {
	
	public List<DiaFestivoDto> obtenerListaDiasFestivos();
	
	public List<DiaFestivoDto> obtenerListaDiasFestivosCatalogo();

	public DiaFestivoDto buscaDiaFestivo(Integer id_festivo);

	public DiaFestivoDto modificaDiaFestivo(DiaFestivoDto diaFestivo);

	public DiaFestivoDto agregaDiaFestivo(DiaFestivoDto diaFestivo);

	public void eliminaDiaFestivo(Integer idDia);
}
