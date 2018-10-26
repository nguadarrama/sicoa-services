package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
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
import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.NivelOrganizacionalRepository;

@Repository
public class NivelOrganizacionalRepositoryImpl implements NivelOrganizacionalRepository{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<NivelOrganizacionalDto> obtenerListaNiveles() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_nivel, nivel, id_horario, horario ");
        qry.append("from c_nivel_organizacional ");        
        List<Map<String, Object>> nivelesOrganizacionales = jdbcTemplate.queryForList(qry.toString());
        List<NivelOrganizacionalDto> listaNiveles = new ArrayList<>();
        for (Map<String, Object> nv : nivelesOrganizacionales) {
        	NivelOrganizacionalDto nivel = new NivelOrganizacionalDto();
        	nivel.setIdNivel((Integer) nv.get("id_nivel"));
    		nivel.setNivel((String) nv.get("nivel"));
    		nivel.setIdHorario((Integer) nv.get("id_horario"));
    		nivel.setHorario((String) nv.get("horario"));
    		listaNiveles.add(nivel);
    	}
     return listaNiveles;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public NivelOrganizacionalDto buscaNivel(Integer idNivel) {
		System.out.println("NivelOrganizacionalrepoImpl--method--NivelOrganizacionalDto idNivel: "+idNivel);
		StringBuilder qry = new StringBuilder();
		NivelOrganizacionalDto nivelDto = new NivelOrganizacionalDto();
		qry.append("select id_nivel, nivel, id_horario, horario ");
        qry.append("from c_nivel_organizacional ");
        qry.append("where id_nivel = :idNivel");
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idNivel", idNivel);
        
        try {
			nivelDto = nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<NivelOrganizacionalDto>(NivelOrganizacionalDto.class));
			if(nivelDto != null) {
				nivelDto.setMensaje("Se muestra correctamente la información solicitada. ");
			} else {
				nivelDto.setMensaje("Error al obtener la consulta. ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			nivelDto.setMensaje("Error al obtener la consulta. ");
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("PeriodoRepoImpl-- method--buscaNivel: "+gson.toJson(nivelDto));
		return nivelDto;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public NivelOrganizacionalDto modificaNivel(NivelOrganizacionalDto nivelDto) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("NivelOrgRepoImpl--method--modificaNivel: "+gson.toJson(nivelDto));
		StringBuilder qry = new StringBuilder();
		qry.append("update c_nivel_organizacional ");
		qry.append(" set id_horario = :idHorario, horario = :horario ");
		qry.append(" where id_nivel = :idNivel");
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idNivel", nivelDto.getIdNivel());
		map.addValue("idHorario", nivelDto.getIdHorario());
		map.addValue("horario", nivelDto.getHorario());
		nameParameterJdbcTemplate.update(qry.toString(), map);
		try {
			Integer exitoso = nameParameterJdbcTemplate.update(qry.toString(), map);
			if(exitoso == 1) {
				nivelDto.setMensaje("El horario se ha modificado correctamente. ");
			} else {
				nivelDto.setMensaje("Error al modificar el horario. ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			nivelDto.setMensaje("Error al modificar el horario, verifiqure la información. ");
		}
		
		System.out.println("PeriodoRepoImpl-- method--modificaNivel: "+gson.toJson(nivelDto));
		return nivelDto;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public NivelOrganizacionalDto agregaNivel(NivelOrganizacionalDto nivelDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("insert into c_nivel_organizacional (nivel,id_horario,horario) ");
		qry.append(" values ( :nivel, :idHorario,  :horario)");
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("nivel", nivelDto.getNivel());
		map.addValue("idHorario", nivelDto.getIdHorario());
		map.addValue("horario", nivelDto.getHorario());
		try {
			Integer exitoso = nameParameterJdbcTemplate.update(qry.toString(), map);
			if(exitoso == 1) {
				nivelDto.setMensaje("Se ha asignado horario laboral a nivel de empleado correctamente. ");
			} else {
				nivelDto.setMensaje("Error al asignar el horario laboral,debe seleccionar un horario laboral verifique por favor. ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			nivelDto.setMensaje("El horario: "+nivelDto.getHorario()+" ya se encuentra asignado al Nivel Organizacional, verifique por favor. ");
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("PeriodoRepoImpl-- method--agregaNivel: "+gson.toJson(nivelDto));
		return nivelDto;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public NivelOrganizacionalDto eliminaNivel(Integer idNivel) {
		StringBuilder qry = new StringBuilder();
		NivelOrganizacionalDto nivelDto = new NivelOrganizacionalDto();
		qry.append(" delete from c_nivel_organizacional ");
		qry.append(" where id_nivel = :idNivel ");
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idNivel", idNivel);
		try {
			Integer exitoso = nameParameterJdbcTemplate.update(qry.toString(), map);
			if(exitoso == 1) {
				nivelDto.setMensaje("El horario del nivel organizacional se ha eliminado correctamente. ");
			} else {
				nivelDto.setMensaje("Error al elimiar el horario al Nivel Organizacional. ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			nivelDto.setMensaje("Error al elimiar el horario al Nivel Organizacional. ");
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("PeriodoRepoImpl-- method--eliminaPeriodo: "+gson.toJson(nivelDto));
		return nivelDto;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean existeNivel(Integer idHorario) {
		boolean existe = false;
		StringBuilder qry = new StringBuilder();
		qry.append(" select id_nivel, nivel, id_horario, horario ");
		qry.append(" from c_nivel_organizacional ");
		qry.append(" where id_horario =:idHorario ");
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idHorario", idHorario);
		SqlRowSet rs = nameParameterJdbcTemplate.queryForRowSet(qry.toString(), param);
		if(rs.next()) {
			existe = true;
		}
		return existe;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<UsuarioDto> nivelesEmpleado() {
		StringBuilder qry = new StringBuilder();
		qry.append(" select distinct nivel from m_usuario where nivel is not null ");
		qry.append(" order by nivel asc ");
		List<Map<String, Object>> nivelesOrganizacionales = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioDto> listaNiveles = new ArrayList<>();
        for (Map<String, Object> nv : nivelesOrganizacionales) {
        	UsuarioDto user = new UsuarioDto();
        	user.setNivel((String) nv.get("nivel"));
    		listaNiveles.add(user);
    	}
     return listaNiveles;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<UsuarioDto> empleadosNivel(String nivel) {
		StringBuilder qry = new StringBuilder();
		qry.append(" select id_usuario, id_area, cve_c_perfil, id_horario, id_puesto, ");
		qry.append(" cve_m_usuario, nombre, apellido_paterno, apellido_materno, fecha_ingreso, ");
		qry.append(" password, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos,  ");
		qry.append(" bloqueado, fecha_bloqueo, primera_vez, estatus, rfc, nivel, nombre_jefe");
		qry.append(" from m_usuario where nivel = :nivel");
		List<UsuarioDto> usuarios = new ArrayList<>();
		for(UsuarioDto user: usuarios) {
			UsuarioDto u = new UsuarioDto();
			u.setIdUsuario(user.getIdUsuario());
			u.setIdArea(user.getIdArea());
			u.setClavePerfil(user.getClavePerfil());
			u.setIdHorario(user.getIdHorario());
			u.setIdPuesto(user.getIdPuesto());
			u.setClaveUsuario(user.getClaveUsuario());
			u.setNombre(user.getNombre());
			u.setApellidoPaterno(user.getApellidoPaterno());
			u.setApellidoMaterno(user.getApellidoMaterno());
			u.setFechaIngreso(user.getFechaIngreso());
			u.setPassword(user.getPassword());
			u.setActivo(user.getActivo());
			u.setNuevo(user.getNuevo());
			u.setEnSesion(user.getEnSesion());
			u.setUltimoAcceso(user.getUltimoAcceso());
			u.setNumeroIntentos(user.getNumeroIntentos());
			u.setBloqueado(user.getBloqueado());
			u.setFechaBloqueo(user.getFechaBloqueo());
			u.setPrimeraVez(user.getPrimeraVez());
			u.setEstatus(user.getEstatus());
			u.setRfc(user.getRfc());
			u.setNivel(user.getNivel());
			u.setNombreJefe(user.getNombreJefe());
			usuarios.add(u);
		}
		return usuarios;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public Integer actualizaHorarioEmpleado(Integer idHorario, String nivel) {
		Integer exitoso = 0;
		StringBuilder qry = new StringBuilder();
		qry.append(" update m_usuario set id_horario = :idHorario ");
		qry.append(" where nivel = :nivel ");
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idHorario", idHorario);
		map.addValue("nivel", nivel);
		try {
			 exitoso = nameParameterJdbcTemplate.update(qry.toString(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("PeriodoRepoImpl-- method--modificaNivel: "+gson.toJson(exitoso));
		return exitoso;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public Horario buscaHorario(Integer idHorario) {
		System.out.println("idConsultaHorario: "+idHorario);
		StringBuilder qry = new StringBuilder();
		qry.append(" select id_horario, nombre, hora_entrada, hora_salida, activo ");
        qry.append(" from c_horario ");
        qry.append(" where id_horario = :idHorario");
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idHorario", idHorario);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<Horario>(Horario.class));
	}

}