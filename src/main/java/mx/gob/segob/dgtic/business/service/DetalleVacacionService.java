package mx.gob.segob.dgtic.business.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionesAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.Reporte;
import net.sf.jasperreports.engine.JRException;

public interface DetalleVacacionService {

	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones();
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle);
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public DetalleVacacionDto agregaDetalleVacacion(VacacionesAux detalleVacacionDto);
	public DetalleVacacionDto eliminaDetalleVacacion(Integer idDetalle);
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String idUnidad, String idEstatus);
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, String idPeriodo, String idEstatus, String pfechaInicio, String pfechaFin );
	public Reporte generaReporteVacaciones(GeneraReporteArchivo generaReporteArchivo) throws FileNotFoundException, ParseException, JRException;
	public DetalleVacacionDto cancelaVacaciones(Integer idDetalle);
}
