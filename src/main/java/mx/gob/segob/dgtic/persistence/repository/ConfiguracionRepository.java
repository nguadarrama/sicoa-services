package mx.gob.segob.dgtic.persistence.repository;

import java.sql.Timestamp;

public interface ConfiguracionRepository {

	public Timestamp obtieneUltimaFechaCargaAsistencia();
	public void actualizaUltimaFechaCargaAsistencia();
	public String obtieneUltimaFechaCargaUsuarios();
	public void actualizaUltimaFechaCargaUsuarios();
	
}
