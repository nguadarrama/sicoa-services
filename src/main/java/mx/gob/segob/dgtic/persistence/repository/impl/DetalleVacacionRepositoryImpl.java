package mx.gob.segob.dgtic.persistence.repository.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
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
        qry.append("SELECT detalle.id_detalle, detalle.fecha_registro, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad ");
        qry.append("FROM d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario ");
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString());
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get("id_detalle"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get("id_usuario"));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get("cve_m_usuario"));
        	usuarioDto.setNombre((String)detalleVacacion.get("nombre"));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get("apellido_paterno"));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get("apellido_materno"));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get("nombre_unidad"));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get("id_unidad"));
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
        	estatusDto.setDescripcion((String)detalleVacacion.get("estatus"));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	System.out.println("Vacaciones recuperadas "+detalleVacacion.get("id_detalle"));
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        	System.out.println("fecha actual"+(Date)detalleVacacion.get("fecha_inicio"));
        	String fechaIni=""+detalleVacacion.get("fecha_inicio");
        	String fechaFin=""+detalleVacacion.get("fecha_fin");
        	String fechaRe=""+detalleVacacion.get("fecha_registro");
        	Date fechaInicio=null;
        	Date fechaFinal=null;
        	Date fechaRegistro=null;
        	try {
        		fechaInicio = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFin);
        		fechaRegistro=sdf.parse(fechaRe);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        	detalleVacacionDto.setFechaInicio(fechaInicio);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	System.out.println("fecha actual "+detalleVacacionDto.getFechaInicio());
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
     return listaDetalleVacacion;
	}

	@Override
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("select detalle.id_detalle, detalle.fecha_registro, detalle.id_responsable, detalle.fecha_registro, detalle.id_usuario, detalle.id_vacacion, detalle.id_archivo, detalle.id_estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad, ");
        qry.append("usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.rfc, usuario.id_puesto, usuario.fecha_ingreso, estatus.id_estatus, estatus.estatus,  periodo.descripcion ");
		qry.append("from d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, r_periodo periodo, m_vacacion_periodo vacacion_periodo,c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where usuario.id_usuario=detalle.id_usuario and detalle.id_estatus=estatus.id_estatus and periodo.id_periodo=vacacion_periodo.id_periodo and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and vacacion_periodo.id_vacacion=detalle.id_vacacion and id_detalle = :idDetalle ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idDetalle", idDetalle);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        detalleVacacionDto.setIdDetalle((Integer)informacionConsulta.get("id_detalle"));
        detalleVacacionDto.setIdResponsable((Integer)informacionConsulta.get("id_responsable"));
        UsuarioDto usuarioDto= new UsuarioDto();
        usuarioDto.setIdUsuario((Integer)informacionConsulta.get("id_usuario"));
        usuarioDto.setClaveUsuario((String)informacionConsulta.get("cve_m_usuario"));
        usuarioDto.setNombre((String)informacionConsulta.get("nombre"));
        usuarioDto.setApellidoPaterno((String)informacionConsulta.get("apellido_paterno"));
        usuarioDto.setApellidoMaterno((String)informacionConsulta.get("apellido_materno"));
        usuarioDto.setIdPuesto((String)informacionConsulta.get("id_puesto"));
        usuarioDto.setFechaIngreso((Date)informacionConsulta.get("fecha_ingreso"));
        usuarioDto.setRfc((String)informacionConsulta.get("rfc"));
        usuarioDto.setIdUnidad((Integer)informacionConsulta.get("id_unidad"));
        usuarioDto.setNombreUnidad((String)informacionConsulta.get("nombre_unidad"));
        detalleVacacionDto.setIdUsuario(usuarioDto);
        ArchivoDto archivoDto = new ArchivoDto();
        archivoDto.setIdArchivo((Integer)informacionConsulta.get("id_archivo"));
        detalleVacacionDto.setIdArchivo(archivoDto);
        VacacionPeriodoDto vacacionDto= new VacacionPeriodoDto();
        PeriodoDto periodoDto = new PeriodoDto();
        periodoDto.setDescripcion((String)informacionConsulta.get("descripcion"));
        vacacionDto.setIdPeriodo(periodoDto);
        vacacionDto.setIdVacacion((Integer)informacionConsulta.get("id_vacacion"));
        detalleVacacionDto.setIdVacacion(vacacionDto);
        EstatusDto estatusDto= new EstatusDto();
        estatusDto.setIdEstatus((Integer)informacionConsulta.get("id_estatus"));
        System.out.println("Id Estatussssssssssssssssssssssss "+informacionConsulta.get("id_estatus"));
        estatusDto.setEstatus((String)informacionConsulta.get("estatus"));
        detalleVacacionDto.setIdEstatus(estatusDto);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    	System.out.println("fecha actual"+(Date)detalleVacacion.get("fecha_inicio"));
    	String fechaIni=""+informacionConsulta.get("fecha_inicio");
    	String fechaFin=""+informacionConsulta.get("fecha_fin");
    	String fechaRe=""+informacionConsulta.get("fecha_registro");
    	Date fechaRegistro=null;
    	Date fechaInicio=null;
    	Date fechaFinal=null;
    	try {
    		fechaInicio = sdf.parse(fechaIni);
    		fechaFinal = sdf.parse(fechaFin);
    		fechaRegistro=sdf.parse(fechaRe);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("informacionConsulta.get "+fechaInicio+" "+fechaFin);
    	detalleVacacionDto.setFechaInicio(fechaInicio);
    	detalleVacacionDto.setFechaFin(fechaFinal);
    	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        System.out.println("informacionConsulta.get "+informacionConsulta.get("fecha_fin"));
        detalleVacacionDto.setDias((Integer)informacionConsulta.get("dias"));
        
        return detalleVacacionDto;
        //return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<DetalleVacacionDto>(DetalleVacacionDto.class));
	}

	@Override
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE d_detalle_vacacion SET id_archivo= :idArchivo ");
		qry.append("WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idDetalle", detalleVacacionDto.getIdDetalle());
		parametros.addValue("idArchivo", detalleVacacionDto.getIdArchivo().getIdArchivo());
		try{
			Integer i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				detalleVacacionDto.setMensaje("El registro de vacaciones se actualizó correctamente.");
			}else{
				detalleVacacionDto.setMensaje("Se ha generado un error al actualizar vacaciones, revise la información");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return detalleVacacionDto;
		
		
	}

	@Override
	public DetalleVacacionDto agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		Integer i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaIni=sdf.format(detalleVacacionDto.getFechaInicio());
		String fechaF=sdf.format(detalleVacacionDto.getFechaFin());
		//select id_detalle from d_detalle_vacacion where ((fecha_inicio between '2018-10-25' and'2018-10-26') or (fecha_fin between '2018-10-25' and '2018-10-26')) and id_usuario='936'
		String query="select id_detalle from d_detalle_vacacion where ((fecha_inicio between '"+fechaIni+"' and '"+fechaF+"') "
				+ "or (fecha_fin between '"+fechaIni+"' and '"+fechaF+"' )) and id_usuario='"+detalleVacacionDto.getIdUsuario().getIdUsuario()+"' ";
		System.out.println("query "+query);
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(query);
        System.out.println("Datos de la consulta "+detalleVacaciones.size());
        if(detalleVacaciones.size()==0 || detalleVacaciones==null){
        	
			Date fechaActual = new Date();
			System.out.println("Fecha actual "+fechaActual);
			detalleVacacionDto.setFechaRegistro(fechaActual);
			StringBuilder qry = new StringBuilder();
			qry.append("INSERT INTO d_detalle_vacacion (id_usuario, id_vacacion, id_responsable, id_estatus, fecha_inicio,fecha_fin, dias, fecha_registro ) ");
			qry.append("VALUES (:idUsuario, :idVacacion, :idResponsable, :idEstatus, :fechaInicio, :fechaFin, :dias, :fechaRegistro) ");
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("idUsuario", detalleVacacionDto.getIdUsuario().getIdUsuario());
			parametros.addValue("idVacacion", detalleVacacionDto.getIdVacacion().getIdVacacion());
			parametros.addValue("idResponsable", detalleVacacionDto.getIdResponsable());
			parametros.addValue("idEstatus", detalleVacacionDto.getIdEstatus().getIdEstatus());
			parametros.addValue("fechaInicio", detalleVacacionDto.getFechaInicio());
			parametros.addValue("fechaFin", detalleVacacionDto.getFechaFin());
			parametros.addValue("dias", detalleVacacionDto.getDias());
			parametros.addValue("fechaRegistro", detalleVacacionDto.getFechaRegistro());
			
			try{
				i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			}catch(Exception e){
				e.printStackTrace();
			}
        }
        if(i == 1)
			detalleVacacionDto.setMensaje("El registro de vacaciones se realizó correctamente.");
		else
			detalleVacacionDto.setMensaje("Se ha generado un error al guardar vacaciones, revise la información");
		return detalleVacacionDto;
		
	}

	@Override
	public void eliminaDetalleVacacion(Integer idDetalle) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM d_detalle_vacacion  WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idDetalle", idDetalle);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE d_detalle_vacacion SET id_estatus= :idEstatus ");
		qry.append("WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idEstatus", detalleVacacionDto.getIdEstatus().getIdEstatus());
		parametros.addValue("idDetalle", detalleVacacionDto.getIdDetalle());
		
		try{
			Integer i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				detalleVacacionDto.setMensaje("El cambio en vacciones se realizó correctamente.");
			}else{
				detalleVacacionDto.setMensaje("Se ha generado un error al administrar vacaciones, revise la información");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return detalleVacacionDto;
		
	}

	@Override
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(String claveUsuario, String idPeriodo, String idEstatus, String pFechaInicio, String pFechaFinal) {
		String query="";
		//StringBuilder qry = new StringBuilder();
		//qry.append("SELECT detalle.id_detalle, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.descripcion, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad ");
		//qry.append("FROM d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion, m_vacacion_periodo vacacionPeriodo, r_periodo periodo ");
		//qry.append("where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and detalle.id_vacacion=vacacionPeriodo.id_vacacion and vacacionPeriodo.id_periodo=periodo.id_periodo and usuario.cve_m_usuario= = ? ");
		//qry.append("and detalle.fecha_inicio >= ? ");
		//qry.append("and detalle.fecha_inicio <= ? ");
//		if(idPeriodo!=null){
//		qry.append("and periodo.id_periodo = ? ");
//		List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString(), claveUsuario, pFechaInicio, pFechaFinal, idPeriodo, idEstatus);
//		}
//		if(idEstatus!=null){
//		qry.append("and estatus.id_estatus = ? ");
//		}
		query+="SELECT detalle.id_detalle, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.fecha_registro ,detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad, periodo.descripcion descripcion_periodo, periodo.id_periodo ";
        query+="FROM d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion, m_vacacion_periodo vacacionPeriodo, r_periodo periodo ";
        query+="where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and detalle.id_vacacion=vacacionPeriodo.id_vacacion and vacacionPeriodo.id_periodo=periodo.id_periodo and usuario.cve_m_usuario='"+claveUsuario+"' ";
//        if(claveUsuario!=null && !claveUsuario.trim().isEmpty()){
//        	query+="and usuario.cve_m_usuario like '%"+claveUsuario+"%' ";
//        }
        if(idPeriodo!=null && !idPeriodo.trim().isEmpty()){
        	query+="and periodo.id_periodo = +'"+idPeriodo+"' ";
//        	query+="and periodo.id_periodo='"+idPeriodo+"' ";
        }
        if((pFechaInicio!=null && !pFechaInicio.trim().isEmpty())&& (pFechaFinal!=null && !pFechaFinal.trim().isEmpty())){
        	query+="and detalle.fecha_inicio between '"+pFechaInicio+"' and '"+pFechaFinal+"' ";
        }else if(pFechaInicio!=null && !pFechaInicio.trim().isEmpty()){
        	query+="and detalle.fecha_inicio='"+pFechaInicio+"'";
        }else if(pFechaFinal!=null && !pFechaFinal.trim().isEmpty()){
        	query+="and detalle.fecha_fin='"+pFechaInicio+"'";
        }
//        if(idEstatus!=null && idEstatus!=""){
        if(idEstatus!=null && !idEstatus.trim().isEmpty()){
        	query+="and detalle.id_estatus='"+idEstatus+"' ";
        }
  //      	query+="and between ";
        
//        if(pFechaFinal!=null){
//        	query+="and ";
//        }
        System.out.println("query "+query+" datos de consulta ");
		System.out.println("Datos para la consulta claveUsuario "+claveUsuario+" fechaInicio "+pFechaInicio+" fechaFinal "+pFechaFinal+" idEstatus "+idEstatus);
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(query);
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get("id_detalle"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get("id_usuario"));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get("cve_m_usuario"));
        	usuarioDto.setNombre((String)detalleVacacion.get("nombre"));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get("apellido_paterno"));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get("apellido_materno"));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get("nombre_unidad"));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get("id_unidad"));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get("id_vacacion"));
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)detalleVacacion.get("id_periodo"));
        	periodoDto.setDescripcion((String)detalleVacacion.get("descripcion_periodo"));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)detalleVacacion.get("id_archivo"));
        	detalleVacacionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get("id_estatus"));
        	estatusDto.setEstatus((String)detalleVacacion.get("estatus"));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	System.out.println("Vacaciones recuperadas "+detalleVacacion.get("id_detalle"));
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        	System.out.println("fecha actual"+(Date)detalleVacacion.get("fecha_inicio"));
        	String fechaIni=""+detalleVacacion.get("fecha_inicio");
        	String fechaFin=""+detalleVacacion.get("fecha_fin");
        	String fechaRe=""+detalleVacacion.get("fecha_registro");
        	Date fechaInicio=null;
        	Date fechaFinal=null;
        	Date fechaRegistro=null;
        	try {
        		fechaInicio = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFin);
        		fechaRegistro=sdf.parse(fechaRe);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	detalleVacacionDto.setFechaInicio(fechaInicio);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        	//System.out.println("fecha actual "+detalleVacacionDto.getFechaInicio());
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
     return listaDetalleVacacion;
	}

	@Override
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno, String idUnidad, String idEstatus) {
		String query="";
//		query+="SELECT detalle.id_detalle, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.descripcion, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad ";
//        query+="FROM d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ";
//        query+="where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and usuario.cve_m_usuario='"+claveUsuario+"';";
		query+="SELECT detalle.id_detalle, detalle.fecha_registro, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad, vacacionPeriodo.dias dias_disponibles, periodo.descripcion descripcion_periodo, periodo.id_periodo ";
        query+="FROM d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion, m_vacacion_periodo vacacionPeriodo, r_periodo periodo ";
        query+="where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and detalle.id_vacacion=vacacionPeriodo.id_vacacion and vacacionPeriodo.id_periodo=periodo.id_periodo ";
        if(claveUsuario!=null && !claveUsuario.trim().isEmpty()){
        	query+="and usuario.cve_m_usuario like '%"+claveUsuario+"%' ";
        }
        if(nombre!=null && !nombre.trim().isEmpty()){
        	query+="and usuario.nombre like '%"+nombre+"%' ";
        }
        if(apellidoPaterno!=null && !apellidoPaterno.trim().isEmpty()){
        	query+="and usuario.apellido_paterno like '%"+apellidoPaterno+"%' ";
        }
        if(apellidoMaterno!=null && !apellidoMaterno.trim().isEmpty()){
        	query+="and usuario.apellido_materno like '%"+apellidoMaterno+"%' ";
        }
        if(idUnidad!=null && !idUnidad.trim().isEmpty()){
        	query+="and unidad.id_unidad='"+idUnidad+"' ";
        }
       
        if(idEstatus!=null && !idEstatus.trim().isEmpty()){
        	query+="and estatus.id_estatus='"+idEstatus+"' ";
        }
       System.out.println("Query "+query);
       System.out.println("Datos para la consulta claveUsuario "+claveUsuario+" nombre "+nombre+" apellidoPaterno "+apellidoPaterno+" apellidoMaterno "+apellidoMaterno+" idEstatus "+idEstatus);
		List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(query);
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get("id_detalle"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get("id_usuario"));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get("cve_m_usuario"));
        	usuarioDto.setNombre((String)detalleVacacion.get("nombre"));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get("apellido_paterno"));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get("apellido_materno"));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get("nombre_unidad"));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get("id_unidad"));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get("id_vacacion"));
        	vacacionPeriodoDto.setDias((Integer) detalleVacacion.get("dias_disponibles"));
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)detalleVacacion.get("id_periodo"));
        	periodoDto.setDescripcion((String)detalleVacacion.get("descripcion_periodo"));
        	System.out.println("detalleVacacion.get(descripcion_periodo) "+detalleVacacion.get("descripcion_periodo"));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)detalleVacacion.get("id_archivo"));
        	detalleVacacionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get("id_estatus"));
        	estatusDto.setEstatus((String)detalleVacacion.get("estatus"));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	
        	
        	System.out.println("Vacaciones recuperadas "+detalleVacacion.get("id_detalle"));
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        	System.out.println("fecha actual"+(Date)detalleVacacion.get("fecha_inicio"));
        	String fechaIni=""+detalleVacacion.get("fecha_inicio");
        	String fechaFin=""+detalleVacacion.get("fecha_fin");
        	String fechaRe=""+detalleVacacion.get("fecha_registro");
        	Date fechaInicio=null;
        	Date fechaFinal=null;
        	Date fechaRegistro=null;
        	try {
        		fechaInicio = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFin);
        		//fechaRegistro=sdf.parse(fechaRe);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	detalleVacacionDto.setFechaInicio(fechaInicio);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        	System.out.println("fecha actual "+detalleVacacionDto.getFechaInicio());
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
        
     return listaDetalleVacacion;
     
	}
	
	
	
}
