package mx.gob.segob.dgtic.persistence.repository;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;

public interface DetalleVacacionRepository {
	
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones();
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle);
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public void agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public void eliminaDetalleVacacion(Integer idDetalle);
	public void aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, Integer idUnidad, Integer idEstatus);
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, Integer idPeriodo, Integer idEstatus, String pfechaInicio, String pfechaFin );
}
