/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.validator;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import mx.gob.segob.dgtic.comun.util.resteasy.token.builder.TokenBuilder;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.TokenDto;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;
import mx.gob.segob.dgtic.comun.util.resteasy.token.rules.TokenIdRules;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;


/**
 * Objeto encargado de realizar las evaluaciones de la cadena del token.
 */
public class TokenValidator {

	/**
	 * El token builder.
	 */
	private TokenBuilder tokenBuilder = new TokenBuilder();
	
	/**
	 * El dto que contiene la informaci&oacute;n que compone el Token
	 */
	private TokenDto tokenDto;

	/**
	 * Bandera que indica si un token a vencido
	 */
	private boolean tokenExpiro;
	/**
	 * Obtiene el token dto que contiene la informaci&oacute;n del token .
	 *
	 * @return El dto del token
	 */
	public TokenDto getTokenDto() {
		return tokenDto;
	}

	
	/**
	 * Crea una instancia del Token Validator con el token a descomponer
	 *
	 * @param tokenHeader El token a descomponer
	 */
	public TokenValidator(String tokenHeader) {
		
		try {
			//Descompone el token en sus atributos.
			tokenDto = tokenBuilder.buildTokenHeaderToDto(tokenHeader);
			tokenExpiro = false;
		} catch (SignatureException e) {
			tokenDto = null;
			tokenExpiro = false;
		} catch (ExpiredJwtException e) {
			tokenDto = null;
			tokenExpiro = true;
		}
	}
	
	/**
	 * Evalua si el token contiene un emisor valido, con respecto al ambiente donde se genero.
	 *
	 * @return true, Si el emisor del token corresponde al ambiente que se envio
	 */
	public boolean esEmisorValido(){
		boolean emisorValido = Boolean.TRUE;
		
		try {
			String emisorEsperado= tokenBuilder.obtenerEmisorVariableEntorno();
			if(StringUtils.isEmpty(tokenDto.getEmisor()) ||
					!tokenDto.getEmisor().equals(emisorEsperado)) {
				emisorValido = Boolean.FALSE;
			}
		} catch (TokenBuilderException e) {
			emisorValido = Boolean.FALSE;
		}
		return emisorValido;
	}
	

	/**
	 * Evalua si el token aun es vigente
	 *
	 * @return true, Si el token es vigente
	 */
	public boolean esTokenVigente(){
		boolean tokenVigente = Boolean.TRUE;
		
		Date ahora = Calendar.getInstance().getTime();
		if( tokenExpiro || ( tokenDto != null && 
							ahora.after(tokenDto.getFechaExpiraToken()))){
			tokenVigente = Boolean.FALSE;
		}
		return tokenVigente;
	}
	
	/**
	 * Se eval&uacute;a si el id del token corresponde a la informaci&oacute;n que contiene el token. 
	 *
	 * @return true, Si la informaci&oacute;n corresponde al id
	 */
	public boolean tieneIdentificadorValido() {
		boolean tokenIdValido = Boolean.TRUE;
		
		//Se construye el id en base a la informaci&oacute;n que conforma el token.
		String idTokenRequest = TokenIdRules.buildTokenId(tokenDto.getInformacionAdicional(), tokenDto.getSolicitante() ,tokenDto.getEmisor());
		if(StringUtils.isEmpty(tokenDto.getIdToken()) ||
				!tokenDto.getIdToken().equals(idTokenRequest)){
			tokenIdValido = Boolean.FALSE;
		}
		return tokenIdValido;
	}

	/**
	 * Evalua si el asunto del token es valido, Corresponde a SubjectTokenEnum.AUTENTICACION, SubjectTokenEnum.ACCESO
	 *
	 * @return true, Si el asunto es correcto
	 */
	public boolean tieneAsuntoValido(){
		return (tokenDto.getAsunto() == null)?Boolean.FALSE : Boolean.TRUE;
	}
	
	/**
	 * Si se puede recuperar la informacion del token es un token valido
	 *
	 * @return true, if successful
	 */
	public boolean esTokenValido() {
		return (tokenDto == null)?Boolean.FALSE : Boolean.TRUE;
	}
	

}
