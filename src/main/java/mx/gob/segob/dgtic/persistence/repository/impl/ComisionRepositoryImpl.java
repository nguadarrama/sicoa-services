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
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;

@Repository
public class ComisionRepositoryImpl implements ComisionRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<ComisionDto> obtenerListaComisiones() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_comision, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, comision");
        qry.append("FROM m_comision ");
        
        List<Map<String, Object>> comisiones = jdbcTemplate.queryForList(qry.toString());
        List<ComisionDto> listacomision = new ArrayList<>();
        
        for (Map<String, Object> comision : comisiones) {
        	ComisionDto comisionDto = new ComisionDto();
        	comisionDto.setIdComision((Integer)comision.get("id_comision"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)comision.get("id_usuario"));
        	comisionDto.setIdUsuario(usuarioDto);
        	comisionDto.setIdResponsable((Integer)comision.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)comision.get("id_archivo"));
        	comisionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)comision.get("id_estatus"));
        	comisionDto.setIdEstatus(estatusDto);
        	comisionDto.setFechaInicio((Date)comision.get("fecha_inicio"));
        	comisionDto.setFechaFin((Date)comision.get("fecha_fin"));
        	comisionDto.setDias((Integer)comision.get("dias"));
        	comisionDto.setComision((String)comision.get("comision"));
    		listacomision.add(comisionDto);
    	}
		return listacomision;
	}

	@Override
	public ComisionDto buscaComision(Integer idComision) {
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_comision, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, comision");
        qry.append("FROM m_comision ");
        qry.append("WHERE id_comision = :idComision");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idComision", idComision);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<ComisionDto>(ComisionDto.class));
		
	}

	@Override
	public void modificaComision(ComisionDto comisionDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_comision SET fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, dias = :dias, comision = :comision");
		qry.append("WHERE id_comision = :idComision");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idComision", comisionDto.getIdComision());
		parametros.addValue("fechaInicio", comisionDto.getFechaInicio());
		parametros.addValue("fechaFin", comisionDto.getFechaFin());
		parametros.addValue("dias", comisionDto.getDias());
		parametros.addValue("comision", comisionDto.getComision());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaComision(ComisionDto comisionDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_comision (id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio,fecha_fin, dias, comision ) ");
		qry.append("VALUES (:idUsuario, :idResponsable, :idArchivo, :idEstatus, :fechaInicio, :fechaFin, :dias, :comision ) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", comisionDto.getIdUsuario().getIdUsuario());
		parametros.addValue("idResponsable", comisionDto.getIdResponsable());
		parametros.addValue("idArchivo", comisionDto.getIdArchivo().getIdArchivo());
		parametros.addValue("idEstatus", comisionDto.getIdEstatus().getIdEstatus());
		parametros.addValue("fechaInicio", comisionDto.getFechaInicio());
		parametros.addValue("fechaFin", comisionDto.getFechaFin());
		parametros.addValue("dias", comisionDto.getDias());
		parametros.addValue("comision", comisionDto.getComision());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaComision(Integer idComision) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_comision  WHERE id_comision = :idComision");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idComision", idComision);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

}
