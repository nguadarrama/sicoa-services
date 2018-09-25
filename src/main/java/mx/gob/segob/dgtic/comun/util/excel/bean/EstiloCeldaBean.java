/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.excel.bean;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.Serializable;

/**
 * Clase que contiene los atributos para dar estilo a una celda
 */
public class EstiloCeldaBean implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8319415573256252538L;

    /** La alineaci&oacute;n. */
    private short alinear;

    /** Alineaci&oacute;n vertical. */
    private short alinearVertical;

    /** Fondo de la celda. */
    private short background;

    /** Patron de llenado. */
    private short fillPatern;

    /** Borde izquierdo. */
    private short borderIzquierdo;

    /** Borde superior. */
    private short borderTop;

    /** Formato de la fuente fuente. */
    private FormatoFuenteBean fuente;

    /** Estilo de la celda. */
    private CellStyle estilo;


    /**
     * Instanci un estilo nuevo
     *
     * @param poLibro El libro del que se generara el estilo de la celda
     */
    public EstiloCeldaBean(Workbook poLibro) {
        super();
        this.estilo = poLibro.createCellStyle();
    }


    /**
     * Obtiene la alineaci&oacute;n
     *
     * @return la alineaci&oacute;n
     */
    public short getAlinear() {
        return alinear;
    }

    /**
     * Asigna la alineaci&oacute;n
     *
     * @param alinear La alineaci&oacute;n
     */
    public void setAlinear(short alinear) {
        this.estilo.setAlignment(alinear);
        this.alinear = alinear;
    }

    /**
     * Obtener la alineaci&oacute;n vertical
     *
     * @return La alineaci&oacute;n vertical
     */
    public short getAlinearVertical() {
        return alinearVertical;
    }

    /**
     * Asigna la alineaci&oacute;n vertical
     *
     * @param alinearVertical la alineaci&oacute;n vertical
     */
    public void setAlinearVertical(short alinearVertical) {
        this.estilo.setVerticalAlignment(alinearVertical);
        this.alinearVertical = alinearVertical;
    }

    /**
     * Obtiene el fondo de pantalla
     *
     * @return el fondo de pantalla
     */
    public short getBackground() {
        return background;
    }

    /**
     * Asigna el fondo de pantalla
     *
     * @param background el fondo de pantalla
     */
    public void setBackground(short background) {
        this.estilo.setFillForegroundColor(background);
        this.background = background;
    }

    /**
     * Obtiene el patron de llenado
     *
     * @return el patron de llenado
     */
    public short getFillPatern() {
        return fillPatern;
    }

    /**
     * Asigna el patron de llenado
     *
     * @param fillPatern el patron de llenado
     */
    public void setFillPatern(short fillPatern) {
        this.estilo.setFillPattern(fillPatern);
        this.fillPatern = fillPatern;
    }

    /**
     * Obtiene el borde izquierdo.
     *
     * @return el borde izquierdo
     */
    public short getBorderIzquierdo() {
        return borderIzquierdo;
    }

    /**
     * Asigna el borde izquierdo.
     *
     * @param borderIzquierdo el borde izquierdo
     */
    public void setBorderIzquierdo(short borderIzquierdo) {
        this.estilo.setBorderRight(borderIzquierdo);
        this.borderIzquierdo = borderIzquierdo;
    }

    /**
     * Obtiene el borde superior
     *
     * @return el borde superior
     */
    public short getBorderTop() {
        return borderTop;
    }

    /**
     * Asigna el borde superior
     *
     * @param borderTop el borde superior
     */
    public void setBorderTop(short borderTop) {
        this.estilo.setBorderTop(borderTop);
        this.borderTop = borderTop;
    }



    /**
     * Obtiene la fuente
     *
     * @return la fuente
     */
    public FormatoFuenteBean getFuente() {
        return fuente;
    }

    /**
     * Asigna la fuente
     *
     * @param fuente la fuente
     */
    public void setFuente(FormatoFuenteBean fuente) {
        this.estilo.setFont(fuente.getFuente());
        this.fuente = fuente;
    }

    /**
     * Obtiene el estilo.
     *
     * @return el
     */
    public CellStyle getEstilo() {
        return estilo;
    }

    /**
     * Asigna los bordes a los estilos 
     *
     * @param border el borde a aplicar
     */
    public void setBorders(short border) {
        this.estilo.setBorderRight(border);
        this.estilo.setBorderTop(border);
        this.estilo.setBorderLeft(border);
        this.estilo.setBorderBottom(border);

        this.borderIzquierdo = border;
        this.borderTop = border;
    }

    /**
     * Asigna el color a los bordes
     *
     * @param borderColor el color de los bordes
     */
    public void setBordersColor(short borderColor) {
        this.estilo.setBottomBorderColor( borderColor );
        this.estilo.setTopBorderColor( borderColor );
        this.estilo.setLeftBorderColor( borderColor );
        this.estilo.setRightBorderColor( borderColor );
    }
}
