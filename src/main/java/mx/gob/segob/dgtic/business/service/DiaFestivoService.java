package mx.gob.segob.dgtic.business.service;

import java.util.List;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;

public interface DiaFestivoService {
	
	public List<DiaFestivoDto> obtenerListaDiasFestivos();

	public DiaFestivoDto buscaDiaFestivo(Integer id_festivo);

	public void modificaDiaFestivo(DiaFestivoDto diaFestivo);

	public void agregaDiaFestivo(DiaFestivoDto diaFestivo);

	public void eliminaDiaFestivo(Integer idDia);
}
