package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;

public interface HorarioRepository {

	public List<Horario> obtenerListaHorarios();
	public Horario buscaHorario(int idHorario);
	public Horario modificaHorario(Horario horario);
	public Horario agregaHorario(Horario horaorio);
	public void eliminaHorario(Integer id);
	public List<Horario> obtenerListaHorariosCatalogo();
		
}
