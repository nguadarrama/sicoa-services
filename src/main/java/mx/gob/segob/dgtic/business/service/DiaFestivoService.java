/**
 * 
 */
package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;

/**
 * @author Anzen Digital
 *
 */
public interface DiaFestivoService {

	public DiaFestivoDto buscaDiaFestivo(int id);

	public List<DiaFestivoDto> obtenerListaDiasFestivos();

	public void modificaDiaFestivo(DiaFestivoDto dia);

	public void agregaDiaFestivo(DiaFestivoDto dia);

	public void eliminaDiaFestivo(Integer id);

}
