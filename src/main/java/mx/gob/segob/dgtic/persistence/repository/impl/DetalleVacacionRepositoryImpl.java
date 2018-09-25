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

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.DetalleVacacionRepository;

@Repository
public class DetalleVacacionRepositoryImpl implements DetalleVacacionRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Override
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_detalle, id_usuario, id_vacacion, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias");
        qry.append("FROM d_detalle_vacacion ");
        
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString());
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get("id_detalle"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get("id_usuario"));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get("id_vacacion"));
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)detalleVacacion.get("id_archivo"));
        	detalleVacacionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get("id_estatus"));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	detalleVacacionDto.setFechaInicio((Date)detalleVacacion.get("fecha_inicio"));
        	detalleVacacionDto.setFechaFin((Date)detalleVacacion.get("fecha_fin"));
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
     return listaDetalleVacacion;
	}

	@Override
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle) {
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_detalle, id_usuario, id_vacacion, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias ");
        qry.append("FROM d_detalle_vacacion ");
        qry.append("WHERE id_detalle = :idDetalle");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idDetalle", idDetalle);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<DetalleVacacionDto>(DetalleVacacionDto.class));
	}

	@Override
	public void modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE D_DETALLE_VACACION SET fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, dias = :dias ");
		qry.append("WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idDetalle", detalleVacacionDto.getIdDetalle());
		parametros.addValue("fechaInicio", detalleVacacionDto.getFechaInicio());
		parametros.addValue("fechaFin", detalleVacacionDto.getFechaFin());
		parametros.addValue("dias", detalleVacacionDto.getDias());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO D_DETALLE_VACACION (id_usuario, id_vacacion, id_responsable, id_archivo, id_estatus, fecha_inicio,fecha_fin, dias ) ");
		qry.append("VALUES (:idUsuario, :idVacacion, :idResponsable, :idArchivo, :idEstatus, :fechaInicio, :fechaFin, :dias) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", detalleVacacionDto.getIdUsuario().getIdUsuario());
		parametros.addValue("idVacacion", detalleVacacionDto.getIdVacacion().getIdVacacion());
		parametros.addValue("idResponsable", detalleVacacionDto.getIdResponsable());
		parametros.addValue("idArchivo", detalleVacacionDto.getIdArchivo().getIdArchivo());
		parametros.addValue("idEstatus", detalleVacacionDto.getIdEstatus().getIdEstatus());
		parametros.addValue("fechaInicio", detalleVacacionDto.getFechaInicio());
		parametros.addValue("fechaFin", detalleVacacionDto.getFechaFin());
		parametros.addValue("dias", detalleVacacionDto.getDias());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaDetalleVacacion(Integer idDetalle) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM D_DETALLE_VACACION  WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idDetalle", idDetalle);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
	
	
}
