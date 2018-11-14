/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.excel.bean;

import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Clase que contiene los atributos de una fuente
 */
public class FormatoFuenteBean implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6199460299460456783L;

    /** La fuente. */
    private Font fuente;

    /** El tama&ntildeo. */
    private short tamanho;

    /** El estilo. */
    private short estilo;

    /** El color. */
    private short color;

    /** El tipo de letra. */
    private String letra;


    /**
     * Instancia un nuevo formato de fuente.
     *
     * @param poLibro El libro del cual se generara la fuente
     */
    public FormatoFuenteBean(Workbook poLibro) {
        super();
        this.fuente = poLibro.createFont();
    }

    /**
     *  Instancia un nuevo formato de fuente.
     *
     * @param poLibro El libro del cual se generara la fuente
     * @param tamanho El tama&ntildeo de la fuente
     * @param estilo El estilo 
     * @param color El color de la fuente
     * @param letra El tipo de letra
     */
    public FormatoFuenteBean(Workbook poLibro, short tamanho, short estilo, 
                             short color, String letra) {
        super();
        this.tamanho = tamanho;
        this.estilo = estilo;
        this.color = color;
        this.letra = letra;

        this.fuente = poLibro.createFont();
        this.fuente.setFontHeightInPoints(tamanho);
        this.fuente.setFontName(letra);
        this.fuente.setBoldweight(estilo);
        this.fuente.setColor(color);
    }

    /**
     * Obtiene la fuente.
     *
     * @return la fuente
     */
    public Font getFuente() {
        return fuente;
    }

    /**
     * Obtiene el tama&ntilde;o
     *
     * @return el tama&ntilde;o
     */
    public short getTamanho() {
        return tamanho;
    }

    /**
     * Asigna el tama&ntilde;o
     *
     * @param tamanho el tama&ntilde;o
     */
    public void setTamanho(short tamanho) {
        this.fuente.setFontHeightInPoints(tamanho);
        this.tamanho = tamanho;
    }

    /**
     * Obtiene El estilo
     *
     * @return el estilo
     */
    public short getEstilo() {
        return estilo;
    }

    /**
     * Asigna el estilo.
     *
     * @param estilo el estilo
     */
    public void setEstilo(short estilo) {
        this.fuente.setBoldweight(estilo);
        this.estilo = estilo;
    }

    /**
     * Obtiene el color.
     *
     * @return el color
     */
    public short getColor() {
        return color;
    }

    /**
     * Asigna el color.
     *
     * @param color el color
     */
    public void setColor(short color) {
        this.fuente.setColor(color);
        this.color = color;
    }

    /**
     * Obtiene el tipo de letra.
     *
     * @return el tipo de letra
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Asigna el tipo de letra
     *
     * @param letra el tipo de letra
     */
    public void setLetra(String letra) {
        this.fuente.setFontName(letra);
        this.letra = letra;
    }

}
