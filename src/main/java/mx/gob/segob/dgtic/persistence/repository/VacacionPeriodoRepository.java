package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;

public interface VacacionPeriodoRepository {

	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo();
	public VacacionPeriodoDto buscaVacacionPeriodo(Integer idVacacion);
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto);
	public void agregaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto);
	public void eliminaVacacionPeriodo(Integer idVacacion);
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario);
	
	public void generarVacacionPeriodotodos(int idUsuario, int idPeriodo, int estatus, String inicio, int dias, boolean activo);
	public List<VacacionPeriodoDto> obtenerUsuariosConVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String idUnidad);
	
}
