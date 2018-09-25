package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.ArchivoRules;
import mx.gob.segob.dgtic.business.service.ArchivoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;

@Service
public class ArchivoServiceImpl implements ArchivoService{

	@Autowired
	private ArchivoRules archivoRules;
	
	@Override
	public List<ArchivoDto> obtenerListaArchivos() {
		
		return archivoRules.obtenerListaArchivos();
	}

	@Override
	public ArchivoDto buscaArchivo(Integer idArchivo) {
		
		return archivoRules.buscaArchivo(idArchivo);
	}

	@Override
	public void modificaArchivo(ArchivoDto archivoDto) {
		
		archivoRules.modificaArchivo(archivoDto);
		
	}

	@Override
	public void agregaArhivo(ArchivoDto archivoDto) {
		
		archivoRules.agregaArchivo(archivoDto);
		
	}

	@Override
	public void eliminaArchivo(Integer idArchivo) {
		
		archivoRules.eliminaArchivo(idArchivo);
		
	}

}
