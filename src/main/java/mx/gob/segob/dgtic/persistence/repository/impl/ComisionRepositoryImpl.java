package mx.gob.segob.dgtic.persistence.repository.impl;

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
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;

@Repository
public class ComisionRepositoryImpl implements ComisionRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<ComisionDto> obtenerListaComisiones() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_comision, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, comision, id_horario");
        qry.append("FROM m_comision ");
        
        List<Map<String, Object>> comisiones = jdbcTemplate.queryForList(qry.toString());
        List<ComisionDto> listacomision = new ArrayList<>();
        
        for (Map<String, Object> comision : comisiones) {
        	ComisionDto comisionDto = new ComisionDto();
        	comisionDto.setIdComision((Integer)comision.get("id_comision"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)comision.get("id_usuario"));
        	comisionDto.setIdUsuario(usuarioDto);
        	comisionDto.setIdResponsable((Integer)comision.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)comision.get("id_archivo"));
        	comisionDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto = new EstatusDto();
        	estatusDto.setIdEstatus((Integer)comision.get("id_estatus"));
        	comisionDto.setIdEstatus(estatusDto);
        	comisionDto.setFechaInicio((Date)comision.get("fecha_inicio"));
        	comisionDto.setFechaFin((Date)comision.get("fecha_fin"));
        	comisionDto.setDias((Integer)comision.get("dias"));
        	comisionDto.setComision((String)comision.get("comision"));
        	Horario horario = new Horario();
        	horario.setIdHorario((Integer)comision.get("id_horario"));
        	comisionDto.setIdHorario(horario);
        	
    		listacomision.add(comisionDto);
    	}
		return listacomision;
	}

	@Override
	public ComisionDto buscaComision(Integer idComision) {
		StringBuilder qry = new StringBuilder();
//		qry.append("SELECT id_comision, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, comision, fecha_registro ");
//        qry.append("FROM m_comision ");
//        qry.append("WHERE id_comision = :idComision");
        
        qry.append("SELECT comision.id_comision, comision.fecha_registro, comision.id_responsable, comision.fecha_registro, comision.id_usuario, comision.id_archivo, "); 
        qry.append("comision.id_estatus, comision.fecha_inicio, comision.fecha_fin, comision.dias, comision.fecha_registro, comision.comision, usuario.cve_m_usuario, "); 
        qry.append("usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.rfc, "); 
        qry.append("usuario.id_puesto, usuario.fecha_ingreso, estatus.id_estatus, estatus.estatus, unidad.id_unidad, unidad.nombre nombre_unidad, horario.id_horario ");
        qry.append("FROM m_comision comision ");
        qry.append("RIGHT JOIN m_usuario usuario ON usuario.id_usuario=comision.id_usuario ");
        qry.append("LEFT JOIN m_estatus estatus ON estatus.id_estatus=comision.id_estatus ");
        qry.append("LEFT JOIN usuario_unidad_administrativa relacion ON usuario.cve_m_usuario=relacion.cve_m_usuario ");
        qry.append("LEFT JOIN c_unidad_administrativa unidad ON unidad.id_unidad=relacion.id_unidad ");
        qry.append("LEFT JOIN c_horario horario ON horario.id_horario = comision.id_horario ");
        qry.append("WHERE comision.id_comision = :idComision ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idComision", idComision);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        ComisionDto comisionDto = new ComisionDto();
        comisionDto.setIdComision((Integer)informacionConsulta.get("id_comision"));
        comisionDto.setIdResponsable((Integer)informacionConsulta.get("id_responsable"));
        UsuarioDto usuarioDto= new UsuarioDto();
        usuarioDto.setIdUsuario((Integer)informacionConsulta.get("id_usuario"));
        usuarioDto.setClaveUsuario((String)informacionConsulta.get("cve_m_usuario"));
        System.out.println("claveUsuario "+informacionConsulta.get("cve_m_usuario"));
        usuarioDto.setNombre((String)informacionConsulta.get("nombre"));
        usuarioDto.setApellidoPaterno((String)informacionConsulta.get("apellido_paterno"));
        usuarioDto.setApellidoMaterno((String)informacionConsulta.get("apellido_materno"));
        usuarioDto.setIdPuesto((String)informacionConsulta.get("id_puesto"));
        usuarioDto.setFechaIngreso((Date)informacionConsulta.get("fecha_ingreso"));
        System.out.println("Fecha ingreso usuario "+ usuarioDto.getFechaIngreso());
        usuarioDto.setRfc((String)informacionConsulta.get("rfc"));
        usuarioDto.setIdUnidad((Integer)informacionConsulta.get("id_unidad"));
        usuarioDto.setNombreUnidad((String)informacionConsulta.get("nombre_unidad"));
        comisionDto.setIdUsuario(usuarioDto);
        ArchivoDto archivoDto = new ArchivoDto();
        archivoDto.setIdArchivo((Integer)informacionConsulta.get("id_archivo"));
        comisionDto.setIdArchivo(archivoDto);
        EstatusDto estatusDto= new EstatusDto();
        estatusDto.setIdEstatus((Integer)informacionConsulta.get("id_estatus"));
        System.out.println("Id Estatussssssssssssssssssssssss "+informacionConsulta.get("id_estatus"));
        estatusDto.setEstatus((String)informacionConsulta.get("estatus"));
        comisionDto.setIdEstatus(estatusDto);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//      System.out.println("fecha actual"+(Date)detalleVacacion.get("fecha_inicio"));
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
        System.out.println("FechaInicio: "+fechaInicio+" FechaFin: "+fechaFinal + " FechaRegistro: " + fechaRegistro);
        comisionDto.setFechaInicio(fechaInicio);
        comisionDto.setFechaFin(fechaFinal);
        comisionDto.setFechaRegistro(fechaRegistro);
        comisionDto.setComision((String)informacionConsulta.get("comision"));
        Horario idHorario = new Horario();
        idHorario.setIdHorario((Integer)informacionConsulta.get("id_horario"));
        comisionDto.setIdHorario(idHorario);
        System.out.println("informacionConsulta.get "+informacionConsulta.get("fecha_fin"));
        comisionDto.setDias((Integer)informacionConsulta.get("dias"));
        
        return comisionDto;
//        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<ComisionDto>(ComisionDto.class));
		
	}

  @Override
  public ComisionDto modificaComision(ComisionDto comisionDto) {
    StringBuilder qry = new StringBuilder();
    Integer i = 0;
    qry.append(
        "UPDATE m_comision SET fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, dias = :dias, comision = :comision, id_horario = :idHorario ");
    qry.append("WHERE id_comision = :idComision");

    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue("idComision", comisionDto.getIdComision());
    parametros.addValue("fechaInicio", comisionDto.getFechaInicio());
    parametros.addValue("fechaFin", comisionDto.getFechaFin());
    parametros.addValue("dias", comisionDto.getDias());
    parametros.addValue("comision", comisionDto.getComision());
    parametros.addValue("idHorario", comisionDto.getIdHorario().getIdHorario());

    try {
      i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (i == 1) {
      comisionDto.setMensaje("El registro de comisión se actualizó correctamente.");
    } else {
      comisionDto
          .setMensaje("Se ha generado un error al actualizar comisión, revise la información");
    }

    return comisionDto;

  }
	
  @Override
  public ComisionDto modificaComisionEstatusArchivo(ComisionDto comisionDto) {
    StringBuilder qry = new StringBuilder();
    qry.append("UPDATE m_comision SET id_estatus= :idEstatus, id_archivo= :idArchivo ");
    qry.append("WHERE id_comision = :idComision");
    Integer i = 0;
    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue("idComision", comisionDto.getIdComision());
    parametros.addValue("idEstatus", comisionDto.getIdEstatus().getIdEstatus());
    parametros.addValue("idArchivo", comisionDto.getIdArchivo().getIdArchivo());

    try {
      i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (i == 1) {
      comisionDto.setMensaje("El registro de comisión se actualizó correctamente.");
    } else {
      comisionDto.setMensaje(
          "Se ha generado un error al actualizar comisión, revise la información");
    }

    return comisionDto;
  }
	

  @Override
  public ComisionDto agregaComision(ComisionDto comisionDto) {
    Integer i = 0;
    System.out.println("Insertando en repository");
    System.out.println("id usuario: " + comisionDto.getIdUsuario().getIdUsuario());
    StringBuilder qry = new StringBuilder();
    qry.append(
        "INSERT INTO m_comision (id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio,fecha_fin, dias, comision, fecha_registro, id_horario) ");
    qry.append(
        "VALUES (:idUsuario, :idResponsable, :idArchivo, :idEstatus, :fechaInicio, :fechaFin, :dias, :comision, :fechaRegistro, :idHorario) ");

    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue("idUsuario", comisionDto.getIdUsuario().getIdUsuario());
    parametros.addValue("idResponsable", comisionDto.getIdResponsable());
    parametros.addValue("idArchivo", comisionDto.getIdArchivo().getIdArchivo());
    parametros.addValue("idEstatus", comisionDto.getIdEstatus().getIdEstatus());
    parametros.addValue("fechaInicio", comisionDto.getFechaInicio());
    parametros.addValue("fechaFin", comisionDto.getFechaFin());
    parametros.addValue("dias", comisionDto.getDias());
    parametros.addValue("comision", comisionDto.getComision());
    parametros.addValue("fechaRegistro", comisionDto.getFechaRegistro());
    parametros.addValue("idHorario", comisionDto.getIdHorario().getIdHorario());
    try {
      i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (i == 1)
      comisionDto.setMensaje("El registro de comisiones se realizó correctamente.");
    else
      comisionDto.setMensaje(
          "Se ha generado un error al guardar vacaciones, revise que los datos sean correctos");
    return comisionDto;
  }

		
	


	@Override
	public void eliminaComision(Integer idComision) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_comision  WHERE id_comision = :idComision");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idComision", idComision);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

  @Override
  public List<ComisionDto> obtenerListaComisionPorFiltros(String claveUsuario, String fechaInicio,
      String fechaFin, String idEstatus) {
    String qry="SELECT usuario.id_usuario, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, comision.id_comision, ";
    qry+="comision.id_responsable, archivo.id_archivo, archivo.nombre, archivo.url, estatus.id_estatus, estatus.estatus, comision.fecha_inicio, ";
    qry+="comision.fecha_fin, comision.dias, comision.comision, comision.dias, comision.fecha_registro ";
    qry+="FROM m_comision comision ";
    qry+="LEFT JOIN m_usuario usuario ON usuario.id_usuario = comision.id_usuario ";
    qry+="LEFT JOIN m_archivo archivo ON archivo.id_archivo = comision.id_archivo ";
    qry+="LEFT JOIN m_estatus estatus ON estatus.id_estatus = comision.id_estatus ";
    qry+="WHERE ";
    
    qry += "comision.id_usuario = '" + claveUsuario + "' ";

    if (idEstatus != null && !idEstatus.trim().isEmpty() && !idEstatus.equals("null")) {
      qry += "and comision.id_estatus = '" + idEstatus + "' ";
    }
    if ((fechaInicio != null && !fechaInicio.trim().isEmpty())
        && (fechaFin != null && !fechaFin.trim().isEmpty())) {
      qry += "and comision.fecha_inicio between '" + fechaInicio + "' and '" + fechaFin + "' ";
    } else if (fechaInicio != null && !fechaInicio.trim().isEmpty()) {
      qry += "and comision.fecha_inicio='" + fechaInicio + "'";
    } else if (fechaFin != null && !fechaFin.trim().isEmpty()) {
      qry += "and comision.fecha_fin='" + fechaInicio + "'";
    }

    System.out.println("query " + qry);
    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry);
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      ComisionDto comision = new ComisionDto();
      UsuarioDto usuario = new UsuarioDto();
      usuario.setIdUsuario((Integer) comisiones.get("id_usuario"));
      usuario.setClaveUsuario((String) comisiones.get("cve_m_usuario"));
      usuario.setNombre((String) comisiones.get("nombre"));
      usuario.setApellidoPaterno((String) comisiones.get("apellido_paterno"));
      usuario.setApellidoMaterno((String) comisiones.get("apellido_materno"));
      comision.setIdUsuario(usuario);
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus((Integer) comisiones.get("id_estatus"));
      estatus.setEstatus((String) comisiones.get("estatus"));
      comision.setIdEstatus(estatus);
      ArchivoDto archivo = new ArchivoDto();
      archivo.setIdArchivo((Integer) comisiones.get("id_archivo"));
      archivo.setUrl((String) comisiones.get("url"));
      archivo.setNombre((String) comisiones.get("nombre_archivo"));
      comision.setIdArchivo(archivo);
      comision.setIdComision((Integer) comisiones.get("id_comision"));
      System.out.println("Dato recuperado " + comisiones.get("id_usuario"));
      comision.setDias((Integer) comisiones.get("dias"));
      comision.setFechaFin((Date) comisiones.get("fecha_fin"));
      comision.setFechaInicio((Date) comisiones.get("fecha_Inicio"));
      comision.setIdResponsable((Integer) comisiones.get("id_responsable"));
      comision.setComision((String) comisiones.get("comision"));
      comision.setFechaRegistro((Date) comisiones.get("fecha_registro"));
      listaComisiones.add(comision);
    }
    return listaComisiones;
  }

  @Override
  public List<ComisionDto> obtenerListaComisionPorFiltrosEmpleados(String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno, String idEstatus,
      String idUnidad) {
    String qry="SELECT usuario.id_usuario, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, comision.id_comision, ";
    qry+="unidad.id_unidad, unidad.nombre nombre_unidad, comision.id_responsable, estatus.id_estatus, estatus.estatus, comision.fecha_inicio, ";
    qry+="comision.fecha_fin, comision.dias, comision.comision, comision.dias, comision.fecha_registro ";
    qry+="FROM m_comision comision, m_usuario usuario, m_estatus estatus, c_unidad_administrativa unidad ";
    qry+="WHERE usuario.id_usuario=comision.id_usuario and estatus.id_estatus=comision.id_estatus ";
    
    if (claveUsuario != null && !claveUsuario.trim().isEmpty()) {
      qry += "and usuario.cve_m_usuario like '%" + claveUsuario + "%' ";
    }
    if (nombre != null && !nombre.trim().isEmpty()) {
      qry += "and usuario.nombre like '%" + nombre + "%' ";
    }
    if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty()) {
      qry += "and usuario.apellido_paterno like '%" + apellidoPaterno + "%' ";
    }
    if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
      qry += "and usuario.apellido_materno like '%" + apellidoMaterno + "%' ";
    }
    if (idUnidad != null && !idUnidad.trim().isEmpty()) {
      qry += "and unidad.id_unidad='" + idUnidad + "' ";
    }

    if (idEstatus != null && !idEstatus.trim().isEmpty()) {
      qry += "and estatus.id_estatus='" + idEstatus + "' ";
    }
    
    System.out.println("query " + qry);
    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry);
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      ComisionDto comision = new ComisionDto();
      UsuarioDto usuario = new UsuarioDto();
      usuario.setIdUsuario((Integer) comisiones.get("id_usuario"));
      usuario.setClaveUsuario((String) comisiones.get("cve_m_usuario"));
      usuario.setNombre((String) comisiones.get("nombre"));
      usuario.setApellidoPaterno((String) comisiones.get("apellido_paterno"));
      usuario.setApellidoMaterno((String) comisiones.get("apellido_materno"));
      usuario.setNombreUnidad((String)comisiones.get("nombre_unidad"));
      usuario.setIdUnidad((Integer)comisiones.get("id_unidad"));
      comision.setIdUsuario(usuario);
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus((Integer) comisiones.get("id_estatus"));
      estatus.setEstatus((String) comisiones.get("estatus"));
      comision.setIdEstatus(estatus);
      ArchivoDto archivo = new ArchivoDto();
      comision.setIdArchivo(archivo);
      comision.setIdComision((Integer) comisiones.get("id_comision"));
      System.out.println("Dato recuperado " + comisiones.get("id_usuario"));
      comision.setDias((Integer) comisiones.get("dias"));
      comision.setFechaFin((Date) comisiones.get("fecha_fin"));
      comision.setFechaInicio((Date) comisiones.get("fecha_Inicio"));
      comision.setIdResponsable((Integer) comisiones.get("id_responsable"));
      comision.setComision((String) comisiones.get("comision"));
      comision.setFechaRegistro((Date) comisiones.get("fecha_registro"));
      listaComisiones.add(comision);
    }
    return listaComisiones;
  }

  @Override
  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad, String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno) {
    String qry="";
    qry+="select usuario.id_usuario ,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.nivel, unidad.id_unidad, unidad.nombre as nombre_unidad ";
    qry+="from m_comision comision right join m_usuario usuario on usuario.id_usuario=comision.id_usuario ";
    qry+="left join usuario_unidad_administrativa relacion on usuario.cve_m_usuario=relacion.cve_m_usuario ";
    qry+="left join c_unidad_administrativa unidad on unidad.id_unidad=relacion.id_unidad ";
    qry+="where unidad.id_unidad ='"+idUnidad+"' ";
    
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
    qry+= "group by usuario.id_usuario";
    
    System.out.println("sqy "+qry);
     List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry);
        List<ComisionDto> listaComisiones = new ArrayList<>();
        
        for (Map<String, Object> comisiones : consulta) {
            ComisionDto comisionDto= new ComisionDto();
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setIdUsuario((Integer)comisiones.get("id_usuario"));
            usuarioDto.setClaveUsuario((String)comisiones.get("cve_m_usuario"));
            usuarioDto.setNombre((String)comisiones.get("nombre"));
            usuarioDto.setApellidoPaterno((String)comisiones.get("apellido_paterno"));
            usuarioDto.setApellidoMaterno((String)comisiones.get("apellido_materno"));
            usuarioDto.setIdUnidad((Integer)comisiones.get("id_unidad"));
            usuarioDto.setNombreUnidad((String)comisiones.get("nombre_unidad"));
            usuarioDto.setNivel((String)comisiones.get("nivel"));
            comisionDto.setIdUsuario(usuarioDto);
            listaComisiones.add(comisionDto);
        }
        System.out.println("Numero de registros recuperados "+listaComisiones.size());
        return listaComisiones;  
  }

}
