package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;

public interface JustificacionService {
	
	public List<JustificacionDto> obtenerListaJustificaciones();

	public List<JustificacionDto> obtenerLista();
	
	public JustificacionDto buscaJustificacion(Integer idJustificacion);

	public JustificacionDto modificaJustificacion(JustificacionDto justificacionDto);

	public JustificacionDto agregaJustificacion(JustificacionDto justificacionDto);

	public void eliminaJustificacion(Integer idJustificacion);

}
