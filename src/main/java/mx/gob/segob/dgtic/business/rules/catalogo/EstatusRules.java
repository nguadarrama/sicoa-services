package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.persistence.repository.EstatusRepository;

@Component
public class EstatusRules {

	@Autowired
	private EstatusRepository estatusRepository;
	
	public List<EstatusDto> obtenerListaEstatus() {
			
			return estatusRepository.obtenerListaEstatus();
	}
	
	public EstatusDto buscaEstatus (Integer idEstatus){
		return estatusRepository.buscaEstatus(idEstatus);
	}
	
	public void modificaEstatus(EstatusDto estatusDto){
		estatusRepository.modificaEstatus(estatusDto);
	}
	
	public void agregaEstatus(EstatusDto estatusDto){
		estatusRepository.agregaEstatus(estatusDto);
	}
	
	public void eliminaEstatus(Integer idEstatus){
		estatusRepository.eliminaEstatus(idEstatus);
	}
}
