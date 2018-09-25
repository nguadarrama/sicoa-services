package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.ArchivoRepository;

@Repository
public class ArchivoRepositoryImpl implements ArchivoRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<ArchivoDto> obtenerListaArchivos(){
		
		 StringBuilder qry = new StringBuilder();
	        qry.append("SELECT id_archivo, nombre, url, size, activo");
	        qry.append("FROM m_archivo ");
	        
	        List<Map<String, Object>> archivos = jdbcTemplate.queryForList(qry.toString());
	        List<ArchivoDto> listaArchivo = new ArrayList<>();
	        
	        for (Map<String, Object> archivo : archivos) {
	        	ArchivoDto archivoDto = new ArchivoDto();
	    		archivoDto.setIdArchivo((Integer)archivo.get("id_archivo"));
	    		archivoDto.setNombre((String)archivo.get("nombre"));
	    		archivoDto.setUrl((String)archivo.get("url"));
	    		archivoDto.setSize((Integer)archivo.get("size"));
	    		archivoDto.setActivo((Boolean)archivo.get("activo"));
	    		listaArchivo.add(archivoDto);
	    	}
	     return listaArchivo;	
	}
	
	@Override
	public ArchivoDto buscaArchivo(Integer idArchivo){
		
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_archivo, nombre, url, size, activo ");
        qry.append("FROM m_archivo ");
        qry.append("WHERE id_archivo = :idArchivo");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idArchivo", idArchivo);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<ArchivoDto>(ArchivoDto.class));
		
	}
	
	@Override
	public void modificaArchivo(ArchivoDto archivoDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE M_ARCHIVO SET nombre= :nombre, url = :url, size = :size, activo = :activo ");
		qry.append("WHERE id_archivo = :idArchivo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArchivo", archivoDto.getIdArchivo());
		parametros.addValue("nombre", archivoDto.getNombre());
		parametros.addValue("size", archivoDto.getSize());
		parametros.addValue("activo", archivoDto.getActivo());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void agregaArchivo (ArchivoDto archivoDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO M_ARCHIVO (nombre, url, size, activo) ");
		qry.append("VALUES (:nombre, :url, :size, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("nombre", archivoDto.getNombre());
		parametros.addValue("url", archivoDto.getUrl());
		parametros.addValue("size", archivoDto.getSize());
		parametros.addValue("activo", archivoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void eliminaArchivo (Integer idArchivo){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM M_ARCHIVO WHERE id_archivo = :idArchivo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArchivo", idArchivo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
}
