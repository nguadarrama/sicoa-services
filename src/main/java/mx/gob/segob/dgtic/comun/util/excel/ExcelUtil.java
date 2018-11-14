/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.excel;

import mx.gob.segob.dgtic.comun.util.StringUtil;
import mx.gob.segob.dgtic.comun.util.archivo.FileUtil;
import mx.gob.segob.dgtic.comun.util.excel.bean.EstiloCeldaBean;
import mx.gob.segob.dgtic.comun.util.excel.bean.FormatoCeldaBean;
import mx.gob.segob.dgtic.comun.util.excel.bean.FormatoFuenteBean;
import mx.gob.segob.dgtic.comun.util.excel.bean.TextoBean;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.apache.poi.hssf.util.HSSFColor.LIGHT_GREEN;
import org.apache.poi.hssf.util.HSSFColor.WHITE;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase de apoyo para la generaci&oacute;n de archivos excel
 * <p>
 * Ejemplo 
 * 
 * <pre class="code">
* 	String titulo = "Bitacoras";
*   String[] encabezados = {"Usuario","Perfil","Acci\u00f3n","Fecha", "Hora"};
*   int totalCeldas = encabezados.length;
*   
* 	List<String[]> datos = new ArrayList<String[]>(0);
*	datos.add(new String[]{"usuario 1", "Perfil 1", "Accion 1", "01/01/2010", "10:10"});
*	datos.add(new String[]{"usuario 2", "Perfil 2", "Accion 2", "01/01/2011", "10:20"});
*	datos.add(new String[]{"usuario 3", "Perfil 3", "Accion 3", "01/01/2012", "10:30"});
*	datos.add(new String[]{"usuario 4", "Perfil 4", "Accion 4", "01/01/2013", "10:40"});
*
*   ExcelUtil excel = new ExcelUtil(titulo,true);
*   excel.generaTitulo(titulo, totalCeldas);
*   excel.generaEncabezados(encabezados);
*   excel.contenidoListadoNon(datos);
*   excel.adaptarColumnas(encabezados);
*
*   byte[] barchivo = excel.generaArchivoExcel();
 * </pre>
 * 
 * 
 * 
 * 
 */
public class ExcelUtil {

    /**
     * The logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /** The Constant TIPO_LETRA. */
    private static final String TIPO_LETRA = "Arial";

    /** The n filas. */
    private int nFilas;

    /** The columna inicial. */
    private int columnaInicial;

    /** The columna final. */
    private int columnaFinal;

    /** The libro. */
    private Workbook libro;

    /** The hoja. */
    private Sheet hoja;

    /** The titulo. */
    private FormatoCeldaBean titulo;

    /** The subtitulo. */
    private FormatoCeldaBean subtitulo;

    /** The celda non. */
   private FormatoCeldaBean celdaNon;

    /** The encabezado. */
    private FormatoCeldaBean encabezado;

    /** The total. */
    private FormatoCeldaBean total;

    /**
     * The celda firma.
     */
    private boolean celdaFirma;

    /**
     * Instantiates a new excel co.
     */
    public ExcelUtil() {
        this(false,null, false);
    }

    /**
     * Instantiates a new excel util.
     *
     * @param firma the firma
     */
    public ExcelUtil(boolean firma) {
        this(firma,null, false);
    }
    
    /**
     * Instantiates a new excel util.
     *
     * @param psNombreHoja the ps nombre hoja
     */
    public ExcelUtil(String psNombreHoja) {
        this(false, psNombreHoja, false);
    }
    
    /**
     * Instantiates a new excel util.
     *
     * @param psNombreHoja the ps nombre hoja
     * @param isLarge the is large
     */
    public ExcelUtil(String psNombreHoja, boolean isLarge) {
        this(false, psNombreHoja, isLarge);
    }

    /**
     * Instantiates a new excel util.
     *
     * @param firma the firma
     * @param psNombreHoja the ps nombre hoja
     * @param isLarge the is large
     */
    public ExcelUtil(boolean firma, String psNombreHoja, boolean isLarge) {
        super();
        inicializar(firma,psNombreHoja, isLarge);
    }

