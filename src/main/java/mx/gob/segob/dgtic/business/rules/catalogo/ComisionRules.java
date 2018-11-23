package mx.gob.segob.dgtic.business.rules.catalogo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Component
public class ComisionRules extends RecursoBase {

  @Autowired
  private ComisionRepository comisionRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AsistenciaRepository asistenciaRepository;

  @Autowired
  private DiaFestivoRepository diaFestivoRepository;
  
  private static final Integer TIPO_COMISION = 7;
  private static final Integer TIPO_INASISTENCIA = 8;
  private static final Integer ESTATUS_ACEPTADA = 2;
  private static final Integer ESTATUS_RECHAZADA = 3;
  private static final Integer ESTATUS_CANCELADA = 6;

  public List<ComisionDto> obtenerListaComisiones() {
    return comisionRepository.obtenerListaComisiones();
  }

  public ComisionDto buscaComision(Integer idComision) {
    return comisionRepository.buscaComision(idComision);
  }

  public ComisionDto modificaComisionEstatusArchivo(ComisionDto comisionDto) {
    ComisionDto comisionAux = null;
    if (comisionDto.getIdEstatus().getIdEstatus() == ESTATUS_ACEPTADA) {
      comisionAux = aceptarComision(comisionDto);
    } else if (comisionDto.getIdEstatus().getIdEstatus() == ESTATUS_RECHAZADA) {
      comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
    } else if (comisionDto.getIdEstatus().getIdEstatus() == ESTATUS_CANCELADA) {
      comisionAux = cancelarComision(comisionDto);
    } else {
      comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
    }
    return comisionAux;
  }

  public ComisionDto modificaComision(ComisionDto comisionDto) {
    return comisionRepository.modificaComision(comisionDto);
  }

