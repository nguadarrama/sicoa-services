/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.archivo;

import org.apache.poi.util.IOUtils;

import mx.gob.segob.dgtic.comun.exception.ArchivoException;

import java.io.*;

/**
 * Clase de apoyo para manejo de archivos.
 */
public class FileUtil {


    /**
     * Constructor de utiler&iacute;a para acceso completamente est&aacute;tico
     */
    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    
	/**
	 * Convierte una instancia de File a un InputStream
	 *
	 * @param file Instancia del archivo a convertir
	 * @return El stream que crea de la instancia File
	 * @throws FileNotFoundException Excepci&oacute;n si archivo no existe
	 */
	public static InputStream fileToInputStream(File file) throws FileNotFoundException {
       return new FileInputStream(file);
    }

    /**
     * Convierte una instancia de un InputStream a un arreglo de bytes
     *
     * @param is El inputstream a convertir
     * @return El archivo de bytes que se crea del inputStream
     * @throws IOException Excepci&oacute;n que ocurre al leer el stream
     */
    public static byte[] inputStreamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int c = -1;
        while ((c = is.read()) != -1) {
            os.write(c);
        }
        os.close();
        is.close();
        return os.toByteArray();
    }

    /**
     * Escribe un arreglo de bytes a un archivo en el filesystem
     * 
     * @param bytes Arreglo de bytes a escribir al filesystem
     * @param sPath El path del filesystem donde se debera escribir los bytes
     * @throws IOException Excepci&oacute;n que ocurre al escribir los bytes
     */
    public static void bytesToFile(byte[] bytes,
                                               String sPath) throws IOException {
        try(
                FileOutputStream fos = new FileOutputStream(sPath);
        ){
            fos.write(bytes);
        }
    }

    /**
     * Convierte un arreglo de bytes a un input stream.
     *
     * @param bytes Los bytes a convertir
     * @return El input stream que se crea
     */
    public static InputStream bytesToInputStream(byte[] bytes) {
        return  new ByteArrayInputStream(bytes);
    }
    
    /**
     * Obtiene archivos ubicados en la carpeta /src/main/resources de la estructura del proyecto
     *
     * @param pathRelativeFile El path relativo con respecto a la ra&iacute;z (/src/main/resources) del archivo.
     * @return El arreglo de bytes del archivo identificado con esa ruta 
     * @throws ArchivoException Excepci&oacute;n al obtener el archivo.
     */
    public static byte[] getArchivoByteArrayFromCarpetaResources(String pathRelativeFile) throws ArchivoException {
        ClassLoader loader = getContextClassLoader();
        byte [] archivo = null;
        try(
            InputStream streamFile = loader.getResourceAsStream(pathRelativeFile);
        ){
            archivo =  IOUtils.toByteArray(streamFile);
        } catch (Exception e){
            throw new ArchivoException(e.getMessage(), e);
        }
        return archivo;
    }
    
    /**
     * Obtiene el input stream de archivos ubicados en la carpeta /src/main/resources de la estructura del proyecto
     *
     * @param pathRelativeFile El path relativo con respecto a la ra&iacute;z (/src/main/resources) del archivo.
     * @return El input stream que se crea para el archivo solicitado
     */
    public static InputStream getArchivoStreamFromCarpetaResources(String pathRelativeFile) {
        ClassLoader loader = getContextClassLoader();
        InputStream streamFile = null;
        streamFile = loader.getResourceAsStream(pathRelativeFile);
        return streamFile;
    }
    

    /**
     * Obtiene la extension de un archivo
     *
     * @param nombreArchivo El nombre del archivo que se requiere conocer su extensi&oacute;n
     * @return La extensi&oacute;n que identifica el tipo de archivo
     */
    public static String getExtensionNombreArchivo(String nombreArchivo){
        try {
            return nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Obtiene el classloader del proyecto
     *
     * @return El classloader del proyecto
     */
    private static ClassLoader getContextClassLoader() {
        return FileUtil.class.getClassLoader();

    }
    
}