    /**
     * Inicializar.
     *
     * @param firma the firma
     * @param psNombreHoja the ps nombre hoja
     * @param isLarge the is large
     */
    private void inicializar(boolean firma, String psNombreHoja, boolean isLarge) 
    {
        this.nFilas = 0;
        this.columnaInicial = 0;
        this.columnaFinal = 0;
        this.setCeldaFirma(firma);
        if( isLarge ){
            libro = new SXSSFWorkbook();
            ((SXSSFWorkbook)libro).setCompressTempFiles(true);
            
            hoja = ((SXSSFWorkbook)libro).createSheet((psNombreHoja != null) ? psNombreHoja : "renap");
            ((SXSSFSheet)hoja).setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
        }
        else{
            libro = new HSSFWorkbook();        
            hoja = libro.createSheet((psNombreHoja != null) ? psNombreHoja : "renap");
        }                
        
        short border = 
            (firma) ? CellStyle.BORDER_MEDIUM : CellStyle.BORDER_THIN;

        //Titulo
        titulo = new FormatoCeldaBean();
        titulo.setComponeneteExcel(FormatoCeldaBean.ComponenteExcel.TITULO);
        titulo.setFuente(new FormatoFuenteBean(libro, (short)13, (short)700,
                                               new BLACK().getIndex(), 
                                               TIPO_LETRA));

        EstiloCeldaBean tituloEstiloCeldaBean =new EstiloCeldaBean(libro);
        tituloEstiloCeldaBean.setFuente(titulo.getFuente());
        tituloEstiloCeldaBean.setAlinear(CellStyle.ALIGN_CENTER);
        tituloEstiloCeldaBean.setAlinearVertical(CellStyle.VERTICAL_CENTER);
        tituloEstiloCeldaBean.setBackground(new WHITE().getIndex());
        tituloEstiloCeldaBean.setFillPatern((short)1);
        tituloEstiloCeldaBean.setBorders(CellStyle.BORDER_NONE);
        tituloEstiloCeldaBean.setBordersColor(IndexedColors.WHITE.getIndex());
        titulo.setCelda(tituloEstiloCeldaBean);

        //Subtitulo
        subtitulo = new FormatoCeldaBean();
        subtitulo.setComponeneteExcel(FormatoCeldaBean.ComponenteExcel.SUBTITULO);
        subtitulo.setFuente(new FormatoFuenteBean(libro, (short)11, (short)700, 
                                                  new BLACK().getIndex(), 
                                                  TIPO_LETRA));
        EstiloCeldaBean subtituloEstiloCeldaBean =new EstiloCeldaBean(libro);
        subtituloEstiloCeldaBean.setFuente(subtitulo.getFuente());
        subtituloEstiloCeldaBean.setAlinear(CellStyle.ALIGN_CENTER);
        subtituloEstiloCeldaBean.setAlinearVertical(CellStyle.VERTICAL_CENTER);
        subtituloEstiloCeldaBean.setBackground(new WHITE().getIndex());
        subtituloEstiloCeldaBean.setFillPatern((short)1);
        subtituloEstiloCeldaBean.setBorders(CellStyle.BORDER_NONE);
        subtituloEstiloCeldaBean.setBordersColor(IndexedColors.SEA_GREEN.getIndex());
        subtitulo.setCelda(subtituloEstiloCeldaBean);

        //Encabezado
        encabezado = new FormatoCeldaBean();
        encabezado.setComponeneteExcel(FormatoCeldaBean.ComponenteExcel.ENCABEZADO);
        encabezado.setFuente(new FormatoFuenteBean(libro, (short)10, 
                                                   (short)700, 
                                                   new WHITE().getIndex(), 
                                                   TIPO_LETRA));
        EstiloCeldaBean encabezadoEstiloCeldaBean =new EstiloCeldaBean(libro);
        encabezadoEstiloCeldaBean.setFuente(encabezado.getFuente());
        encabezadoEstiloCeldaBean.setAlinear(CellStyle.ALIGN_LEFT);
        encabezadoEstiloCeldaBean.setAlinearVertical(CellStyle.VERTICAL_CENTER);
        encabezadoEstiloCeldaBean.setBackground(IndexedColors.SEA_GREEN.getIndex());
        encabezadoEstiloCeldaBean.setFillPatern((short)1);
        encabezadoEstiloCeldaBean.setBorders(border);
        encabezadoEstiloCeldaBean.setBordersColor(IndexedColors.SEA_GREEN.getIndex());
        encabezado.setCelda(encabezadoEstiloCeldaBean);

        //Celda Non
        celdaNon = new FormatoCeldaBean();
        celdaNon.setComponeneteExcel(FormatoCeldaBean.ComponenteExcel.CELDA_NON);
        celdaNon.setFuente(new FormatoFuenteBean(libro, (short)9, (short)400, 
                                                 new BLACK().getIndex(), 
                                                 TIPO_LETRA));
        EstiloCeldaBean celdaNonEstiloCeldaBean =new EstiloCeldaBean(libro);
        celdaNonEstiloCeldaBean.setFuente(celdaNon.getFuente());
        celdaNonEstiloCeldaBean.setAlinear(CellStyle.ALIGN_LEFT);
        celdaNonEstiloCeldaBean.setAlinearVertical(CellStyle.VERTICAL_CENTER);
        celdaNonEstiloCeldaBean.setBackground((short)9);
        celdaNonEstiloCeldaBean.setFillPatern((short)1);
        celdaNonEstiloCeldaBean.setBorders(border);
        celdaNonEstiloCeldaBean.setBordersColor(IndexedColors.SEA_GREEN.getIndex());
        celdaNon.setCelda(celdaNonEstiloCeldaBean);

        //Total
        total = new FormatoCeldaBean();
        total.setComponeneteExcel(FormatoCeldaBean.ComponenteExcel.TOTAL);
        total.setFuente(new FormatoFuenteBean(libro, (short)10, (short)700, 
                                              new BLACK().getIndex(), 
                                              TIPO_LETRA));
        EstiloCeldaBean totalEstiloCeldaBean =new EstiloCeldaBean(libro);
        totalEstiloCeldaBean.setFuente(total.getFuente());
        totalEstiloCeldaBean.setAlinear((short)5);
        totalEstiloCeldaBean.setAlinearVertical((short)0);
        totalEstiloCeldaBean.setBackground(new LIGHT_GREEN().getIndex());
        totalEstiloCeldaBean.setFillPatern((short)1);
        totalEstiloCeldaBean.setBorders(border);
        totalEstiloCeldaBean.setBordersColor(IndexedColors.SEA_GREEN.getIndex());
        total.setCelda(totalEstiloCeldaBean);

        //Celda Pie pagina
        FormatoCeldaBean piePagina = new FormatoCeldaBean();
        piePagina.setComponeneteExcel(FormatoCeldaBean.ComponenteExcel.PIE_PAGINA);
        piePagina.setFuente(new FormatoFuenteBean(libro, (short)10, (short)700, 
                                                  new BLACK().getIndex(), 
                                                  TIPO_LETRA));
        EstiloCeldaBean piePaginaEstiloCeldaBean =new EstiloCeldaBean(libro);
        piePaginaEstiloCeldaBean.setFuente(piePagina.getFuente());
        piePaginaEstiloCeldaBean.setAlinear((short)5);
        piePaginaEstiloCeldaBean.setAlinearVertical((short)0);
        piePaginaEstiloCeldaBean.setBackground((short)9);
        piePaginaEstiloCeldaBean.setFillPatern((short)1);
        piePaginaEstiloCeldaBean.setBorders(border);
        piePaginaEstiloCeldaBean.setBordersColor(IndexedColors.SEA_GREEN.getIndex());
        piePagina.setCelda(piePaginaEstiloCeldaBean);
    }
    
