package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.TipoDiaRepository;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class TipoDiaRepositoryImpl implements TipoDiaRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<TipoDiaDto> obtenerListaTipoDias(){
	
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_tipo_dia, nombre, observacion, incidencia ");
        qry.append("FROM c_tipo_dia ");
        
        List<Map<String, Object>> tipoDias = jdbcTemplate.queryForList(qry.toString());
        List<TipoDiaDto> listaTipoDia = new ArrayList<>();
        
        for (Map<String, Object> tipoDia : tipoDias) {
    		TipoDiaDto tipoDiaDto = new TipoDiaDto();
    		tipoDiaDto.setIdTipoDia((Integer)tipoDia.get(RepositoryConstants.ID_TIPO_DIA));
    		tipoDiaDto.setNombre((String)tipoDia.get(RepositoryConstants.NOMBRE));
    		tipoDiaDto.setObservacion((String)tipoDia.get(RepositoryConstants.OBSERVACION));
    		tipoDiaDto.setIncidencia((Boolean)tipoDia.get(RepositoryConstants.INCIDENCIA));
    		listaTipoDia.add(tipoDiaDto);
    	}
     return listaTipoDia;
	}
	
	@Override
	public TipoDiaDto buscaTipoDia (Integer idTipoDia){
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_tipo_dia, nombre, observacion, incidencia ");
        qry.append("FROM c_tipo_dia ");
        qry.append("WHERE id_tipo_dia = :idTipoDia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_TIPO_DIA2, idTipoDia);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<TipoDiaDto>(TipoDiaDto.class));
	}
	
	@Override
	public void modificaTipoDia (TipoDiaDto tipoDiaDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE c_tipo_dia SET nombre= :nombre, observacion = :observacion, incidencia = :incidencia ");
		qry.append("WHERE id_tipo_dia = :idTipoDia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idTipoDia", tipoDiaDto.getIdTipoDia());
		parametros.addValue("nombre", tipoDiaDto.getNombre());
		parametros.addValue("observacion", tipoDiaDto.getObservacion());
		parametros.addValue("incidencia", tipoDiaDto.getIncidencia());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override 
	public void agregaTipoDia (TipoDiaDto tipoDiaDto){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO c_tipo_dia (nombre, observacion, incidencia) ");
		qry.append("VALUES (:nombre, :observacion, :incidencia) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("nombre", tipoDiaDto.getNombre());
		parametros.addValue("observacion", tipoDiaDto.getObservacion());
		parametros.addValue("incidencia", tipoDiaDto.getIncidencia());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void eliminaTipoDia (Integer idTipoDia){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM c_tipo_dia WHERE id_tipo_dia = :idTipoDia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idTipoDia", idTipoDia);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
}
