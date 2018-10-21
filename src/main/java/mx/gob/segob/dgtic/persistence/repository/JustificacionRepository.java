/**
 * 
 */
package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;

/**
 * @author Anzen Digital
 *
 */
public interface JustificacionRepository {

	public List<JustificacionDto> obtenerListaJustificaciones();

	public JustificacionDto buscaJustificacion(Integer idJustificacion);

	public JustificacionDto modificaJustificacion(JustificacionDto justificacionDto);

	public JustificacionDto agregaJustificacion(JustificacionDto justificacionDto);

	public void eliminaJustificacion(Integer idJustificacion);

	public List<JustificacionDto> obtenerLista();

}
