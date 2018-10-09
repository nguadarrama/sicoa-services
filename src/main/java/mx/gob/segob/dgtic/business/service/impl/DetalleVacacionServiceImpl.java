package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.DetalleVacacionRules;
import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;

@Service
public class DetalleVacacionServiceImpl implements DetalleVacacionService {

	@Autowired
	private DetalleVacacionRules detalleVacacionRules;
	
	@Override
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones() {
		
		return detalleVacacionRules.obtenerListaDetalleVacaciones();
	}

	@Override
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle) {
		
		return detalleVacacionRules.buscaDetalleVacacion(idDetalle);
	}

	@Override
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		detalleVacacionRules.modificaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public void agregaDetalleVacacion (DetalleVacacionDto detalleVacacionDto) {
		
		detalleVacacionRules.agregaDetalleVacacion(detalleVacacionDto);
		
	}

	@Override
	public void eliminaDetalleVacacion(Integer idDetalle) {
		
		detalleVacacionRules.eliminaDetalleVacacion(idDetalle);	
	}

	@Override
	public void aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		detalleVacacionRules.aceptaORechazaDetalleVacacion(detalleVacacionDto);
		
	}
}
