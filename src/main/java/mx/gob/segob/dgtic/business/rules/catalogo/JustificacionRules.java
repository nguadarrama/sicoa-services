package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;
import mx.gob.segob.dgtic.persistence.repository.JustificacionRepository;

@Component
public class JustificacionRules {
	
	@Autowired 
	private JustificacionRepository justificacionRepository; 
	
	public List<JustificacionDto> obtenerListaJustificacions (){
		return justificacionRepository.obtenerListaJustificaciones();
	}
	
	
	public JustificacionDto buscaJustificacion (Integer idJustificacion){
		return justificacionRepository.buscaJustificacion(idJustificacion);
	}
	
	
	public void modificaJustificacion (JustificacionDto justificacionDto){
		justificacionRepository.modificaJustificacion(justificacionDto);
	}
	
	
	public void agregaJustificacion (JustificacionDto justificacionDto){
		justificacionRepository.agregaJustificacion(justificacionDto);
	}
	
	
	public void eliminaJustificacion (Integer idJustificacion){
		justificacionRepository.eliminaJustificacion(idJustificacion);
	}

} 

