package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.EstatusRepository;

@Repository
public class EstatusRepositoryImpl implements EstatusRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<EstatusDto> obtenerListaEstatus(){
		
		 StringBuilder qry = new StringBuilder();
	        qry.append("SELECT id_estatus, estatus, descripcion ");
	        qry.append("FROM m_estatus ");
	        
	        List<Map<String, Object>> listaestatus = jdbcTemplate.queryForList(qry.toString());
	        List<EstatusDto> listaEstatus = new ArrayList<>();
	        
	        for (Map<String, Object> estatus : listaestatus) {
	    		EstatusDto estatusDto = new EstatusDto();
	    		estatusDto.setIdEstatus((Integer)estatus.get("id_estatus"));
	    		estatusDto.setDescripcion((String)estatus.get("descripcion"));
	    		estatusDto.setEstatus((String)estatus.get("estatus"));
	    		listaEstatus.add(estatusDto);
	    	}
	     return listaEstatus;	
	}
	
	@Override
	public EstatusDto buscaEstatus(Integer idEstatus){
		
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_estatus, descripcion, estatus ");
        qry.append("FROM m_estatus ");
        qry.append("WHERE id_estatus = :idEstatus");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idEstatus", idEstatus);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<EstatusDto>(EstatusDto.class));
		
	}
	
	@Override
	public void modificaEstatus(EstatusDto estatusDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_estatus SET descripcion= :descripcion, estatus = :estatus ");
		qry.append("WHERE id_estatus = :idEstatus");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idEstatus", estatusDto.getIdEstatus());
		parametros.addValue("descripcion", estatusDto.getDescripcion());
		parametros.addValue("estatus", estatusDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void agregaEstatus (EstatusDto estatusDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO M_ESTATUS (descripcion, estatus) ");
		qry.append("VALUES (:descripcion, :estatus) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("descripcion", estatusDto.getDescripcion());
		parametros.addValue("estatus", estatusDto.getEstatus());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void eliminaEstatus (Integer idEstatus){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM M_ESTATUS WHERE id_estatus = :idEstatus");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idEstatus", idEstatus);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
}