    /**
     * Genera titulo.
     *
     * @param psTitulo the ps titulo
     * @param totalCeldas the total celdas
     */
    public void generaTitulo(String psTitulo, int totalCeldas) {
        agregaFilaTitulo(StringUtil.esNull(psTitulo).toUpperCase(),
                         totalCeldas);
    }

    /**
     * Genera sub titulo.
     *
     * @param psSubTitulo the ps sub titulo
     * @param totalCeldas the total celdas
     */
    public void generaSubTitulo(String psSubTitulo, int totalCeldas) {
        agregaFilaSubTitulo(StringUtil.esNull(psSubTitulo), totalCeldas, 0);
    }

    /**
     * Genera titulo.
     *
     * @param psTitulo the ps titulo
     * @param columnaInicial the columna inicial
     * @param totalCeldas the total celdas
     */
    public void generaTitulo(String psTitulo, int columnaInicial, 
                             int totalCeldas) {
        agregaFilaTitulo(StringUtil.esNull(psTitulo).toUpperCase(), 
                         columnaInicial, totalCeldas);
    }

    /**
     * Genera sub titulo.
     *
     * @param psSubTitulo the ps sub titulo
     * @param totalCeldas the total celdas
     * @param tamnhoCol the tamnho col
     */
    public void generaSubTitulo(String psSubTitulo, int totalCeldas, 
                                int tamnhoCol) {
        agregaFilaSubTitulo(StringUtil.esNull(psSubTitulo), totalCeldas, 
                            tamnhoCol);
    }

    /**
     * Agrega filatitulo.
     *
     * @param psTitulo the ps titulo
     * @param nTotalColumnas the n total columnas
     */
    public void agregaFilaTitulo(String psTitulo, int nTotalColumnas) {
        int numeroTotalColumnas = nTotalColumnas - 1;
        this.nFilas = 0;
        Row loFila = hoja.createRow((short)this.nFilas);
        agregaDimension(loFila, 0, numeroTotalColumnas,
                        titulo.getCelda().getEstilo(), psTitulo);
    }

