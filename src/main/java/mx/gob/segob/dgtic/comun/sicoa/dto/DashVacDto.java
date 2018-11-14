package mx.gob.segob.dgtic.comun.sicoa.dto;


import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class DashVacDto {
	
	@MapeaColumna
	private Integer idPeriodo = 0;
		
	@MapeaColumna 
	private Integer dias = 0;
		
	@MapeaColumna 
	private String descripcion = "";

		/**
		 * 
		 */
		public DashVacDto() {
			super();
		}
		
		
		/**
		 * @param id_periodo
		 * @param dias
		 * @param descripcion
		 */
		public DashVacDto(Integer idPeriodo, Integer dias, String descripcion) {
			super();
			this.idPeriodo = idPeriodo;
			this.dias = dias;
			this.descripcion = descripcion;
		}


		public Integer getIdPeriodo() {
			return idPeriodo;
		}


		public void setIdPeriodo(Integer idPeriodo) {
			this.idPeriodo = idPeriodo;
		}


		public Integer getDias() {
			return dias;
		}


		public void setDias(Integer dias) {
			this.dias = dias;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		

}
