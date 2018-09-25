package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;

public interface ArchivoService {

	public List<ArchivoDto> obtenerListaArchivos();
	public ArchivoDto buscaArchivo(Integer idArchivo);
	public void modificaArchivo(ArchivoDto archivoDto);
	public void agregaArhivo(ArchivoDto archivoDto);
	public void eliminaArchivo(Integer idArchivo);
}
