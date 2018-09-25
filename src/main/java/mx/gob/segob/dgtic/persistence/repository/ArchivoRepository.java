package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;

public interface ArchivoRepository {

	public List<ArchivoDto> obtenerListaArchivos();
	public ArchivoDto buscaArchivo(Integer idArchivo);
	public void modificaArchivo(ArchivoDto archivoDto);
	public void agregaArchivo(ArchivoDto archivoDto);
	public void eliminaArchivo(Integer idArchivo);
}
