package mx.gob.segob.dgtic.persistence.repository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.persistence.repository.DetalleVacacionRepository;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class DetalleVacacionRepositoryImpl extends RepositoryBase implements DetalleVacacionRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	private static final String AND = "' and '";
	
	@Autowired DiaFestivoRepository diaFestivoRepository;

	@Override
	public List<DetalleVacacionDto> obtenerListaDetalleVacaciones() {
		StringBuilder qry = new StringBuilder();
        qry.append("select distinct(detalle.id_detalle) id_detalle, detalle.fecha_registro, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad ");
        qry.append("from d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario ");
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString());
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get(RepositoryConstants.ID_DETALLE));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get(RepositoryConstants.ID_USUARIO));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get(RepositoryConstants.CLAVE_M_USUARIO));
        	usuarioDto.setNombre((String)detalleVacacion.get(RepositoryConstants.NOMBRE));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_PATERNO));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_MATERNO));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get(RepositoryConstants.NOMBRE_UNIDAD));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get(RepositoryConstants.ID_UNIDAD));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get(RepositoryConstants.ID_VACACION));
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get(RepositoryConstants.ID_RESPONSABLE));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)detalleVacacion.get(RepositoryConstants.ID_ARCHIVO));
        	detalleVacacionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get(RepositoryConstants.ID_ESTATUS));
        	estatusDto.setDescripcion((String)detalleVacacion.get(RepositoryConstants.ESTATUS));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	logger.info("Vacaciones recuperadas--: {} ",detalleVacacion.get(RepositoryConstants.ID_DETALLE));
        	SimpleDateFormat sdf = new SimpleDateFormat(RepositoryConstants.YYYY_MM_DD);
        	String fechaIni=""+detalleVacacion.get(RepositoryConstants.FECHA_INICIO);
        	String fechaFinS=""+detalleVacacion.get(RepositoryConstants.FECHA_FIN);
        	String fechaRe=""+detalleVacacion.get(RepositoryConstants.FECHA_REGISTRO);
        	Date fechaInicioS=null;
        	Date fechaFinal=null;
        	Date fechaRegistro=null;
        	try {
        		fechaInicioS = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFinS);
        		fechaRegistro=sdf.parse(fechaRe);
			} catch (ParseException e) {
				logger.warn("Eror: {} ", e);
			}
        	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        	detalleVacacionDto.setFechaInicio(fechaInicioS);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	logger.info("fecha actual: {} ",detalleVacacionDto.getFechaInicio());
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
     return listaDetalleVacacion;
	}

	@Override
	public DetalleVacacionDto buscaDetalleVacacion(Integer idDetalle) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("select distinct(detalle.id_detalle) id_detalle, detalle.id_responsable, detalle.fecha_registro, detalle.id_usuario, detalle.id_vacacion, detalle.id_archivo, detalle.id_estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad, ");
        qry.append("usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.rfc, usuario.id_puesto, usuario.fecha_ingreso, estatus.id_estatus, estatus.estatus,  periodo.descripcion ");
		qry.append("from d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, r_periodo periodo, m_vacacion_periodo vacacion_periodo,c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where usuario.id_usuario=detalle.id_usuario and detalle.id_estatus=estatus.id_estatus and periodo.id_periodo=vacacion_periodo.id_periodo and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and vacacion_periodo.id_vacacion=detalle.id_vacacion and id_detalle = :idDetalle ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_DETALLE2, idDetalle);
        logger.info("Consulta: {} ",qry);
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        detalleVacacionDto.setIdDetalle((Integer)informacionConsulta.get(RepositoryConstants.ID_DETALLE));
        detalleVacacionDto.setIdResponsable((Integer)informacionConsulta.get(RepositoryConstants.ID_RESPONSABLE));
        UsuarioDto usuarioDto= new UsuarioDto();
        usuarioDto.setIdUsuario((Integer)informacionConsulta.get(RepositoryConstants.ID_USUARIO));
        usuarioDto.setClaveUsuario((String)informacionConsulta.get(RepositoryConstants.CLAVE_M_USUARIO));
        logger.info("claveUsuario..: {} ",informacionConsulta.get(RepositoryConstants.CLAVE_M_USUARIO));
        usuarioDto.setNombre((String)informacionConsulta.get(RepositoryConstants.NOMBRE));
        usuarioDto.setApellidoPaterno((String)informacionConsulta.get(RepositoryConstants.APELLIDO_PATERNO));
        usuarioDto.setApellidoMaterno((String)informacionConsulta.get(RepositoryConstants.APELLIDO_MATERNO));
        usuarioDto.setIdPuesto((String)informacionConsulta.get(RepositoryConstants.ID_PUESTO));
        usuarioDto.setFechaIngreso((Date)informacionConsulta.get(RepositoryConstants.FECHA_INGRESO));
        usuarioDto.setRfc((String)informacionConsulta.get(RepositoryConstants.RFC));
        usuarioDto.setIdUnidad((Integer)informacionConsulta.get(RepositoryConstants.ID_UNIDAD));
        usuarioDto.setNombreUnidad((String)informacionConsulta.get(RepositoryConstants.NOMBRE_UNIDAD));
        detalleVacacionDto.setIdUsuario(usuarioDto);
        ArchivoDto archivoDto = new ArchivoDto();
        archivoDto.setIdArchivo((Integer)informacionConsulta.get(RepositoryConstants.ID_ARCHIVO));
        detalleVacacionDto.setIdArchivo(archivoDto);
        VacacionPeriodoDto vacacionDto= new VacacionPeriodoDto();
        PeriodoDto periodoDto = new PeriodoDto();
        periodoDto.setDescripcion((String)informacionConsulta.get(RepositoryConstants.DESCRIPCION));
        vacacionDto.setIdPeriodo(periodoDto);
        vacacionDto.setIdVacacion((Integer)informacionConsulta.get(RepositoryConstants.ID_VACACION));
        detalleVacacionDto.setIdVacacion(vacacionDto);
        EstatusDto estatusDto= new EstatusDto();
        estatusDto.setIdEstatus((Integer)informacionConsulta.get(RepositoryConstants.ID_ESTATUS));
        logger.info("Id Estatussssssssssssssssssssssss: {} ",informacionConsulta.get(RepositoryConstants.ID_ESTATUS)+" "+informacionConsulta.get(RepositoryConstants.FECHA_INICIO));
        estatusDto.setEstatus((String)informacionConsulta.get(RepositoryConstants.ESTATUS));
        detalleVacacionDto.setIdEstatus(estatusDto);
    	detalleVacacionDto.setFechaInicio((Date)informacionConsulta.get(RepositoryConstants.FECHA_INICIO));
    	detalleVacacionDto.setFechaFin((Date)informacionConsulta.get(RepositoryConstants.FECHA_FIN));
    	detalleVacacionDto.setFechaRegistro((Date)informacionConsulta.get(RepositoryConstants.FECHA_REGISTRO));
        logger.info("informacionConsulta.get: {} ",detalleVacacionDto.getFechaInicio());
        detalleVacacionDto.setDias((Integer)informacionConsulta.get("dias"));
        
        return detalleVacacionDto;
	}

	@Override
	public DetalleVacacionDto modificaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE d_detalle_vacacion SET id_archivo= :idArchivo ");
		qry.append("WHERE id_detalle = :idDetalle");
		logger.warn("Vamos a actualizar el archivo: {} ", detalleVacacionDto.getIdDetalle()+" detalleVacacion: {} "+detalleVacacionDto.getIdArchivo().getIdArchivo() );
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_DETALLE2, detalleVacacionDto.getIdDetalle());
		parametros.addValue(RepositoryConstants.ID_ARCHIVO2, detalleVacacionDto.getIdArchivo().getIdArchivo());
		try{
			Integer i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				detalleVacacionDto.setMensaje("El registro de vacaciones se actualizó correctamente.");
			}else{
				detalleVacacionDto.setMensaje("Se ha generado un error al actualizar vacaciones, revise la información");
			}
			
		}catch(Exception e){
			logger.warn("warsn.- {} ",e);
		}
		return detalleVacacionDto;
		
		
	}

	@Override
	public DetalleVacacionDto agregaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		Integer i = 0;
		diasTotales=0;
		SimpleDateFormat sdf = new SimpleDateFormat(RepositoryConstants.YYYY_MM_DD);
		String fechaIni=sdf.format(detalleVacacionDto.getFechaInicio());
		String fechaF=sdf.format(detalleVacacionDto.getFechaFin());
		StringBuilder qry = new StringBuilder();
		qry.append("select id_detalle from d_detalle_vacacion where (((fecha_inicio between '"+fechaIni+AND+fechaF+"') ");
		qry.append("or (fecha_fin between '"+fechaIni+AND+fechaF+"' )) ");
		qry.append("or('"+fechaIni+"'>fecha_inicio and fecha_inicio<'"+fechaF+"' and fecha_fin>'"+fechaF+"')) and (id_estatus != 3 and id_estatus != 6) ");
		qry.append("and id_usuario='"+detalleVacacionDto.getIdUsuario().getIdUsuario()+"' ");
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString());
        logger.info("Datos de la consulta-- {} ",detalleVacaciones.size());
        if(detalleVacaciones.isEmpty()){
        	
			Date fechaActual = new Date();
			/**System.out.println("Fecha actual "+fechaActual+" dias por descontar "+detalleVacacionDto.getDias());**/
			detalleVacacionDto.setFechaRegistro(fechaActual);
			qry = new StringBuilder();
			qry.append("INSERT INTO d_detalle_vacacion (id_usuario, id_vacacion, id_responsable, id_estatus, fecha_inicio,fecha_fin, dias, fecha_registro ) ");
			qry.append("VALUES (:idUsuario, :idVacacion, :idResponsable, :idEstatus, :fechaInicio, :fechaFin, :dias, :fechaRegistro) ");
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue(RepositoryConstants.ID_USUARIO2, detalleVacacionDto.getIdUsuario().getIdUsuario());
			parametros.addValue(RepositoryConstants.ID_VACACION2, detalleVacacionDto.getIdVacacion().getIdVacacion());
			parametros.addValue(RepositoryConstants.ID_RESPONSABLE2, detalleVacacionDto.getIdResponsable());
			parametros.addValue(RepositoryConstants.ID_ESTATUS2, detalleVacacionDto.getIdEstatus().getIdEstatus());
			parametros.addValue(RepositoryConstants.FECHA_INICIO2, detalleVacacionDto.getFechaInicio());
			parametros.addValue(RepositoryConstants.FECHA_FIN2, detalleVacacionDto.getFechaFin());
			parametros.addValue(RepositoryConstants.DIAS, detalleVacacionDto.getDias());
			parametros.addValue(RepositoryConstants.FECHA_REGISTRO2, detalleVacacionDto.getFechaRegistro());
			
			try{
				i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			}catch(Exception e){
				logger.warn("Wrn : {} ",e);
			}
        }
        if(i == 1) {
			detalleVacacionDto.setMensaje("El registro de vacaciones se realizó correctamente.");
        }
		else
			detalleVacacionDto.setMensaje("Se ha generado un error al guardar vacaciones, revise que los datos sean correctos");
        
        String respuesta=repetirValidaciones(detalleVacacionDto.getIdUsuario().getClaveUsuario(), detalleVacacionDto.getFechaInicio(), detalleVacacionDto.getDias()
				, detalleVacacionDto.getFechaFin());
		/**System.out.println("validacion de los datos en repository "+respuesta+" claveUsuario "+detalleVacacionDto.getIdUsuario().getClaveUsuario()); **/
		if(!respuesta.isEmpty())
			detalleVacacionDto.setMensaje(respuesta);
		return detalleVacacionDto;
		
		
	}

	@Override
	public DetalleVacacionDto eliminaDetalleVacacion(Integer idDetalle) {
		DetalleVacacionDto detalleVacacion = new DetalleVacacionDto();
		Integer i=0;
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM d_detalle_vacacion  WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idDetalle", idDetalle);

		try{
			i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				detalleVacacion.setMensaje("La eliminación de vacaciones se realizó correctamente.");
			}else{
				detalleVacacion.setMensaje("Se ha generado un error al eliminar vacaciones, revise la información");
			}
			
		}catch(Exception e){
			logger.error("error.. {} ",e);
		}
		return detalleVacacion;
	}

	@Override
	public DetalleVacacionDto aceptaORechazaDetalleVacacion(DetalleVacacionDto detalleVacacionDto) {
		StringBuilder qry = new StringBuilder();
		Integer i=0;
		qry.append("UPDATE d_detalle_vacacion SET id_estatus= :idEstatus ");
		qry.append("WHERE id_detalle = :idDetalle");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idEstatus", detalleVacacionDto.getIdEstatus().getIdEstatus());
		parametros.addValue("idDetalle", detalleVacacionDto.getIdDetalle());
		
		try{
			 i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			
			if(i == 1){
				detalleVacacionDto.setMensaje("El cambio en vacaciones se realizó correctamente.");
			}else{
				detalleVacacionDto.setMensaje("Se ha generado un error al administrar vacaciones, revise la información");
			}
			
		}catch(Exception e){
			logger.warn("Wrn . {} ",e);
		}
		detalleVacacionDto.setFechaRegistro(null);
		detalleVacacionDto.setFechaFin(null);
		detalleVacacionDto.setFechaInicio(null);
		return detalleVacacionDto;
		
	}

	@Override
	public List<DetalleVacacionDto> consultaVacacionesPropiasPorFiltros(BusquedaDto busquedaDto) {
		String query="";
		query+="select distinct(detalle.id_detalle) id_detalle, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.fecha_registro ,detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad, periodo.descripcion descripcion_periodo, periodo.id_periodo "
        +"from d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion, m_vacacion_periodo vacacionPeriodo, r_periodo periodo "
        +"where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and detalle.id_vacacion=vacacionPeriodo.id_vacacion and vacacionPeriodo.id_periodo=periodo.id_periodo and usuario.cve_m_usuario='"+busquedaDto.getClaveUsuario()+"' ";

        if(busquedaDto.getIdPeriodo()!=null && !busquedaDto.getIdPeriodo().trim().isEmpty()){
        	query+="and periodo.id_periodo = +'"+busquedaDto.getIdPeriodo()+"' ";
        }
        if((busquedaDto.getFechaInicio()!=null && !busquedaDto.getFechaInicio().trim().isEmpty())&& (busquedaDto.getFechaFin()!=null && !busquedaDto.getFechaFin().trim().isEmpty())){
        	query+="and detalle.fecha_inicio between '"+busquedaDto.getFechaInicio()+AND+busquedaDto.getFechaFin()+"' ";
        }else if(busquedaDto.getFechaInicio()!=null && !busquedaDto.getFechaInicio().trim().isEmpty()){
        	query+="and detalle.fecha_inicio='"+busquedaDto.getFechaInicio()+"'";
        }else if(busquedaDto.getFechaFin()!=null && !busquedaDto.getFechaFin().trim().isEmpty()){
        	query+="and detalle.fecha_fin='"+busquedaDto.getFechaFin()+"'";
        }

        if(busquedaDto.getIdEstatus()!=null && !busquedaDto.getIdEstatus().trim().isEmpty()){
        	query+="and detalle.id_estatus='"+busquedaDto.getIdEstatus()+"' ";
        }
  
       /** System.out.println("query "+query+" datos de consulta ");
		System.out.println("Datos para la consulta claveUsuario "+claveUsuario+" fechaInicio "+pFechaInicio+" fechaFinal "+pFechaFinal+" idEstatus "+idEstatus); **/
        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(query);
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get(RepositoryConstants.ID_DETALLE));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get(RepositoryConstants.ID_USUARIO));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get(RepositoryConstants.CLAVE_M_USUARIO));
        	usuarioDto.setNombre((String)detalleVacacion.get(RepositoryConstants.NOMBRE));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_PATERNO));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_MATERNO));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get(RepositoryConstants.NOMBRE_UNIDAD));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get(RepositoryConstants.ID_UNIDAD));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get(RepositoryConstants.ID_VACACION));
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)detalleVacacion.get(RepositoryConstants.ID_PERIODO));
        	periodoDto.setDescripcion((String)detalleVacacion.get(RepositoryConstants.DESCRIPCION_PERIODO));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get(RepositoryConstants.ID_RESPONSABLE));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)detalleVacacion.get(RepositoryConstants.ID_ARCHIVO));
        	detalleVacacionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get(RepositoryConstants.ID_ESTATUS));
        	estatusDto.setEstatus((String)detalleVacacion.get(RepositoryConstants.ESTATUS));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	logger.info("Vacaciones recuperadas: {} ",detalleVacacion.get(RepositoryConstants.ID_DETALLE));
        	SimpleDateFormat sdf = new SimpleDateFormat(RepositoryConstants.YYYY_MM_DD);
        	String fechaIni=""+detalleVacacion.get(RepositoryConstants.FECHA_INICIO);
        	String fechaFinss=""+detalleVacacion.get(RepositoryConstants.FECHA_FIN);
        	String fechaRe=""+detalleVacacion.get(RepositoryConstants.FECHA_REGISTRO);
        	Date fechaInicioe=null;
        	Date fechaFinal=null;
        	Date fechaRegistro=null;
        	try {
        		fechaInicioe = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFinss);
        		fechaRegistro=sdf.parse(fechaRe);
			} catch (ParseException error) {
				logger.warn("Error: ", error);
			}
        	detalleVacacionDto.setFechaInicio(fechaInicioe);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
     return listaDetalleVacacion;
	}

	@Override
	public List<DetalleVacacionDto> obtenerVacacionesPorFiltros(BusquedaDto busquedaDto) {
		String query="";
		query="select distinct(detalle.id_detalle) id_detalle, detalle.fecha_registro, usuario.id_usuario,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, detalle.id_vacacion, detalle.id_responsable, detalle.id_archivo, detalle.id_estatus, estatus.estatus, detalle.fecha_inicio, detalle.fecha_fin, detalle.dias, unidad.id_unidad, unidad.nombre nombre_unidad, vacacionPeriodo.dias dias_disponibles, periodo.descripcion descripcion_periodo, periodo.id_periodo "
        +"from d_detalle_vacacion detalle, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion, m_vacacion_periodo vacacionPeriodo, r_periodo periodo "
        +"where usuario.id_usuario=detalle.id_usuario and estatus.id_estatus=detalle.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario and relacion.cve_m_usuario=usuario.cve_m_usuario and detalle.id_vacacion=vacacionPeriodo.id_vacacion and vacacionPeriodo.id_periodo=periodo.id_periodo ";
        if(busquedaDto.getClaveUsuario()!=null && !busquedaDto.getClaveUsuario().trim().isEmpty()){
        	query+="and usuario.cve_m_usuario like '%"+busquedaDto.getClaveUsuario()+"%' ";
        }
        if(busquedaDto.getNombre()!=null && !busquedaDto.getNombre().trim().isEmpty()){
        	query+="and usuario.nombre like '%"+busquedaDto.getNombre()+"%' ";
        }
        if(busquedaDto.getApellidoPaterno()!=null && !busquedaDto.getApellidoPaterno().trim().isEmpty()){
        	query+="and usuario.apellido_paterno like '%"+busquedaDto.getApellidoPaterno()+"%' ";
        }
        if(busquedaDto.getApellidoMaterno()!=null && !busquedaDto.getApellidoMaterno().trim().isEmpty()){
        	query+="and usuario.apellido_materno like '%"+busquedaDto.getApellidoMaterno()+"%' ";
        }
        if(busquedaDto.getIdUnidad()!=null && !busquedaDto.getIdUnidad().trim().isEmpty()){
        	query+="and unidad.id_unidad='"+busquedaDto.getIdUnidad()+"' ";
        }
       
        if(busquedaDto.getIdEstatus()!=null && !busquedaDto.getIdEstatus().trim().isEmpty()){
        	query+="and estatus.id_estatus='"+busquedaDto.getIdEstatus()+"' ";
        }
       logger.info("Query: {} ",query);
       logger.info("Datos para la consulta claveUsuario: {} ",claveUsuario);
       logger.info(" nombre- {} ",busquedaDto.getNombre());
       logger.info(" apellidoPaterno--  {} ",busquedaDto.getApellidoPaterno());
       logger.info("apellidoMaterno.. {} ",busquedaDto.getApellidoMaterno());
       logger.info("idEstatus.. {} ",busquedaDto.getIdEstatus());
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
        	logger.info("detalleVacacion.get(descripcion_periodo)-- {} ",detalleVacacion.get("descripcion_periodo"));
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
        	
        	
        	logger.info("Vacaciones recuperadas: {} ",detalleVacacion.get("id_detalle"));
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String fechaIni = "" + detalleVacacion.get("fecha_inicio");
        	String fechaFinO = "" + detalleVacacion.get("fecha_fin");
        
        	Date fechaInicioN = null;
        	Date fechaFinal = null;
        	Date fechaRegistro = null;
        	try {
        		fechaInicioN = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFinO);
			} catch (ParseException e) {
				logger.warn("  Error {} ", e);
			}
        	detalleVacacionDto.setFechaInicio(fechaInicioN);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	detalleVacacionDto.setFechaRegistro(fechaRegistro);
        	logger.info("fecha actual: {} ",detalleVacacionDto.getFechaInicio());
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
        
     return listaDetalleVacacion;
     
	}
	
	String claveUsuario=null; 
	Date fechaInicio = null;
	Integer dias = 0;
	Date fechaFin = null; 
	Boolean bandera = false; 
	Integer diasTotales = 0;
	
	
	private String repetirValidaciones(String claveUsuario, Date fechaInicio, Integer dias, Date fechaFin){
		this.claveUsuario=claveUsuario;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		diasTotales=dias;
		String respuesta = "";
		Integer contador = 0;
		do{
		validaFechasVacaciones(this.claveUsuario, this.fechaInicio, this.fechaFin);
		contador++;
		}
		while(bandera);
			logger.info("Contador: {} ",contador);
		if(diasTotales>10){
			respuesta="Estas solicitando vacaciones un día inmediato posterior o anterior, a otra solicitud de vacaciones "
					+ "que sumados dan más de 10 días, considera que tu solicitud puede ser declinada";
		}
		
		return respuesta;
	}
	
	private String validaFechasVacaciones(String claveUsuario, Date fechaInicio, Date fechaFin){
		List<DiaFestivoDto> listaDiasFestivos=diaFestivoRepository.obtenerDiasFestivosActivos();
		BusquedaDto busquedaDto = new BusquedaDto();
		busquedaDto.setClaveUsuario(claveUsuario);
		busquedaDto.setFechaFin("");
		busquedaDto.setFechaInicio("");
		busquedaDto.setIdPeriodo("");
		busquedaDto.setIdEstatus("");
		List<DetalleVacacionDto> listaDiasVacaciones=consultaVacacionesPropiasPorFiltros(busquedaDto);
		logger.info("comprobando días ");
    
		Calendar c1 = Calendar.getInstance();
	    c1.setTime(fechaInicio);
	    Calendar c2 = Calendar.getInstance();
	    c2.setTime(fechaFin);
	    if(c1.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
	    	c1.add(Calendar.DAY_OF_WEEK,-2);
	    	logger.info("Dia antes: {} ",c1.getTime());
	    }
	    c1.add(Calendar.DAY_OF_WEEK,-1);
	    if(c2.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
	    	c2.add(Calendar.DAY_OF_WEEK,+2);
	    	logger.info("Dia despues: {} ",c2.getTime());
	    }
	    c2.add(Calendar.DAY_OF_WEEK,+1);
	    for(DiaFestivoDto diaFestivos: listaDiasFestivos){
	
		    Calendar diaFestivo = Calendar.getInstance();
			diaFestivo.setTime(diaFestivos.getFecha());
			if(diaFestivo.equals(c1)){
				c1.add(Calendar.DAY_OF_WEEK,-1);
				logger.info("Dia menos festivo: {} ",c1.getTime());
			}else
			{
				logger.info("Dia no festivo: {} ",c1.getTime());
			}
			if(diaFestivo.equals(c2)){
				c2.add(Calendar.DAY_OF_WEEK,+1);
				logger.info("Dia menos festivo: {} ",c2.getTime());
			}else
			{
				logger.info("Dia no festivo: {} ",c1.getTime());
			}
		}
	    Integer con=0;
		for(DetalleVacacionDto vacacion: listaDiasVacaciones){
			 Calendar diaInicio = Calendar.getInstance();
			 Calendar diaFin = Calendar.getInstance();
			 diaInicio.setTime(vacacion.getFechaInicio());
			 diaFin.setTime(vacacion.getFechaFin());
			 
			if(c1.equals(diaFin)){
				this.fechaInicio=diaInicio.getTime();
				this.fechaFin=c2.getTime();
				bandera=true;
				con++;
				logger.info("Dias que se deben sumar fechaFin: {} ",vacacion.getDias());
				logger.info(" bandera: {} ",bandera);
				diasTotales+=vacacion.getDias();
			}
			
			if(c2.equals(diaInicio)){
				this.fechaInicio=diaInicio.getTime();
				bandera=true;
				con++;
				this.fechaFin=diaFin.getTime();
				logger.info("Dias que se deben sumar fechaInicio: {} ",vacacion.getDias());
				logger.info(" bandera: {} ",bandera);
				diasTotales+=vacacion.getDias();
			}
		}
		if(con==0){
			bandera=false;
		}
		logger.info("fechaInicio: {} ",this.fechaInicio); 
		logger.info("fechaFin: {} ",this.fechaFin); 
		logger.info("Dias totales: {} ",diasTotales);
		return"Dias totales "+diasTotales;
	}

	@Override
	public List<DetalleVacacionDto> buscaDetalleVacacionReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		StringBuilder qry = new StringBuilder();
	       
        qry.append("select u.cve_m_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.nivel, "); 
		qry.append("ua.nombre as unidad_administrativa, d.id_estatus, d.fecha_inicio, d.fecha_fin, e.estatus ");
		qry.append("from d_detalle_vacacion d ");
		qry.append("inner join m_usuario u on d.id_usuario = u.id_usuario ");
		qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
		qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
		qry.append("left join m_estatus e on e.id_estatus = d.id_estatus ");
		qry.append("where uua.id_unidad = " + asistenciaBusquedaUtil.getIdUnidadCoordinador());

        if (!asistenciaBusquedaUtil.getCveMusuario().isEmpty()) {
        	qry.append(" and u.cve_m_usuario = " + asistenciaBusquedaUtil.getCveMusuario());
    	}
        
        if (!asistenciaBusquedaUtil.getNombre().isEmpty()) {
        	qry.append(" and u.nombre like '%" + asistenciaBusquedaUtil.getNombre() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getPaterno().isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + asistenciaBusquedaUtil.getPaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getMaterno().isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + asistenciaBusquedaUtil.getMaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getNivel().isEmpty()) {
        	qry.append(" and u.nivel like '%" + asistenciaBusquedaUtil.getNivel() + "%' ");
        }
        
        if (asistenciaBusquedaUtil.getEstado() != null) {
        	qry.append(" and e.id_estatus = " + asistenciaBusquedaUtil.getEstado());
        }
        
        if (asistenciaBusquedaUtil.getFechaInicialDate() != null && asistenciaBusquedaUtil.getFechaFinalDate() != null) {
    		qry.append(" and d.fecha_inicio between '" + asistenciaBusquedaUtil.getFechaInicialDate() +AND+ asistenciaBusquedaUtil.getFechaFinalDate() + "'");
        }

        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString());
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get(RepositoryConstants.ID_DETALLE));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get(RepositoryConstants.ID_USUARIO));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get(RepositoryConstants.CLAVE_M_USUARIO));
        	usuarioDto.setNombre((String)detalleVacacion.get(RepositoryConstants.NOMBRE));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_PATERNO));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_MATERNO));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get(RepositoryConstants.NOMBRE_UNIDAD));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get(RepositoryConstants.ID_UNIDAD));
        	usuarioDto.setNombreUnidad((String) detalleVacacion.get(RepositoryConstants.UNIDAD_ADMINISTRATIVA));
        	usuarioDto.setNivel((String) detalleVacacion.get(RepositoryConstants.NIVEL));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get(RepositoryConstants.ID_VACACION));
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)detalleVacacion.get(RepositoryConstants.ID_PERIODO));
        	periodoDto.setDescripcion((String)detalleVacacion.get(RepositoryConstants.DESCRIPCION_PERIODO));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get(RepositoryConstants.ID_RESPONSABLE));
        	
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get(RepositoryConstants.ID_ESTATUS));
        	estatusDto.setEstatus((String)detalleVacacion.get(RepositoryConstants.ESTATUS));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat(RepositoryConstants.YYYY_MM_DD);
        	String fechaIni=""+detalleVacacion.get(RepositoryConstants.FECHA_INICIO);
        	String fechaFinss=""+detalleVacacion.get(RepositoryConstants.FECHA_FIN);

        	Date fechaInicioe=null;
        	Date fechaFinal=null;

        	try {
        		fechaInicioe = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFinss);
			} catch (ParseException e) {
				logger.warn("error: ", e);
			}
        	detalleVacacionDto.setFechaInicio(fechaInicioe);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
        
        return listaDetalleVacacion;
	}
	
	@Override
	public List<DetalleVacacionDto> buscaDetalleVacacionReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		StringBuilder qry = new StringBuilder();
	       
        qry.append("select u.cve_m_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.nivel, "); 
		qry.append("ua.nombre as unidad_administrativa, d.id_estatus, d.fecha_inicio, d.fecha_fin, e.estatus ");
		qry.append("from d_detalle_vacacion d ");
		qry.append("inner join m_usuario u on d.id_usuario = u.id_usuario ");
		qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
		qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
		qry.append("left join m_estatus e on e.id_estatus = d.id_estatus ");
		qry.append("where 1 = 1");

        if (!asistenciaBusquedaUtil.getCveMusuario().isEmpty()) {
        	qry.append(" and u.cve_m_usuario = " + asistenciaBusquedaUtil.getCveMusuario());
    	}
        
        if (!asistenciaBusquedaUtil.getNombre().isEmpty()) {
        	qry.append(" and u.nombre like '%" + asistenciaBusquedaUtil.getNombre() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getPaterno().isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + asistenciaBusquedaUtil.getPaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getMaterno().isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + asistenciaBusquedaUtil.getMaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getNivel().isEmpty()) {
        	qry.append(" and u.nivel like '%" + asistenciaBusquedaUtil.getNivel() + "%' ");
        }
        
        if (asistenciaBusquedaUtil.getEstado() != null) {
        	qry.append(" and e.id_estatus = " + asistenciaBusquedaUtil.getEstado());
        }
        
        if (asistenciaBusquedaUtil.getFechaInicialDate() != null && asistenciaBusquedaUtil.getFechaFinalDate() != null) {
    		qry.append(" and d.fecha_inicio between '" + asistenciaBusquedaUtil.getFechaInicialDate() +AND+ asistenciaBusquedaUtil.getFechaFinalDate() + "'");
        }

        List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(qry.toString());
        List<DetalleVacacionDto> listaDetalleVacacion = new ArrayList<>();
        
        for (Map<String, Object> detalleVacacion : detalleVacaciones) {
        	DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
        	detalleVacacionDto.setIdDetalle((Integer)detalleVacacion.get(RepositoryConstants.ID_DETALLE));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)detalleVacacion.get(RepositoryConstants.ID_USUARIO));
        	usuarioDto.setClaveUsuario((String)detalleVacacion.get(RepositoryConstants.CLAVE_M_USUARIO));
        	usuarioDto.setNombre((String)detalleVacacion.get(RepositoryConstants.NOMBRE));
        	usuarioDto.setApellidoPaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_PATERNO));
        	usuarioDto.setApellidoMaterno((String)detalleVacacion.get(RepositoryConstants.APELLIDO_MATERNO));
        	usuarioDto.setNombreUnidad((String)detalleVacacion.get(RepositoryConstants.NOMBRE_UNIDAD));
        	usuarioDto.setIdUnidad((Integer)detalleVacacion.get(RepositoryConstants.ID_UNIDAD));
        	usuarioDto.setNombreUnidad((String) detalleVacacion.get(RepositoryConstants.UNIDAD_ADMINISTRATIVA));
        	usuarioDto.setNivel((String) detalleVacacion.get(RepositoryConstants.NIVEL));
        	detalleVacacionDto.setIdUsuario(usuarioDto);
        	
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get(RepositoryConstants.ID_VACACION));
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)detalleVacacion.get(RepositoryConstants.ID_PERIODO));
        	periodoDto.setDescripcion((String)detalleVacacion.get(RepositoryConstants.DESCRIPCION_PERIODO));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdVacacion(vacacionPeriodoDto);
        	detalleVacacionDto.setIdResponsable((Integer)detalleVacacion.get(RepositoryConstants.ID_RESPONSABLE));
        	
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)detalleVacacion.get(RepositoryConstants.ID_ESTATUS));
        	estatusDto.setEstatus((String)detalleVacacion.get(RepositoryConstants.ESTATUS));
        	detalleVacacionDto.setIdEstatus(estatusDto);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat(RepositoryConstants.YYYY_MM_DD);
        	String fechaIni=""+detalleVacacion.get(RepositoryConstants.FECHA_INICIO);
        	String fechaFinss=""+detalleVacacion.get(RepositoryConstants.FECHA_FIN);

        	Date fechaInicioe=null;
        	Date fechaFinal=null;

        	try {
        		fechaInicioe = sdf.parse(fechaIni);
        		fechaFinal = sdf.parse(fechaFinss);
			} catch (ParseException e) {
				logger.warn("Error: {} ", e);
			}
        	detalleVacacionDto.setFechaInicio(fechaInicioe);
        	detalleVacacionDto.setFechaFin(fechaFinal);
        	detalleVacacionDto.setDias((Integer)detalleVacacion.get("dias"));
    		listaDetalleVacacion.add(detalleVacacionDto);
    	}
        
        return listaDetalleVacacion;
	}
	
}
