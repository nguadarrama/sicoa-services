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
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class JustificacionRepositoryImpl extends RepositoryBase implements JustificacionRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<JustificacionDto> obtenerListaJustificaciones(){
	
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT clave, id_justificacion, justificacion, activo ");
        qry.append("FROM c_justificacion ")
        .append("WHERE activo = true ");
        
        List<Map<String, Object>> justificaciones = jdbcTemplate.queryForList(qry.toString());
        List<JustificacionDto> listaJustificacion = new ArrayList<>();
        
        for (Map<String, Object> justificacion : justificaciones) {
    		JustificacionDto justificacionDto = new JustificacionDto();
    		justificacionDto.setClave((String)justificacion.get("clave"));
    		justificacionDto.setIdJustificacion((Integer)justificacion.get("id_justificacion"));
    		justificacionDto.setJustificacion((String)justificacion.get("justificacion"));
    		justificacionDto.setActivo((Boolean)justificacion.get("activo"));
    		listaJustificacion.add(justificacionDto);
    	}
     return listaJustificacion;
	}
	
	@Override
	public List<JustificacionDto> obtenerLista(){
	
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT clave, id_justificacion, justificacion, activo ");
        qry.append("FROM c_justificacion ");
        
        List<Map<String, Object>> justificaciones = jdbcTemplate.queryForList(qry.toString());
        List<JustificacionDto> listaJustificacion = new ArrayList<>();
        
        for (Map<String, Object> justificacion : justificaciones) {
    		JustificacionDto justificacionDto = new JustificacionDto();
    		justificacionDto.setClave((String)justificacion.get("clave"));
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
		qry.append("SELECT id_justificacion, clave, justificacion, activo ");
        qry.append("FROM c_justificacion ");
        qry.append("WHERE id_justificacion = :idJustificacion");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idJustificacion", idJustificacion);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<JustificacionDto>(JustificacionDto.class));
	}
	
	@Override
	public JustificacionDto modificaJustificacion (JustificacionDto justificacionDto){
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE c_justificacion SET clave = :clave, justificacion = :justificacion, activo = :activo ");
		qry.append("WHERE id_justificacion = :idJustificacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("clave", justificacionDto.getClave());
		parametros.addValue("idJustificacion", justificacionDto.getIdJustificacion());
		parametros.addValue("justificacion", justificacionDto.getJustificacion());
		parametros.addValue("activo", justificacionDto.isActivo());
		try{
		Integer i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1)
				justificacionDto.setMensaje("La justificación se ha actualizado correctamente.");
			else
				justificacionDto.setMensaje("Se ha generado un error al guardar, revise la información");
		}catch(Exception e){
			e.printStackTrace();
			justificacionDto.setMensaje("El registro ya existe en el sistema, favor de validar");
		}
		return justificacionDto;
	}
	
	@Override 
	public JustificacionDto agregaJustificacion(JustificacionDto justificacionDto){
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO c_justificacion (clave, justificacion, activo) ");
		qry.append("VALUES (:clave, :justificacion, :activo ) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("clave", justificacionDto.getClave());
		parametros.addValue("justificacion", justificacionDto.getJustificacion());
		parametros.addValue("activo", justificacionDto.isActivo());
		try{
			Integer i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				justificacionDto.setMensaje("La justificación se ha registrado correctamente.");
			}else{
				justificacionDto.setMensaje("Se ha generado un error al guardar, revise la información");
			}
		}catch(Exception e){
			logger.info("Error: {} ", e);
			justificacionDto.setMensaje("El registro ya existe en el sistema, favor de validar");
		}
		return justificacionDto;
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