    /**
     * Agrega filatitulo.
     *
     * @param psTitulo the ps titulo
     * @param columnaInicial the columna inicial
     * @param nTotalColumnas the n total columnas
     */
    public void agregaFilaTitulo(String psTitulo, int columnaInicial, 
                                 int nTotalColumnas) {
        int numeroTotalColumnas = nTotalColumnas - 1;

        this.nFilas = 0;
        Row loFila = hoja.createRow((short)this.nFilas);
        agregaDimension(loFila, columnaInicial, numeroTotalColumnas,
                        titulo.getCelda().getEstilo(), psTitulo);
    }

    /**
     * Agrega fila sub titulo.
     *
     * @param psSubTitulo the ps sub titulo
     * @param nTotalColumnas the n total columnas
     * @param tamanhoCol the tamanho col
     */
    public void agregaFilaSubTitulo(String psSubTitulo, int nTotalColumnas, 
                                    int tamanhoCol) {

        int numeroTotalColumnas = nTotalColumnas - 1;
        this.nFilas += 1;
        Row loFila = hoja.createRow((short)this.nFilas);
        if (tamanhoCol > 0) {
            loFila.setHeight((short)tamanhoCol); //DML --> tamaño de la celda       
        } else {
            loFila.setHeight((short)350); //DML --> tamaño de la celda    
        }
        agregaDimension(loFila, 0, numeroTotalColumnas,
                        subtitulo.getCelda().getEstilo(), psSubTitulo);
    }

    /**
     * Combinar texto.
     *
     * @param listTextos the list textos
     */
    public void combinarTexto(List<TextoBean> listTextos) {
        this.nFilas += 1;
        this.columnaInicial = 0;
        this.columnaFinal = 0;

        Row loFila = hoja.createRow((short)this.nFilas);
        if (this.isCeldaFirma()) {
            loFila.setHeight((short)700); //DML --> tamaño de la celda
        }

        if (listTextos != null) {
            for (TextoBean texto: listTextos) {
                this.columnaInicial = this.columnaFinal;
                this.columnaFinal = 
                        (this.columnaInicial + texto.getNumeroFilas() - 1);
                if (texto.getFormato() != null && 
                    texto.getFormato().getCelda() != null) {
                    agregaDimension(loFila, this.columnaInicial, 
                                    this.columnaFinal, 
                                    texto.getFormato().getCelda().getEstilo(), 
                                    texto.getTexto());
                } else {
                    agregaDimension(loFila, this.columnaInicial, 
                                    this.columnaFinal, 
                                    this.titulo.getCelda().getEstilo(), 
                                    texto.getTexto());
                }
            }
        }
    }
    
    /**
     * Combinar texto.
     *
     * @param listTextosStr the list textos str
     */
    public void combinarTexto(String[] listTextosStr) {
        this.nFilas += 1;
        this.columnaInicial = 0;
        this.columnaFinal = 0;

        Row loFila = hoja.createRow((short)this.nFilas);
        if (this.isCeldaFirma()) {
            loFila.setHeight((short)700); //DML --> tamaño de la celda
        }

        if (listTextosStr != null) {
            for (String textoStr: listTextosStr) {
                this.columnaInicial = this.columnaFinal;
                this.columnaFinal = (1);
                
                agregaDimension(loFila, 
                                this.columnaInicial, 
                                this.columnaFinal, 
                                this.titulo.getCelda().getEstilo(), 
                                textoStr);                
            }
        }
    }
    
    /**
     * Combinar texto.
     *
     * @param listTextosStr the list textos str
     * @param estiloCelda the estilo celda
     */
    public void combinarTexto(String[] listTextosStr, EstiloCeldaBean estiloCelda) {
        this.nFilas += 1;
        this.columnaInicial = 0;
        this.columnaFinal = 0;

        Row loFila = hoja.createRow((short)this.nFilas);
        if (listTextosStr != null) {
            for (String texto: listTextosStr) {
                this.columnaInicial = this.columnaFinal;
                this.columnaFinal = (this.columnaInicial);
                agregaDimension(loFila, this.columnaInicial, 
                                this.columnaFinal, 
                                estiloCelda.getEstilo(), 
                                texto);
            }
        }
    }

    /*
	 * combina
	 */

