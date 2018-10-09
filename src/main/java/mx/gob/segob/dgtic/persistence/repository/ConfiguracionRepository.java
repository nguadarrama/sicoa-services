package mx.gob.segob.dgtic.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;

public interface ConfiguracionRepository {

	public Timestamp obtieneUltimaFechaCargaAsistencia();
	public void actualizaUltimaFechaCargaAsistencia();
	public String obtieneUltimaFechaCargaUsuarios();
	public void actualizaUltimaFechaCargaUsuarios();
	
}
