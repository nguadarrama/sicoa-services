/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.io.InputStream;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class ArchivoDto {
	
	   /**
	    * El id archivo.
	    */
	   @MapeaColumna(columna = "id_archivo") private Integer idArchivo;
	   
	   /**
	    * El nombre.
	    */
	   @MapeaColumna(columna = "nombre") private String nombre;
	   
	   /**
	    * La url.
	    */
	   @MapeaColumna(columna = "url") private String url;
	   
	   /**
	    * El tamaño.
	    */
	   @MapeaColumna(columna = "size") private Integer size;
	   
	   /**
	    * El dato activo.
	    */
	   @MapeaColumna(columna = "activo") private Boolean activo;
	   
	   private byte[] archivo;
	   
	   private String accion;
	   
	   private String claveUsuario;
	   
	   @Transient
	   private String mensaje;
	   
	   public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
		 * Obtener el id archivo.
		 *
		 * @return idArchivo
		 */

		public Integer getIdArchivo() {
			return idArchivo;
		}
		
		/**
		 * Pasar el id archivo.
		 *
		 * @param idArchivo
		 */
		public void setIdArchivo(Integer idArchivo) {
			this.idArchivo = idArchivo;
		}
		
		/**
		 * Obtener el nombre de archivo.
		 *
		 * @return nombre.
		 */
		public String getNombre() {
			return nombre;
		}
		
		/**
		 * Pasar el nombre de archivo.
		 *
		 * @param nombre
		 */
		public void setNombre (String nombre) {
			this.nombre = nombre;
		}
		
		/**
		 * Obtener la url del archivo.
		 *
		 * @return url.
		 */
		public String getUrl() {
			return url;
		}
		
		/**
		 * Pasar la url del archivo.
		 *
		 * @param url
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		
		/**
		 * Obtener el tamaño del archivo.
		 *
		 * @return size
		 */
		public Integer getSize() {
			return size;
		}
		
		/**
		 * Pasar el tamaño del archivo.
		 *
		 * @param size
		 */
		public void setSize(Integer size) {
			this.size = size;
		}
		
		/**
		 * Obtener el dato activo
		 *
		 * @return activo
		 */
		public Boolean getActivo() {
			return activo;
		}
		
		/**
		 * Pasar el dato activo
		 *
		 * @param activo
		 */
		public void setActivo(Boolean activo) {
			this.activo = activo;
		}

		public byte[] getArchivo() {
			return archivo;
		}

		public void setArchivo(byte[] archivo) {
			this.archivo = archivo;
		}

		public String getAccion() {
			return accion;
		}

		public void setAccion(String accion) {
			this.accion = accion;
		}

		public String getClaveUsuario() {
			return claveUsuario;
		}

		public void setClaveUsuario(String claveUsuario) {
			this.claveUsuario = claveUsuario;
		}
}
