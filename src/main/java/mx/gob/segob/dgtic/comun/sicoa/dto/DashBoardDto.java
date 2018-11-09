package mx.gob.segob.dgtic.comun.sicoa.dto;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

public class DashBoardDto implements Serializable {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -8131614104530576096L;

		@MapeaColumna 
		private Long asistencia;
		
	    @MapeaColumna 
		private Long incidencias;
		
	    @MapeaColumna
		private Long justificaciones;
		
	    @MapeaColumna
		private Long descuentos;
		
	    @MapeaColumna
		private Long licencias;
		
	    @MapeaColumna
		private Long diasIncapacidad;
		
	    @MapeaColumna
		private Long comisionesT;
		
	    @MapeaColumna
		private Long comisiones;
		
		@Transient 
		private List<DashVacDto> vacaciones;

		/**
		 * 
		 */
		public DashBoardDto() {
			super();
		}

		/**
		 * @return the asistencia
		 */
		public Long getAsistencia() {
			return asistencia;
		}

		/**
		 * @param asistencia the asistencia to set
		 */
		public void setAsistencia(Long asistencia) {
			this.asistencia = asistencia;
		}

		/**
		 * @return the incidencias
		 */
		public Long getIncidencias() {
			return incidencias;
		}

		/**
		 * @param incidencias the incidencias to set
		 */
		public void setIncidencias(Long incidencias) {
			this.incidencias = incidencias;
		}

		/**
		 * @return the justificaciones
		 */
		public Long getJustificaciones() {
			return justificaciones;
		}

		/**
		 * @param justificaciones the justificaciones to set
		 */
		public void setJustificaciones(Long justificaciones) {
			this.justificaciones = justificaciones;
		}

		/**
		 * @return the descuentos
		 */
		public Long getDescuentos() {
			return descuentos;
		}

		/**
		 * @param descuentos the descuentos to set
		 */
		public void setDescuentos(Long descuentos) {
			this.descuentos = descuentos;
		}

		/**
		 * @return the licencias
		 */
		public Long getLicencias() {
			return licencias;
		}

		/**
		 * @param licencias the licencias to set
		 */
		public void setLicencias(Long licencias) {
			this.licencias = licencias;
		}

		/**
		 * @return the diasIncapacidad
		 */
		public Long getDiasIncapacidad() {
			return diasIncapacidad;
		}

		/**
		 * @param diasIncapacidad the diasIncapacidad to set
		 */
		public void setDiasIncapacidad(Long diasIncapacidad) {
			this.diasIncapacidad = diasIncapacidad;
		}

		/**
		 * @return the comisionesT
		 */
		public Long getComisionesT() {
			return comisionesT;
		}

		/**
		 * @param comisionesT the comisionesT to set
		 */
		public void setComisionesT(Long comisionesT) {
			this.comisionesT = comisionesT;
		}

		/**
		 * @return the comisiones
		 */
		public Long getComisiones() {
			return comisiones;
		}

		/**
		 * @param comisiones the comisiones to set
		 */
		public void setComisiones(Long comisiones) {
			this.comisiones = comisiones;
		}

		/**
		 * @return the vacaciones
		 */
		public List<DashVacDto> getVacaciones() {
			return vacaciones;
		}

		/**
		 * @param vacaciones the vacaciones to set
		 */
		public void setVacaciones(List<DashVacDto> vacaciones) {
			this.vacaciones = vacaciones;
		}
		
}
