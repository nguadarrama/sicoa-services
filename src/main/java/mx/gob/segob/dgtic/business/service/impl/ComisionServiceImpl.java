package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.service.ComisionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;

@Service
public class ComisionServiceImpl implements ComisionService {

	@Autowired
	private ComisionService comisionService;
	
	@Override
	public List<ComisionDto> obtenerListacomisiones() {
		
		return comisionService.obtenerListacomisiones();
	}

	@Override
	public ComisionDto buscaComision(Integer idComision) {
		
		return comisionService.buscaComision(idComision);
	}

	@Override
	public void modificaComision(ComisionDto comisionDto) {
		comisionService.modificaComision(comisionDto);
		
	}

	@Override
	public void agregaComision(ComisionDto comisionDto) {
		comisionService.agregaComision(comisionDto);
		
	}

	@Override
	public void eliminaComision(Integer idComision) {
		comisionService.eliminaComision(idComision);
		
	}

}
