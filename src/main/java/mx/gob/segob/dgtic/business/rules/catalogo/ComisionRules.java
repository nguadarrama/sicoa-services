package mx.gob.segob.dgtic.business.rules.catalogo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class ComisionRules {

  @Autowired
  private ComisionRepository comisionRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AsistenciaRepository asistenciaRepository;

  @Autowired
  private DiaFestivoRepository diaFestivoRepository;

  public List<ComisionDto> obtenerListaComisiones() {
    return comisionRepository.obtenerListaComisiones();
  }

  public ComisionDto buscaComision(Integer idComision) {
    return comisionRepository.buscaComision(idComision);
  }

  public ComisionDto modificaComisionEstatusArchivo(ComisionDto comisionDto) {
    ComisionDto comisionAux = new ComisionDto();
    comisionAux = buscaComision(comisionDto.getIdComision());
    System.out.println("RULES MODIFICA COMISION ESTATUS IDCOMISION" + comisionDto.getIdComision());
    System.out.println(
        "ESTATUS OBJETO: " + ReflectionToStringBuilder.toString(comisionDto.getIdEstatus()));
    System.out
        .println("Archivo objeto" + ReflectionToStringBuilder.toString(comisionDto.getIdArchivo()));
    if (comisionDto.getIdEstatus().getIdEstatus() == 2) {
      List<DiaFestivoDto> listaDiasFestivos = diaFestivoRepository.obtenerDiasFestivosActivos();

      comisionDto.setFechaInicio(comisionAux.getFechaInicio());
      comisionDto.setFechaFin(comisionAux.getFechaFin());
      Date fechaInicio = comisionDto.getFechaInicio();
      Date fechaFin = comisionDto.getFechaFin();
      Calendar c1 = Calendar.getInstance();
      System.out.println("Fechas fecha inicial " + comisionDto.getFechaInicio() + " fecha final "
          + comisionDto.getFechaFin());
      c1.setTime(fechaInicio);
      Calendar c2 = Calendar.getInstance();
      c2.setTime(fechaFin);
      List<Date> listaFechas = new ArrayList<Date>();
      while (!c1.after(c2)) {
        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
          c1.add(Calendar.DAY_OF_MONTH, 1);
        } else {
          listaFechas.add(c1.getTime());
          c1.add(Calendar.DAY_OF_MONTH, 1);
        }
      }

      for (DiaFestivoDto diaFestivo : listaDiasFestivos) {
        for (Date fecha : listaFechas) {
          if (diaFestivo.getFecha().equals(fecha)) {
            listaFechas.remove(fecha);
          }
        }

      }
      EstatusDto estatusDto = new EstatusDto();
      estatusDto.setIdEstatus(2);
      TipoDiaDto tipoDiaDto = new TipoDiaDto();
      tipoDiaDto.setIdTipoDia(7);
      UsuarioDto usuarioDto = new UsuarioDto();
      usuarioDto = usuarioRepository.buscaUsuarioPorId(comisionDto.getIdUsuario().getIdUsuario());
      for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
        Date date = it.next();
        AsistenciaDto asistenciaDto = new AsistenciaDto();
        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
        asistenciaDto.setSalida(new Timestamp(date.getTime()));
        System.out.println("comisionDto.getIdUsuario().getIdUsuario() "
            + comisionDto.getIdUsuario().getIdUsuario());
        asistenciaDto.setUsuarioDto(usuarioDto);
        asistenciaDto.setIdEstatus(estatusDto);
        asistenciaDto.setIdTipoDia(tipoDiaDto);
        asistenciaRepository.agregaAsistencia(asistenciaDto);
        System.out.println("Fecha " + date + " registro insertado");
        comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
      }

    } else if (comisionDto.getIdEstatus().getIdEstatus() == 3) {
      comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
    } else {
      comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
    }
    return comisionAux;
  }

  public void modificaComision(ComisionDto comisionDto) {
    comisionRepository.modificaComision(comisionDto);
  }

  public ComisionDto agregaComision(ComisionDto comisionDto) {
    return comisionRepository.agregaComision(comisionDto);
  }

  public void eliminaComision(Integer idComision) {
    comisionRepository.eliminaComision(idComision);
  }

  public List<ComisionDto> obtenerListaComisionPorFiltros(String claveUsuario, String fechaInicio,
      String fechaFin, String idEstatus) {
    return comisionRepository.obtenerListaComisionPorFiltros(claveUsuario, fechaInicio, fechaFin,
        idEstatus);
  }

  public List<ComisionDto> obtenerListaComisionPorFiltrosEmpleados(String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno, String idEstatus,
      String idUnidad) {
    return comisionRepository.obtenerListaComisionPorFiltrosEmpleados(claveUsuario, nombre,
        apellidoPaterno, apellidoMaterno, idEstatus, idUnidad);
  }

  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad, String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno) {
    return comisionRepository.obtenerComisionesPorUnidad(idUnidad, claveUsuario, nombre,
        apellidoPaterno, apellidoMaterno);
  }
}
