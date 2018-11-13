package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.DashBoardDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DashVacDto;
import mx.gob.segob.dgtic.persistence.repository.DashBoardRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class DashBoardRepositoryImpl extends RecursoBase implements DashBoardRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public DashBoardDto dashBoard(Integer idUsuario) {
		DashBoardDto dash = new DashBoardDto();
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT ")
			.append("(SELECT count(*) asistencia FROM m_asistencia a inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia WHERE id_usuario = ")
			.append(idUsuario)
			.append(" and t.incidencia = false and year(a.entrada) = year(current_date)  and month(a.entrada) = month(current_date)  ) as asistencia ,  ")
			.append("(SELECT count(*) incidencias FROM m_asistencia a inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ")
			.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia WHERE id_usuario = ")
			.append(idUsuario)
			.append(" and t.incidencia = true ")
			.append("and i.descuento = null and year(a.entrada) = year(current_date) and month(a.entrada) = month(current_date) ) as incidencias, ")  
			.append("(SELECT count(*) justificaciones FROM m_asistencia a inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ")
			.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia WHERE id_usuario = ")
			.append(idUsuario)
			.append(" and t.incidencia = true ")
			.append("and i.descuento = false and year(a.entrada) = year(current_date) and month(a.entrada) = month(current_date)) as justificaciones, ")
			.append("(SELECT count(*) descuentos FROM m_asistencia a inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia left join m_incidencia i on a.id_asistencia = i.id_asistencia ") 
			.append("WHERE id_usuario = ")
			.append(idUsuario)
			.append(" and t.incidencia = true and i.descuento = true and year(a.entrada) = year(current_date)  ")
			.append("and month(a.entrada) = month(current_date)) as descuentos,  ")
			.append("(SELECT count(*) incapacidades FROM m_licencia_medica l where id_usuario = ")
			.append(idUsuario)
			.append(" and year(l.fecha_inicio) = year(current_date) ) as incapacidades, ") 
			.append("(SELECT count(*) diasIncapacidad FROM m_licencia_medica l where id_usuario = ")
			.append(idUsuario)
			.append(" and year(l.fecha_inicio) = year(current_date)  ")
			.append("and month(l.fecha_inicio) = month(current_date)) as diasIncapacidad,  ")
			.append("(SELECT count(*) comisionesT FROM m_comision c where id_usuario = ")
			.append(idUsuario)
			.append(" and year(c.fecha_inicio) = year(current_date) ) as comisionesT, ")
			.append("(SELECT count(*) comisiones FROM m_comision c where id_usuario = ")
			.append(idUsuario)
			.append(" and year(c.fecha_inicio) = year(current_date)  ")
			.append("and month(c.fecha_inicio) = month(current_date)) as comisiones ");

        
        List<Map<String, Object>> dashQ = jdbcTemplate.queryForList(qry.toString());
        
        for (Map<String, Object> aux : dashQ) {
        	logger.info(": {}",aux.get("asistencia"));
        	dash.setAsistencia((Long)aux.get("asistencia"));
        	dash.setIncidencias((Long)aux.get("incidencias"));
        	dash.setJustificaciones((Long)aux.get("justificaciones"));
        	dash.setDescuentos((Long)aux.get("descuentos"));
        	dash.setLicencias((Long)aux.get("incapacidades"));
        	dash.setDiasIncapacidad((Long)aux.get("diasIncapacidad"));
        	dash.setComisionesT((Long)aux.get("comisionesT"));
        	dash.setComisiones((Long)aux.get("comisiones"));
        	
    	}

        StringBuilder qry1 = new StringBuilder();
        qry1.append("SELECT v.id_periodo, v.dias, p.descripcion ")
        	.append("FROM m_vacacion_periodo v left join r_periodo p on v.id_periodo = p.id_periodo ")
        	.append("where v.dias > 0  and p.activo = true and v.id_usuario = ")
        	.append(idUsuario)
        	.append(" order by id_vacacion desc ");
        List<Map<String, Object>> vacaciones = jdbcTemplate.queryForList(qry1.toString());
        List<DashVacDto> listaVacaciones = new ArrayList<>();
        
        for (Map<String, Object> aux : vacaciones) {
        	DashVacDto vac = new DashVacDto();
    		vac.setId_periodo((Integer)aux.get("id_periodo"));
    		vac.setDias((Integer)aux.get("dias"));
    		vac.setDescripcion((String)aux.get("descripcion"));
    		listaVacaciones.add(vac);
    	}
        dash.setVacaciones(listaVacaciones);
        return dash;
	}
	
}
