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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.VacacionPeriodoRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;

@Repository
public class VacacionPeriodoRepositoryImpl extends RepositoryBase implements VacacionPeriodoRepository{

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
		qry.append("SELECT vacacion.id_vacacion, vacacion.id_usuario, vacacion.id_periodo, vacacion.id_estatus, vacacion.fecha_inicio, vacacion.dias, vacacion.activo, periodo.id_periodo, periodo.descripcion ");
        qry.append("FROM m_vacacion_periodo vacacion, r_periodo periodo ");
        qry.append("WHERE vacacion.id_periodo=periodo.id_periodo and id_vacacion = :idVacacion");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idVacacion", idVacacion);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        VacacionPeriodoDto vacacion=new VacacionPeriodoDto();
        PeriodoDto periodo= new PeriodoDto();
        periodo.setIdPeriodo((Integer)informacionConsulta.get("id_periodo"));
        periodo.setDescripcion((String)informacionConsulta.get("descripcion"));
        vacacion.setIdPeriodo(periodo);
        vacacion.setIdVacacion((Integer)informacionConsulta.get("id_vacacion"));
        UsuarioDto usuario= new UsuarioDto();
        usuario.setIdUsuario((Integer)informacionConsulta.get("id_usuario"));
        EstatusDto estatus = new EstatusDto();
        estatus.setIdEstatus((Integer)informacionConsulta.get("id_estatus"));
        vacacion.setIdUsuario(usuario);
        vacacion.setFechaInicio((Date)informacionConsulta.get("fecha_inicio"));
        vacacion.setDias((Integer)informacionConsulta.get("dias"));
        vacacion.setActivo((Boolean)informacionConsulta.get("activo"));
        return vacacion;
       // return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<VacacionPeriodoDto>(VacacionPeriodoDto.class));
	}

	@Override
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_vacacion_periodo SET id_estatus = :idEstatus, fecha_inicio = :fechaInicio, dias = :dias, activo = :activo ");
		qry.append("WHERE id_vacacion = :idVacacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idEstatus", 1);
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
	
