package mx.gob.segob.dgtic.persistence.repository.impl;

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
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.LicenciaMedicaRepository;

@Repository
public class LicenciaMedicaRepositoryImpl implements LicenciaMedicaRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_licencia, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, padecimiento ");
        qry.append("FROM m_licencia_medica ");
        
        List<Map<String, Object>> licenciasMedicas = jdbcTemplate.queryForList(qry.toString());
        List<LicenciaMedicaDto> listaLicenciaMedica = new ArrayList<>();
        
        for (Map<String, Object> licenciaMedica : licenciasMedicas) {
        	LicenciaMedicaDto licenciaMedicaDto = new LicenciaMedicaDto();
        	licenciaMedicaDto.setIdLicencia((Integer)licenciaMedica.get("id_licencia"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)licenciaMedica.get("id_usuario"));
        	licenciaMedicaDto.setIdUsuario(usuarioDto);
        	licenciaMedicaDto.setIdResponsable((Integer)licenciaMedica.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)licenciaMedica.get("id_archivo"));
        	licenciaMedicaDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto= new EstatusDto();
    		estatusDto.setIdEstatus((Integer)licenciaMedica.get("id_estatus"));
    		licenciaMedicaDto.setIdEstatus(estatusDto);
    		licenciaMedicaDto.setFechaInicio((Date)licenciaMedica.get("fecha_inicio"));
    		licenciaMedicaDto.setFechaFin((Date)licenciaMedica.get("fecha_fin"));
    		licenciaMedicaDto.setDias((Integer)licenciaMedica.get("dias"));
    		licenciaMedicaDto.setPadecimiento((String) licenciaMedica.get("padecimiento"));
    		listaLicenciaMedica.add(licenciaMedicaDto);
    	}
     return listaLicenciaMedica;
	}

	@Override
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("select distinct(licencia.id_licencia) id_licencia, licencia.fecha_inicio, licencia.id_responsable, licencia.fecha_fin, licencia.dias, licencia.padecimiento, licencia.fecha_registro, usuario.id_usuario, usuario.nombre, ");
        qry.append("usuario.cve_m_usuario, usuario.apellido_paterno, usuario.apellido_materno, usuario.fecha_ingreso, usuario.rfc, usuario.id_puesto, unidad.id_unidad, unidad.nombre nombre_unidad, estatus.id_estatus, ");
        qry.append("estatus.estatus, archivo.id_archivo, archivo.url, archivo.nombre nombre_archivo ");
        qry.append("from m_licencia_medica licencia left join m_usuario usuario on usuario.id_usuario=licencia.id_usuario left join m_estatus estatus on ");
        qry.append("estatus.id_estatus=licencia.id_estatus left join m_archivo archivo on archivo.id_archivo=licencia.id_archivo left join usuario_unidad_administrativa relacion ");
        qry.append("on usuario.cve_m_usuario=relacion.cve_m_usuario left join c_unidad_administrativa unidad on unidad.id_unidad=relacion.id_unidad ");
        qry.append("where licencia.id_licencia = :idLicencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idLicencia", idLicencia);
        System.out.println("id para consulta "+idLicencia+ " consulta "+qry.toString());
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        LicenciaMedicaDto licencia=new LicenciaMedicaDto();
        licencia.setIdLicencia((Integer)informacionConsulta.get("id_licencia"));
        licencia.setPadecimiento((String)informacionConsulta.get("padecimiento"));
        licencia.setFechaInicio((Date)informacionConsulta.get("fecha_inicio"));
        licencia.setFechaFin((Date)informacionConsulta.get("fecha_fin"));
        licencia.setDias((Integer)informacionConsulta.get("dias"));
        licencia.setFechaRegistro((Date)informacionConsulta.get("fecha_registro"));
        System.out.println("Datos de usuario fec "+informacionConsulta.get("fecha_registro"));
        UsuarioDto usuarioDto= new UsuarioDto();
        usuarioDto.setIdUsuario((Integer)informacionConsulta.get("id_usuario"));
        usuarioDto.setClaveUsuario((String)informacionConsulta.get("cve_m_usuario"));
        System.out.println("claveUsuario "+informacionConsulta.get("cve_m_usuario"));
        usuarioDto.setNombre((String)informacionConsulta.get("nombre"));
        usuarioDto.setApellidoPaterno((String)informacionConsulta.get("apellido_paterno"));
        usuarioDto.setApellidoMaterno((String)informacionConsulta.get("apellido_materno"));
        usuarioDto.setIdPuesto((String)informacionConsulta.get("id_puesto"));
        usuarioDto.setFechaIngreso((Date)informacionConsulta.get("fecha_ingreso"));
        usuarioDto.setRfc((String)informacionConsulta.get("rfc"));
        usuarioDto.setIdUnidad((Integer)informacionConsulta.get("id_unidad"));
        usuarioDto.setNombreUnidad((String)informacionConsulta.get("nombre_unidad"));
        licencia.setIdUsuario(usuarioDto);
        ArchivoDto archivoDto = new ArchivoDto();
        archivoDto.setIdArchivo((Integer)informacionConsulta.get("id_archivo"));
        licencia.setIdArchivo(archivoDto);
        EstatusDto estatusDto= new EstatusDto();
        estatusDto.setIdEstatus((Integer)informacionConsulta.get("id_estatus"));
        System.out.println("Id Estatussssssssssssssssssssssss "+informacionConsulta.get("id_estatus"));
        estatusDto.setEstatus((String)informacionConsulta.get("estatus"));
        licencia.setIdEstatus(estatusDto);
        return licencia;
        //return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<LicenciaMedicaDto>(LicenciaMedicaDto.class));
	}

	@Override
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto) {
		StringBuilder qry = new StringBuilder();
		System.out.println("Peticion a actualizar licencia "+licenciaMedicaDto.getIdLicencia()+" archivo "+licenciaMedicaDto.getIdArchivo().getIdArchivo()+" estatus "+licenciaMedicaDto.getIdEstatus().getIdEstatus());
		qry.append("UPDATE m_licencia_medica SET id_estatus= :idEstatus, id_archivo= :idArchivo ");
		qry.append("WHERE id_licencia = :idLicencia");
		Integer i=0;
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idLicencia", licenciaMedicaDto.getIdLicencia());
		parametros.addValue("idEstatus", licenciaMedicaDto.getIdEstatus().getIdEstatus());
		parametros.addValue("idArchivo", licenciaMedicaDto.getIdArchivo().getIdArchivo());
		try{
			i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(i == 1){
        	licenciaMedicaDto.setMensaje("El registro de licencia medica se actualizó correctamente.");
        }else{
        	licenciaMedicaDto.setMensaje("Se ha generado un error al actualizar licencia medica, revise la información");
        }
		
		return licenciaMedicaDto;
		
	}

	@Override
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto) {
		Integer i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaIni=sdf.format(licenciaMedicaDto.getFechaInicio());
		String fechaF=sdf.format(licenciaMedicaDto.getFechaFin());
		String query="select id_licencia from m_licencia_medica where (((fecha_inicio between '"+fechaIni+"' and '"+fechaF+"') "
				+ "or (fecha_fin between '"+fechaIni+"' and '"+fechaF+"' )) "+
				" or('"+fechaIni+"'>fecha_inicio and fecha_inicio<'"+fechaF+"' and fecha_fin>'"+fechaF+"'))"
						+ "and id_usuario='"+licenciaMedicaDto.getIdUsuario().getIdUsuario()+"' ";
		System.out.println("query "+query);
        List<Map<String, Object>> detalleLicencia = jdbcTemplate.queryForList(query);
        System.out.println("Datos de la consulta "+detalleLicencia.size());
        if(detalleLicencia.size()==0 || detalleLicencia==null){
			StringBuilder qry = new StringBuilder();
			Date fechaActual = new Date();
			System.out.println("Fecha actual "+fechaActual+" idUsuario "+licenciaMedicaDto.getIdUsuario().getIdUsuario());
			licenciaMedicaDto.setFechaRegistro(fechaActual);
			qry.append("INSERT INTO m_licencia_medica (id_usuario, id_estatus, fecha_inicio, fecha_fin, dias, padecimiento, fecha_registro ) ");
			qry.append("VALUES (:idUsuario, :idEstatus, :fechaInicio, :fechaFin, :dias, :padecimiento, :fechaRegistro) ");
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("idUsuario", licenciaMedicaDto.getIdUsuario().getIdUsuario());
			//parametros.addValue("idResponsable", licenciaMedicaDto.getIdResponsable());
			parametros.addValue("idEstatus", licenciaMedicaDto.getIdEstatus().getIdEstatus());
			parametros.addValue("fechaInicio", licenciaMedicaDto.getFechaInicio());
			parametros.addValue("fechaFin", licenciaMedicaDto.getFechaFin());
			parametros.addValue("dias", licenciaMedicaDto.getDias());
			parametros.addValue("padecimiento", licenciaMedicaDto.getPadecimiento());
			parametros.addValue("fechaRegistro", licenciaMedicaDto.getFechaRegistro());
	
			try{
				i= nameParameterJdbcTemplate.update(qry.toString(), parametros);
			}catch(Exception e){
				e.printStackTrace();
			}
        }
        if(i == 1){
        	licenciaMedicaDto.setMensaje("El registro de licencia medica se realizó correctamente.");
        }else{
        	licenciaMedicaDto.setMensaje("Se ha generado un error al guardar licencia medica, revise la información");
        }
        return licenciaMedicaDto;
		
	}

	@Override
	public void eliminaLicenciaMedica(Integer idLicencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_licencia_medica WHERE id_licencia = :idLicencia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idLicencia", idLicencia);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(String claveUsuario, String fechaInicio, String fechaFin, String idEstatus) {
		String qry="select usuario.id_usuario, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, licencia.id_licencia, ";
		qry+="licencia.id_responsable, estatus.id_estatus, estatus.estatus, licencia.fecha_inicio, ";
		qry+="licencia.fecha_fin, licencia.dias, licencia.padecimiento, licencia.dias ";
		qry+="from m_usuario usuario, m_licencia_medica licencia, m_estatus estatus ";
		qry+="where usuario.id_usuario=licencia.id_usuario and estatus.id_estatus=licencia.id_estatus and usuario.cve_m_usuario='"+claveUsuario+"'";
		System.out.println("Fechas para la consulta fechaInicio "+fechaInicio+" fechaFin "+fechaFin);
		if(idEstatus!=null && !idEstatus.trim().isEmpty()){
			qry+="and estatus.id_estatus = +'"+idEstatus+"' ";
//        	query+="and estat.id_periodo='"+idPeriodo+"' ";
        }
        if((fechaInicio!=null && !fechaInicio.trim().isEmpty())&& (fechaFin!=null && !fechaFin.trim().isEmpty())){
        	qry+="and licencia.fecha_inicio between '"+fechaInicio+"' and '"+fechaFin+"' ";
        }else if(fechaInicio!=null && !fechaInicio.trim().isEmpty()){
        	qry+="and licencia.fecha_inicio='"+fechaInicio+"'";
        }else if(fechaFin!=null && !fechaFin.trim().isEmpty()){
        	qry+="and licencia.fecha_fin='"+fechaInicio+"'";
        }
//        if(idEstatus!=null && idEstatus!=""){
//        if(idEstatus!=null && !idEstatus.trim().isEmpty()){
//        	query+="and detalle.id_estatus='"+idEstatus+"' ";
//        }
        System.out.println("query "+qry);
        List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry);
        List<LicenciaMedicaDto> listaLicencias = new ArrayList<>();
        
        for (Map<String, Object> licencias : consulta) {
        	LicenciaMedicaDto licencia= new LicenciaMedicaDto();
        	UsuarioDto usuario= new UsuarioDto();
        	usuario.setIdUsuario((Integer)licencias.get("id_usuario"));
        	usuario.setClaveUsuario((String)licencias.get("cve_m_usuario"));
        	usuario.setNombre((String)licencias.get("nombre"));
        	usuario.setApellidoPaterno((String)licencias.get("apellido_paterno"));
        	usuario.setApellidoMaterno((String)licencias.get("apellido_materno"));
        	licencia.setIdUsuario(usuario);
        	EstatusDto estatus= new EstatusDto();
        	estatus.setIdEstatus((Integer)licencias.get("id_estatus"));
        	estatus.setEstatus((String)licencias.get("estatus"));
        	licencia.setIdEstatus(estatus);
        	ArchivoDto archivo= new ArchivoDto();
        	archivo.setIdArchivo((Integer)licencias.get("id_archivo"));
        	archivo.setUrl((String)licencias.get("url"));
        	archivo.setNombre((String)licencias.get("nombre_archivo"));
        	licencia.setIdArchivo(archivo);
        	licencia.setIdLicencia((Integer)licencias.get("id_licencia"));
        	System.out.println("Dato recuperado "+licencias.get("id_licencia"));
        	licencia.setDias((Integer)licencias.get("dias"));
        	licencia.setFechaFin((Date)licencias.get("fecha_fin"));
        	licencia.setFechaInicio((Date)licencias.get("fecha_Inicio"));
        	licencia.setIdResponsable((Integer)licencias.get("id_responsable"));
        	licencia.setPadecimiento((String)licencias.get("padecimiento"));
        	listaLicencias.add(licencia);
        }
        return listaLicencias;
	}

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(String claveUsuario, String nombre,
		String apellidoPaterno, String apellidoMaterno, String idEstatus, String idUnidad) {
		String qry="select usuario.id_usuario, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, licencia.id_licencia, ";
		qry+="licencia.id_responsable, estatus.id_estatus, estatus.estatus, licencia.fecha_inicio, ";
		qry+="licencia.fecha_fin, licencia.dias, licencia.padecimiento, licencia.dias ";
		qry+="from m_usuario usuario, m_licencia_medica licencia, m_estatus estatus, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ";
		qry+="where usuario.id_usuario=licencia.id_usuario and estatus.id_estatus=licencia.id_estatus and unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario ";
		if(claveUsuario!=null && !claveUsuario.trim().isEmpty()){
			qry+="and usuario.cve_m_usuario like '%"+claveUsuario+"%' ";
	    }
	    if(nombre!=null && !nombre.trim().isEmpty()){
	    	qry+="and usuario.nombre like '%"+nombre+"%' ";
	    }
	    if(apellidoPaterno!=null && !apellidoPaterno.trim().isEmpty()){
	    	qry+="and usuario.apellido_paterno like '%"+apellidoPaterno+"%' ";
	    }
	    if(apellidoMaterno!=null && !apellidoMaterno.trim().isEmpty()){
	    	qry+="and usuario.apellido_materno like '%"+apellidoMaterno+"%' ";
	    }
	    if(idUnidad!=null && !idUnidad.trim().isEmpty()){
	    	qry+="and unidad.id_unidad='"+idUnidad+"' ";
	    }
	   
	    if(idEstatus!=null && !idEstatus.trim().isEmpty()){
	    	qry+="and estatus.id_estatus='"+idEstatus+"' ";
	    }
        System.out.println("query de licencias empleados "+qry+" idUnidad "+idUnidad);
        List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry);
        List<LicenciaMedicaDto> listaLicencias = new ArrayList<>();
        
        for (Map<String, Object> licencias : consulta) {
        	LicenciaMedicaDto licencia= new LicenciaMedicaDto();
        	UsuarioDto usuario= new UsuarioDto();
        	usuario.setIdUsuario((Integer)licencias.get("id_usuario"));
        	usuario.setClaveUsuario((String)licencias.get("cve_m_usuario"));
        	usuario.setNombre((String)licencias.get("nombre"));
        	usuario.setApellidoPaterno((String)licencias.get("apellido_paterno"));
        	usuario.setApellidoMaterno((String)licencias.get("apellido_materno"));
        	licencia.setIdUsuario(usuario);
        	EstatusDto estatus= new EstatusDto();
        	estatus.setIdEstatus((Integer)licencias.get("id_estatus"));
        	estatus.setEstatus((String)licencias.get("estatus"));
        	licencia.setIdEstatus(estatus);
        	ArchivoDto archivo= new ArchivoDto();
        	archivo.setIdArchivo((Integer)licencias.get("id_archivo"));
        	archivo.setUrl((String)licencias.get("url"));
        	archivo.setNombre((String)licencias.get("nombre_archivo"));
        	licencia.setIdArchivo(archivo);
        	licencia.setIdLicencia((Integer)licencias.get("id_licencia"));
        	System.out.println("Dato recuperado "+licencias.get("id_licencia"));
        	licencia.setDias((Integer)licencias.get("dias"));
        	licencia.setFechaFin((Date)licencias.get("fecha_fin"));
        	licencia.setFechaInicio((Date)licencias.get("fecha_Inicio"));
        	licencia.setIdResponsable((Integer)licencias.get("id_responsable"));
        	licencia.setPadecimiento((String)licencias.get("padecimiento"));
        	listaLicencias.add(licencia);
        }
        return listaLicencias;
	}

	@Override
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(String idUnidad,String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno) {
		//StringBuilder qry = new StringBuilder();
		String qry="";
		qry+="select sum(licencia.dias) suma_dias, count(licencia.id_licencia) total_licencias, usuario.id_usuario ,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno ";
		qry+="from m_licencia_medica licencia right join m_usuario usuario on usuario.id_usuario=licencia.id_usuario, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ";
		//qry+="left join usuario_unidad_administrativa relacion on usuario.cve_m_usuario=relacion.cve_m_usuario ";
		//qry+="left join c_unidad_administrativa unidad on unidad.id_unidad=relacion.id_unidad ";
		qry+="where usuario.cve_m_usuario=relacion.cve_m_usuario and unidad.id_unidad=relacion.id_unidad and unidad.id_unidad ='"+idUnidad+"' ";
		//MapSqlParameterSource parametros = new MapSqlParameterSource();
		//parametros.addValue("idUnidad", idUnidad);
		if(claveUsuario!=null && !claveUsuario.trim().isEmpty()){
			qry+="and usuario.cve_m_usuario = '"+claveUsuario+"' ";
	    }
	    if(nombre!=null && !nombre.trim().isEmpty()){
	    	qry+="and usuario.nombre like '%"+nombre+"%' ";
	    }
	    if(apellidoPaterno!=null && !apellidoPaterno.trim().isEmpty()){
	    	qry+="and usuario.apellido_paterno like '%"+apellidoPaterno+"%' ";
	    }
	    if(apellidoMaterno!=null && !apellidoMaterno.trim().isEmpty()){
	    	qry+="and usuario.apellido_materno like '%"+apellidoMaterno+"%' ";
	    }
	    qry+="group by usuario.id_usuario ";
		System.out.println("sqy "+qry);
		 List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry);
	        List<LicenciaMedicaDto> listaLicencias = new ArrayList<>();
	        
	        for (Map<String, Object> licencias : consulta) {
	        	LicenciaMedicaDto licenciaMedicaDto= new LicenciaMedicaDto();
	        	String diasRecuperados=null;
	        	if(licencias.get("suma_dias")==null){
	        		diasRecuperados="0";
	        	}else{
	        	diasRecuperados=""+licencias.get("suma_dias");
	        	}
	        	licenciaMedicaDto.setDiasTotales(diasRecuperados);
	        	String diasTotales=""+licencias.get("total_licencias");
	        	licenciaMedicaDto.setTotalLicencias((String)diasTotales);
	        	UsuarioDto usuarioDto = new UsuarioDto();
	        	usuarioDto.setIdUsuario((Integer)licencias.get("id_usuario"));
	        	usuarioDto.setClaveUsuario((String)licencias.get("cve_m_usuario"));
	        	usuarioDto.setNombre((String)licencias.get("nombre"));
	        	usuarioDto.setApellidoPaterno((String)licencias.get("apellido_paterno"));
	        	usuarioDto.setApellidoMaterno((String)licencias.get("apellido_materno"));
	        	licenciaMedicaDto.setIdUsuario(usuarioDto);
	        	listaLicencias.add(licenciaMedicaDto);
	        }
	        System.out.println("Numero de registros recuperados "+listaLicencias.size());
	        return listaLicencias;  
	}

	@Override
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario) {
		StringBuilder qry = new StringBuilder();
		qry.append("select sum(licencia.dias) suma_dias, count(licencia.id_licencia) total_licencias ");
        qry.append("from m_licencia_medica licencia right join m_usuario usuario on usuario.id_usuario=licencia.id_usuario ");
        qry.append("where usuario.cve_m_usuario = :claveUsuario");
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", claveUsuario);
		Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        LicenciaMedicaDto licencia=new LicenciaMedicaDto();
        String diasRecuperados=null;
    	if(informacionConsulta.get("suma_dias")==null){
    		diasRecuperados="0";
    	}else{
    	diasRecuperados=""+informacionConsulta.get("suma_dias");
    	}
    	licencia.setDiasTotales(diasRecuperados);
    	String diasTotales=""+informacionConsulta.get("total_licencias");
    	licencia.setTotalLicencias((String)diasTotales);
    	return licencia;
		
	}
}
