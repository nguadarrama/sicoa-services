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
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class ComisionRepositoryImpl extends RecursoBase implements ComisionRepository {

  private static final String COLUMNA_ID_COMISION = "id_comision";
  private static final String COLUMNA_ID_USUARIO = "id_usuario";
  private static final String COLUMNA_ID_RESPONSABLE = "id_responsable";
  private static final String COLUMNA_ID_ARCHIVO = "id_archivo";
  private static final String COLUMNA_URL = "url";
  private static final String COLUMNA_NOMBRE_ARCHIVO = "nombre_archivo";
  private static final String COLUMNA_ID_ESTATUS = "id_estatus";
  private static final String COLUMNA_FECHA_INICIO = "fecha_inicio";
  private static final String COLUMNA_FECHA_FIN = "fecha_fin";
  private static final String COLUMNA_FECHA_REGISTRO = "fecha_registro";
  private static final String COLUMNA_DIAS = "dias";
  private static final String COLUMNA_COMISION = "comision";
  private static final String COLUMNA_ID_HORARIO = "id_horario";

  private static final String COLUMNA_CVE_M_USUARIO = "cve_m_usuario";
  private static final String COLUMNA_NOMBRE = "nombre";
  private static final String COLUMNA_APELLIDO_PATERNO = "apellido_paterno";
  private static final String COLUMNA_APELLIDO_MATERNO = "apellido_materno";
  private static final String COLUMNA_ID_PUESTO = "id_puesto";
  private static final String COLUMNA_FECHA_INGRESO = "fecha_ingreso";
  private static final String COLUMNA_RFC = "rfc";
  private static final String COLUMNA_ID_UNIDAD = "id_unidad";
  private static final String COLUMNA_NOMBRE_UNIDAD = "nombre_unidad";
  private static final String COLUMNA_ESTATUS = "estatus";
  private static final String COLUMNA_NIVEL = "nivel";

  private static final String QUERY_LOGGER = "Query: {}";
  private static final String EXCEPTION_LOGGER = "Exception: {}";
  private static final String MENSAJE_EXITO = "El registro de comisión se actualizó correctamente.";
  private static final String MENSAJE_ERROR = "Se ha generado un error al actualizar comisión, revise la información";
  
  private static final String PARAMETRO_COMISION = "comision";
  private static final String PARAMETRO_ID_COMISION = "idComision";
  private static final String PARAMETRO_FECHA_INICIO = "fechaInicio";
  private static final String PARAMETRO_FECHA_FIN = "fechaFin";
  private static final String PARAMETRO_DIAS = "dias";
  private static final String PARAMETRO_ID_HORARIO = "idHorario";
  private static final String PARAMETRO_ID_ESTATUS = "idEstatus";
  private static final String PARAMETRO_ID_ARCHIVO = "idArchivo";
  private static final String PARAMETRO_ID_USUARIO = "idUsuario";
  private static final String PARAMETRO_ID_RESPONSABLE = "idResponsable";
  private static final String PARAMETRO_FECHA_REGISTRO = "fechaRegistro";
  private static final String FROM_M_COMISION_COMISION = "FROM m_comision comision ";
  private static final String UNIDAD_ADMINISTRATIVA = "unidad_administrativa";
  private static final String AND = "' and '";

  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

  @Override
  public List<ComisionDto> obtenerListaComisiones() {
    StringBuilder qry = new StringBuilder();
    qry.append(
        "SELECT id_comision, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, comision, id_horario");
    qry.append("FROM m_comision ");
    logger.info(QUERY_LOGGER, qry);

    List<Map<String, Object>> comisiones = jdbcTemplate.queryForList(qry.toString());
    List<ComisionDto> listacomision = new ArrayList<>();

    for (Map<String, Object> comision : comisiones) {
      ComisionDto comisionDto = new ComisionDto();
      comisionDto.setIdComision((Integer) comision.get(COLUMNA_ID_COMISION));
      UsuarioDto usuarioDto = new UsuarioDto();
      usuarioDto.setIdUsuario((Integer) comision.get(COLUMNA_ID_USUARIO));
      comisionDto.setIdUsuario(usuarioDto);
      comisionDto.setIdResponsable((Integer) comision.get(COLUMNA_ID_RESPONSABLE));
      ArchivoDto archivoDto = new ArchivoDto();
      archivoDto.setIdArchivo((Integer) comision.get(COLUMNA_ID_ARCHIVO));
      comisionDto.setIdArchivo(archivoDto);
      EstatusDto estatusDto = new EstatusDto();
      estatusDto.setIdEstatus((Integer) comision.get(COLUMNA_ID_ESTATUS));
      comisionDto.setIdEstatus(estatusDto);
      comisionDto.setFechaInicio((Date) comision.get(COLUMNA_FECHA_INICIO));
      comisionDto.setFechaFin((Date) comision.get(COLUMNA_FECHA_FIN));
      comisionDto.setDias((Integer) comision.get(COLUMNA_DIAS));
      comisionDto.setComisionDesc((String) comision.get(COLUMNA_COMISION));
      Horario horario = new Horario();
      horario.setIdHorario((Integer) comision.get(COLUMNA_ID_HORARIO));
      comisionDto.setIdHorario(horario);

      listacomision.add(comisionDto);
    }
    return listacomision;
  }

  @Override
  public ComisionDto buscaComision(Integer idComision) {
    StringBuilder qry = new StringBuilder();
    qry.append(
        "SELECT comision.id_comision, comision.fecha_registro, comision.id_responsable, comision.fecha_registro, comision.id_usuario, comision.id_archivo, ");
    qry.append(
        "comision.id_estatus, comision.fecha_inicio, comision.fecha_fin, comision.dias, comision.fecha_registro, comision.comision, usuario.cve_m_usuario, ");
    qry.append("usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.rfc, ");
    qry.append(
        "usuario.id_puesto, usuario.fecha_ingreso, estatus.id_estatus, estatus.estatus, unidad.id_unidad, unidad.nombre nombre_unidad, horario.id_horario ");
    qry.append(FROM_M_COMISION_COMISION);
    qry.append("RIGHT JOIN m_usuario usuario ON usuario.id_usuario=comision.id_usuario ");
    qry.append("LEFT JOIN m_estatus estatus ON estatus.id_estatus=comision.id_estatus ");
    qry.append(
        "LEFT JOIN usuario_unidad_administrativa relacion ON usuario.cve_m_usuario=relacion.cve_m_usuario ");
    qry.append("LEFT JOIN c_unidad_administrativa unidad ON unidad.id_unidad=relacion.id_unidad ");
    qry.append("LEFT JOIN c_horario horario ON horario.id_horario = comision.id_horario ");
    qry.append("WHERE comision.id_comision = :idComision ");

    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue(PARAMETRO_ID_COMISION, idComision);
    logger.info(QUERY_LOGGER, qry);

    Map<String, Object> informacionConsulta =
        nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
    ComisionDto comisionDto = new ComisionDto();
    comisionDto.setIdComision((Integer) informacionConsulta.get(COLUMNA_ID_COMISION));
    comisionDto.setIdResponsable((Integer) informacionConsulta.get(COLUMNA_ID_RESPONSABLE));
    UsuarioDto usuarioDto = new UsuarioDto();
    usuarioDto.setIdUsuario((Integer) informacionConsulta.get(COLUMNA_ID_USUARIO));
    usuarioDto.setClaveUsuario((String) informacionConsulta.get(COLUMNA_CVE_M_USUARIO));
    logger.info("Clave usuario: {}", informacionConsulta.get(COLUMNA_CVE_M_USUARIO));
    usuarioDto.setNombre((String) informacionConsulta.get(COLUMNA_NOMBRE));
    usuarioDto.setApellidoPaterno((String) informacionConsulta.get(COLUMNA_APELLIDO_PATERNO));
    usuarioDto.setApellidoMaterno((String) informacionConsulta.get(COLUMNA_APELLIDO_MATERNO));
    usuarioDto.setIdPuesto((String) informacionConsulta.get(COLUMNA_ID_PUESTO));
    usuarioDto.setFechaIngreso((Date) informacionConsulta.get(COLUMNA_FECHA_INGRESO));
    logger.info("Fecha ingreso usuario: {}", usuarioDto.getFechaIngreso());
    usuarioDto.setRfc((String) informacionConsulta.get(COLUMNA_RFC));
    usuarioDto.setIdUnidad((Integer) informacionConsulta.get(COLUMNA_ID_UNIDAD));
    usuarioDto.setNombreUnidad((String) informacionConsulta.get(COLUMNA_NOMBRE_UNIDAD));
    comisionDto.setIdUsuario(usuarioDto);
    ArchivoDto archivoDto = new ArchivoDto();
    archivoDto.setIdArchivo((Integer) informacionConsulta.get(COLUMNA_ID_ARCHIVO));
    comisionDto.setIdArchivo(archivoDto);
    EstatusDto estatusDto = new EstatusDto();
    estatusDto.setIdEstatus((Integer) informacionConsulta.get(COLUMNA_ID_ESTATUS));
    logger.info("Id estatus: {}", informacionConsulta.get(COLUMNA_ID_ESTATUS));
    estatusDto.setEstatus((String) informacionConsulta.get(COLUMNA_ESTATUS));
    comisionDto.setIdEstatus(estatusDto);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaIni = "" + informacionConsulta.get(COLUMNA_FECHA_INICIO);
    String fechaFin = "" + informacionConsulta.get(COLUMNA_FECHA_FIN);
    String fechaRe = "" + informacionConsulta.get(COLUMNA_FECHA_REGISTRO);
    Date fechaRegistro = null;
    Date fechaInicio = null;
    Date fechaFinal = null;
    try {
      fechaInicio = sdf.parse(fechaIni);
      fechaFinal = sdf.parse(fechaFin);
      fechaRegistro = sdf.parse(fechaRe);
    } catch (ParseException e) {
      logger.error(EXCEPTION_LOGGER, e.getMessage());
    }
    comisionDto.setFechaInicio(fechaInicio);
    comisionDto.setFechaFin(fechaFinal);
    comisionDto.setFechaRegistro(fechaRegistro);
    comisionDto.setComisionDesc((String) informacionConsulta.get(COLUMNA_COMISION));
    Horario idHorario = new Horario();
    idHorario.setIdHorario((Integer) informacionConsulta.get(COLUMNA_ID_HORARIO));
    comisionDto.setIdHorario(idHorario);
    comisionDto.setDias((Integer) informacionConsulta.get(COLUMNA_DIAS));

    return comisionDto;

  }

  @Override
  public ComisionDto modificaComision(ComisionDto comisionDto) {
    StringBuilder qry = new StringBuilder();
    Integer i = 0;
    qry.append(
        "UPDATE m_comision SET fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, dias = :dias, comision = :comision, id_horario = :idHorario ");
    qry.append("WHERE id_comision = :idComision");

    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue(PARAMETRO_ID_COMISION, comisionDto.getIdComision());
    parametros.addValue(PARAMETRO_FECHA_INICIO, comisionDto.getFechaInicio());
    parametros.addValue(PARAMETRO_FECHA_FIN, comisionDto.getFechaFin());
    parametros.addValue(PARAMETRO_DIAS, comisionDto.getDias());
    parametros.addValue(PARAMETRO_COMISION, comisionDto.getComisionDesc());
    parametros.addValue(PARAMETRO_ID_HORARIO, comisionDto.getIdHorario().getIdHorario());
    logger.info(QUERY_LOGGER, qry);
    try {
      i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
    } catch (Exception e) {
      logger.error(EXCEPTION_LOGGER, e.getMessage());
    }
    if (i == 1) {
      comisionDto.setMensaje(MENSAJE_EXITO);
    } else {
      comisionDto
          .setMensaje(MENSAJE_ERROR);
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
    parametros.addValue(PARAMETRO_ID_COMISION, comisionDto.getIdComision());
    parametros.addValue(PARAMETRO_ID_ESTATUS, comisionDto.getIdEstatus().getIdEstatus());
    parametros.addValue(PARAMETRO_ID_ARCHIVO, comisionDto.getIdArchivo().getIdArchivo());
    logger.info(QUERY_LOGGER, qry);
    try {
      i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
    } catch (Exception e) {
      logger.error(EXCEPTION_LOGGER, e.getMessage());
    }
    if (i == 1) {
      comisionDto.setMensaje(MENSAJE_EXITO);
    } else {
      comisionDto
          .setMensaje(MENSAJE_ERROR);
    }

    return comisionDto;
  }


  @Override
  public ComisionDto agregaComision(ComisionDto comisionDto) {
    logger.info("Agregando comision IdUsuario: {}", comisionDto.getIdUsuario().getIdUsuario());
    Integer i = 0;
    StringBuilder qry = new StringBuilder();
    qry.append(
        "INSERT INTO m_comision (id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio,fecha_fin, dias, comision, fecha_registro, id_horario) ");
    qry.append(
        "VALUES (:idUsuario, :idResponsable, :idArchivo, :idEstatus, :fechaInicio, :fechaFin, :dias, :comision, :fechaRegistro, :idHorario) ");

    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue(PARAMETRO_ID_USUARIO, comisionDto.getIdUsuario().getIdUsuario());
    parametros.addValue(PARAMETRO_ID_RESPONSABLE, comisionDto.getIdResponsable());
    parametros.addValue(PARAMETRO_ID_ARCHIVO, comisionDto.getIdArchivo().getIdArchivo());
    parametros.addValue(PARAMETRO_ID_ESTATUS, comisionDto.getIdEstatus().getIdEstatus());
    parametros.addValue(PARAMETRO_FECHA_INICIO, comisionDto.getFechaInicio());
    parametros.addValue(PARAMETRO_FECHA_FIN, comisionDto.getFechaFin());
    parametros.addValue(PARAMETRO_DIAS, comisionDto.getDias());
    parametros.addValue(PARAMETRO_COMISION, comisionDto.getComisionDesc());
    parametros.addValue(PARAMETRO_FECHA_REGISTRO, comisionDto.getFechaRegistro());
    parametros.addValue(PARAMETRO_ID_HORARIO, comisionDto.getIdHorario().getIdHorario());
    logger.info(QUERY_LOGGER, qry);
    try {
      i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
    } catch (Exception e) {
      logger.error(EXCEPTION_LOGGER, e.getMessage());
    }
    if (i == 1) {
      comisionDto.setMensaje(MENSAJE_EXITO);
    } else {
      comisionDto.setMensaje(MENSAJE_ERROR);
    }
    return comisionDto;
  }

  @Override
  public void eliminaComision(Integer idComision) {
    StringBuilder qry = new StringBuilder();
    qry.append("DELETE FROM m_comision  WHERE id_comision = :idComision");

    MapSqlParameterSource parametros = new MapSqlParameterSource();
    parametros.addValue(PARAMETRO_ID_COMISION, idComision);
    logger.info(QUERY_LOGGER, qry);
    nameParameterJdbcTemplate.update(qry.toString(), parametros);

  }

  @Override
  public List<ComisionDto> obtenerListaComisionPorFiltros(String claveUsuario, String fechaInicio,
      String fechaFin, String idEstatus) {
    StringBuilder qry2 = new StringBuilder();
    qry2.append("SELECT usuario.id_usuario, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, comision.id_comision, ");
    qry2.append("comision.id_responsable, archivo.id_archivo, archivo.nombre as nombre_archivo, archivo.url, estatus.id_estatus, estatus.estatus, comision.fecha_inicio, ");
    qry2.append("comision.fecha_fin, comision.dias, comision.comision, comision.dias, comision.fecha_registro ");
    qry2.append(FROM_M_COMISION_COMISION);
    qry2.append("LEFT JOIN m_usuario usuario ON usuario.id_usuario = comision.id_usuario ");
    qry2.append("LEFT JOIN m_archivo archivo ON archivo.id_archivo = comision.id_archivo ");
    qry2.append("LEFT JOIN m_estatus estatus ON estatus.id_estatus = comision.id_estatus ");
    qry2.append("WHERE ");
    qry2.append("comision.id_usuario = '" + claveUsuario + "' ");
    
    if (idEstatus != null && !idEstatus.trim().isEmpty() && !idEstatus.equals("null")) {
      qry2.append("and comision.id_estatus = '" + idEstatus + "' ");
    }
    if ((fechaInicio != null && !fechaInicio.trim().isEmpty())
        && (fechaFin != null && !fechaFin.trim().isEmpty())) {
      qry2.append("and comision.fecha_inicio between '" + fechaInicio + AND + fechaFin + "' ");
    } else if (fechaInicio != null && !fechaInicio.trim().isEmpty()) {
      qry2.append("and comision.fecha_inicio='" + fechaInicio + "'");
    } else if (fechaFin != null && !fechaFin.trim().isEmpty()) {
      qry2.append("and comision.fecha_fin='" + fechaInicio + "'");
    }

    logger.info(QUERY_LOGGER, qry2);
    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry2.toString());
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      ComisionDto comision = new ComisionDto();
      UsuarioDto usuario = new UsuarioDto();
      usuario.setIdUsuario((Integer) comisiones.get(COLUMNA_ID_USUARIO));
      usuario.setClaveUsuario((String) comisiones.get(COLUMNA_CVE_M_USUARIO));
      usuario.setNombre((String) comisiones.get(COLUMNA_NOMBRE));
      usuario.setApellidoPaterno((String) comisiones.get(COLUMNA_APELLIDO_PATERNO));
      usuario.setApellidoMaterno((String) comisiones.get(COLUMNA_APELLIDO_MATERNO));
      comision.setIdUsuario(usuario);
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus((Integer) comisiones.get(COLUMNA_ID_ESTATUS));
      estatus.setEstatus((String) comisiones.get(COLUMNA_ESTATUS));
      comision.setIdEstatus(estatus);
      ArchivoDto archivo = new ArchivoDto();
      archivo.setIdArchivo((Integer) comisiones.get(COLUMNA_ID_ARCHIVO));
      archivo.setUrl((String) comisiones.get(COLUMNA_URL));
      archivo.setNombre((String) comisiones.get(COLUMNA_NOMBRE_ARCHIVO));
      comision.setIdArchivo(archivo);
      comision.setIdComision((Integer) comisiones.get(COLUMNA_ID_COMISION));
      logger.info("Dato recuperado: {}", comisiones.get(COLUMNA_ID_COMISION));
      comision.setDias((Integer) comisiones.get(COLUMNA_DIAS));
      comision.setFechaFin((Date) comisiones.get(COLUMNA_FECHA_FIN));
      comision.setFechaInicio((Date) comisiones.get(COLUMNA_FECHA_INICIO));
      comision.setIdResponsable((Integer) comisiones.get(COLUMNA_ID_RESPONSABLE));
      comision.setComisionDesc((String) comisiones.get(COLUMNA_COMISION));
      comision.setFechaRegistro((Date) comisiones.get(COLUMNA_FECHA_REGISTRO));
      listaComisiones.add(comision);
    }
    return listaComisiones;
  }

  @Override
  public List<ComisionDto> obtenerListaComisionPorFiltrosEmpleados(String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno, String idEstatus,
      String idUnidad) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT usuario.id_usuario,  usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, ");
    query.append("relacion.cve_m_usuario, relacion.id_unidad, unidad.nombre nombre_unidad, comision.id_comision, ");
    query.append("comision.id_responsable, comision.id_estatus, estatus.estatus, comision.fecha_inicio, ");
    query.append("comision.fecha_fin, comision.dias, comision.comision, comision.dias, comision.fecha_registro ");
    query.append(FROM_M_COMISION_COMISION);
    query.append("INNER JOIN m_usuario usuario ON usuario.id_usuario = comision.id_usuario ");
    query.append("INNER JOIN usuario_unidad_administrativa relacion on usuario.cve_m_usuario=relacion.cve_m_usuario ");
    query.append("INNER JOIN m_estatus estatus ON estatus.id_estatus = comision.id_estatus ");
    query.append("INNER JOIN c_unidad_administrativa unidad ON unidad.id_unidad = relacion.id_unidad ");

    if (idUnidad != null && !idUnidad.trim().isEmpty()) {
      query.append(" where unidad.id_unidad ='" + idUnidad + "' ");
    }
    
    if (claveUsuario != null && !claveUsuario.trim().isEmpty()) {
      query.append("and usuario.cve_m_usuario like '%" + removerGuionBajo(claveUsuario) + "%' ");
    }
    if (nombre != null && !nombre.trim().isEmpty()) {
      query.append("and usuario.nombre like '%" + removerGuionBajo(nombre) + "%' ");
    }
    if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty()) {
      query.append("and usuario.apellido_paterno like '%" + removerGuionBajo(apellidoPaterno) + "%' ");
    }
    if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
      query.append("and usuario.apellido_materno like '%" + removerGuionBajo(apellidoMaterno) + "%' ");
    }
    if (idUnidad != null && !idUnidad.trim().isEmpty()) {
      query.append("and unidad.id_unidad='" + idUnidad + "' ");
    }

    if (idEstatus != null && !idEstatus.trim().isEmpty()) {
      query.append("and estatus.id_estatus='" + idEstatus + "' ");
    }

    logger.info(QUERY_LOGGER, query);
    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(query.toString());
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      ComisionDto comision = new ComisionDto();
      UsuarioDto usuario = new UsuarioDto();
      usuario.setIdUsuario((Integer) comisiones.get(COLUMNA_ID_USUARIO));
      usuario.setClaveUsuario((String) comisiones.get(COLUMNA_CVE_M_USUARIO));
      usuario.setNombre((String) comisiones.get(COLUMNA_NOMBRE));
      usuario.setApellidoPaterno((String) comisiones.get(COLUMNA_APELLIDO_PATERNO));
      usuario.setApellidoMaterno((String) comisiones.get(COLUMNA_APELLIDO_MATERNO));
      usuario.setNombreUnidad((String) comisiones.get(COLUMNA_NOMBRE_UNIDAD));
      usuario.setIdUnidad((Integer) comisiones.get(COLUMNA_ID_UNIDAD));
      comision.setIdUsuario(usuario);
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus((Integer) comisiones.get(COLUMNA_ID_ESTATUS));
      estatus.setEstatus((String) comisiones.get(COLUMNA_ESTATUS));
      comision.setIdEstatus(estatus);
      ArchivoDto archivo = new ArchivoDto();
      comision.setIdArchivo(archivo);
      comision.setIdComision((Integer) comisiones.get(COLUMNA_ID_COMISION));
      comision.setDias((Integer) comisiones.get(COLUMNA_DIAS));
      comision.setFechaFin((Date) comisiones.get(COLUMNA_FECHA_FIN));
      comision.setFechaInicio((Date) comisiones.get(COLUMNA_FECHA_INICIO));
      comision.setIdResponsable((Integer) comisiones.get(COLUMNA_ID_RESPONSABLE));
      comision.setComisionDesc((String) comisiones.get(COLUMNA_COMISION));
      comision.setFechaRegistro((Date) comisiones.get(COLUMNA_FECHA_REGISTRO));
      listaComisiones.add(comision);
    }
    return listaComisiones;
  }

  @Override
  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad, String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno) {
    StringBuilder query = new StringBuilder();
    query.append("select usuario.id_usuario ,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.nivel, unidad.id_unidad, unidad.nombre as nombre_unidad ");
    query.append("from m_comision comision right join m_usuario usuario on usuario.id_usuario=comision.id_usuario ");
    query.append("left join usuario_unidad_administrativa relacion on usuario.cve_m_usuario=relacion.cve_m_usuario ");
    query.append("left join c_unidad_administrativa unidad on unidad.id_unidad=relacion.id_unidad ");
    query.append("where unidad.id_unidad ='" + idUnidad + "' ");
    
    if (claveUsuario != null && !claveUsuario.trim().isEmpty()) {
      query.append("and usuario.cve_m_usuario like '%" + removerGuionBajo(claveUsuario) + "%' ");
    }
    if (nombre != null && !nombre.trim().isEmpty()) {
      query.append("and usuario.nombre like '%" + removerGuionBajo(nombre) + "%' ");
    }
    if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty()) {
      query.append("and usuario.apellido_paterno like '%" + removerGuionBajo(apellidoPaterno) + "%' ");
    }
    if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
      query.append("and usuario.apellido_materno like '%" + removerGuionBajo(apellidoMaterno) + "%' ");
    }
    query.append("group by usuario.id_usuario");

    logger.info(QUERY_LOGGER, query);
    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(query.toString());
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      ComisionDto comisionDto = new ComisionDto();
      UsuarioDto usuarioDto = new UsuarioDto();
      usuarioDto.setIdUsuario((Integer) comisiones.get(COLUMNA_ID_USUARIO));
      usuarioDto.setClaveUsuario((String) comisiones.get(COLUMNA_CVE_M_USUARIO));
      usuarioDto.setNombre((String) comisiones.get(COLUMNA_NOMBRE));
      usuarioDto.setApellidoPaterno((String) comisiones.get(COLUMNA_APELLIDO_PATERNO));
      usuarioDto.setApellidoMaterno((String) comisiones.get(COLUMNA_APELLIDO_MATERNO));
      usuarioDto.setIdUnidad((Integer) comisiones.get(COLUMNA_ID_UNIDAD));
      usuarioDto.setNombreUnidad((String) comisiones.get(COLUMNA_NOMBRE_UNIDAD));
      usuarioDto.setNivel((String) comisiones.get(COLUMNA_NIVEL));
      comisionDto.setIdUsuario(usuarioDto);
      listaComisiones.add(comisionDto);
    }
    logger.info("Número de registros recuperados: {}", listaComisiones.size());
    return listaComisiones;
  }

  @Override
  public List<ComisionDto> obtenerComisionesPorUsuarioRango(Integer idUsuario, Date fechaInicio,
      Date fechaFin) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT comision.id_comision, comision.id_usuario, comision.id_estatus, comision.fecha_inicio, ");
    query.append("comision.fecha_fin, comision.dias ");
    query.append(FROM_M_COMISION_COMISION);
    query.append("WHERE ((comision.fecha_inicio <= '" + fechaInicio + "' AND comision.fecha_fin >= '" + fechaFin + "') ");
    query.append("OR comision.fecha_fin BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ");
    query.append("OR comision.fecha_inicio BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "') ");
    query.append("AND comision.id_usuario = '" + idUsuario + "' AND id_estatus IN (1,2)");
    
    logger.info(QUERY_LOGGER, query);
    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(query.toString());
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      ComisionDto comisionDto = new ComisionDto();
      comisionDto.setIdComision((Integer) comisiones.get(COLUMNA_ID_COMISION));
      UsuarioDto usuarioDto = new UsuarioDto();
      usuarioDto.setIdUsuario((Integer) comisiones.get(COLUMNA_ID_USUARIO));
      comisionDto.setIdUsuario(usuarioDto);
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus((Integer) comisiones.get(COLUMNA_ID_ESTATUS));
      comisionDto.setIdEstatus(estatus);
      comisionDto.setFechaInicio((Date) comisiones.get(COLUMNA_FECHA_INICIO));
      comisionDto.setFechaFin((Date) comisiones.get(COLUMNA_FECHA_FIN));
      comisionDto.setDias((Integer) comisiones.get(COLUMNA_DIAS));
      listaComisiones.add(comisionDto);
    }
    logger.info("Número de registros recuperados: {}", listaComisiones.size());
    return listaComisiones;
  }
  
  private String removerGuionBajo(String string) {
    return string.replace("_", " ");
  }
  
  @Override
	public List<ComisionDto> buscaComisionReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		StringBuilder qry = new StringBuilder();
	       
      qry.append("select u.cve_m_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.nivel, "); 
		qry.append("ua.nombre as unidad_administrativa, c.id_estatus, c.fecha_inicio, c.fecha_fin, e.estatus ");
		qry.append("from m_comision c ");
		qry.append("inner join m_usuario u on c.id_usuario = u.id_usuario ");
		qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
		qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
		qry.append("left join m_estatus e on e.id_estatus = c.id_estatus ");
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
  		qry.append(" and c.fecha_inicio between '" + asistenciaBusquedaUtil.getFechaInicialDate() + AND + asistenciaBusquedaUtil.getFechaFinalDate() + "'");

      }

      List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry.toString());
      List<ComisionDto> listaComisiones = new ArrayList<>();

      for (Map<String, Object> comisiones : consulta) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdUsuario((Integer) comisiones.get(COLUMNA_ID_USUARIO));
        usuarioDto.setClaveUsuario((String) comisiones.get(COLUMNA_CVE_M_USUARIO));
        usuarioDto.setNombre((String) comisiones.get(COLUMNA_NOMBRE));
        usuarioDto.setApellidoPaterno((String) comisiones.get(COLUMNA_APELLIDO_PATERNO));
        usuarioDto.setApellidoMaterno((String) comisiones.get(COLUMNA_APELLIDO_MATERNO));
        usuarioDto.setNivel((String) comisiones.get(COLUMNA_NIVEL));
        usuarioDto.setNombreUnidad((String) comisiones.get(UNIDAD_ADMINISTRATIVA));
        
        EstatusDto estatus = new EstatusDto();
        estatus.setIdEstatus((Integer) comisiones.get(COLUMNA_ID_ESTATUS));
        estatus.setEstatus((String) comisiones.get(RepositoryConstants.ESTATUS));
        
        ComisionDto comisionDto = new ComisionDto();
        comisionDto.setIdUsuario(usuarioDto);
        comisionDto.setIdEstatus(estatus);
        comisionDto.setFechaInicio((Date) comisiones.get(COLUMNA_FECHA_INICIO));
        comisionDto.setFechaFin((Date) comisiones.get(COLUMNA_FECHA_FIN));
        comisionDto.setDias((Integer) comisiones.get(COLUMNA_DIAS));
        
        
        listaComisiones.add(comisionDto);
      }

      return listaComisiones;
	}
  
  @Override
	public List<ComisionDto> buscaComisionReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
		StringBuilder qry = new StringBuilder();
	       
    qry.append("select u.cve_m_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.nivel, "); 
		qry.append("ua.nombre as unidad_administrativa, c.id_estatus, c.fecha_inicio, c.fecha_fin, e.estatus ");
		qry.append("from m_comision c ");
		qry.append("inner join m_usuario u on c.id_usuario = u.id_usuario ");
		qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
		qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
		qry.append("left join m_estatus e on e.id_estatus = c.id_estatus ");
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
		qry.append(" and c.fecha_inicio between '" + asistenciaBusquedaUtil.getFechaInicialDate() + AND + asistenciaBusquedaUtil.getFechaFinalDate() + "'");

    }

    List<Map<String, Object>> consulta = jdbcTemplate.queryForList(qry.toString());
    List<ComisionDto> listaComisiones = new ArrayList<>();

    for (Map<String, Object> comisiones : consulta) {
      UsuarioDto usuarioDto = new UsuarioDto();
      usuarioDto.setIdUsuario((Integer) comisiones.get(COLUMNA_ID_USUARIO));
      usuarioDto.setClaveUsuario((String) comisiones.get(COLUMNA_CVE_M_USUARIO));
      usuarioDto.setNombre((String) comisiones.get(COLUMNA_NOMBRE));
      usuarioDto.setApellidoPaterno((String) comisiones.get(COLUMNA_APELLIDO_PATERNO));
      usuarioDto.setApellidoMaterno((String) comisiones.get(COLUMNA_APELLIDO_MATERNO));
      usuarioDto.setNivel((String) comisiones.get(COLUMNA_NIVEL));
      usuarioDto.setNombreUnidad((String) comisiones.get(UNIDAD_ADMINISTRATIVA));
      
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus((Integer) comisiones.get(COLUMNA_ID_ESTATUS));
      estatus.setEstatus((String) comisiones.get(RepositoryConstants.ESTATUS));
      
      ComisionDto comisionDto = new ComisionDto();
      comisionDto.setIdUsuario(usuarioDto);
      comisionDto.setIdEstatus(estatus);
      comisionDto.setFechaInicio((Date) comisiones.get(COLUMNA_FECHA_INICIO));
      comisionDto.setFechaFin((Date) comisiones.get(COLUMNA_FECHA_FIN));
      comisionDto.setDias((Integer) comisiones.get(COLUMNA_DIAS));
      
      
      listaComisiones.add(comisionDto);
    }

    return listaComisiones;
	}

}
