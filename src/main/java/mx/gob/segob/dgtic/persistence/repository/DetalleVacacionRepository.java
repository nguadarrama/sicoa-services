package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;

public interface DetalleVacacionRepository {
	
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones();
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle);
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public DetalleVacacionDto agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public DetalleVacacionDto eliminaDetalleVacacion(Integer idDetalle);
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String idUnidad, String idEstatus);
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, String idPeriodo, String idEstatus, String pfechaInicio, String pfechaFin );
}