    /**
     * Combinar texto.
     *
     * @param listTextos the list textos
     * @param columnaInicial the columna inicial
     * @param fila the fila
     */
    public void combinarTexto(List<TextoBean> listTextos, int columnaInicial, 
                              int fila) {
        this.nFilas = fila;
        this.columnaInicial = columnaInicial;
        this.columnaFinal = columnaInicial;

        Row loFila = hoja.getRow(this.nFilas);
        if (hoja.getRow(this.nFilas) == null) {
            loFila = hoja.createRow(this.nFilas);
        }

        if (listTextos != null) {
            for (TextoBean texto: listTextos) {
                this.columnaInicial = this.columnaFinal;
                this.columnaFinal = 
                        (this.columnaInicial + texto.getNumeroFilas() - 1);
                if (texto.getFormato() != null && 
                    texto.getFormato().getCelda() != null) {
                    agregaDimension(loFila, this.columnaInicial, 
                                    this.columnaFinal, 
                                    texto.getFormato().getCelda().getEstilo(), 
                                    texto.getTexto());
                } else {
                    agregaDimension(loFila, this.columnaInicial, 
                                    this.columnaFinal, 
                                    this.titulo.getCelda().getEstilo(), 
                                    texto.getTexto());
                }
            }
        }
    }

    /**
     * Contenido listado non.
     *
     * @param pvDatos the pv datos
     */
    public void contenidoListadoNon(List<String[]> pvDatos) {
        if (pvDatos != null) {
            for (String[] lsaDatos: pvDatos) {
                generaFilaColumnas(celdaNon.getCelda().getEstilo(), lsaDatos);
            }
        }
    }
    
    /**
     * Contenido listado non.
     *
     * @param lsaDatos the pv datos
     */
    public void contenidoListadoNon(String[] lsaDatos) 
    {
        this.generaFilaColumnas(celdaNon.getCelda().getEstilo(), lsaDatos);
    }

    /**
     * Contenido listado non.
     *
     * @param pvDatos the pv datos
     * @param columnaInicial the columna inicial
     */
    public void contenidoListadoNon(List<String[]> pvDatos, 
                                    int columnaInicial) {
        if (pvDatos != null) {
            for (String[] lsaDatos: pvDatos) {
                generaFilaColumnas(celdaNon.getCelda().getEstilo(), lsaDatos, 
                                   columnaInicial);
            }
        }
    }
    
    /**
     * Contenido listado non.
     *
     * @param psaDatos the psa datos
     * @param columnaInicial the columna inicial
     */
    public void contenidoListadoNon(String[] psaDatos, int columnaInicial) {
        generaFilaColumnas(celdaNon.getCelda().getEstilo(), psaDatos, 
                           columnaInicial);
    }

    /**
     * Genera fila columnas.
     *
     * @param poEstylo the po estylo
     * @param lsaColumnas the lsa columnas
     */
    private void generaFilaColumnas(CellStyle poEstylo, 
                                    String[] lsaColumnas) {
        if ((lsaColumnas != null) && (lsaColumnas.length > 0)) {
            Row loFilas = hoja.createRow((++this.nFilas));
            if (this.isCeldaFirma()) {
                loFilas.setHeight((short)700); //DML --> tamaño de la celda
            }
            for (int nColum = 0; nColum < lsaColumnas.length; nColum++) {
                Cell loCeldas = loFilas.createCell(nColum);
                loCeldas.setCellStyle(poEstylo);
                loCeldas.setCellType(1);
                loCeldas.setCellValue(new HSSFRichTextString(StringUtil.esNull(lsaColumnas[nColum])));
            }
        }
    }

    /**
     * Genera fila columnas.
     *
     * @param poEstylo the po estylo
     * @param lsaColumnas the lsa columnas
     * @param columnaInicial the columna inicial
     */
    private void generaFilaColumnas(CellStyle poEstylo, 
                                    String[] lsaColumnas, int columnaInicial) {
        if ((lsaColumnas != null) && (lsaColumnas.length > 0)) {
            int fila = this.nFilas + 1;
            int nColumnaReporte = columnaInicial;

            Row loFilas = hoja.getRow(fila);
            if (loFilas == null) {
                loFilas = hoja.createRow(++this.nFilas);
            } else {
                //Solo se aumenta la fila
                this.nFilas = this.nFilas + 1;
            }

            for (int nColum = 0; nColum < lsaColumnas.length; nColum++) {

                Cell loCeldas = loFilas.createCell(nColumnaReporte);
                loCeldas.setCellStyle(poEstylo);
                loCeldas.setCellType(1);
                loCeldas.setCellValue(new HSSFRichTextString(StringUtil.esNull(lsaColumnas[nColum])));
                nColumnaReporte = nColumnaReporte + 1;
            }
        }
    }

    /**
     * Genera espacio blanco.
     *
     * @param nTotalColumnas the n total columnas
     */
    public void generaEspacioBlanco(int nTotalColumnas) {
        this.columnaInicial = 0;
        int totalColumnas = nTotalColumnas - 1;
        Row loFila = hoja.createRow((short)(++this.nFilas));
        agregaDimension(loFila, this.columnaInicial, totalColumnas,
                        celdaNon.getCelda().getEstilo(), null);
    }

