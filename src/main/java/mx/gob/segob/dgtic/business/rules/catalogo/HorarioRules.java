package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.HorarioRepository;

@Component
public class HorarioRules {

	@Autowired
	private HorarioRepository horarioRepository;
	
	public List<Horario> obtenerListaHorarios() {
		
		return horarioRepository.obtenerListaHorarios();
	}
	
	public List<Horario> obtenerListaHorariosCatalogo() {
		
		return horarioRepository.obtenerListaHorariosCatalogo();
	}
	
	public Horario buscaHorario(int id) {
		
		return horarioRepository.buscaHorario(id);
	}
	
	public Horario modificaHorario(Horario horario) {
		return horarioRepository.modificaHorario(horario);
	}
	
	public Horario agregaHorario(Horario horario) {
		return horarioRepository.agregaHorario(horario);
	}
	
	public void eliminaHorario(Integer id) {
		horarioRepository.eliminaHorario(id);
	}
}
