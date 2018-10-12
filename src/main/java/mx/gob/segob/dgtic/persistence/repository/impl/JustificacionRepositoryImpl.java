package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.JustificacionRepository;

@Repository
public class JustificacionRepositoryImpl implements JustificacionRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<JustificacionDto> obtenerListaJustificaciones(){
	
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_justificacion, justificacion, activo ");
        qry.append("FROM c_justificacion ");
        
        List<Map<String, Object>> justificaciones = jdbcTemplate.queryForList(qry.toString());
        List<JustificacionDto> listaJustificacion = new ArrayList<>();
        
        for (Map<String, Object> justificacion : justificaciones) {
    		JustificacionDto justificacionDto = new JustificacionDto();
    		justificacionDto.setIdJustificacion((Integer)justificacion.get("id_justificacion"));
    		justificacionDto.setJustificacion((String)justificacion.get("justificacion"));
    		justificacionDto.setActivo((Boolean)justificacion.get("activo"));
    		listaJustificacion.add(justificacionDto);
    	}
     return listaJustificacion;
	}
	
	@Override
	public JustificacionDto buscaJustificacion (Integer idJustificacion){
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_justificacion, justificacion, activo ");
        qry.append("FROM c_justificacion ");
        qry.append("WHERE id_justificacion = :idJustificacion");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idJustificacion", idJustificacion);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<JustificacionDto>(JustificacionDto.class));
	}
	
	@Override
	public void modificaJustificacion (JustificacionDto justificacionDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE c_justificacion SET justificacion = :justificacion, activo = :activo ");
		qry.append("WHERE id_justificacion = :idJustificacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idJustificacion", justificacionDto.getIdJustificacion());
		parametros.addValue("justificacion", justificacionDto.getJustificacion());
		parametros.addValue("activo", justificacionDto.isActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void agregaJustificacion(JustificacionDto justificacionDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO c_justificacion (justificacion, activo) ");
		qry.append("VALUES ( :justificacion, :activo ) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("justificacion", justificacionDto.getJustificacion());
		parametros.addValue("activo", justificacionDto.isActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void eliminaJustificacion (Integer idJustificacion){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM c_justificacion WHERE id_justificacion = :idJustificacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idJustificacion", idJustificacion);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
}