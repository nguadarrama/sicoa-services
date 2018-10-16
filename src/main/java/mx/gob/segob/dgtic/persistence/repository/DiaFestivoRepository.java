package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;

public interface DiaFestivoRepository {

	public List<DiaFestivoDto> obtenerListaDiasFestivos();

	public DiaFestivoDto buscaDiaFestivo(Integer id_festivo);

	public void modificaDiaFestivo(DiaFestivoDto diaFestivo);

	public void agregaDiaFestivo(DiaFestivoDto diaFestivo);

	public void eliminaDiaFestivo(Integer idDia);

}
