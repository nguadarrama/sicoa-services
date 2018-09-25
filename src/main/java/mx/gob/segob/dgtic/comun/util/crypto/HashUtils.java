/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.crypto;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  Clase de apoyo para realizar una conversi&oacute;n de HASH.
 */
public class HashUtils {

	/**
	 * Intancia para realizar log 
	 */
	private static final Logger logger = LoggerFactory.getLogger(HashUtils.class);

	/**
	 * Constructor de utiler&iacute;a para acceso completamente est&aacute;tico
	 */
	private HashUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Funci&oacute;n que ejecuta el mismo algoritmo utilizado por la funcion MySql.password()
	 * <p>
	 * Se utiliza un algoritmo de doble hash SHA1
	 *
	 * @param cadena El valor al cual se aplicara el hash indicado 
	 * @return El valor con la conversi&oacute;n password
	 */
	public static String mysqlPassword(String cadena){
		try {
	        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
	        sha1.reset();
	        sha1.update(cadena.getBytes());
	        byte[] encoded1 = sha1.digest();
	
	        sha1.reset();
	        sha1.update(encoded1);
	        byte[] encoded2 = sha1.digest();

	        return "*".concat(new String(Hex.encodeHex(encoded2, false)));
		}  catch (NoSuchAlgorithmException exception) {
			logger.error(exception.getMessage(), exception);
			return cadena;
		}
	}
	
	/**
	 * Funci&oacute;n que ejecuta el algoritmo MD5
	 *
	 * @param cadena El valor al cual se aplicara el hash indicado 
	 * @return  El valor con la conversi&oacute;n MD5
	 */
	public static String md5(String cadena) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(cadena.getBytes());
			byte [] md5Byte = md.digest();
			StringBuilder md5String = new StringBuilder();
			for (byte b : md5Byte) {
				md5String.append(String.format("%02x", b));
			}
			return md5String.toString();
		} catch (NoSuchAlgorithmException exception) {
			logger.error(exception.getMessage(), exception);
			return cadena;
		}
	}
}