//	@Override
//	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario){
//		StringBuilder qry = new StringBuilder();
//		qry.append("select vacacion.id_vacacion, vacacion.dias ");
//        qry.append("from r_periodo periodo, m_vacacion_periodo vacacion , m_usuario usuario ");
//        qry.append("where vacacion.activo=true and vacacion.id_usuario=usuario.id_usuario and vacacion.dias>0 and periodo.activo=true and usuario.cve_m_usuario = :claveUsuario and periodo.id_periodo = :idPeriodo and vacacion.id_periodo=periodo.id_periodo order by vacacion.fecha_inicio asc limit 1 ");
//        
//        MapSqlParameterSource parametros = new MapSqlParameterSource();
//        parametros.addValue("claveUsuario", claveUsuario);
//        parametros.addValue("idPeriodo", idPeriodo);
//
//        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<VacacionPeriodoDto>(VacacionPeriodoDto.class));
//	}
	
	@Override
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario){
		StringBuilder qry = new StringBuilder();
		System.out.println("Dato claveUsuario "+claveUsuario+" idPeriodo "+idPeriodo);
		qry.append("select vacacion.id_vacacion, vacacion.dias, usuario.cve_m_usuario ");
        qry.append("from r_periodo periodo, m_vacacion_periodo vacacion , m_usuario usuario ");
        qry.append("where vacacion.activo=true and vacacion.id_usuario=usuario.id_usuario and vacacion.dias>0 and periodo.activo=true and usuario.cve_m_usuario = :claveUsuario and periodo.id_periodo = :idPeriodo and vacacion.id_periodo=periodo.id_periodo order by vacacion.fecha_inicio asc limit 1 ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        parametros.addValue("idPeriodo", idPeriodo);
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        VacacionPeriodoDto vacacion= new VacacionPeriodoDto();
        UsuarioDto usuario= new UsuarioDto();
        		vacacion.setIdVacacion((Integer)informacionConsulta.get("id_vacacion"));
        		vacacion.setDias((Integer)informacionConsulta.get("dias"));
        		usuario.setClaveUsuario((String)informacionConsulta.get("cve_m_usuario"));
        		vacacion.setIdUsuario(usuario);
        return vacacion;
       // return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<VacacionPeriodoDto>(VacacionPeriodoDto.class));
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public void generarVacacionPeriodotodos(int idUsuario, int idPeriodo, int estatus, String inicio, int dias, boolean activo) {
		System.out.println("valores recibidos vacacionesPeriodoRepoImpl: "
				+ " idUsusario: "+idUsuario+" idPeriodo: "+idPeriodo+" estatus: "+estatus+" fechaInicio: "+inicio+" dias: "+dias+" activo: "+activo);
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_vacacion_periodo (id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ) ");
		qry.append("VALUES (:idUsuario, :idPeriodo, :idEstatus, '");
		qry.append(inicio);
		qry.append("', :dias, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", idUsuario);
		parametros.addValue("idPeriodo", idPeriodo);
		parametros.addValue("idEstatus", estatus);
		parametros.addValue("dias", dias);
		parametros.addValue("activo", activo);
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		System.out.println("parametros method--VacacionPeriodoRepoImpl: "+gson.toJson(parametros));
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}


	@Override
	public List<VacacionPeriodoDto> obtenerUsuariosConVacacionesPorFiltros(String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno, String idUnidad) {
		System.out.println("Valores "+claveUsuario+" "+nombre+ " "+apellidoPaterno+" "+apellidoMaterno+" "+idUnidad);
		String query="";
		 
		query+="select usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, vacacionPeriodo.dias, periodo.descripcion, periodo.id_periodo, vacacionPeriodo.id_vacacion ";
        query+="from m_usuario usuario, m_vacacion_periodo vacacionPeriodo, r_periodo periodo, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ";
        query+="where periodo.id_periodo=vacacionPeriodo.id_periodo and usuario.id_usuario=vacacionPeriodo.id_usuario and vacacionPeriodo.dias>0 and unidad.id_unidad = relacion.id_unidad ";
        if(claveUsuario!=null && !claveUsuario.isEmpty()){
        	query+="and usuario.cve_m_usuario like '%"+claveUsuario+"%' ";
        }
        if(nombre!=null && !nombre.isEmpty()){
        	query+="and usuario.nombre like '%"+nombre+"%' ";
        }
        if(apellidoPaterno!=null && !apellidoPaterno.isEmpty()){
        	query+="and usuario.apellido_paterno like '%"+apellidoPaterno+"%' ";
        }
        if(apellidoMaterno!=null && !apellidoMaterno.isEmpty()){
        	query+="and usuario.apellido_materno like '%"+apellidoMaterno+"%' ";
        }
        if(idUnidad!=null && !idUnidad.isEmpty()){
        	query+="and unidad.id_unidad ='"+idUnidad+"' ";
        }
       System.out.println("Query "+query);
		List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(query);
		List<VacacionPeriodoDto> listaVacacionPeriodo=new  ArrayList<>();
		for (Map<String, Object> detalleVacacion : detalleVacaciones) {
			VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setIdUsuario((Integer)detalleVacacion.get("id_usuario"));
			usuarioDto.setClaveUsuario((String)detalleVacacion.get("cve_m_usuario"));
			usuarioDto.setNombre((String)detalleVacacion.get("nombre"));
			usuarioDto.setApellidoPaterno((String)detalleVacacion.get("apellido_paterno"));
			usuarioDto.setApellidoMaterno((String)detalleVacacion.get("apellido_materno"));
			vacacionPeriodoDto.setIdUsuario(usuarioDto);
			PeriodoDto periodoDto = new PeriodoDto();
			periodoDto.setIdPeriodo((Integer)detalleVacacion.get("id_periodo"));
			periodoDto.setDescripcion((String)detalleVacacion.get("descripcion"));
			vacacionPeriodoDto.setIdPeriodo(periodoDto);
			vacacionPeriodoDto.setDias((Integer)detalleVacacion.get("dias"));
			vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get("id_vacacion"));
			listaVacacionPeriodo.add(vacacionPeriodoDto);
		}
		return listaVacacionPeriodo;
			
	}

}
