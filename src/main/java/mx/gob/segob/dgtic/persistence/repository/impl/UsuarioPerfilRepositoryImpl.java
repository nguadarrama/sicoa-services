package mx.gob.segob.dgtic.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.persistence.repository.UsuarioPerfilRepository;

@Repository
public class UsuarioPerfilRepositoryImpl implements UsuarioPerfilRepository{

	//@Autowired
    //private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public void agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO D_USUARIO_PERFIL (cve_m_usuario, cve_c_perfil) ");
		qry.append("VALUES (:claveUsuario, :clavePerfil) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", usuarioPerfilDto.getClaveUsuario());
		parametros.addValue("clavePerfil", usuarioPerfilDto.getClavePerfil());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	
}
