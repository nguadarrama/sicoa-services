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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.ComisionRules;
import mx.gob.segob.dgtic.business.service.ComisionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GenerarReporteArchivoComision;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.reporte;
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
public class ComisionServiceImpl implements ComisionService {

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
  public void modificaComision(ComisionAux comisionAux) {
    System.out.println("ComisionAux service back " + ReflectionToStringBuilder.toString(comisionAux));
    ComisionDto comisionDto = new ComisionDto();
    Date fechaInicial = new Date();
    Date fechaFinal = new Date();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    if (comisionAux.getFechaInicio() != null && !comisionAux.getFechaInicio().isEmpty()
        && comisionAux.getFechaFin() != null && !comisionAux.getFechaFin().isEmpty()) {
      try {
        fechaInicial = df.parse(comisionAux.getFechaInicio());
        fechaFinal = df.parse(comisionAux.getFechaFin());
        System.out.println("fechaInicio " + fechaInicial);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      comisionDto.setFechaInicio(fechaInicial);
      comisionDto.setFechaFin(fechaFinal);
    }
    if (comisionAux.getDias() != null && comisionAux.getIdHorario() != null) {
      comisionDto.setDias(comisionAux.getDias());
      Horario horario = new Horario();
      horario.setIdHorario(comisionAux.getIdHorario());
    }
    if (comisionAux.getComision() != null && !comisionAux.getComision().isEmpty()) {
      comisionDto.setComision(comisionAux.getComision());
    }
    if (comisionAux.getIdEstatus() != null) {
      EstatusDto estatus = new EstatusDto();
      estatus.setIdEstatus(comisionAux.getIdEstatus());
      comisionDto.setIdEstatus(estatus);
    }

    if (comisionAux.getIdComision() != null) {
      comisionDto.setIdComision(comisionAux.getIdComision());
    }
    if (comisionAux.getIdArchivo() != null) {
      ArchivoDto archivo = new ArchivoDto();
      archivo.setIdArchivo(comisionAux.getIdArchivo());
      comisionDto.setIdArchivo(archivo);
    }

    
    System.out.println("ComisionDTO service back envio" + ReflectionToStringBuilder.toString(comisionDto));
    comisionRules.modificaComision(comisionDto);

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
      fechaFinal=df.parse(comisionAux.getFechaFin());
      fechaSolicitud=df.parse(comisionAux.getFechaRegistro());
      System.out.println("fechaInicio "+fechaInicial);
  } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
  }
    comisionDto.setFechaInicio(fechaInicial);
    comisionDto.setFechaFin(fechaFinal);
    comisionDto.setFechaRegistro(fechaSolicitud);
    comisionDto.setComision(comisionAux.getComision());
    comisionDto.setDias(comisionAux.getDias());
    Horario horario = new Horario();
    horario.setIdHorario(comisionAux.getIdHorario());
    comisionDto.setIdHorario(horario);
    System.out.println("Enviar a rules ");
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
    return comisionRules.obtenerComisionesPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno, apellidoMaterno);
  }

  @Override
  public reporte generaReporteComisiones(GenerarReporteArchivoComision generarReporteArchivo) {
    reporte repo = new reporte();
    byte[] output = null;
    try {
      InputStream template = null;
      try {
		File file = new File("/documentos/sicoa/jasper/comision/comisiones.jrxml");
        template = new FileInputStream(file);
      } catch (FileNotFoundException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      if (template != null) {
        JasperReport jasperReport = JasperCompileManager.compileReport(template);
        System.out.println("Datos " + generarReporteArchivo.getNombre());
        JRDataSource dataSource = new JREmptyDataSource();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("nombre", generarReporteArchivo.getNombre());
        parametros.put("unidad", generarReporteArchivo.getUnidad());
        parametros.put("comision", generarReporteArchivo.getComision());
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        Date fechaInicio = null;
        Date fechaFin = null;
        
        try {
          if(generarReporteArchivo.getFechaInicio()!= null){
              fechaInicio= df.parse(generarReporteArchivo.getFechaInicio());
          }
          if(generarReporteArchivo.getFechaFinal()!= null){
              fechaFin= df.parse(generarReporteArchivo.getFechaFinal());
          }
      } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
        parametros.put("fechaInicio", fechaInicio);
        parametros.put("fechaFinal", fechaFin);
        parametros.put("horario", generarReporteArchivo.getHorario());

        System.out.println("Parametros para el archivo " + generarReporteArchivo.getNombre()
            + " unidad " + generarReporteArchivo.getUnidad() + " comision "
            + generarReporteArchivo.getComision() + " fechaInicio "
            + generarReporteArchivo.getFechaInicio());
        JasperPrint jasperPrint =
            JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        output = JasperExportManager.exportReportToPdf(jasperPrint);
        repo.setNombre(output);
      }
    } catch (JRException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return repo;

  }

  @Override
  public ComisionDto modificaComisionEstatusArchivo(ComisionAux comisionAuxDto) {
    ComisionDto comisionDto = new ComisionDto();
    EstatusDto estatus= new EstatusDto();
    estatus.setIdEstatus(comisionAuxDto.getIdEstatus());
    comisionDto.setIdEstatus(estatus);
    UsuarioDto usuario= new UsuarioDto(); 
    usuario.setIdUsuario(comisionAuxDto.getIdUsuario());
    comisionDto.setIdUsuario(usuario);
    ArchivoDto archivo= new ArchivoDto();
    archivo.setIdArchivo(comisionAuxDto.getIdArchivo());
    comisionDto.setIdArchivo(archivo);
    comisionDto.setIdComision(comisionAuxDto.getIdComision());
    return comisionRules.modificaComisionEstatusArchivo(comisionDto);
  }

}
