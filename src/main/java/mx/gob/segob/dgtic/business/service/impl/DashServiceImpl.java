package mx.gob.segob.dgtic.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.business.rules.catalogo.DashRules;
import mx.gob.segob.dgtic.business.service.DashService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DashBoardDto;

@Component
public class DashServiceImpl implements DashService {

	
	@Autowired
	private DashRules dashRules;

	@Override
	public DashBoardDto dashBoard(Integer id_usuario) {
		return dashRules.dashBoard(id_usuario);
	}

}
