package mx.gob.segob.dgtic.business.service;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionesAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;

public interface DetalleVacacionService {

	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones();
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle);
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public void agregaDetalleVacacion(VacacionesAux detalleVacacionDto);
	public void eliminaDetalleVacacion(Integer idDetalle);
	public void aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, Integer idUnidad, Integer idEstatus);
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, Integer idPeriodo, Integer idEstatus, String pfechaInicio, String pfechaFin );
	public reporte generaReporteVacaciones(GeneraReporteArchivo generaReporteArchivo);
}
