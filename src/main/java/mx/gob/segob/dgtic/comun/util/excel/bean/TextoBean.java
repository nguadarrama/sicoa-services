/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.excel.bean;

import java.io.Serializable;

/**
 * Clase que contiene los atributos para un texto.
 */
public class TextoBean implements Serializable {
    
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -5866287201646362682L;

    /** El texto. */
    private String texto;

    /** El numero filas del texto. */
    private int numeroFilas;

    /** El formato a aplicat. */
    private FormatoCeldaBean formato;

    /**
     * Obtiene el texto.
     * 
     * @return el texto.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Asigna el texto.
     * 
     * @param texto el texto.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Obtiene el numero filas.
     * 
     * @return el numero filas.
     */
    public int getNumeroFilas() {
        return numeroFilas;
    }

    /**
     * Asigna el numero filas.
     * 
     * @param numeroFilas el numero filas.
     */
    public void setNumeroFilas(int numeroFilas) {
        this.numeroFilas = numeroFilas;
    }

    /**
     * Obtiene el formato
     * 
     * @return el formato
     */
    public FormatoCeldaBean getFormato() {
        return formato;
    }

    /**
     * Asigna el formato
     * 
     * @param formato el formato
     */
    public void setFormato(FormatoCeldaBean formato) {
        this.formato = formato;
    }

}
