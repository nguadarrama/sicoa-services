package mx.gob.segob.dgtic.comun.sicoa.dto;


import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class DashVacDto {
	
	@MapeaColumna
	private Integer id_periodo = 0;
		
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
		
		
		public DashVacDto(Integer id_periodo, Integer dias, String descripcion) {
			super();
			this.id_periodo = id_periodo;
			this.dias = dias;
			this.descripcion = descripcion;
		}


		/**
		 * @return the id_periodo
		 */
		public Integer getId_periodo() {
			return id_periodo;
		}


		/**
		 * @param id_periodo the id_periodo to set
		 */
		public void setId_periodo(Integer id_periodo) {
			this.id_periodo = id_periodo;
		}


		/**
		 * @return the dias
		 */
		public Integer getDias() {
			return dias;
		}


		/**
		 * @param dias the dias to set
		 */
		public void setDias(Integer dias) {
			this.dias = dias;
		}


		/**
		 * @return the descripcion
		 */
		public String getDescripcion() {
			return descripcion;
		}


		/**
		 * @param descripcion the descripcion to set
		 */
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	
}
