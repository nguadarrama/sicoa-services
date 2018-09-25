package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.PermisoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.PermisoRepository;

@Repository
public class PermisoRepositoryImpl implements PermisoRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<PermisoDto> obtenerListaPermisos(){
		
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT cve_c_permiso, descripcion, estatus ");
        qry.append("FROM c_permiso ");
        
        List<Map<String, Object>> permisos = jdbcTemplate.queryForList(qry.toString());
        List<PermisoDto> listaPermiso = new ArrayList<>();
        
        for (Map<String, Object> permiso : permisos) {
    		PermisoDto perfilDto = new PermisoDto();
    		perfilDto.setIdPermiso((String)permiso.get("cve_c_permiso"));
    		perfilDto.setDescripcion((String)permiso.get("descripcion"));
    		perfilDto.setEstatus((String)permiso.get("estatus"));
    		listaPermiso.add(perfilDto);
    	}
     return listaPermiso;
	}
	
	@Override
	public PermisoDto buscaPermiso(String idPermiso){
		
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT cve_c_permiso, descripcion, estatus ");
        qry.append("FROM c_permiso ");
        qry.append("WHERE cve_c_permiso = :idPermiso");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idPermiso", idPermiso);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PermisoDto>(PermisoDto.class));
		
	}
	
	@Override
	public void modificaPermiso(PermisoDto permisoDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE C_PERMISO SET descripcion= :descripcion, estatus = :estatus ");
		qry.append("WHERE cve_c_permiso = :idPermiso");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPermiso", permisoDto.getIdPermiso());
		parametros.addValue("descripcion", permisoDto.getDescripcion());
		parametros.addValue("estatus", permisoDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void agregaPermiso (PermisoDto permisoDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO C_PERMISO (cve_c_permiso, descripcion, estatus) ");
		qry.append("VALUES (:cve_c_permiso, :descripcion, :estatus) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("cve_c_permiso", permisoDto.getIdPermiso());
		parametros.addValue("descripcion", permisoDto.getDescripcion());
		parametros.addValue("estatus", permisoDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void eliminaPermiso (String idPermiso){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM C_PERMISO WHERE cve_c_permiso = :idPermiso");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPermiso", idPermiso);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
}
