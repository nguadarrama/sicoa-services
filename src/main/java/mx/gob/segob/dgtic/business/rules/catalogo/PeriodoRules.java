package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;

@Component
public class PeriodoRules extends ServiceBase{

	@Autowired
	private PeriodoRepository periodoRepository;
	
	public List<PeriodoDto> obtenerListaPeriodos() {
			
			return periodoRepository.obtenerListaPeriodos();
	}
	
	public List<PeriodoDto> obtenerListaPeriodosCatalogo() {
		
		return periodoRepository.obtenerListaPeriodosCatalogo();
}
	
	public PeriodoDto buscaPeriodo (Integer idPeriodo){
		return periodoRepository.buscaPeriodo(idPeriodo);
	}
	
	public void modificaPeriodo(PeriodoDto periodoDto){
		 periodoRepository.modificaPeriodo(periodoDto);
	}
	
	public PeriodoDto agregaPeriodo(PeriodoDto periodoDto){
		PeriodoDto periodo;
		periodo = periodoRepository.agregaPeriodo(periodoDto);
		return periodo;
	}
	
	public void eliminaPeriodo(Integer idPeriodo){
		periodoRepository.eliminaPeriodo(idPeriodo);
	}
	
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario){
		return periodoRepository.buscaPeriodoPorClaveUsuario(claveUsuario);
	}
	
	public PeriodoDto generaPeriodoVacacional(PeriodoDto periodo) {
		return periodoRepository.agregaPeriodo(periodo);
	}
	
	public List<PeriodoDto> topPeriodo (){
		return periodoRepository.topPeriodo();
	}
	
	public PeriodoDto cambioEstatusPeriodo (PeriodoDto periodo) {
		periodo = periodoRepository.cambioEstatusPeriodo(periodo);
		return periodo;
	}
	
	public void modificaEstatustPeridoEmpleados(Integer id, boolean activo) {
		periodoRepository.modificaEstatustPeridoEmpleados(id, activo);
	}
	
	public boolean existePeriodo(String fecha) {
		boolean existe = false;
		existe = periodoRepository.existePeriodo(fecha);
		logger.info("PeriodoRules method-- existePeriodo= {} ",existe);
		return existe;
	}
}
