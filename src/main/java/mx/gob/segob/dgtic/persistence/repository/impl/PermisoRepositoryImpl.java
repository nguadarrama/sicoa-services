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
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

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
    		perfilDto.setIdPermiso((String)permiso.get(RepositoryConstants.CVE_C_PERMISO));
    		perfilDto.setDescripcion((String)permiso.get(RepositoryConstants.DESCRIPCION));
    		perfilDto.setEstatus((String)permiso.get(RepositoryConstants.ESTATUS));
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
        parametros.addValue(RepositoryConstants.ID_PERMISO2, idPermiso);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PermisoDto>(PermisoDto.class));
		
	}
	
	@Override
	public void modificaPermiso(PermisoDto permisoDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE c_permiso SET descripcion= :descripcion, estatus = :estatus ");
		qry.append("WHERE cve_c_permiso = :idPermiso");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERMISO2, permisoDto.getIdPermiso());
		parametros.addValue(RepositoryConstants.DESCRIPCION, permisoDto.getDescripcion());
		parametros.addValue(RepositoryConstants.ESTATUS, permisoDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void agregaPermiso (PermisoDto permisoDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO c_permiso (cve_c_permiso, descripcion, estatus) ");
		qry.append("VALUES (:cve_c_permiso, :descripcion, :estatus) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.CVE_C_PERMISO, permisoDto.getIdPermiso());
		parametros.addValue(RepositoryConstants.DESCRIPCION, permisoDto.getDescripcion());
		parametros.addValue(RepositoryConstants.ESTATUS, permisoDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void eliminaPermiso (String idPermiso){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM c_permiso WHERE cve_c_permiso = :idPermiso");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERMISO2, idPermiso);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
}
