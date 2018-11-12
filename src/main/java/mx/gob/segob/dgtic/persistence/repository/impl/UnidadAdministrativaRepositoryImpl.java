package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;
import mx.gob.segob.dgtic.persistence.repository.UnidadAdministrativaRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;
@Repository
public class UnidadAdministrativaRepositoryImpl extends RepositoryBase implements UnidadAdministrativaRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<UsuarioUnidadAdministrativaDto> obtenerListaUnidadAdministrativa() {
		StringBuilder qry = new StringBuilder();
        qry.append("select unidad.id_unidad,unidad.nombre,usuario.cve_m_usuario ");
        qry.append("from c_unidad_administrativa unidad, usuario_unidad_administrativa relacion, m_usuario usuario ");
        qry.append("where unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario ");
        
        List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioUnidadAdministrativaDto> listaUnidadAdministrativa = new ArrayList<>();
        
        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
        	UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();
    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get(RepositoryConstants.ID_UNIDAD));
    		unidadAdministrativaDto.setNombre((String)unidad.get(RepositoryConstants.NOMBRE));
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
    		usuarioDto.setClaveUsuario((String)unidad.get(RepositoryConstants.CLAVE_M_USUARIO));
    		usuarioUnidadAdministrativaDto.setClaveUsuario(usuarioDto);
    		
    		listaUnidadAdministrativa.add(usuarioUnidadAdministrativaDto);
    	}
     return listaUnidadAdministrativa;
	}

	@Override
	public void guardaUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto) {
	
		StringBuilder qry = new StringBuilder();
		qry.append("insert into usuario_unidad_administrativa (cve_m_usuario, id_unidad) ");
		qry.append("values (:claveUsuario, :idUnidad) ");
		logger.info("usuarioUnidadAdministrativaDto.getIdUnidad().getIdUnidad(): {} ",usuarioUnidadAdministrativaDto.getIdUnidad().getIdUnidad());
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.CLAVE_USUARIO2, usuarioUnidadAdministrativaDto.getClaveUsuario().getClaveUsuario());
		parametros.addValue("idUnidad", usuarioUnidadAdministrativaDto.getIdUnidad().getIdUnidad());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public UsuarioUnidadAdministrativaDto buscaUsuarioUnidadAdministrativa(String claveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("select id_unidad, cve_m_usuario ");
        qry.append("from usuario_unidad_administrativa ");
        qry.append("where cve_m_usuario = :claveUsuario and encargado=false");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto= new UsuarioUnidadAdministrativaDto();
        Map<String, Object> informacionConsulta= null;
        try{
        informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        }catch(EmptyResultDataAccessException error){
        	logger.warn("Error: {} ", error);
        }
        if(informacionConsulta!=null){
        
        UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
		unidadAdministrativaDto.setIdUnidad((Integer)informacionConsulta.get(RepositoryConstants.ID_UNIDAD));
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
		usuarioDto.setClaveUsuario((String)informacionConsulta.get(RepositoryConstants.CLAVE_M_USUARIO));
		usuarioUnidadAdministrativaDto.setClaveUsuario(usuarioDto);
        }else{
        	usuarioUnidadAdministrativaDto=null;
        }
        return 		usuarioUnidadAdministrativaDto;

	}

	@Override
	public void actualizaUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("update usuario_unidad_administrativa set id_unidad= :idUnidad ");
		qry.append("where cve_m_usuario = :claveUsuario ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUnidad", usuarioUnidadAdministrativaDto.getIdUnidad().getIdUnidad());
		parametros.addValue("claveUsuario", usuarioUnidadAdministrativaDto.getClaveUsuario().getClaveUsuario());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public List<UsuarioUnidadAdministrativaDto> consultaResponsable(String claveUsuario) {
		
		String query="select usuario.id_usuario, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, unidad.id_unidad, unidad.nombre nombre_unidad ";
		query+="from c_unidad_administrativa unidad, m_usuario usuario, usuario_unidad_administrativa relacion where usuario.cve_m_usuario=relacion.cve_m_usuario and unidad.id_unidad=relacion.id_unidad and relacion.encargado=true and unidad.id_unidad in ";
		query+="(select unidad.id_unidad ";
		query+="from c_unidad_administrativa unidad, m_usuario usuario, usuario_unidad_administrativa relacion ";
		query+="where usuario.cve_m_usuario=relacion.cve_m_usuario and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario ='"+claveUsuario+"');";
		logger.info("query: {} ",query);
		List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(query);
        List<UsuarioUnidadAdministrativaDto> listaUnidadAdministrativa = new ArrayList<>();
        
        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
        	UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();
        	logger.info("unidad: {} ",unidad.get(RepositoryConstants.NOMBRE));
    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get(RepositoryConstants.ID_UNIDAD));
    		unidadAdministrativaDto.setNombre((String)unidad.get(RepositoryConstants.NOMBRE_UNIDAD));
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
    		usuarioDto.setIdUsuario((Integer)unidad.get(RepositoryConstants.ID_USUARIO));
    		usuarioDto.setClaveUsuario((String)unidad.get(RepositoryConstants.CLAVE_M_USUARIO));
    		usuarioDto.setNombre((String)unidad.get(RepositoryConstants.NOMBRE));
    		usuarioDto.setApellidoPaterno((String)unidad.get(RepositoryConstants.APELLIDO_PATERNO));
    		usuarioDto.setApellidoMaterno((String)unidad.get(RepositoryConstants.APELLIDO_MATERNO));
    		logger.info("Datos : {} ",unidad.get(RepositoryConstants.CLAVE_M_USUARIO)+" "+unidad.get(RepositoryConstants.NOMBRE));
    		usuarioUnidadAdministrativaDto.setClaveUsuario(usuarioDto);
    		
    		listaUnidadAdministrativa.add(usuarioUnidadAdministrativaDto);
    	}
     return listaUnidadAdministrativa;
	}

	@Override
	public List<UsuarioUnidadAdministrativaDto> obtenerUnidadesAdministrativas() {
		
			StringBuilder qry = new StringBuilder();
	        qry.append(RepositoryConstants.L151);
	        qry.append(RepositoryConstants.L152);
	        
	        List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(qry.toString());
	        List<UsuarioUnidadAdministrativaDto> listaUnidadAdministrativa = new ArrayList<>();
	        
	        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
	        	UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();
	        	logger.info("unidad recuperada: {} ",unidad.get(RepositoryConstants.NOMBRE));
	    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
	    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get(RepositoryConstants.ID_UNIDAD));
	    		unidadAdministrativaDto.setNombre((String)unidad.get(RepositoryConstants.NOMBRE));
	    		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
	    		listaUnidadAdministrativa.add(usuarioUnidadAdministrativaDto);
	    	}
	     return listaUnidadAdministrativa;
	}

	@Override
	public List<UsuarioUnidadAdministrativaDto> consultasoloUnidades() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_unidad, nombre ");
        qry.append("from c_unidad_administrativa ");

        
        List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioUnidadAdministrativaDto> listaUnidadAdministrativa = new ArrayList<>();
        
        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
        	UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();

    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get("id_unidad"));
    		unidadAdministrativaDto.setNombre((String)unidad.get("nombre"));
    		listaUnidadAdministrativa.add(usuarioUnidadAdministrativaDto);
    	}
        return listaUnidadAdministrativa;
	}

	
	@Override
	public List<UnidadAdministrativaDto> obtenerUnidades() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_unidad, nombre ");
        qry.append("from c_unidad_administrativa ");
        
        List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(qry.toString());
        List<UnidadAdministrativaDto> listaUnidades = new ArrayList<>();
        
        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
        	
    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get("id_unidad"));
    		unidadAdministrativaDto.setNombre((String)unidad.get("nombre"));
    		
    		listaUnidades.add(unidadAdministrativaDto);
    	}
     return listaUnidades;
	}
}
