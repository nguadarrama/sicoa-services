package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.segob.dgtic.business.rules.catalogo.JustificacionRules;
import mx.gob.segob.dgtic.business.service.JustificacionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;


@Service
public class JustificacionServiceImpl implements JustificacionService {
	
	@Autowired
	private JustificacionRules justificacionRules;
	
	@Override
	public List<JustificacionDto> obtenerListaJustificaciones() {
		return justificacionRules.obtenerListaJustificacions();
	}

	@Override
	public List<JustificacionDto> obtenerLista(){
		return justificacionRules.obtenerLista();
	}
	
	@Override
	public JustificacionDto buscaJustificacion(Integer id) {		
		return justificacionRules.buscaJustificacion(id);
	}

	@Override
	public JustificacionDto modificaJustificacion(JustificacionDto justificacionDto) {		
		return justificacionRules.modificaJustificacion(justificacionDto);
	}
	
	@Override
	public JustificacionDto agregaJustificacion(JustificacionDto justificacionDto) {		
		return justificacionRules.agregaJustificacion(justificacionDto);
	}

	@Override
	public void eliminaJustificacion(Integer id) {
		justificacionRules.eliminaJustificacion(id);
	}
	
	
}