    /*
	 * combina
	 */

    /**
     * Genera espacio blanco.
     *
     * @param nTotalColumnas the n total columnas
     * @param columnaInicial the columna inicial
     */
    public void generaEspacioBlanco(int nTotalColumnas, int columnaInicial) {
        this.columnaInicial = columnaInicial;
        int fila = this.nFilas + 1;
        int totalColumnas = nTotalColumnas - 1;

        Row loFila = hoja.getRow(fila);
        if (loFila == null) {
            loFila = hoja.createRow((++this.nFilas));
        }
        agregaDimension(loFila, this.columnaInicial, totalColumnas,
                        celdaNon.getCelda().getEstilo(), null);
    }

    /**
     * Agrega dimension.
     *
     * @param poFila the po fila
     * @param nColumnaInicial the n columna inicial
     * @param nColumnaFinal the n columna final
     * @param poStylo the po stylo
     * @param psDescripcion the ps descripcion
     */
    private void agregaDimension(Row poFila, int nColumnaInicial, 
                                 int nColumnaFinal, CellStyle poStylo, 
                                 String psDescripcion) {
        short intRegion = poStylo.getBorderTop();
        Cell loCelda = poFila.createCell(nColumnaInicial);
        loCelda.setCellStyle(poStylo);
        loCelda.setCellType(Cell.CELL_TYPE_STRING);
        loCelda.setCellValue(new HSSFRichTextString(psDescripcion));
        
        org.apache.poi.ss.util.CellRangeAddress region = new CellRangeAddress(this.nFilas, 
                                                                              this.nFilas,
                                                                              nColumnaInicial, 
                                                                              nColumnaFinal);
        hoja.addMergedRegion(region);
        
        if( hoja instanceof HSSFSheet ){
            HSSFRegionUtil.setBorderRight(intRegion, region, (HSSFSheet)hoja, (HSSFWorkbook)libro);
            HSSFRegionUtil.setBorderBottom(intRegion, region, (HSSFSheet)hoja, (HSSFWorkbook)libro);
            HSSFRegionUtil.setBorderTop(intRegion, region, (HSSFSheet)hoja, (HSSFWorkbook)libro);
            HSSFRegionUtil.setBorderLeft(intRegion, region, (HSSFSheet)hoja, (HSSFWorkbook)libro);
        }
        
        this.columnaInicial += 1;
        this.columnaFinal += 1;
    }

    /**
     * Adaptar columnas.
     *
     * @param nColumnas the n columnas
     */
    public void adaptarColumnas(int nColumnas) {
        for (int i = 0; i < nColumnas; i++) {
            hoja.autoSizeColumn(i);
        }
    }
    
    /**
     * Adaptar columnas.
     *
     * @param lsaColumnas the n columnas
     */
    public void adaptarColumnas(String[] lsaColumnas) {
        int tamanho = 0;
        
        for (int i = 0; i < lsaColumnas.length; i++) {
            tamanho = lsaColumnas[i].length() + 10;
            hoja.setColumnWidth(i,(tamanho*256) );
        }
    }

    /**
     * Genera archivo excel.
     *
     * @param nombreArchivo the ps nombre archivo
     * @return true, if successful
     */
    public boolean generaArchivoExcel(String nombreArchivo) {
        boolean lbOk = false;
        FileOutputStream archivoSalida = null;
        String lnombreArchivo;
        try {
            if( hoja instanceof HSSFSheet ){
                lnombreArchivo = nombreArchivo + ".xls";
            }
            else{
                lnombreArchivo = nombreArchivo + ".xlsx";
            }
            
            File loFile = new File(lnombreArchivo);
            if (loFile.exists()) {
                boolean fueBorrado = loFile.delete();
                if(!fueBorrado) {
                    logger.debug("No se pudo eliminar el archivo");
                }
            }
            archivoSalida = new FileOutputStream(loFile);
            libro.write(archivoSalida);
            archivoSalida.close();
            if (loFile.exists())
                lbOk = true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            lbOk = false;
        }

        return lbOk;
    }

    /**
     * Genera archivo excel.
     *
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public byte[] generaArchivoExcel() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        libro.write(bytes);
        return bytes.toByteArray();
    }

    /**
     * Gets the titulo.
     *
     * @return the titulo
     */
    public FormatoCeldaBean getTitulo() {
        return titulo;
    }

    /**
     * Gets the sub titulo.
     *
     * @return the sub titulo
     */
    public FormatoCeldaBean getSubTitulo() {
        return subtitulo;
    }

    /**
     * Gets the celda non.
     *
     * @return the celda non
     */
    public FormatoCeldaBean getCeldaNon() {
        return celdaNon;
    }

