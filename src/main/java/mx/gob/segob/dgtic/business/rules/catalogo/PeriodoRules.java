package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;

@Component
public class PeriodoRules {

	@Autowired
	private PeriodoRepository periodoRepository;
	
	public List<PeriodoDto> obtenerListaPeriodos() {
			
			return periodoRepository.obtenerListaPeriodos();
	}
	
	public PeriodoDto buscaPeriodo (Integer idPeriodo){
		return periodoRepository.buscaPeriodo(idPeriodo);
	}
	
	public void modificaPeriodo(PeriodoDto periodoDto){
		periodoRepository.modificaPeriodo(periodoDto);
	}
	
	public void agregaPeriodo(PeriodoDto periodoDto){
		periodoRepository.agregaPeriodo(periodoDto);
	}
	
	public void eliminaPeriodo(Integer idPeriodo){
		periodoRepository.eliminaPeriodo(idPeriodo);
	}
	
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario){
		return periodoRepository.buscaPeriodoPorClaveUsuario(claveUsuario);
	}
	
	public int generaPeriodoVacacional(String inicio, String fin, String descripcion, boolean activo) {
		return periodoRepository.generaPeriodoVacacional(inicio, fin, descripcion, activo);
	}
	
	public List<PeriodoDto> topPeriodo (){
		return periodoRepository.topPeriodo();
	}
	
	public void cambioEstatusPeriodo (Integer id, boolean activo) {
		periodoRepository.cambioEstatusPeriodo(id, activo);
	}
	
	public void modificaEstatustPeridoEmpleados(Integer id, boolean activo) {
		periodoRepository.modificaEstatustPeridoEmpleados(id, activo);
	}
	
	public boolean existePeriodo(String fecha) {
		boolean existe = false;
		existe = periodoRepository.existePeriodo(fecha);
		System.out.println("PeriodoRules method-- existePeriodo= "+existe);
		return existe;
	}
}
