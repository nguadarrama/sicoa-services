package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.UnidadAdministrativaRepository;
@Repository
public class UnidadAdministrativaRepositoryImpl implements UnidadAdministrativaRepository{

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
        	System.out.println("unidad "+unidad.get("nombre"));
    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get("id_unidad"));
    		unidadAdministrativaDto.setNombre((String)unidad.get("nombre"));
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
    		usuarioDto.setClaveUsuario((String)unidad.get("cve_m_usuario"));
    		System.out.println("Datos "+unidad.get("cve_m_usuario")+" "+unidad.get("nombre"));
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
		System.out.println("usuarioUnidadAdministrativaDto.getIdUnidad().getIdUnidad() "+usuarioUnidadAdministrativaDto.getIdUnidad().getIdUnidad());
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", usuarioUnidadAdministrativaDto.getClaveUsuario().getClaveUsuario());
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
        	
        }

        		//queryForMap(qry.toString(), parametros);
        if(informacionConsulta!=null){
        
        UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
		unidadAdministrativaDto.setIdUnidad((Integer)informacionConsulta.get("id_unidad"));
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
		usuarioDto.setClaveUsuario((String)informacionConsulta.get("cve_m_usuario"));
		usuarioUnidadAdministrativaDto.setClaveUsuario(usuarioDto);
        }else{
        	usuarioUnidadAdministrativaDto=null;
        }
        return 		usuarioUnidadAdministrativaDto;
        //return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<UsuarioUnidadAdministrativaDto>(UsuarioUnidadAdministrativaDto.class));
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
		System.out.println("query "+query);
		List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(query);
        List<UsuarioUnidadAdministrativaDto> listaUnidadAdministrativa = new ArrayList<>();
        
        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
        	UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();
        	System.out.println("unidad "+unidad.get("nombre"));
    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get("id_unidad"));
    		unidadAdministrativaDto.setNombre((String)unidad.get("nombre_unidad"));
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
    		usuarioDto.setIdUsuario((Integer)unidad.get("id_usuario"));
    		usuarioDto.setClaveUsuario((String)unidad.get("cve_m_usuario"));
    		usuarioDto.setNombre((String)unidad.get("nombre"));
    		usuarioDto.setApellidoPaterno((String)unidad.get("apellido_paterno"));
    		usuarioDto.setApellidoMaterno((String)unidad.get("apellido_materno"));
    		System.out.println("Datos "+unidad.get("cve_m_usuario")+" "+unidad.get("nombre"));
    		usuarioUnidadAdministrativaDto.setClaveUsuario(usuarioDto);
    		
    		listaUnidadAdministrativa.add(usuarioUnidadAdministrativaDto);
    	}
     return listaUnidadAdministrativa;
	}

	@Override
	public List<UsuarioUnidadAdministrativaDto> obtenerUnidadesAdministrativas() {
		
			StringBuilder qry = new StringBuilder();
	        qry.append("select id_unidad, nombre ");
	        qry.append("from c_unidad_administrativa ");
	        
	        List<Map<String, Object>> unidadesArdminsitrativas = jdbcTemplate.queryForList(qry.toString());
	        List<UsuarioUnidadAdministrativaDto> listaUnidadAdministrativa = new ArrayList<>();
	        
	        for (Map<String, Object> unidad : unidadesArdminsitrativas) {
	        	UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = new UsuarioUnidadAdministrativaDto();
	        	System.out.println("unidad recuperada "+unidad.get("nombre"));
	    		UnidadAdministrativaDto unidadAdministrativaDto = new UnidadAdministrativaDto();
	    		unidadAdministrativaDto.setIdUnidad((Integer)unidad.get("id_unidad"));
	    		unidadAdministrativaDto.setNombre((String)unidad.get("nombre"));
	    		usuarioUnidadAdministrativaDto.setIdUnidad(unidadAdministrativaDto);
	    		listaUnidadAdministrativa.add(usuarioUnidadAdministrativaDto);
	    	}
	     return listaUnidadAdministrativa;
	}

}
