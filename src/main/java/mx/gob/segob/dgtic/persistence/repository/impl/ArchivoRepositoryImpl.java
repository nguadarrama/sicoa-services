package mx.gob.segob.dgtic.persistence.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
		qry.append("UPDATE m_archivo SET nombre= :nombre, url = :url, size = :size, activo = :activo ");
		qry.append("WHERE id_archivo = :idArchivo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArchivo", archivoDto.getIdArchivo());
		parametros.addValue("nombre", archivoDto.getNombre());
		parametros.addValue("size", archivoDto.getSize());
		parametros.addValue("activo", archivoDto.getActivo());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public Integer agregaArchivo (ArchivoDto archivoDto){
		
		//String ruta="C:/Sicoa/"+archivoDto.getClaveUsuario()+"/"+archivoDto.getAccion();
		if(archivoDto.getArchivo()!=null){
			try {
				System.out.println("Guardando archivo");
			byte[] bite=archivoDto.getArchivo();
			System.out.println("datos del archivo "+bite);
			Path path= Paths.get(archivoDto.getUrl());
			Path pathNombre= Paths.get(archivoDto.getUrl()+archivoDto.getNombre());
			if (!Files.exists(path)){
			    Files.createDirectories(path);
			}
				
				Files.write(pathNombre, bite);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//String valorRuta= ruta+archivoDto.getArchivo().getOriginalFilename();
		//archivoDto.setUrl(valorRuta);
		//archivoDto.setSize((int) (long) archivoDto.getArchivo().getSize());
		//archivoDto.setActivo(true);
	    //archivoDto.setNombre(archivoDto.getArchivo().getOriginalFilename());
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_archivo (nombre, url, size, activo) ");
		qry.append("VALUES (:nombre, :url, :size, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("nombre", archivoDto.getNombre());
		parametros.addValue("url", archivoDto.getUrl());
		parametros.addValue("size", archivoDto.getSize());
		parametros.addValue("activo", archivoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		//Obtenemos el d del archivo que se acaba de insertar
		qry = new StringBuilder();
		qry.append("select max(id_usuario) as id_usuario ");
		qry.append("from m_usuario");
		Map<String, Object> archivo = jdbcTemplate.queryForMap(qry.toString());
        Integer idArchivo = (Integer) archivo.get("id_usuario");
        return idArchivo;
	}
	
	@Override 
	public void eliminaArchivo (Integer idArchivo){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_archivo WHERE id_archivo = :idArchivo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArchivo", idArchivo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	
}
