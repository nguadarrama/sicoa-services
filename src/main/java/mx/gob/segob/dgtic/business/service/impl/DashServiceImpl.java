package mx.gob.segob.dgtic.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.business.rules.catalogo.DashRules;
import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioRules;
import mx.gob.segob.dgtic.business.service.DashService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DashBoardDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

@Component
public class DashServiceImpl implements DashService {

	
	@Autowired
	private DashRules dashRules;
	
	@Autowired
	private UsuarioRules usuarioRules;

	@Override
	public DashBoardDto dashBoard(Integer idUsuario) {
		UsuarioDto user = usuarioRules.buscaUsuarioPorId(idUsuario);
		return dashRules.dashBoard(idUsuario, user.getClaveUsuario());
	}

}
