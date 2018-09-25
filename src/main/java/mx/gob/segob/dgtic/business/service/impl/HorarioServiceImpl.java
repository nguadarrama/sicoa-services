package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.HorarioRules;
import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.HorarioRepository;

@Service
public class HorarioServiceImpl implements HorarioService {
	
	@Autowired
	private HorarioRules horarioRules;
	
	public List<Horario> obtenerListaHorarios() {

		return horarioRules.obtenerListaHorarios();
	}

	@Override
	public Horario buscaHorario(int id) {
		
		return horarioRules.buscaHorario(id);
	}

	@Override
	public void modificaHorario(Horario horario) {
		
		horarioRules.modificaHorario(horario);
	}
	
	@Override
	public void agregaHorario(Horario horario) {
		
		horarioRules.agregaHorario(horario);
	}

	@Override
	public void eliminaHorario(Integer id) {
		horarioRules.eliminaHorario(id);
		
	}
	
	
}
