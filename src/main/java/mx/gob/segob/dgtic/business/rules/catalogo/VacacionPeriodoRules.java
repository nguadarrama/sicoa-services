package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.VacacionPeriodoRepository;

@Component
public class VacacionPeriodoRules {

	@Autowired
	private VacacionPeriodoRepository vacacionPeriodoRepository;
	
	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo() {
			return vacacionPeriodoRepository.obtenerListaVacacionPeriodo();
	}
	
	public VacacionPeriodoDto buscaVacacionPeriodo (Integer idVacacion){
		return vacacionPeriodoRepository.buscaVacacionPeriodo(idVacacion);
	}
	
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto){
		vacacionPeriodoRepository.modificaVacacionPeriodo(vacacionPeriodoDto);
	}
	
	public void agregaVacacionPeriodo (VacacionPeriodoDto vacacionPeriodoDto){
		vacacionPeriodoRepository.agregaVacacionPeriodo(vacacionPeriodoDto);
	}
	
	public void eliminaVacacionPeriodo(Integer idVacacion){
		vacacionPeriodoRepository.eliminaVacacionPeriodo(idVacacion);
	}
	
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario){
		return vacacionPeriodoRepository.consultaVacacionPeriodoPorClaveUsuarioYPeriodo(idPeriodo, claveUsuario);
	}
	
	public void generarVacacionesTodos (int idUsuario, int idPeriodo, int estatus, String inicio, int dias, boolean activo, String ingreso) {
		vacacionPeriodoRepository.generarVacacionPeriodotodos(idUsuario, idPeriodo, estatus, inicio, dias, activo, ingreso);
	}
	
	public List<VacacionPeriodoDto> obtenerUsuariosConVacacionesPorFiltros(String claveUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String idUnidad) {
		return vacacionPeriodoRepository.obtenerUsuariosConVacacionesPorFiltros(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idUnidad);
}
}
