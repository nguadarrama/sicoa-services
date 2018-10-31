package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;

public interface PeriodoRepository {

	public List<PeriodoDto> obtenerListaPeriodos();
	public PeriodoDto buscaPeriodo(Integer idPerido);
	public void modificaPeriodo(PeriodoDto periodoDto);
	public PeriodoDto agregaPeriodo(PeriodoDto periodoDto);
	public void eliminaPeriodo(Integer idPeriodo);
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario);
	
	public int generaPeriodoVacacional (String inicio, String fin, String descripcion, boolean activo);
	public List<PeriodoDto> topPeriodo();
	public PeriodoDto cambioEstatusPeriodo(PeriodoDto periodo);
	public void modificaEstatustPeridoEmpleados(Integer id, boolean activo);
	public boolean existePeriodo(String fechaInicio);
	public List<PeriodoDto> obtenerListaPeriodosCatalogo();
}
