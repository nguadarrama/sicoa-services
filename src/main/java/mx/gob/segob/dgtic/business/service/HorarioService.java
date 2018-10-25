package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;

public interface HorarioService {
	public List<Horario> obtenerListaHorarios();
	public Horario buscaHorario(int id);
	public Horario modificaHorario(Horario horario);
	public Horario agregaHorario(Horario horario);
	public void eliminaHorario(Integer id);
}
