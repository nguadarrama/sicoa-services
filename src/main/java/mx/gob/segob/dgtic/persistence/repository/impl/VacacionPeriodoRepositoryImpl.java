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

import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.VacacionPeriodoRepository;

@Repository
public class VacacionPeriodoRepositoryImpl implements VacacionPeriodoRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_vacacion, id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ");
        qry.append("FROM m_vacacion_periodo ");
        
        List<Map<String, Object>> vacacionesPeriodos = jdbcTemplate.queryForList(qry.toString());
        List<VacacionPeriodoDto> listaVacacionPeriodo = new ArrayList<>();
        
        for (Map<String, Object> vacacionPeriodo : vacacionesPeriodos) {
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)vacacionPeriodo.get("id_vacacion"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)vacacionPeriodo.get("id_usuario"));
        	vacacionPeriodoDto.setIdUsuario(usuarioDto);
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)vacacionPeriodo.get("id_periodo"));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	EstatusDto estatusDto= new EstatusDto();
    		estatusDto.setIdEstatus((Integer)vacacionPeriodo.get("id_estatus"));
    		vacacionPeriodoDto.setIdEstatus(estatusDto);
    		vacacionPeriodoDto.setFechaInicio((Date)vacacionPeriodo.get("fecha_inicio"));
    		vacacionPeriodoDto.setDias((Integer)vacacionPeriodo.get("dias"));
    		vacacionPeriodoDto.setActivo((Boolean) vacacionPeriodo.get("activo"));
    		listaVacacionPeriodo.add(vacacionPeriodoDto);
    	}
     return listaVacacionPeriodo;
	}

	@Override
	public VacacionPeriodoDto buscaVacacionPeriodo(Integer idVacacion) {
		System.out.println("idVacacion para la busqueda "+idVacacion);
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_vacacion, id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ");
        qry.append("FROM m_vacacion_periodo ");
        qry.append("WHERE id_vacacion = :idVacacion");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idVacacion", idVacacion);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<VacacionPeriodoDto>(VacacionPeriodoDto.class));
	}

	@Override
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_vacacion_periodo SET id_estatus = :idEstatus, fecha_inicio = :fechaInicio, dias = :dias, activo = :activo ");
		qry.append("WHERE id_vacacion = :idVacacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idEstatus", vacacionPeriodoDto.getIdEstatus().getIdEstatus());
		parametros.addValue("idVacacion", vacacionPeriodoDto.getIdVacacion());
		parametros.addValue("fechaInicio", vacacionPeriodoDto.getFechaInicio());
		parametros.addValue("dias", vacacionPeriodoDto.getDias());
		parametros.addValue("activo", vacacionPeriodoDto.getActivo());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_vacacion_periodo (id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ) ");
		qry.append("VALUES (:idUsuario, :idPeriodo, :idEstatus, :fechaInicio, :dias, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", vacacionPeriodoDto.getIdUsuario().getIdUsuario());
		parametros.addValue("idPeriodo", vacacionPeriodoDto.getIdPeriodo().getIdPeriodo());
		parametros.addValue("idEstatus", vacacionPeriodoDto.getIdEstatus().getIdEstatus());
		parametros.addValue("fechaInicio", vacacionPeriodoDto.getFechaInicio());
		parametros.addValue("dias", vacacionPeriodoDto.getDias());
		parametros.addValue("activo", vacacionPeriodoDto.getActivo());
		

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaVacacionPeriodo(Integer idVacacion) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_vacacion_periodo WHERE id_vacacion = :idVacacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idVacacion", idVacacion);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
	
	@Override
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario){
		StringBuilder qry = new StringBuilder();
		qry.append("select vacacion.id_vacacion, vacacion.dias ");
        qry.append("from r_periodo periodo, m_vacacion_periodo vacacion , m_usuario usuario ");
        qry.append("where vacacion.activo=true and vacacion.id_usuario=usuario.id_usuario and vacacion.dias>0 and periodo.activo=true and usuario.cve_m_usuario = :claveUsuario and periodo.id_periodo = :idPeriodo and vacacion.id_periodo=periodo.id_periodo order by vacacion.fecha_inicio asc limit 1 ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        parametros.addValue("idPeriodo", idPeriodo);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<VacacionPeriodoDto>(VacacionPeriodoDto.class));
	}

}
