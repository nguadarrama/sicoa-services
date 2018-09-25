package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.persistence.repository.DetalleVacacionRepository;
@Component
public class DetalleVacacionRules {
	
	@Autowired
	private DetalleVacacionRepository detalleVacacionRepository;
	
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones() {
			return detalleVacacionRepository.obtenerListaDetalleVacaciones();
	}
	
	public DetalleVacacionDto buscaDetalleVacacion (Integer idDetalle){
		return detalleVacacionRepository.buscaDetalleVacacion(idDetalle);
	}
	
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto){
		detalleVacacionRepository.modificaDetalleVacacion(detalleVacacionDto);
	}
	
	public void agregaArchivo(DetalleVacacionDto detalleVacacionDto){
		detalleVacacionRepository.agregaDetalleVacacion(detalleVacacionDto);
	}
	
	public void eliminaDetalleVacacion(Integer idDetalle){
		detalleVacacionRepository.eliminaDetalleVacacion(idDetalle);
	}

}
