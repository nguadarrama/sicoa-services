package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.persistence.repository.UsuarioPerfilRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class UsuarioPerfilRepositoryImpl extends RepositoryBase implements UsuarioPerfilRepository{
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer agregaUsuarioPerfil(UsuarioPerfilDto usuarioPerfilDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("insert into d_usuario_perfil (cve_m_usuario, cve_c_perfil) ");
		qry.append("values (:claveUsuario, :clavePerfil) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", usuarioPerfilDto.getClaveUsuario().getClaveUsuario());
		parametros.addValue("clavePerfil", usuarioPerfilDto.getClavePerfil().getClavePerfil());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminarUsuarioPerfil(Integer idUsuarioPerfil) {
		StringBuilder qry = new StringBuilder();
		qry.append("delete from d_usuario_perfil where id_usuario_perfil = :idUsuarioPerfil");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuarioPerfil", idUsuarioPerfil);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public List<UsuarioPerfilDto> consultaUsuarioPerfil(String claveUsuario) {
		logger.info("clave usuario para consulta: {} ",claveUsuario);
		/**StringBuilder qry = new StringBuilder();
		qry.append("select usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, perfil.cve_c_perfil, perfil.descripcion ");
		qry.append("");**/
		String query="select relacion.id_usuario_perfil, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, perfil.cve_c_perfil, perfil.descripcion "
		+"from m_usuario usuario, c_perfil perfil, d_usuario_perfil relacion "
		+"where relacion.cve_m_usuario=usuario.cve_m_usuario and relacion.cve_c_perfil=perfil.cve_c_perfil and usuario.cve_m_usuario = '"+claveUsuario+"'";
		logger.info("consulta:{} ",query);
		
        List<Map<String, Object>> usuariosPerfiles = jdbcTemplate.queryForList(query.toString());
        List<UsuarioPerfilDto> listaUsuarioPerfil = new ArrayList<>();
        
        for (Map<String, Object> usuarioPerfil : usuariosPerfiles) {
        	UsuarioPerfilDto usuarioPerfilDto = new UsuarioPerfilDto();
        	
    		PerfilDto perfilDto = new PerfilDto();
    		perfilDto.setClavePerfil((String)usuarioPerfil.get(RepositoryConstants.CVE_C_PERFIL));
    		perfilDto.setDescripcion((String)usuarioPerfil.get(RepositoryConstants.DESCRIPCION));
    		usuarioPerfilDto.setClavePerfil(perfilDto);
    		logger.info("descripcion : {}",usuarioPerfil.get(RepositoryConstants.DESCRIPCION));
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioDto.setClaveUsuario((String)usuarioPerfil.get(RepositoryConstants.CLAVE_M_USUARIO));
    		usuarioDto.setNombre((String)usuarioPerfil.get(RepositoryConstants.NOMBRE));
    		usuarioDto.setApellidoPaterno((String)usuarioPerfil.get(RepositoryConstants.APELLIDO_PATERNO));
    		usuarioDto.setApellidoMaterno((String)usuarioPerfil.get(RepositoryConstants.APELLIDO_MATERNO));
    		usuarioPerfilDto.setClaveUsuario(usuarioDto);
    		usuarioPerfilDto.setIdUsuarioPerfil((Integer)usuarioPerfil.get(RepositoryConstants.ID_USUARIO_PERFIL));
    		
    		listaUsuarioPerfil.add(usuarioPerfilDto);
    	}
     return listaUsuarioPerfil;
	}

	@Override
	public List<UsuarioPerfilDto> consultaUsuarisePerfiles() {
		StringBuilder qry = new StringBuilder();
        qry.append("select usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, perfil.cve_c_perfil, perfil.descripcion ");
        qry.append("from m_usuario usuario, c_perfil perfil, d_usuario_perfil relacion ");
        qry.append("where relacion.cve_m_usuario=usuario.cve_m_usuario and relacion.cve_c_perfil=perfil.cve_c_perfil ");
        
        List<Map<String, Object>> usuariosPerfiles = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioPerfilDto> listaUsuarioPerfil = new ArrayList<>();
        
        for (Map<String, Object> usuarioPerfil : usuariosPerfiles) {
        	UsuarioPerfilDto usuarioPerfilDto = new UsuarioPerfilDto();
        	
    		PerfilDto perfilDto = new PerfilDto();
    		perfilDto.setClavePerfil((String)usuarioPerfil.get("cve_c_perfil"));
    		perfilDto.setDescripcion((String)usuarioPerfil.get("descripcion"));
    		usuarioPerfilDto.setClavePerfil(perfilDto);
    		
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioDto.setClaveUsuario((String)usuarioPerfil.get("cve_m_usuario"));
    		usuarioDto.setNombre((String)usuarioPerfil.get("nombre"));
    		usuarioDto.setApellidoPaterno((String)usuarioPerfil.get("apellido_paterno"));
    		usuarioDto.setApellidoMaterno((String)usuarioPerfil.get("apellido_materno"));
    		usuarioPerfilDto.setClaveUsuario(usuarioDto);
    		
    		listaUsuarioPerfil.add(usuarioPerfilDto);
    	}
     return listaUsuarioPerfil;
	}

	@Override
	public void actualizaUsuarioPerfil(Integer idUsuarioPerfil) {
		/**
		 * 
		 */
	}

	
}
