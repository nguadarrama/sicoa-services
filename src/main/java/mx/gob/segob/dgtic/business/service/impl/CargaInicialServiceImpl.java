package mx.gob.segob.dgtic.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.CargaInicialRules;
import mx.gob.segob.dgtic.business.rules.catalogo.UnidadAdministrativaRules;
import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioPerfilRules;
import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioRules;
import mx.gob.segob.dgtic.business.service.CargaInicialService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;
@Service
public class CargaInicialServiceImpl  implements CargaInicialService{

	@Autowired CargaInicialRules cargaInicialRules;
	@Autowired UsuarioRules usuarioRules;
	@Autowired UnidadAdministrativaRules unidadAdministrativaRules;
	@Autowired UsuarioPerfilRules usuarioPerfilRules;
	
	@Override
	public void cargaInicial() {
		List<UsuarioDto> listaUsuariosRecuperados = new ArrayList<UsuarioDto>();
		listaUsuariosRecuperados = cargaInicialRules.recuperarUsuariosCargaInicial();
		PerfilDto perfilDto = new PerfilDto();
		perfilDto.setClavePerfil("3");
		for(UsuarioDto usuarioDto: listaUsuariosRecuperados){
			usuarioRules.agregaUsuario(usuarioDto);
			UsuarioPerfilDto usuarioPerfilDto= new UsuarioPerfilDto();
			usuarioPerfilDto.setClavePerfil(perfilDto);
			usuarioPerfilDto.setClaveUsuario(usuarioDto);
			UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();
			usuarioUnidadAdministrativaDto.setClaveUsuario(usuarioDto);
			usuarioPerfilRules.agregaUsuarioPerfil(usuarioPerfilDto);
			
			UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
			unidadAdministrativaDto.setIdUnidad(1);
			
			usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
			unidadAdministrativaRules.consultaRegistraUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		}
		
	}
}
