package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;

public interface DetalleVacacionService {

	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones();
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle);
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public void agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto);
	public void eliminaDetalleVacacion(Integer idDetalle);
}
