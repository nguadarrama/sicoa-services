package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;

public interface JustificacionService {
	
	public List<JustificacionDto> obtenerListaJustificaciones();

	public JustificacionDto buscaJustificacion(Integer idJustificacion);

	public void modificaJustificacion(JustificacionDto justificacionDto);

	public void agregaJustificacion(JustificacionDto justificacionDto);

	public void eliminaJustificacion(Integer idJustificacion);

}
