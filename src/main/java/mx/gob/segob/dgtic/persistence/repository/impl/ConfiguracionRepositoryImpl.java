package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.ConfiguracionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.ConfiguracionRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class ConfiguracionRepositoryImpl extends RecursoBase implements ConfiguracionRepository {
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Override
	public Timestamp obtieneUltimaFechaCargaAsistencia() {
		StringBuilder qry = new StringBuilder();
        qry.append("select fecha from c_configuracion where id_configuracion = :idConfiguracion");
        
        int idConfiguracion = 1; //Última asistencia procesada
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idConfiguracion", idConfiguracion);
        
        ConfiguracionDto configuracion = null;
        
        try {
        	configuracion = nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<ConfiguracionDto>(ConfiguracionDto.class));
        } catch (Exception e) {
        	logger.error("No se logró cargar la configuración <última fecha de carga> " + e.getMessage());
        }
        
        return configuracion.getFecha();
	}

	@Override
	public void actualizaUltimaFechaCargaAsistencia() {

		StringBuilder qry = new StringBuilder();
		qry.append("update c_configuracion set fecha = :ultimaFecha ");
		qry.append("where id_configuracion = :idConfiguracion");
		
		int idConfiguracion = 1; //ultima asistencia procesada
		Timestamp ultimaFechaCargaAsistencia = new Timestamp(System.currentTimeMillis());
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("ultimaFecha", ultimaFechaCargaAsistencia);
		parametros.addValue("idConfiguracion", idConfiguracion);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
        
	}

	

}
