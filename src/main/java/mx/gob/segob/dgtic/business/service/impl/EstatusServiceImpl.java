package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.EstatusRules;
import mx.gob.segob.dgtic.business.service.EstatusService;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;

@Service
public class EstatusServiceImpl implements EstatusService{

	@Autowired
	private EstatusRules estatusRules;
	
	@Override
	public List<EstatusDto> obtenerListaEstatus() {
		return estatusRules.obtenerListaEstatus();
	}
	
	@Override
	public EstatusDto buscaEstatus (Integer idEstatus){
		return estatusRules.buscaEstatus(idEstatus);	
	}
	
	@Override
	public void modificaEstatus(EstatusDto estatusDto){
		estatusRules.modificaEstatus(estatusDto);
	}
	
	@Override
	public void agregaEstatus(EstatusDto estatusDto){
		estatusRules.agregaEstatus(estatusDto);
	}
	
	@Override
	public void eliminaEstatus(Integer idEstatus){
		estatusRules.eliminaEstatus(idEstatus);
	}
	
	@Override
	public List<EstatusDto> obtenerListaCompletaEstatus() {
		return estatusRules.obtenerListaCompletaEstatus();
	}
}
