package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;

@Repository
public class PeriodoRepositoryImpl implements PeriodoRepository{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<PeriodoDto> obtenerListaPeriodos() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ");
        qry.append("from r_periodo ");
        
        List<Map<String, Object>> periodos = jdbcTemplate.queryForList(qry.toString());
        List<PeriodoDto> listaPeriodo = new ArrayList<>();
        
        for (Map<String, Object> periodo : periodos) {
    		PeriodoDto periodoDto = new PeriodoDto();
    		periodoDto.setIdPeriodo((Integer)periodo.get("id_periodo"));
    		periodoDto.setFechaInicio((Date)periodo.get("fecha_inicio"));
    		periodoDto.setFechaFin((Date)periodo.get("fecha_fin"));
    		periodoDto.setDescripcion((String) periodo.get("descripcion"));
    		periodoDto.setActivo((Boolean)periodo.get("activo"));
    		listaPeriodo.add(periodoDto);
    	}
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("listaPeriodo: "+gson.toJson(listaPeriodo));
     return listaPeriodo;	
	}

	@Override
	public PeriodoDto buscaPeriodo(Integer idPeriodo) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ");
        qry.append("from r_periodo ");
        qry.append("where id_periodo = :idPeriodo");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idPeriodo", idPeriodo);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PeriodoDto>(PeriodoDto.class));
	}

	@Override
	public void modificaPeriodo(PeriodoDto periodoDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("update r_periodo set fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, descripcion = :descripcion, activo = :activo ");
		qry.append("where id_periodo = :idPeriodo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPeriodo", periodoDto.getIdPeriodo());
		parametros.addValue("fechaInicio", periodoDto.getFechaInicio());
		parametros.addValue("fechaFin", periodoDto.getFechaFin());
		parametros.addValue("descripcion", periodoDto.getDescripcion());
		parametros.addValue("activo", periodoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaPeriodo(PeriodoDto periodoDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("insert into r_periodo (fecha_inicio, fecha_fin, descripcion, activo) ");
		qry.append("values (:fechaInicio, :fechaFin, :descripcion, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("fechaInicio", periodoDto.getFechaInicio());
		parametros.addValue("fechaFin", periodoDto.getFechaFin());
		parametros.addValue("descripcion", periodoDto.getDescripcion());
		parametros.addValue("activo", periodoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaPeriodo(Integer idPeriodo) {
		StringBuilder qry = new StringBuilder();
		qry.append("delete from r_periodo where idPeriodo = :idPeriodo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPeriodo", idPeriodo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("select periodo.id_periodo, periodo.descripcion, periodo.fecha_inicio, periodo.fecha_fin, periodo.activo ");
        qry.append("from r_periodo periodo, m_vacacion_periodo vacacion, m_usuario usuario ");
        qry.append("where vacacion.activo=true and vacacion.id_usuario=usuario.id_usuario and vacacion.dias>0 and periodo.activo=true and usuario.cve_m_usuario= :claveUsuario order by vacacion.fecha_inicio asc limit 1");
        System.out.println("Consulta "+qry.toString());
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        try{
        PeriodoDto periodo= nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PeriodoDto>(PeriodoDto.class));
        System.out.println("periodo recuperado "+periodo.getIdPeriodo());
        return periodo;
        }catch(Exception e){
        	e.printStackTrace();
        	return new PeriodoDto();
        }
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public int generaPeriodoVacacional(String inicio, String fin, String descripcion, boolean activo) {
		int periodo = 0;
		StringBuilder qry = new StringBuilder();
		qry.append("insert into r_periodo (fecha_inicio, fecha_fin, descripcion, activo) ");
		qry.append("values (:fechaInicio, :fechaFin, :descripcion, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("fechaInicio", inicio);
		parametros.addValue("fechaFin", fin);
		parametros.addValue("descripcion", descripcion);
		parametros.addValue("activo", activo);

		return periodo = nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<PeriodoDto> topPeriodo() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ");
        qry.append("from r_periodo ");
        qry.append("order by id_periodo desc limit 1");
        
        List<Map<String, Object>> periodos = jdbcTemplate.queryForList(qry.toString());
        List<PeriodoDto> topPeriodo = new ArrayList<>();
        
        for (Map<String, Object> periodo : periodos) {
    		PeriodoDto periodoDto = new PeriodoDto();
    		periodoDto.setIdPeriodo((Integer)periodo.get("id_periodo"));
    		periodoDto.setFechaInicio((Date)periodo.get("fecha_inicio"));
    		periodoDto.setFechaFin((Date)periodo.get("fecha_fin"));
    		periodoDto.setDescripcion((String) periodo.get("descripcion"));
    		periodoDto.setActivo((Boolean)periodo.get("activo"));
    		topPeriodo.add(periodoDto);
    	}
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("listaPeriodo: "+gson.toJson(topPeriodo));
     return topPeriodo;	
	}

	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	@Override
	public void cambioEstatusPeriodo(Integer id, boolean activo) {
		StringBuilder qry = new StringBuilder();
		qry.append("update r_periodo set activo = :activo ");
		qry.append("where id_periodo = :idPeriodo");
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("activo", activo);
		parametros.addValue("idPeriodo", id);
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	@Override
	public void modificaEstatustPeridoEmpleados(Integer id, boolean activo) {
		StringBuilder qry = new StringBuilder();
		qry.append("update m_vacacion_periodo set activo = :activo ");
		qry.append("where id_periodo = :idPeriodo");
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idPeriodo", id);
		parametros.addValue("activo", activo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean existePeriodo(String fechaInicio) {
		boolean existe = false;
		StringBuilder qry = new StringBuilder();
		qry.append(" select id_periodo, descripcion, activo ");
		qry.append(" from r_periodo");
		qry.append(" where fecha_inicio =:fechaInicio");
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("fechaInicio", fechaInicio);
		SqlRowSet rs = nameParameterJdbcTemplate.queryForRowSet(qry.toString(), param);
		if(rs.next()) {
			existe = true;
		}
		return existe;
	}
}
