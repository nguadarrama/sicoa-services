package mx.gob.segob.dgtic.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilPermisoDto;
import mx.gob.segob.dgtic.persistence.repository.PerfilPermisoRepository;
@Repository
public class PerfilPermisoRepositoryImpl implements PerfilPermisoRepository {

	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public void guardarPermisoRepository(PerfilPermisoDto perfilPermisoDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO D_PERFIL_PERMISO (cve_c_perfil, cve_c_permiso) ");
		qry.append("VALUES (:clavePerfil, :clavePermiso) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("clavePermiso", perfilPermisoDto.getClavePermiso());
		parametros.addValue("clavePerfil", perfilPermisoDto.getClavePerfil());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
		
	}

}
