/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.rules;

import mx.gob.segob.dgtic.comun.util.crypto.HashUtils;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.InformacionAdicionalDTO;

import org.apache.commons.lang3.StringUtils;

/**
 * Reglas de negocio para la construcci&oacute;n del identificador del token
 */
public class TokenIdRules {

	/**
	 * Restringe la generaci&oacute;n de un constructor por defecto
	 */
	private TokenIdRules() {
		throw new IllegalStateException("Rules class");
	}

	/**
	 * Construye el id del token en base a la informacion que lo compone
	 *
	 * @param informacionAdicionalToken La informaci&oacute;n adicional del token
	 * @param solicitante El solicitante del token
	 * @param emisorToken El ambiente que emitio el token
	 * @return El id del token
	 */
	public static String buildTokenId(InformacionAdicionalDTO informacionAdicionalToken, String solicitante, String emisorToken){

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(solicitante);
		stringBuilder.append(".").append(emisorToken);

		if(StringUtils.isEmpty(informacionAdicionalToken.getClaveUsuario())){
			stringBuilder.append(".").append(informacionAdicionalToken.getClaveUsuario());
		}		
		stringBuilder.append(".").append(informacionAdicionalToken.getIdInformacion());

		return HashUtils.md5(stringBuilder.toString());
	}
}
