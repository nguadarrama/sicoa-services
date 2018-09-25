package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;

@Repository
public class PeriodoRepositoryImpl implements PeriodoRepository{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Override
	public List<PeriodoDto> obtenerListaPeriodos() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT cve_c_perfil, descripcion, estatus ");
        qry.append("FROM c_perfil ");
        
        List<Map<String, Object>> periodos = jdbcTemplate.queryForList(qry.toString());
        List<PeriodoDto> listaPeriodo = new ArrayList<>();
        
        for (Map<String, Object> periodo : periodos) {
    		PeriodoDto periodoDto = new PeriodoDto();
    		periodoDto.setIdPeriodo((Integer)periodo.get("id_periodo"));
    		periodoDto.setFechaInicio((Date)periodo.get("fecha_inicio"));
    		periodoDto.setFechaFin((Date)periodo.get("fecha_fin"));
    		periodoDto.setActivo((Boolean)periodo.get("activo"));
    		listaPeriodo.add(periodoDto);
    	}
     return listaPeriodo;	
	}

	@Override
	public PeriodoDto buscaPeriodo(Integer idPeriodo) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_periodo, fecha_inicio, fecha_fin, activo ");
        qry.append("FROM r_periodo ");
        qry.append("WHERE id_perfil = :idPerfil");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idPeriodo", idPeriodo);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PeriodoDto>(PeriodoDto.class));
	}

	@Override
	public void modificaPeriodo(PeriodoDto periodoDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE R_PERIODO SET fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, activo = :activo ");
		qry.append("WHERE id_periodo = :idPeriodo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPeriodo", periodoDto.getIdPeriodo());
		parametros.addValue("fechaInicio", periodoDto.getFechaInicio());
		parametros.addValue("fechaFin", periodoDto.getFechaFin());
		parametros.addValue("activo", periodoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaPeriodo(PeriodoDto periodoDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO R_PERIODO (fecha_inicio, fecha_fin, activo) ");
		qry.append("VALUES (:fechaInicio, :fechaFin, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("fechaInicio", periodoDto.getFechaInicio());
		parametros.addValue("fechaFin", periodoDto.getFechaFin());
		parametros.addValue("activo", periodoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaPeriodo(Integer idPeriodo) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM R_PERIODO WHERE idPeriodo = :idPeriodo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPeriodo", idPeriodo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

}
