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
 * Constructor de token de autorizaci&oacute;n para el recurso del login.
 */
public class TokenAutenticacionBuilder {

	/**
	 * Construye el token de autorizaci&oacute;n para el proceso de autenticaci&oacute;n 
	 *
	 * @param solicitante El solicitante de la autorizaci&oacute;n
	 * @return El token de autorizacion
	 * 
	 * @throws TokenBuilderException Excepci&oacute;n al construir el token 
	 */
	public String buildAutenticacionToken(String solicitante) throws TokenBuilderException{
		TokenBuilder tokenBuilder = new TokenBuilder();
		
		//Se obtiene el emisor que identificara donde se creo el token
		String emisorToken = tokenBuilder.obtenerEmisorVariableEntorno();
		validaInformacionSolicitud(solicitante, emisorToken);

		//Se obtiene el tiempo de vida para el token de acceso
		int tiempoVidaTokenAutorizacionSegundos = Integer.parseInt(AplicacionPropertiesUtil.getPropiedades().obtenerPropiedad(TokenConstants.TOKEN_AUTENTICACION_EXPIRA_PROPERTY_NAME));
		Date fechaExpira = tokenBuilder.calculaFechaExpira(tiempoVidaTokenAutorizacionSegundos);

		TokenDto tokenDto = new TokenDto();
		tokenDto.setSolicitante(solicitante);
		tokenDto.setEmisor(emisorToken);
		tokenDto.setFechaExpiraToken(fechaExpira);
		tokenDto.setInformacionAdicional(new InformacionAdicionalDTO());
		tokenDto.setAsunto(SubjectTokenEnum.AUTENTICACION);

		return tokenBuilder.buildDtoToToken(tokenDto);
	}
	
	
	/**
	 * Valida informacion solicitud.
	 *
	 * @param solicitante El solicitante
	 * @param emisorToken El emisor que genera el token
	 * @throws TokenBuilderException  Excepci&oacute;n al construir el token
	 */
	private void validaInformacionSolicitud(String solicitante, String emisorToken) throws TokenBuilderException {
		if(StringUtils.isEmpty(solicitante)){
			throw new TokenBuilderException("Debe enviar el solicitante que consumira los servicios");
		}
		if(StringUtils.isEmpty(emisorToken)){
			throw new TokenBuilderException("Se debe definir la variable de entorno del emisor ");
		}
	}

}
