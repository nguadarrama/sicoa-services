/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.service;

import mx.gob.segob.dgtic.comun.transport.dto.demo.MapeoComplejoPadreDTO;
import mx.gob.segob.dgtic.comun.transport.dto.demo.TablaDemo;

import java.util.List;

import mx.gob.segob.dgtic.comun.transport.dto.demo.DemoAnotacionesMapperDTO;

/**
 * Created by Hp6460b on 05/10/2017.
 */
public interface DemoService {
    
    /**
     * Obtener usuario by anotaciones.
     *
     * @param nombreUsuario the nombre usuario
     * @return the demo anotaciones mapper DTO
     */
    DemoAnotacionesMapperDTO obtenerUsuarioByAnotaciones(String nombreUsuario);

	/**
	 * Obtener usuario mapeo complejo.
	 *
	 * @param nombreUsuario the nombre usuario
	 * @return the mapeo complejo padre DTO
	 */
	MapeoComplejoPadreDTO obtenerUsuarioMapeoComplejo(String nombreUsuario);

	List<TablaDemo> obtenerDatosDemoPagination(Integer registroInicial, Integer numeroRegistros);

	int obtenertotalregistrosPaginar();
}