    /**
     * Sets the celda non.
     *
     * @param celdaNon the new celda non
     */
    public void setCeldaNon(FormatoCeldaBean celdaNon) {
        this.celdaNon = celdaNon;
    }

    /**
     * Gets the encabezado.
     *
     * @return the encabezado
     */
    public FormatoCeldaBean getEncabezado() {
        return encabezado;
    }

    /**
     * Sets the encabezado.
     *
     * @param encabezado the new encabezado
     */
    public void setEncabezado(FormatoCeldaBean encabezado) {
        this.encabezado = encabezado;
    }

    /**
     * Gets the total.
     *
     * @return the total
     */
    public FormatoCeldaBean getTotal() {
        return total;
    }

    /**
     * Sets the total.
     *
     * @param total the new total
     */
    public void setTotal(FormatoCeldaBean total) {
        this.total = total;
    }

    /**
     * Checks if is celda firma.
     *
     * @return true, if is celda firma
     */
    public boolean isCeldaFirma() {
        return celdaFirma;
    }

    /**
     * Sets the celda firma.
     *
     * @param celdaFirma the new celda firma
     */
    public void setCeldaFirma(boolean celdaFirma) {
        this.celdaFirma = celdaFirma;
    }

    /**
     * Agregar imagen.
     *
     * @param psImg the ps img
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void agregarImagen(String psImg) throws IOException {
        InputStream is = new FileInputStream(psImg);
        byte[] bytes = FileUtil.inputStreamToBytes(is);
        int pictureIdx = libro.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);        
        is.close();

        CreationHelper helper = libro.getCreationHelper();
        
        Drawing drawing = hoja.createDrawingPatriarch();        
        ClientAnchor anchor = helper.createClientAnchor();
        
         if( hoja instanceof SXSSFSheet ){
             return;
         }
         else{
             Row loFila = hoja.getRow(0);
             loFila.setHeight((short)1500); //DML --> tamaño de la celda
             anchor.setCol1(0);
             anchor.setRow1(0);
             
             Picture pict = drawing.createPicture(anchor, pictureIdx);
             pict.resize();
         }
         
    }



    
    /**
     * Genera encabezados.
     *
     * @param encabezados the encabezados
     */
    public void generaEncabezados(String[] encabezados) {
        if(encabezados != null) {
            this.combinarTexto(encabezados, this.total.getCelda());
        }
    }

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String [] args) throws Exception {
        //Ejemplo 1
        String archivoEjemplo1 = "C:\\archivoExcelEjemplo1";

        String[] encabezadosEjemplo1 = {"Encabezado1","Encabezado2","Encabezado3","Encabezado4"};
        int totalCeldasEjemplo1 = encabezadosEjemplo1.length;

        String[] datosColumnaEjemplo1 = { "Lorem Ipsum es simplemente el texto 1", "Dato 1.2", "Dato 1.3", "Dato 1.4" };
        String[] datosColumna2Ejemplo1 = { "Dato 2.1", "Dato 2.2", "y archivos de texto. Lorem Ipsum", "Dato 2.4" };
        String[] datosColumna3Ejemplo1 = { "Dato 3.1", "estándar de las industrias desde", "Dato 3.3", "Dato 3.4" };

        List<String[]> datosEjemplo1 = new ArrayList<>(0);
        datosEjemplo1.add(datosColumnaEjemplo1);
        datosEjemplo1.add(datosColumna2Ejemplo1);
        datosEjemplo1.add(datosColumna3Ejemplo1);
        datosEjemplo1.add(new String[] { "Dato 4.1", "Dato 4.2", "Dato 4.3","Popularizado en los 60s" });

        ExcelUtil excelEjemplo1 = new ExcelUtil("nombreHoja",true);
        excelEjemplo1.generaTitulo("Titulo Excel", totalCeldasEjemplo1);
        excelEjemplo1.generaEncabezados(encabezadosEjemplo1);
        excelEjemplo1.contenidoListadoNon(datosEjemplo1);
        excelEjemplo1.adaptarColumnas(totalCeldasEjemplo1);
        excelEjemplo1.generaArchivoExcel(archivoEjemplo1); //DML En caso de generar un arreglo de bytes, el método esta sobrecargado


        //Ejemplo 2
        String archivoEjemplo2 = "C:\\archivoExcelEjemplo2";
        String imagenEjemplo2 =
                "C:\\jdev\\jdevhome\\jdev\\myhtml\\OA_MEDIA\\imgLogoSegob\\logoSegob.jpg";
        String[] encabezadosEjemplo2 =
                { "NO. INCORPORACION", "NOMBRE DEL PARTICIPANTE", "NO. DE EMPLEADO",
                        "PUESTO", "CVE. U.A.", "UNIDAD ADMINISTRATIVA" };

        ExcelUtil excelEjemplo2 = new ExcelUtil("Nombre Reporte", false);

        List<TextoBean> lstTextosEjemplo2 = new ArrayList<>(0);
        for (String enc: encabezadosEjemplo2) {
            TextoBean dtoDatFiscales2 = new TextoBean();
            dtoDatFiscales2.setFormato(excelEjemplo2.getEncabezado());
            dtoDatFiscales2.setTexto(enc);
            dtoDatFiscales2.setNumeroFilas(1);
            lstTextosEjemplo2.add(dtoDatFiscales2);
        }
        List<String[]> datosEjemplo2 = new ArrayList<>(0);
        datosEjemplo2.add(new String[] { "1", "ALBA ABLA LUIS MEGBEL", "505929",
                "SUBDIRECTOR DE ANALISIS ESTUDIOS E INVESTIGACIONES",
                "410",
                "DIRECCION GENERAL DEL REGISTRO NACIONAL DE POBLACION E IDENTIFICACION PERSONAL" });

        int totalCeldasEjemplo2 = encabezadosEjemplo2.length;

        excelEjemplo2.generaTitulo("DIRECCIÓN GENERAL DE RECURSOS HUMANOS\n SUBDIRECCIÓN DE PLANEACIÓN Y DESARROLLO",
                3, totalCeldasEjemplo2);
        excelEjemplo2.generaSubTitulo("Reporte de Capacidades Asignadas a los Puestos",
                totalCeldasEjemplo2);
        excelEjemplo2.combinarTexto(lstTextosEjemplo2);
        excelEjemplo2.contenidoListadoNon(datosEjemplo2);
        excelEjemplo2.adaptarColumnas(totalCeldasEjemplo2);
        excelEjemplo2.agregarImagen(imagenEjemplo2);
        excelEjemplo2.generaArchivoExcel(archivoEjemplo2);


        //Ejemplo 3
        String archivoEjemplo3 = "C:\\archivoExcelEjemplo3";

        int totalCeldasEjemplo3 = 4;

        ExcelUtil excelEjemplo3 = new ExcelUtil();
        List<TextoBean> lstTextosEjemplo3 = new ArrayList<>(0);
        TextoBean dtoDatFiscalesEjemplo3 = new TextoBean();
        dtoDatFiscalesEjemplo3.setFormato(excelEjemplo3.getSubTitulo());
        dtoDatFiscalesEjemplo3.setTexto("Encabezado1");
        dtoDatFiscalesEjemplo3.setNumeroFilas(1);

        TextoBean dtoDatFiscales2Ejemplo3 = new TextoBean();
        dtoDatFiscales2Ejemplo3.setFormato(excelEjemplo3.getEncabezado());
        dtoDatFiscales2Ejemplo3.setTexto("Encabezado2");
        dtoDatFiscales2Ejemplo3.setNumeroFilas(2);

        TextoBean dtoDatFiscales3Ejemplo3 = new TextoBean();
        dtoDatFiscales3Ejemplo3.setFormato(excelEjemplo3.getTotal());
        dtoDatFiscales3Ejemplo3.setTexto("Encabezado3");
        dtoDatFiscales3Ejemplo3.setNumeroFilas(1);

        lstTextosEjemplo3.add(dtoDatFiscalesEjemplo3);
        lstTextosEjemplo3.add(dtoDatFiscales2Ejemplo3);
        lstTextosEjemplo3.add(dtoDatFiscales3Ejemplo3);

        List<String[]> datos = new ArrayList<>(0);
        String[] datosColumnaEjemplo3 =
                { "Dato 1.1", "Dato 1.2", "Dato 1.3", "Dato 1.4" };
        String[] datosColumna2Ejemplo3 =
                { "Dato 2.1", "Dato 2.2", "Dato 2.3", "Dato 2.4" };
        String[] datosColumna3Ejemplo3 =
                { "Dato 3.1", "Dato 3.2", "Dato 3.3", "Dato 3.4" };

        datos.add(datosColumnaEjemplo3);
        datos.add(datosColumna2Ejemplo3);
        datos.add(datosColumna3Ejemplo3);
        datos.add(new String[] { "Dato 4.1", "Dato 4.2", "Dato 4.3",
                "Dato 4.4" });

        excelEjemplo3.generaTitulo("TituloExcel", totalCeldasEjemplo3);
        excelEjemplo3.combinarTexto(lstTextosEjemplo3);

        excelEjemplo3.contenidoListadoNon(datos);
        excelEjemplo3.adaptarColumnas(totalCeldasEjemplo3);
        excelEjemplo3.generaArchivoExcel(archivoEjemplo3);

    }
    
}
