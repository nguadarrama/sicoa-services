/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.excel.bean;

/**
 * The Class FormatoCeldaBean.
 */
public class FormatoCeldaBean {
    
  
    
    /**
     * The componenete excel.
     */
    private ComponenteExcel componeneteExcel;
    
    /**
     * The celda.
     */
    private EstiloCeldaBean celda;
    
    /**
     * The fuente.
     */
    private FormatoFuenteBean fuente;

    /**
     * The Enum ComponenteExcel.
     */
    public enum ComponenteExcel {
        
        /**
         * The titulo.
         */
        TITULO,
        
        /**
         * The subtitulo.
         */
        SUBTITULO,
        
        /**
         * The encabezado.
         */
        ENCABEZADO,
        
        /**
         * The celda non.
         */
        CELDA_NON,
        
        /**
         * The total.
         */
        TOTAL,
        
        /**
         * The pie pagina.
         */
        PIE_PAGINA,
        ;
    }

    /**
     * Gets the componenete excel.
     *
     * @return the componenete excel
     */
    public ComponenteExcel getComponeneteExcel() {
        return componeneteExcel;
    }

    /**
     * Sets the componenete excel.
     *
     * @param componeneteExcel the new componenete excel
     */
    public void setComponeneteExcel(ComponenteExcel componeneteExcel) {
        this.componeneteExcel = componeneteExcel;
    }

    /**
     * Gets the celda.
     *
     * @return the celda
     */
    public EstiloCeldaBean getCelda() {
        return celda;
    }

    /**
     * Sets the celda.
     *
     * @param celda the new celda
     */
    public void setCelda(EstiloCeldaBean celda) {
        this.celda = celda;
    }

    /**
     * Gets the fuente.
     *
     * @return the fuente
     */
    public FormatoFuenteBean getFuente() {
        return fuente;
    }

    /**
     * Sets the fuente.
     *
     * @param fuente the new fuente
     */
    public void setFuente(FormatoFuenteBean fuente) {
        this.fuente = fuente;
    }

}
