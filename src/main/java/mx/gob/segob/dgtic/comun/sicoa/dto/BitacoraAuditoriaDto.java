/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import java.util.Date;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class BitacoraAuditoriaDto {

	   /**
	    * El id bitacora.
	    */
	   @MapeaColumna(columna = "id_h_bitacora") private Integer idBitacora;
	   
	   /**
	    * La fecha y hora.
	    */
	   @MapeaColumna(columna = "fecha_hora") private Date fechaHora;
	   
	   /**
	    * La clave usuario.
	    */
	   @MapeaColumna(columna = "cve_m_usuario") private String claveUsuario;
	   
	   /**
	    * 
	    */
	   @MapeaColumna(columna = "uri") private String uri;
	   
	   /**
	    * El modulo.
	    */
	   @MapeaColumna(columna = "modulo") private String modulo;
	   
	   /**
	    * Los parametros.
	    */
	   @MapeaColumna(columna = "parametros") private String parametros;
	   
	   /**
	    * El codigo de respuesta.
	    */
	   @MapeaColumna(columna = "codigo_respuesta") private Integer codigoRespuesta;
	   
	   /**
	    * El tipo de respuesta.
	    */
	   @MapeaColumna(columna = "content_type_respuesta") private String contentTypeRespuesta;
	   
	   /**
	    * La respuesta.
	    */
	   @MapeaColumna(columna = "respuesta") private String respuesta;
	   
	   /**
		 * Obtener el id bitacora.
		 *
		 * @return idBitacora
		 */
		public Integer getIdBitacora() {
			return idBitacora;
		}
		
		/**
		 * Pasar el id bitacora.
		 *
		 * @param idBitacora
		 */
		public void setIdBitacora(Integer idBitacora) {
			this.idBitacora = idBitacora;
		}
		
		 /**
		 * Obtener la fecha hora.
		 *
		 * @return fechaHora
		 */
		public Date getFechaHora() {
			return fechaHora;
		}
		
		/**
		 * Pasar la fecha hora.
		 *
		 * @param fechaHora
		 */
		public void setFechaHora(Date fechaHora) {
			this.fechaHora = fechaHora;
		}
		
		 /**
		 * Obtener la clave usuario.
		 *
		 * @return claveUsuario
		 */
		public String getClaveUsuario() {
			return claveUsuario;
		}
		
		/**
		 * Pasar la clave usuario.
		 *
		 * @param claveUsuario
		 */
		public void setClaveUsuario(String claveUsuario) {
			this.claveUsuario = claveUsuario;
		}
		
		/**
		 * 
		 *
		 * @return uri
		 */
		public String getUri() {
			return uri;
		}
		
		/**
		 * 
		 *
		 * @param uri
		 */
		public void setUri(String uri) {
			this.uri = uri;
		}
		
		/**
		 * Obtener el m&oacute;dulo.
		 *
		 * @return modulo
		 */
		public String getModulo() {
			return modulo;
		}
		
		/**
		 * Pasar el m&oacute;dulo.
		 *
		 * @param modulo
		 */
		public void setModulo(String modulo) {
			this.modulo = modulo;
		}
		
		/**
		 * Obtener los par&aacute;metros.
		 *
		 * @return parametros
		 */
		public String getParametros() {
			return parametros;
		}
		
		/**
		 * Pasar los par&aacute;metros.
		 *
		 * @param parametros
		 */
		public void setParametros(String parametros) {
			this.parametros = parametros;
		}
		
		/**
		 * Obtener el c&oacute;digo de respuesta.
		 *
		 * @return codigoRespuesta
		 */
		public Integer getCodigoRespuesta() {
			return codigoRespuesta;
		}
		
		/**
		 * Pasar el c&oacute;digo de respuesta.
		 *
		 * @param codigoRespuesta
		 */
		public void setCodigoRespuesta(Integer codigoRespuesta) {
			this.codigoRespuesta = codigoRespuesta;
		}
		
		/**
		 * Obtener el tipo de respuesta.
		 *
		 * @return contentTypeRespuesta
		 */
		public String getContentTypeRespuesta() {
			return contentTypeRespuesta;
		}
		
		/**
		 * Pasar el tipo de respuesta.
		 *
		 * @param contentTypeRespuesta
		 */
		public void setContentTypeRespuesta(String contentTypeRespuesta) {
			this.contentTypeRespuesta = contentTypeRespuesta;
		}
		
		/**
		 * Obtener la respuesta.
		 *
		 * @return respuesta
		 */
		public String getRespuesta() {
			return respuesta;
		}
		
		/**
		 * Pasar la respuesta.
		 *
		 * @param respuesta
		 */
		public void setRespuesta(String respuesta) {
			this.respuesta = respuesta;
		}
}
