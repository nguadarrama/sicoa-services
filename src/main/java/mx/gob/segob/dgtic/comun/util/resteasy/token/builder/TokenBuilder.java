/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.builder;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.jsonwebtoken.*;
import mx.gob.segob.dgtic.comun.util.config.AplicacionPropertiesUtil;
import mx.gob.segob.dgtic.comun.util.config.JndiResourceUtil;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.SubjectTokenEnum;
import mx.gob.segob.dgtic.comun.util.resteasy.token.constant.TokenConstants;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.InformacionAdicionalDTO;
import mx.gob.segob.dgtic.comun.util.resteasy.token.dto.TokenDto;
import mx.gob.segob.dgtic.comun.util.resteasy.token.exception.TokenBuilderException;
import mx.gob.segob.dgtic.comun.util.resteasy.token.rules.TokenIdRules;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Constructor de token's con la estructura definida.
 * <p>
 * El token se genera haciendo uso del est&aacute;ndar JWT (JSON Web Token), donde se requiere la siguiente informaci&oacute;n.
 *  <ul>
 *  	<li><b>solicitante</b> : Una cadena que identifique quien realiza la solicitud</li>
 *  	<li><b>emisor</b> : Una cadena que identifica cual es el origen del token, Se debe configurar una variable de entorno 
 *  		en el servidor de aplicaciones que corresponda a ese origen.</li>
 * 		<li><b>asunto</b> : Cadena que corresponde el asunto al cual corresponde el token. 
 * 			Se cuenta con una constante ({@link mx.gob.segob.dgtic.comun.util.resteasy.token.constant.SubjectTokenEnum}}) 
 * 			que identifica los asuntos disponibles, y que corresponden a un proceso especifico.</li>
 * 		<li><b>expiraci&oacute;n</b> : Una fecha que indica hasta cuando ser&aacute; vigente el token generado</li>
 * 		<li><b>Algoritmo y llave de encriptaci&oacute;n</b> : Se requiere definir una llave y un algoritmo para realizar el firmado del token y asi
 * 			poder verificar que el token es un token valido</li>
 *  	<li><b>Claim de informaci&oacute;n adicional</b> : Un json que contiene informaci&oacute;n adicional a 
 *  	integrar en el token ({@link mx.gob.segob.dgtic.comun.util.resteasy.token.dto.InformacionAdicionalDTO}) . 
 *  </ul>
 *
 * @see io.jsonwebtoken.JwtBuilder
 */
public class TokenBuilder {

    /**
     * La constante para obtener el dominio de las variables de entorno. RESOURCE_DOMINIO_WS_ENV_NAME.
     */
    private static final String RESOURCE_DOMINIO_WS_ENV_NAME = AplicacionPropertiesUtil.getPropiedades().obtenerPropiedad(TokenConstants.JNDI_HOST_WS_PROPERTY_NAME);


    /**
     * Construye el token con la informaci&oacute;n proporcionada 
     *
     *
     * @param tokenDto La informaci&oacute;n que conforma el token
     * @return token generado
     */
    public String buildDtoToToken(TokenDto tokenDto){
        Gson gson = new Gson();
        
        //Se obtiene la llave para encriptar el token 
        String llaveEncriptacion = obtenerLlaveEncriptacionToken();
        
        //Se crea el id del token para la informacion proporcionada
        String idToken = TokenIdRules.buildTokenId(tokenDto.getInformacionAdicional(),
                                                    tokenDto.getSolicitante(),
                                                    tokenDto.getEmisor());


        //Se construye el token
        JwtBuilder builder = Jwts.builder()
                .setId(idToken)
                .setIssuer(Base64.encodeBase64String(tokenDto.getEmisor().getBytes()))
                .setAudience(tokenDto.getSolicitante())
                .setSubject(tokenDto.getAsunto().getSubject())
                .setExpiration(tokenDto.getFechaExpiraToken())
                .signWith(SignatureAlgorithm.HS512, llaveEncriptacion)
                .claim(TokenConstants.CLAIM_INFO_ADICIONAL_TOKEN_NAME, gson.toJson(tokenDto.getInformacionAdicional()));

        return builder.compact();
    }


