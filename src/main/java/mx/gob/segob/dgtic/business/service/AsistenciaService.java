package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

public interface AsistenciaService {
	public List<AsistenciaDto> obtenerListaAsistencia();
	public AsistenciaDto buscaAsistencia(int id);
	public void eliminaAsistencia(Integer id);
}