  public ComisionDto agregaComision(ComisionDto comisionDto) {
    ComisionDto comisionRespuesta = null;
    List<ComisionDto> listaComisiones = comisionRepository.obtenerComisionesPorUsuarioRango(
        comisionDto.getIdUsuario().getIdUsuario(),
        new Timestamp(comisionDto.getFechaInicio().getTime()),
        new Timestamp(comisionDto.getFechaFin().getTime()));
    
    if (listaComisiones.isEmpty()) {
      comisionRespuesta = comisionRepository.agregaComision(comisionDto);
    } else {
      comisionRespuesta = new ComisionDto();
      comisionRespuesta.setMensaje("El periodo dado interfiere con una comisión existente");
    }

    return comisionRespuesta;
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
  
  /**
   * Cancela comision.
   * 
   * @param comisionDto
   * @return
   */
  private ComisionDto cancelarComision(ComisionDto comisionDto) {
    /** Buscamos la comision por el id para obtener periodo. **/
    ComisionDto comisionAux = buscaComision(comisionDto.getIdComision());
    comisionDto.setFechaInicio(comisionAux.getFechaInicio());
    comisionDto.setFechaFin(comisionAux.getFechaFin());
    
    /** Periodo de la comision **/
    Date fechaInicio = comisionDto.getFechaInicio();
    Date fechaFin = comisionDto.getFechaFin();
    
    logger.info("Fechas: fechaInicial: {} fechaFin: {} ", comisionDto.getFechaInicio(),
        comisionDto.getFechaFin());

    UsuarioDto usuarioDto =
        usuarioRepository.buscaUsuarioPorId(comisionDto.getIdUsuario().getIdUsuario());

    /** Obtener lista de inasistencias **/
    List<AsistenciaDto> listaInasistencias =
        asistenciaRepository.buscaAsistenciaEmpleado(usuarioDto.getClaveUsuario(),
           TIPO_INASISTENCIA, 0, new Timestamp(fechaInicio.getTime()) , new Timestamp(fechaFin.getTime()));
    logger.info("listaInasistencias: {}", listaInasistencias.size());
    
    /** Si existe una inasistencia en el rango de fecha de la comision mandará mensaje de error **/
    if (listaInasistencias.isEmpty()) {
      List<AsistenciaDto> listaAsistenciasComision =
          asistenciaRepository.buscaAsistenciaEmpleado(usuarioDto.getClaveUsuario(),
             TIPO_COMISION, 0, new Timestamp(fechaInicio.getTime()), new Timestamp(fechaFin.getTime()));
      logger.info("listaAsistenciasComision: {}", listaAsistenciasComision.size());

      if (!listaAsistenciasComision.isEmpty()) {
        for (Iterator<AsistenciaDto> it = listaAsistenciasComision.iterator(); it.hasNext();) {
          AsistenciaDto asistencia = it.next();
          logger.info("idAsistencia: {}", asistencia.getIdAsistencia());
          asistenciaRepository.eliminaAsistencia(asistencia.getIdAsistencia());
          comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
        }
      } else {
        comisionAux.setMensaje("Hubo un problema al cancelar la comisión");
      }
    } else {
      comisionAux.setMensaje(
          "Lo sentimos el usuario no cuenta con ningún registro de entrada o salida de los días a cancelar");
    }

    return comisionAux;
  }

  /**
   * Elimina fines de semana y dias festivos de un periodo dado.
   * 
   * @param fechaInicio
   * @param fechaFin
   * @return
   */
  private List<Date> removerFinesDeSemana(Date fechaInicio, Date fechaFin) {
    List<Date> listaFechas = new ArrayList<>();
    List<DiaFestivoDto> listaDiasFestivos = diaFestivoRepository.obtenerDiasFestivosActivos();

    Calendar c1 = Calendar.getInstance();
    c1.setTime(fechaInicio);
    Calendar c2 = Calendar.getInstance();
    c2.setTime(fechaFin);
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

    return listaFechas;
  }
  
  /**
   * Compara la lista de fechas con la lista de asistencias eliminando las fechas de listaFechas que
   * contengan asistencias o retardos.
   * 
   * @param listaFechas
   * @param listaAsistencias
   * @return listaFechas Lista de fechas sin asistencias o retardos.
   */
  private Integer listaFechasLimpia(List<Date> listaFechas, List<AsistenciaDto> listaAsistencias) {
    Integer existeAsistencia = 0;
    for (Date date : listaFechas) {
      for (AsistenciaDto asistencia : listaAsistencias) {
        if(date.getTime() == asistencia.getEntrada().getTime()) {
          if (asistencia.getIdTipoDia().getIdTipoDia() == 8) {
            /** Eliminar inasistencia **/
            asistenciaRepository.eliminaAsistencia(asistencia.getIdAsistencia());
          } else {
            existeAsistencia = 1;
            return existeAsistencia;
          }
        }
      }
    }
    return existeAsistencia;
  }
  
  /**
   * Agrega asistencias de tipo comision con las fechas dadas.
   * 
   * @param listaFechas
   * @param usuarioDto
   * @param estatusDto
   * @param tipoDiaDto
   */
  private void agregarFechasAsistencias(List<Date> listaFechas, UsuarioDto usuarioDto, EstatusDto estatusDto,
      TipoDiaDto tipoDiaDto) {
    for (Date date : listaFechas) {
      AsistenciaDto asistenciaDto = new AsistenciaDto();
      asistenciaDto.setEntrada(new Timestamp(date.getTime()));
      asistenciaDto.setSalida(new Timestamp(date.getTime()));
      asistenciaDto.setUsuarioDto(usuarioDto);
      asistenciaDto.setIdEstatus(estatusDto);
      asistenciaDto.setIdTipoDia(tipoDiaDto);
      asistenciaRepository.agregaAsistencia(asistenciaDto);
      logger.info("Registro insertado Fecha: {}", date);
    }
  }
  
  /**
   * Acepta comision.
   * 
   * @param comisionDto
   * @return
   */
  private ComisionDto aceptarComision(ComisionDto comisionDto) {
    ComisionDto comisionAux = buscaComision(comisionDto.getIdComision());
    comisionDto.setFechaInicio(comisionAux.getFechaInicio());
    comisionDto.setFechaFin(comisionAux.getFechaFin());
    Date fechaInicio = comisionDto.getFechaInicio();
    Date fechaFin = comisionDto.getFechaFin();
    logger.info("Fechas: fechaInicial: {} fechaFin: {} ", comisionDto.getFechaInicio(),
        comisionDto.getFechaFin());
    List<Date> listaFechas = removerFinesDeSemana(fechaInicio, fechaFin);

    EstatusDto estatusDto = new EstatusDto();
    estatusDto.setIdEstatus(2);
    TipoDiaDto tipoDiaDto = new TipoDiaDto();
    tipoDiaDto.setIdTipoDia(7);
    UsuarioDto usuarioDto =
        usuarioRepository.buscaUsuarioPorId(comisionDto.getIdUsuario().getIdUsuario());

    /** Obtener lista de Asistencias **/
    List<AsistenciaDto> listaAsistencias =
        asistenciaRepository.buscaAsistenciaEmpleado(usuarioDto.getClaveUsuario(), 0, 0,
            new Timestamp(fechaInicio.getTime()), new Timestamp(fechaFin.getTime()));
    logger.info("listaAsistencias: {}", listaAsistencias.size());

    /** Si lista de Asistencias no es vacia entra a verificar de que tipo son **/
    if (!listaAsistencias.isEmpty()) {
      logger.info("La comision esta corriendo {}", listaFechas.size());
      Integer existeAsistencia = listaFechasLimpia(listaFechas, listaAsistencias);
      logger.info("ExisteAsistencia: {}", existeAsistencia);
      
      if (existeAsistencia == 1) {
        comisionAux.setMensaje("Lo sentimos el usuario cuenta con registro de entrada o salida de los días a aceptar");
        return comisionAux;
      }
      
      agregarFechasAsistencias(listaFechas, usuarioDto, estatusDto, tipoDiaDto);
      comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);
      return comisionAux;
    }

    agregarFechasAsistencias(listaFechas, usuarioDto, estatusDto, tipoDiaDto);
    comisionAux = comisionRepository.modificaComisionEstatusArchivo(comisionDto);

    return comisionAux;
  }
}
