package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.VacacionPeriodoRules;
import mx.gob.segob.dgtic.business.service.VacacionPeriodoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;

@Service
public class VacacionPeriodoServiceImpl implements VacacionPeriodoService {

	@Autowired
	private VacacionPeriodoRules vacacionPeriodoRules;
	
	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo() {
			return vacacionPeriodoRules.obtenerListaVacacionPeriodo();
	}
	
	public VacacionPeriodoDto buscaVacacionPeriodo (Integer idVacacion){
		return vacacionPeriodoRules.buscaVacacionPeriodo(idVacacion);
	}
	
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto){
		vacacionPeriodoRules.modificaVacacionPeriodo(vacacionPeriodoDto);
	}
	
	public void agregaVacacionPeriodo (VacacionPeriodoDto vacacionPeriodoDto){
		vacacionPeriodoRules.agregaVacacionPeriodo(vacacionPeriodoDto);
	}
	
	public void eliminaVacacionPeriodo(Integer idVacacion){
		vacacionPeriodoRules.eliminaVacacionPeriodo(idVacacion);
	}
	
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario){
		return vacacionPeriodoRules.consultaVacacionPeriodoPorClaveUsuarioYPeriodo(idPeriodo, claveUsuario);
	}
}
