/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class EstatusDto {

	   /**
	    * El id estatus.
	    */
	   @MapeaColumna(columna = "id_estatus") private Integer idEstatus;
	   
	   /**
	    * La descripci&oacute;n.
	    */
	   @MapeaColumna(columna = "descripcion") private String descripcion;
	   
	   /**
	    * El id estatus.
	    */
	   @MapeaColumna(columna = "estatus") private String estatus;
	   
	   /**
	    * El id estatus.
	    * @return idEstatus
	    */
		public Integer getIdEstatus() {
			return idEstatus;
		}
		
		/**
		    * El id estatus.
		    * @param idEstatus
		    * @return idEstatus
		    */
		public void setIdEstatus(Integer idEstatus) {
			this.idEstatus = idEstatus;
		}
		
		/**
		    * El estatus.
		    * @return estatus
		    */
		public String getEstatus() {
			return estatus;
		}
		
		/**
		    * El estatus.
		    * @param estatus
		    * @return estatus
		    */
		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}
		
		/**
		    * La descripci&oacute;n.
		    * @return descripcion
		    */
		public String getDescripcion() {
			return descripcion;
		}
		
		/**
		    * La descripci&oacute;n.
		    * @param descripcion
		    */
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		   
	   
	   
	   
}
