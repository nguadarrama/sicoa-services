package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.PeriodoRules;
import mx.gob.segob.dgtic.business.service.PeriodoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;

@Service
public class PeriodoServiceImpl implements PeriodoService {
	
	@Autowired
	private PeriodoRules periodoRules;
	
	@Override
	public List<PeriodoDto> obtenerListaPeriodos() {
		
		return periodoRules.obtenerListaPeriodos();
	}
	
	@Override
	public PeriodoDto buscaPeriodo (Integer idPeriodo){
		//PeriodoDto periodoDto=null;
		//periodoDto= periodoRules.buscaPeriodo(idPeriodo);
		return periodoRules.buscaPeriodo(idPeriodo);
		//System.out.println("periodo "+periodoDto.getIdPerfil());
		
		//return perfilDto;
	}
	
	@Override
	public void modificaPeriodo(PeriodoDto periodoDto){
		periodoRules.modificaPeriodo(periodoDto);
	}
	
	@Override
	public void agregaPeriodo(PeriodoDto periodoDto){
		periodoRules.agregaPeriodo(periodoDto);
	}
	
	@Override
	public void eliminaPeriodo(Integer idPeriodo){
		periodoRules.eliminaPeriodo(idPeriodo);
	}
	
	@Override 
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario){
		return periodoRules.buscaPeriodoPorClaveUsuario(claveUsuario);
	}
}
