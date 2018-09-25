package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;

@Component
public class ComisionRules {

	@Autowired
	private ComisionRepository comisionRepository;
	
	public List<ComisionDto> obtenerListaComisiones() {
			return comisionRepository.obtenerListaComisiones();
	}
	
	public ComisionDto buscacomision (Integer idComision){
		return comisionRepository.buscaComision(idComision);
	}
	
	public void modificaComision(ComisionDto comisionDto){
		comisionRepository.modificaComision(comisionDto);
	}
	
	public void agregaComision(ComisionDto comisionDto){
		comisionRepository.agregaComision(comisionDto);
	}
	
	public void eliminacomision(Integer idComision){
		comisionRepository.eliminaComision(idComision);
	}
}
