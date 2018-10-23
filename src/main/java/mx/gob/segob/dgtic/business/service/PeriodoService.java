package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;

public interface PeriodoService {

	public List<PeriodoDto> obtenerListaPeriodos();
	public PeriodoDto buscaPeriodo(Integer idPeriodo);
	public void modificaPeriodo(PeriodoDto periodoDto);
	public PeriodoDto agregaPeriodo(PeriodoDto periodoDto);
	public void eliminaPeriodo(Integer idPeriodo);
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario);
	
	public void generaPeriodoVacacional (String inicio, String descripcion, boolean activo);
	public PeriodoDto cambiaEstatusPeriodo(PeriodoDto periodo);
}
