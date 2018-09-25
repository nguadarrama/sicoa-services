package mx.gob.segob.dgtic.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.CargaInicialRules;
import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioRules;
import mx.gob.segob.dgtic.business.service.CargaInicialService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
@Service
public class CargaInicialServiceImpl  implements CargaInicialService{

	@Autowired CargaInicialRules cargaInicialRules;
	@Autowired UsuarioRules usuarioRules;
	@Override
	public void cargaInicial() {
		List<UsuarioDto> listaUsuariosRecuperados = new ArrayList<UsuarioDto>();
		listaUsuariosRecuperados = cargaInicialRules.recuperarUsuariosCargaInicial();
		for(UsuarioDto usuarioDto: listaUsuariosRecuperados){
			usuarioRules.agregaUsuario(usuarioDto);
		}
		
	}
}
