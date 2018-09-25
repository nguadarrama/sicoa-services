/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.jasper;

import mx.gob.segob.dgtic.comun.exception.ArchivoException;
import mx.gob.segob.dgtic.comun.util.archivo.FileUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Clase de apoyo para manejo de reportes por la libreria JASPER.
 */
public class JasperUtil {

    /**
     * Constructor de utiler&iacute;a para acceso completamente est&aacute;tico
     */
    private JasperUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generar reporte PDF. De una plantilla Jasper.
     * <p>
     * Enviando los parametros indicados cargan la plantilla jasper y 
     * renderizan el pdf de manera dinamica con la fuente de datos (datasource) y los parametros de entrada
     * Ejemplo
	 * <pre class="code">
	 * 	 String plantilla = "/plantillas/ejemplo.jasper";
	 * 
	 * 	 Objeto o = new Objeto();
	 *   o.setId(1);
	 *   o.setNombre("nombre");
	 *   
	 *   Objeto o2 = new Objeto();
	 *   o2.setId(2);
	 *   o2.setNombre("nombre 2");
	 *   
	 *   List<Objeto> datasource = new ArrayList<>(0);
	 *   datasource.add(o);
	 *   datasource.add(o2);
	 *   
	 *    Map<String, Object> parametros = new HashMap<>(0);
	 *    parametros.put("tituloReporte", "Reporte de prueba");
	 *    
	 *    byte[] archivoPDF = JasperUtil.generarReportePDF(plantilla, datasource, parametros);
	 * 
	 * </pre>
     *
     * @param pathResourcesJasper El path relativo de un archivo .jasper ubicado en la carpeta "/src/main/resources"
     * @param listaDataSource Una lista de objetos a pasar como un datasource para la creacion del reporte
     * @param parametrosReporte Par&aacute;metros de entrada que se env&iacute;an al reporte jasper.
     * @return El archivo en formato PDF como un arreglo de bytes.
     * @throws ArchivoException Excepci&oacute;n generada por la conversi&oacute;n del archivo
     * 
     */
    public static byte[] generarReportePDF(String pathResourcesJasper,
                                           List<?> listaDataSource,
                                           Map<String, Object> parametrosReporte ) throws ArchivoException {

        byte[] reporteBytes = null;

        Map<String, Object> parametro = new HashMap<>(0);
        if(parametrosReporte != null){
            parametro.putAll(parametrosReporte);
        }

        try(
                InputStream inputStream =  FileUtil.getArchivoStreamFromCarpetaResources(pathResourcesJasper);
        ){
            JRDataSource datasource = new JRBeanCollectionDataSource(listaDataSource);

            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, datasource);

            reporteBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (IOException ioException ) {
            throw new ArchivoException(ioException.getMessage(), ioException);
        } catch (JRException jrException) {
            throw new ArchivoException(jrException.getMessage(), jrException);
        }
        return reporteBytes;
    }

}
