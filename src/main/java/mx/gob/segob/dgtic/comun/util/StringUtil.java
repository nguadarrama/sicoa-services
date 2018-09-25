/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util;

import java.text.Normalizer;
import java.util.StringTokenizer;

/**
 * Clase de apoyo para manejo de cadenas.
 */
public class StringUtil {

	/**
	 * Constructor de utiler&iacute;a para acceso completamente est&aacute;tico
	 */
	private StringUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * Covierte el valor de una cadena java a valor cadena de BD.
	 *
	 * @param ps La cadena a evaluar
	 * 
	 * @return "NULL" si la cadena de entrada es null, de lo contrario devuelve 'VALOR'
	 */
	public static String esNullDb(String ps) {
		String cadena = null;
		if (esNull(ps).length() == 0) {
			cadena = "null";
		} else {
			cadena = "'" + ps + "'";
		}
		return cadena;
	}

	/**
	 * Convierte una cadena nula a objeto cadena NULLSAFE
	 *
	 * @param ps Cadena a evaluar
	 * @return Cadena vacia si es nulo,de lo contrario el mismo valor evaluado
	 */
	public static String esNull(String ps) {
		return (ps == null) ? "" : ps;
	}

	/**
	 * Convierte una cadena nula o vac&iacute;a a un valor especificado
	 *
	 * @param ps Cadena a evaluar
	 * @param defaultString Valor a asignar si cadena es nula o vacia
	 * @return El valor nuevo.
	 */
	public static String esNull(String ps, String defaultString) {
		return (ps == null || isEmpty(ps)) ? defaultString : ps;
	}

	/**
	 * Convierte un objeto nulo a una cadena vacia o a su representacion toString()
	 *
	 * @param obj El objeto a evaluar
	 * @return La representacion del objeto generado por el metodo toString()
	 */
	public static String esNull(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * Evalua si una cadena es vacia, evalua NULLSAFE
	 *
	 * @param s La cadena a evaluar
	 * @return true, si la cadena es vacia.
	 */
	public static boolean isEmpty(String s) {
		return esNull(s).length() == 0;
	}

	/**
	 * Evalua si una cadena es vacia, convierte a "0" de ser vacia
	 *
	 * @param ps La cadena evaluada
	 * @return La nueva cadena
	 */
	public static String esCero(String ps) {
		return ( esNull(ps).length() == 0 ) ? "0" : ps;
	}

	/**
	 * Evalua si un objeto es null, de ser null devuelve "0", de lo contrario la representaci&oacute;n del metodo toString() 
	 *
	 * @param obj El objeto a evaluar
	 * @return La nueva cadena
	 */
	public static String esCero(Object obj) {
		if (obj == null) {
			return "0";
		}
		return obj.toString();
	}

	/**
	 * Realiza la separacion de una cadena por medio de un delimitador
	 *
	 * @param s La cadena que se aplicara una separaci&oacute;n
	 * @param delimitador El delimitador considerado para realizar la separaci&oacute;n
	 * @return El arreglo de cadenas que se genero
	 */
	public static String[] split(String s, String delimitador) {
		String[] separadores = null;
		StringTokenizer tokens = new StringTokenizer(s, delimitador);
		separadores = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			separadores[i] = tokens.nextToken();
			i++;
		}
		return separadores;
	}


	/**
	 * Verifica NullSafe si la cadena es nula o vacia
	 *
	 * @param s Cadena a evalluar
	 * @return true, Si es nula o vacia
	 */
	public static boolean esNuloVacio(String s) {
		Boolean res = Boolean.FALSE;
		if (s == null || s.trim().isEmpty()) {
			res = Boolean.TRUE;
		}
		return res;
	}


	/**
	 * Limpiar.
	 *
	 * @param original the original
	 * @return the string
	 */
	public static String limpiar(String original) {

		String cadena = Normalizer.normalize( esNull(original) , Normalizer.Form.NFD);
		return cadena.replaceAll("[^\\p{ASCII}]", "");

	}

}
