package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;

public interface VacacionPeriodoRepository {

	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo();
	public VacacionPeriodoDto buscaVacacionPeriodo(Integer idVacacion);
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto);
	public void agregaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto);
	public void eliminaVacacionPeriodo(Integer idVacacion);
}
