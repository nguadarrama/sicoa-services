package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;

public interface DetalleVacacionRepository {
	
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones();
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle);
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public DetalleVacacionDto agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public DetalleVacacionDto eliminaDetalleVacacion(Integer idDetalle);
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(BusquedaDto busquedaDto);
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(BusquedaDto busquedaDto);
	public List<DetalleVacacionDto> buscaDetalleVacacionReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<DetalleVacacionDto> buscaDetalleVacacionReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil);

}
