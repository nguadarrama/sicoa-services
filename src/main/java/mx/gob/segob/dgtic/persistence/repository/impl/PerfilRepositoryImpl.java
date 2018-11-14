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
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.PerfilRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class PerfilRepositoryImpl extends RepositoryBase implements PerfilRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<PerfilDto> obtenerListaPerfiles(){
		
		 StringBuilder qry = new StringBuilder();
	        qry.append("SELECT cve_c_perfil, descripcion, estatus ");
	        qry.append("FROM c_perfil ");
	        
	        List<Map<String, Object>> perfiles = jdbcTemplate.queryForList(qry.toString());
	        List<PerfilDto> listaPerfil = new ArrayList<>();
	        
	        for (Map<String, Object> perfil : perfiles) {
	    		PerfilDto perfilDto = new PerfilDto();
	    		logger.info("Perfiles: {} ",perfil.get(RepositoryConstants.DESCRIPCION));
	    		perfilDto.setClavePerfil((String)perfil.get(RepositoryConstants.CVE_C_PERFIL));
	    		perfilDto.setDescripcion((String)perfil.get(RepositoryConstants.DESCRIPCION));
	    		perfilDto.setEstatus((String)perfil.get(RepositoryConstants.ESTATUS));
	    		listaPerfil.add(perfilDto);
	    	}
	     return listaPerfil;	
	}
	
	@Override
	public PerfilDto buscaPerfil(String idPerfil){
		
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT cve_c_perfil, descripcion, estatus ");
        qry.append("FROM c_perfil ");
        qry.append("WHERE cve_c_perfil = :idPerfil");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_PERFIL2, idPerfil);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PerfilDto>(PerfilDto.class));
		
	}
	
	@Override
	public void modificaPerfil(PerfilDto perfilDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE c_perfil SET descripcion= :descripcion, estatus = :estatus ");
		qry.append("WHERE cve_c_perfil = :idPerfil");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERFIL2, perfilDto.getClavePerfil());
		parametros.addValue(RepositoryConstants.DESCRIPCION, perfilDto.getDescripcion());
		parametros.addValue(RepositoryConstants.ESTATUS, perfilDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void agregaPerfil (PerfilDto perfilDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO c_perfil (cve_c_perfil, descripcion, estatus) ");
		qry.append("VALUES (:cve_c_perfil, :descripcion, :estatus) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.CVE_C_PERFIL, perfilDto.getClavePerfil());
		parametros.addValue(RepositoryConstants.DESCRIPCION, perfilDto.getDescripcion());
		parametros.addValue(RepositoryConstants.ESTATUS, perfilDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void eliminaPerfil (String idPerfil){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM c_perfil WHERE cve_c_perfil = :idPerfil");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERFIL2, idPerfil);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

}
