/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util;

import java.util.regex.Pattern;

/**
 * Clase de apoyo para validacion de CURP.
 */
public class CurpUtil {

    /**
     * Constructor de utiler&iacute;a para acceso completamente est&aacute;tico
     */
    private CurpUtil() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Valida CURP.
     * <p>
     * Metodo encargado de evaluar la estructura de un CURP que sea valida en base a las siguientes reglas.
     *  <ul>
	*     <li> Primer carácter alfabético del primer apellido                         </li>
	*     <li> Primer vocal no inicial del primer apellido                            </li>
	*     <li> Primer carácter alfabético del segundo apellido                        </li>
	*     <li> Primer carácter alfabético del primer nombre, en caso de José o María, se empleara el segundo nombre si lo hubiera </li>
	*     <li> Dos últimos dígitos del año de nacimiento                              </li>
	*     <li> Dos dígitos del mes de nacimiento                                      </li>
	*     <li> Dos dígitos del día de nacimiento                                      </li>
	*     <li> Carácter H o M para indicar el género Hombre o Mujer                   </li>
	*     <li> Dos caracteres alfabeticos correspondiente a la clave de la entidad federativa de nacimiento (ver listado) </li>
	*     <li> Primer consonante no inicial del primer apellido                       </li>
	*     <li> Primer consonante no inicial del segundo apellido                      </li>
	*     <li> Primer consonante no inicial del nombre                                </li>
	*     <li> Dos dígitos para evitar duplicidades                                   </li>
	* </ul>
	* 
	*  
     *
     * @param curp El curp a evaluar
     * @return True si es valido
     */
    public static Boolean validaCURP(String curp) {

       

        Boolean res = Boolean.FALSE;

        StringBuilder curpRegex = new StringBuilder();
        
        // se evalua la estructura de apellidos y nombre
        // un caracter alfabetico
        // seguido de una vocal
        // mas dos caracteres alfabeticos
        curpRegex.append("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}");

        // se evalua la estructura fecha de nacimiento
        // anio - dos digitos en cualquier combinacion
        // mes - dos digitos 01-12
        // dia - dos digitos 01-31
        curpRegex.append("(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])");

     // se evalua la estructura de sexo
        // un caracter H o M
        curpRegex.append("[HM]{1}");

        // dos caracteres alfabeticos para la entidad federativa
        // de entre cualquiera de los definidos
        // Aguascalientes AS | Baja California BC | Baja California Sur BS
        // Campeche CC | Coahuila CL | Colima CM | Chiapas CS
        // Chihuahua CH | Distrito Federal DF | Durango DG | Guanajuato GT
        // Guerrero GR | Hidalgo HG | Jalisco JC | México MC
        // Michoacán MN | Morelos MS | Nayarit NT | Nuevo León NL
        // Oaxaca OC | Puebla PL | Querétaro QT | Quintana Roo QR
        // San Luis Potosí SP | Sinaloa SL | Sonora SR | Tabasco TC
        // Tamaulipas TS | Tlaxcala TL | Veracruz VZ | Yucatán YN
        // Zacatecas ZS | Nacido Extranjero NE
        curpRegex.append("(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|");
        curpRegex.append("MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)");

        // tres consonantes consecutivas en cualquier combinacion
        curpRegex.append("[B-DF-HJ-NP-TV-Z]{3}");

        // un digito o caracter alfabetico
        // un digito y termina
        curpRegex.append("[0-9A-Z]{1}[0-9]{1}$");

        Pattern patron = Pattern.compile(curpRegex.toString());
        if (patron.matcher(curp).matches())
            res = Boolean.TRUE;
        return res;

    }

}
