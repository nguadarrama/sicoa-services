package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;

public interface VacacionPeriodoService {

	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo();
	public VacacionPeriodoDto buscaVacacionPeriodo(Integer idVacacion);
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto);
	public void agregaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto);
	public void eliminaVacacionPeriodo(Integer idVacacion);
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario);
	public List<VacacionPeriodoDto> obtenerUsuariosConVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String idUnidad);
}
