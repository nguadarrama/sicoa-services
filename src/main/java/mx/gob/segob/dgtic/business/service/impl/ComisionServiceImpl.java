package mx.gob.segob.dgtic.business.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.ComisionRules;
import mx.gob.segob.dgtic.business.service.ComisionService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GenerarReporteArchivoComision;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.Reporte;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ComisionServiceImpl extends ServiceBase implements ComisionService {
  
  private static final String COMISIONDTO_ENVIO_LOGGER = "ComisionDto Envio: {}";
  private static final String EXCEPTION_LOGGER = "Exception: {}";
  private static final String RUTA_JASPER = "/documentos/sicoa/jasper/comision/comisiones.jrxml";
  

  @Autowired
  private ComisionRules comisionRules;

  @Override
  public List<ComisionDto> obtenerListacomisiones() {

    return comisionRules.obtenerListaComisiones();
  }

  @Override
  public ComisionDto buscaComision(Integer idComision) {

    return comisionRules.buscaComision(idComision);
  }

  @Override
  public ComisionDto modificaComision(ComisionAux comisionAux) {
    ComisionDto comisionDto = new ComisionDto();
    Date fechaInicial = null;
    Date fechaFinal = null;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    if (comisionAux.getFechaInicio() != null && !comisionAux.getFechaInicio().isEmpty()
        && comisionAux.getFechaFin() != null && !comisionAux.getFechaFin().isEmpty()) {
      try {
        fechaInicial = df.parse(comisionAux.getFechaInicio());
        fechaFinal = df.parse(comisionAux.getFechaFin());
      } catch (ParseException e) {
        logger.error(EXCEPTION_LOGGER, e.getMessage());
      }
      comisionDto.setFechaInicio(fechaInicial);
      comisionDto.setFechaFin(fechaFinal);
    }
    if (comisionAux.getComision() != null && !comisionAux.getComision().isEmpty()) {
      comisionDto.setComisionDesc(comisionAux.getComision());
    }
    comisionDto.setDias(comisionAux.getDias());
    Horario horario = new Horario();
    horario.setIdHorario(comisionAux.getIdHorario());
    comisionDto.setIdHorario(horario);
    comisionDto.setIdComision(comisionAux.getIdComision());
    
    String comisionDtoJson = gson.toJson(comisionDto);
    logger.info(COMISIONDTO_ENVIO_LOGGER, comisionDtoJson);

    return comisionRules.modificaComision(comisionDto);

  }

  @Override
  public ComisionDto agregaComision(ComisionAux comisionAux) {
    ComisionDto comisionDto = new ComisionDto();

    UsuarioDto usuarioDto = new UsuarioDto();
    usuarioDto.setIdUsuario(comisionAux.getIdUsuario());
    comisionDto.setIdUsuario(usuarioDto);

    comisionDto.setIdResponsable(comisionAux.getIdResponsable());

    EstatusDto estatusDto = new EstatusDto();
    estatusDto.setIdEstatus(comisionAux.getIdEstatus());
    comisionDto.setIdEstatus(estatusDto);
    ArchivoDto archivoDto = new ArchivoDto();
    archivoDto.setIdArchivo(null);
    comisionDto.setIdArchivo(archivoDto);

    Date fechaInicial = new Date();
    Date fechaFinal = new Date();
    Date fechaSolicitud = new Date();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    try {
      fechaInicial = df.parse(comisionAux.getFechaInicio());
      fechaFinal = df.parse(comisionAux.getFechaFin());
      fechaSolicitud = df.parse(comisionAux.getFechaRegistro());
    } catch (ParseException e) {
      logger.error(EXCEPTION_LOGGER, e.getMessage());
    }
    comisionDto.setFechaInicio(fechaInicial);
    comisionDto.setFechaFin(fechaFinal);
    comisionDto.setFechaRegistro(fechaSolicitud);
    comisionDto.setComisionDesc(comisionAux.getComision());
    comisionDto.setDias(comisionAux.getDias());
    Horario horario = new Horario();
    horario.setIdHorario(comisionAux.getIdHorario());
    comisionDto.setIdHorario(horario);
    
    String comisionDtoJson = gson.toJson(comisionDto);
    logger.info(COMISIONDTO_ENVIO_LOGGER, comisionDtoJson);
    return comisionRules.agregaComision(comisionDto);

  }

  @Override
  public void eliminaComision(Integer idComision) {

    comisionRules.eliminaComision(idComision);
  }

  @Override
  public List<ComisionDto> obtenerListaComisionPorFiltros(String claveUsuario, String fechaInicio,
      String fechaFin, String idEstatus) {
    return comisionRules.obtenerListaComisionPorFiltros(claveUsuario, fechaInicio, fechaFin,
        idEstatus);
  }

  @Override
  public List<ComisionDto> obtenerListaComisionEmpleados(String claveUsuario, String nombre,
      String apellidoPaterno, String apellidoMaterno, String idEstatus, String idUnidad) {
    return comisionRules.obtenerListaComisionPorFiltrosEmpleados(claveUsuario, nombre,
        apellidoPaterno, apellidoMaterno, idEstatus, idUnidad);
  }

  @Override
  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad, String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno) {
    return comisionRules.obtenerComisionesPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno,
        apellidoMaterno);
  }

  @Override
  public Reporte generaReporteComisiones(GenerarReporteArchivoComision generarReporteArchivo) {
    Reporte repo = new Reporte();
    byte[] output = null;
    try {
      InputStream template = null;
      File file = new File(RUTA_JASPER);
      template = new FileInputStream(file);
      JasperReport jasperReport = JasperCompileManager.compileReport(template);
      JRDataSource dataSource = new JREmptyDataSource();
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("nombre", generarReporteArchivo.getNombre());
      parametros.put("unidad", generarReporteArchivo.getUnidad());
      parametros.put("comision", generarReporteArchivo.getComision());
      DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
      Date fechaInicio = null;
      Date fechaFin = null;

      if (generarReporteArchivo.getFechaInicio() != null) {
        fechaInicio = df.parse(generarReporteArchivo.getFechaInicio());
      }
      if (generarReporteArchivo.getFechaFinal() != null) {
        fechaFin = df.parse(generarReporteArchivo.getFechaFinal());
      }
      parametros.put("fechaInicio", fechaInicio);
      parametros.put("fechaFinal", fechaFin);
      parametros.put("horario", generarReporteArchivo.getHorario());

      String generarReporteArchivoJson = gson.toJson(generarReporteArchivo);
      logger.info("GenerarReporteArchivo: {}", generarReporteArchivoJson);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
      output = JasperExportManager.exportReportToPdf(jasperPrint);
      repo.setNombre(output);
    } catch (JRException | ParseException | FileNotFoundException e) {
      logger.error(EXCEPTION_LOGGER, e.getMessage());
    }

    return repo;

  }

  @Override
  public ComisionDto modificaComisionEstatusArchivo(ComisionAux comisionAuxDto) {
    ComisionDto comisionDto = new ComisionDto();
    EstatusDto estatus = new EstatusDto();
    estatus.setIdEstatus(comisionAuxDto.getIdEstatus());
    comisionDto.setIdEstatus(estatus);
    UsuarioDto usuario = new UsuarioDto();
    usuario.setIdUsuario(comisionAuxDto.getIdUsuario());
    comisionDto.setIdUsuario(usuario);
    ArchivoDto archivo = new ArchivoDto();
    archivo.setIdArchivo(comisionAuxDto.getIdArchivo());
    comisionDto.setIdArchivo(archivo);
    comisionDto.setIdComision(comisionAuxDto.getIdComision());
    
    String comisionDtoJson = gson.toJson(comisionDto);
    logger.info(COMISIONDTO_ENVIO_LOGGER, comisionDtoJson);
    return comisionRules.modificaComisionEstatusArchivo(comisionDto);
  }

}
