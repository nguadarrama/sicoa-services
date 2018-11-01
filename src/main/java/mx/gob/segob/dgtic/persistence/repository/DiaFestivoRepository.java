package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;

public interface DiaFestivoRepository {

	public List<DiaFestivoDto> obtenerListaDiasFestivos();

	public DiaFestivoDto buscaDiaFestivo(Integer id_festivo);

	public DiaFestivoDto modificaDiaFestivo(DiaFestivoDto diaFestivo);

	public DiaFestivoDto agregaDiaFestivo(DiaFestivoDto diaFestivo);

	public void eliminaDiaFestivo(Integer idDia);

	public List<DiaFestivoDto> obtenerListaDiasCatalogo();
	public List<DiaFestivoDto> obtenerDiasFestivosActivos();

}
