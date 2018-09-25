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
	
}
