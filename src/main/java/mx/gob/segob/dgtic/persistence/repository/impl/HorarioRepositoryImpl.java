package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.HorarioRepository;

@Repository
public class HorarioRepositoryImpl implements HorarioRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
    public List<Horario> obtenerListaHorarios() {

        StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_horario, hora_entrada, hora_salida, activo ");
        qry.append("FROM c_horario ");
        
        List<Map<String, Object>> horarios = jdbcTemplate.queryForList(qry.toString());
        List<Horario> listaHorario = new ArrayList<>();
        
        for (Map<String, Object> h : horarios) {
    		Horario horario = new Horario();
    		horario.setIdHorario((int) h.get("id_horario"));
    		horario.setHoraEntrada((Time) h.get("hora_entrada"));
    		horario.setHoraSalida((Time) h.get("hora_salida"));
    		horario.setActivo((boolean) h.get("activo"));
    		
    		listaHorario.add(horario);
    	}

        
        return listaHorario;
    }

	@Override
	public Horario buscaHorario(int idHorario) {

		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_horario, hora_entrada, hora_salida, activo ");
        qry.append("FROM c_horario ");
        qry.append("WHERE id_horario = :idHorario");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idHorario", idHorario);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<Horario>(Horario.class));
	}

	@Override
	public void modificaHorario(Horario horario) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE C_HORARIO SET hora_entrada = :horaEntrada, hora_salida = :horaSalida, activo = :activo ");
		qry.append("WHERE id_horario = :idHorario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idHorario", horario.getIdHorario());
		parametros.addValue("horaEntrada", horario.getHoraEntrada().toString());
		parametros.addValue("horaSalida", horario.getHoraSalida());
		parametros.addValue("activo", horario.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public void agregaHorario(Horario horario) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO C_HORARIO (hora_entrada, hora_salida, activo) ");
		qry.append("VALUES (:horaEntrada, :horaSalida, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("horaEntrada", horario.getHoraEntrada().toString());
		parametros.addValue("horaSalida", horario.getHoraSalida());
		parametros.addValue("activo", horario.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public void eliminaHorario(Integer id) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM C_HORARIO WHERE id_horario = :idHorario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idHorario", id);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
}
