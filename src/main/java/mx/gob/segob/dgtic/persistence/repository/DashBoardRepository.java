/**
 * 
 */
package mx.gob.segob.dgtic.persistence.repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.DashBoardDto;

/**
 * @author Anzen Digital
 *
 */
public interface DashBoardRepository {

	public DashBoardDto dashBoard(Integer idUsuario);

}
