package mx.gob.segob.dgtic.business.rules.catalogo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.DashBoardDto;
import mx.gob.segob.dgtic.persistence.repository.DashBoardRepository;

@Component
public class DashRules {

	@Autowired
	private DashBoardRepository dashBoardRepository;
	
	public DashBoardDto dashBoard(Integer idUsuario, String clave) {
		return dashBoardRepository.dashBoard(idUsuario, clave);
	}

}
