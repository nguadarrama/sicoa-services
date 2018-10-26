package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.UnidadAdministrativaRules;
import mx.gob.segob.dgtic.business.service.UnidadAdministrativaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;

@Service
public class UnidadAdministrativaServiceImpl implements UnidadAdministrativaService {

	@Autowired 
	UnidadAdministrativaRules unidadAdministrativaRules;
	
	@Override
	public List<UsuarioUnidadAdministrativaDto> obtenerListaUnidadAdministrativa() {
		return unidadAdministrativaRules.obtenerListaUnidadAdministrativa();
	}

	@Override
	public void consultaRegistraUsuarioUnidadAdministrativa(
			UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto) {
		unidadAdministrativaRules.consultaRegistraUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		
	}

	@Override
	public List<UsuarioUnidadAdministrativaDto> consultaResponsable(String claveUsuario) {
		return unidadAdministrativaRules.consultaResponsable(claveUsuario);
	}
	
	@Override
	public List<UsuarioUnidadAdministrativaDto> obtenerUnidadesAdministrativas(){
		return unidadAdministrativaRules.obtenerUnidadesAdministrativas();
	}

	@Override
	public List<UsuarioUnidadAdministrativaDto> consultasoloUnidades() {
		return unidadAdministrativaRules.consultasoloUnidades();
	}
	
	@Override
	public List<UnidadAdministrativaDto> obtieneUnidades() {
		return unidadAdministrativaRules.obtenerUnidades();
	}

}
