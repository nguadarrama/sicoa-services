/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.builder;

import mx.gob.segob.dgtic.comun.util.config.AplicacionPropertiesUtil;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.SubjectTokenEnum;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.TokenConstants;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.InformacionAdicionalDTO;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.TokenDto;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;


/**
 * Constructor de token de acceso a recursos restringidos.
 */
public class TokenAccesoBuilder {

	/**
	 * Construye el token de acceso
	 *
	 * @param claveUsuario La clave del usuario que se autentico
	 * @param solicitante El solicitante del token
	 * @return Token de acceso a recursos restringidos
	 * @throws TokenBuilderException Excepci&oacute;n al construir el token 
	 */
	public String buildAccesoToken(String claveUsuario, String solicitante) throws TokenBuilderException{

		TokenBuilder tokenBuilder = new TokenBuilder();
		
		//Se obtiene el emisor que identificara donde se creo el token
		String emisorToken = tokenBuilder.obtenerEmisorVariableEntorno();
		validaInformacionSolicitud(claveUsuario, solicitante, emisorToken);

		//Se obtiene el tiempo de vida para el token de acceso
		int tiempoVidaTokenAccesoSegundos = Integer.parseInt(AplicacionPropertiesUtil.getPropiedades().obtenerPropiedad(TokenConstants.TOKEN_ACCESO_EXPIRA_PROPERTY_NAME));
		Date fechaExpira = tokenBuilder.calculaFechaExpira(tiempoVidaTokenAccesoSegundos);

		TokenDto tokenDto = new TokenDto();
		tokenDto.setSolicitante(solicitante);
		tokenDto.setEmisor(emisorToken);
		tokenDto.setFechaExpiraToken(fechaExpira);

		InformacionAdicionalDTO informacionAdicional = new InformacionAdicionalDTO();
		informacionAdicional.setClaveUsuario(claveUsuario);

		tokenDto.setInformacionAdicional(informacionAdicional);
		tokenDto.setAsunto(SubjectTokenEnum.ACCESO);

		///Se construye el token
		return tokenBuilder.buildDtoToToken(tokenDto);
	}
	
	
	/**
	 * Valida informaci&oacute;n solicitud.
	 *
	 * @param claveUsuario La clave del usuario al que se generara el token
	 * @param solicitante El solicitante del token
	 * @param emisorToken El emisor del token
	 * @throws TokenBuilderException Excepci&oacute;n si no se cumple alguna condicionante
	 */
	private void validaInformacionSolicitud(String claveUsuario, String solicitante, String emisorToken) throws TokenBuilderException {
		if(StringUtils.isEmpty(solicitante)){
			throw new TokenBuilderException("Debe enviar el solicitante que consumira los servicios");
		}
		if(StringUtils.isEmpty(emisorToken)){
			throw new TokenBuilderException("Se debe definir la variable de entorno del emisor ");
		}

		if(StringUtils.isEmpty(claveUsuario)){
			throw new TokenBuilderException("Debe definir al menos un valor de nombre de usuario o clave de usuario  ");
		}
	}

}
