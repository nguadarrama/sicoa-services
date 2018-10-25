package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.persistence.repository.ArchivoRepository;

@Component
public class ArchivoRules {

	@Autowired
	private ArchivoRepository archivoRepository;
	
	public List<ArchivoDto> obtenerListaArchivos() {
			return archivoRepository.obtenerListaArchivos();
	}
	
	public ArchivoDto buscaArchivo (Integer idArchivo){
		return archivoRepository.buscaArchivo(idArchivo);
	}
	
	public void modificaArchivo(ArchivoDto archivoDto){
		archivoRepository.modificaArchivo(archivoDto);
	}
	
	public Integer agregaArchivo(ArchivoDto archivoDto){
		return archivoRepository.agregaArchivo(archivoDto);
	}
	
	public void eliminaArchivo(Integer idArchivo){
		archivoRepository.eliminaArchivo(idArchivo);
	}
}