    /**
     * Obtiene la informaci&oacute;n que conforma el token.
     *
     * @param tokenHeader el  token que proporciona la petici&oacute;n para obtener su informaci&oacute;n
     * @return La informaci&oacute;n que conforma el token
     * @throws SignatureException Excepcion de al firmar el token
     */
    public TokenDto buildTokenHeaderToDto(String tokenHeader) throws SignatureException {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        
        String token = obtenerTokenValue(tokenHeader);

        String llaveEncriptacion = obtenerLlaveEncriptacionToken();

        Claims claims = Jwts.parser().setSigningKey(llaveEncriptacion).parseClaimsJws(token).getBody();

        String idToken = claims.getId();
        String emisor = new String(Base64.decodeBase64(claims.getIssuer()));
        String solicitante = claims.getAudience();
        String asunto = claims.getSubject();
        Date fechaExpira = claims.getExpiration();

        String infoAdicionalToken = claims.get(TokenConstants.CLAIM_INFO_ADICIONAL_TOKEN_NAME, String.class);
        InformacionAdicionalDTO informacionAdicionalDTO = gson.fromJson(jsonParser.parse(infoAdicionalToken), InformacionAdicionalDTO.class);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setIdToken(idToken);
        tokenDto.setEmisor(emisor);
        tokenDto.setSolicitante(solicitante);

        SubjectTokenEnum asuntoEnum = SubjectTokenEnum.findBySubject(asunto);
        tokenDto.setAsunto(asuntoEnum);

        tokenDto.setFechaExpiraToken(fechaExpira);
        tokenDto.setInformacionAdicional(informacionAdicionalDTO);
        return tokenDto;
    }


    /**
     * Obtener emisor variable entorno.
     * Obtiene de la variable de contexto el ambiente que generara el token
     *
     * @return El emisor
     * @throws TokenBuilderException Excepci&oacute;n si no existe la variable configurada
     */
    public String obtenerEmisorVariableEntorno() throws TokenBuilderException{
        String destinatario = null;
        
        if(StringUtils.isEmpty(RESOURCE_DOMINIO_WS_ENV_NAME)){
        	throw new TokenBuilderException("Se debe definir la variable de entorno del emisor (dominio) : "+TokenConstants.JNDI_HOST_WS_PROPERTY_NAME);
        }
        destinatario = JndiResourceUtil.lookupResourceServer(RESOURCE_DOMINIO_WS_ENV_NAME);
        return destinatario;
    }

    /**
     * Calcula fecha expira.
     *
     * @param tiempoSegundos the tiempo segundos
     * @return the date
     */
    public Date calculaFechaExpira(int tiempoSegundos){
        Calendar fechaExpira = Calendar.getInstance();
        fechaExpira.add(Calendar.SECOND, tiempoSegundos);
        return fechaExpira.getTime();
    }


    /**
     * Obtener llave encriptacion token.
     * Obtiene la llave con que se firmara el token, del archivo de configuracion.
     *
     * @return the string
     */
    private String obtenerLlaveEncriptacionToken(){
        String llaveToken = AplicacionPropertiesUtil.getPropiedades().obtenerPropiedad(TokenConstants.TOKEN_KEY_SECRET_PROPERTY_NAME);
        byte[] llaveBytes = Base64.encodeBase64(llaveToken.getBytes());
        return new String(llaveBytes);
    }

    /**
     * Obtener token value.
     *
     * @param tokenHeader the token header
     * @return the string
     */
    private String obtenerTokenValue(String tokenHeader){
    	return tokenHeader.replaceFirst(TokenConstants.AUTHENTICATION_SCHEME_NAME, "").trim();
    }



}
