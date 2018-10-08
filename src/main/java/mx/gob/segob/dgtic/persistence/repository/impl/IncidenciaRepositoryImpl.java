package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.IncidenciaRepository;

@Repository
public class IncidenciaRepositoryImpl implements IncidenciaRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<IncidenciaDto> obtenerListaIncidencias() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_incidencia, id_asistencia, id_tipo_dia, id_archivo, id_estatus, id_responsable, descuento, observaciones ");
        qry.append("FROM m_incidencia ");
        
        List<Map<String, Object>> incidencias = jdbcTemplate.queryForList(qry.toString());
        List<IncidenciaDto> listaIncidencia = new ArrayList<>();
        
        for (Map<String, Object> incidencia : incidencias) {
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer)incidencia.get("id_tipo_dia"));
        	
        	IncidenciaDto incidenciaDto = new IncidenciaDto();
        	incidenciaDto.setIdIncidencia((Integer)incidencia.get("id_incidencia"));
        	AsistenciaDto asistenciaDto = new AsistenciaDto();
        	asistenciaDto.setIdAsistencia((Integer)incidencia.get("id_asistencia"));
    		incidenciaDto.setIdAsistencia(asistenciaDto);
    		incidenciaDto.setTipoDia(tipoDia);
    		ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)incidencia.get("id_archivo"));
    		incidenciaDto.setIdArchivo(archivoDto);
    		EstatusDto estatusDto= new EstatusDto();
    		estatusDto.setIdEstatus((Integer)incidencia.get("id_estatus"));
    		incidenciaDto.setEstatus(estatusDto);
    		incidenciaDto.setIdResponsable((Integer)incidencia.get("id_responsable"));
    		incidenciaDto.setDescuento((Boolean)incidencia.get("descuento"));
    		incidenciaDto.setObservaciones((String)incidencia.get("observaciones"));
    		listaIncidencia.add(incidenciaDto);
    	}
     return listaIncidencia;	
	}

	@Override
	public IncidenciaDto buscaIncidencia(Integer idIncidencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_incidencia, id_asistencia, id_tipo_incidencia, id_archivo, id_estatus, id_responsable, descuento, observaciones ");
        qry.append("FROM m_incidencia ");
        qry.append("WHERE id_incidencia = :idIncidencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idIncidencia", idIncidencia);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<IncidenciaDto>(IncidenciaDto.class));
	}

	@Override
	public void modificaIncidencia(IncidenciaDto incidenciaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE M_ARCHIVO SET id_tipo_incidencia= :idTipoIncidencia, id_estatus = :idEstatus, descuento = :descuento, observaciones = :observaciones ");
		qry.append("WHERE id_incidencia = :idIncidencia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idIncidencia", incidenciaDto.getIdIncidencia());
		parametros.addValue("idTipoIncidencia", incidenciaDto.getTipoDia());
		parametros.addValue("idEstatus", incidenciaDto.getEstatus().getIdEstatus());
		parametros.addValue("descuento", incidenciaDto.getDescuento());
		parametros.addValue("observaciones", incidenciaDto.getObservaciones());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaIncidencia(IncidenciaDto incidenciaDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO M_INCIDENCIA (id_asistencia, id_tipo_incidencia, id_archivo, id_estatus, id_responsable, descuento, observaciones ) ");
		qry.append("VALUES (:idAsistencia, :idTipoIncidencia, :idArchivo, :idEstatus, :idResponsable, :descuento, :observaciones) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidenciaDto.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idTipoIncidencia", incidenciaDto.getTipoDia());
		parametros.addValue("idArchivo", incidenciaDto.getIdArchivo().getIdArchivo());
		parametros.addValue("idEstatus", incidenciaDto.getEstatus().getIdEstatus());
		parametros.addValue("idReponsable", incidenciaDto.getIdResponsable());
		parametros.addValue("descuento", incidenciaDto.getDescuento());
		parametros.addValue("observaciones", incidenciaDto.getObservaciones());
		

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaIncidencia(Integer idIncidencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM M_INCIDENCIA WHERE id_incidencia = :idIncidencia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idIncidencia", idIncidencia);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

}
