package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.CargaAsistenciaRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class CargaAsistenciaRepositoryImpl extends RecursoBase implements CargaAsistenciaRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Autowired
	@Qualifier("jdbcTemplateSqlServer")
	private JdbcTemplate jdbcTemplateSqlServer;

	@Override
	public List<AsistenciaDto> obtieneAsistencia(Timestamp ultimaFechaCarga) {
		
		//se obtiene la asistencia a partir de a la última fecha que se realizó
		StringBuilder qry = new StringBuilder();
        qry.append("select id_enrolamiento, fecha from ds_asistencia_sesnsp.dbo.m_asistencia where fecha >= ? ");
        List<Map<String, Object>> asistencias = jdbcTemplateSqlServer.queryForList(qry.toString(), ultimaFechaCarga);
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        try {
	        for (Map<String, Object> a : asistencias) {
	    		
	    		UsuarioDto usuarioDto = new UsuarioDto();
	    		usuarioDto.setClaveUsuario(a.get("id_enrolamiento").toString());
	    		
	    		AsistenciaDto asistencia = new AsistenciaDto();
	    		asistencia.setUsuarioDto(usuarioDto);
	    		asistencia.setEntrada((Timestamp) a.get("fecha"));
	    		
	    		listaAsistencia.add(asistencia);
	    	}
        } catch (Exception e) {
        	logger.warn("No se recuperaron las asistencias del sistema de asistencias: " + e.getMessage());
        }
        
        logger.info(listaAsistencia.size() + " eventos en ASISTENCIAS");
        
		return listaAsistencia;
	}
	
	@Override
	public void guardaAsistencia(final List<AsistenciaDto> asistencias) {
		int[] inserciones = null;
		StringBuilder qry = new StringBuilder();
		
		qry.append("insert into m_asistencia (id_usuario, id_tipo_dia, id_estatus, entrada, salida) ");
		qry.append("values (?, ?, null, ?, ?) ");
		
		try {
		
			inserciones = jdbcTemplate.batchUpdate(qry.toString(), new BatchPreparedStatementSetter() {
	
				@Override
				public int getBatchSize() {
					return asistencias.size();
				}
	
				@Override
				public void setValues(java.sql.PreparedStatement ps, int i) throws SQLException {
					AsistenciaDto asistencia = asistencias.get(i);
					ps.setString(1, asistencia.getUsuarioDto().getClaveUsuario());
					ps.setInt(2, asistencia.getIdTipoDia().getIdTipoDia());
					ps.setTimestamp(3, asistencia.getEntrada());
					ps.setTimestamp(4, asistencia.getSalida());
				}
				
			});
			
		} catch (Exception e) {
			logger.warn("No se insertaron las asistencias: " + e.getMessage());
		}
			
		logger.info(inserciones.length + " asistencias guardadas");
	}
	
}
