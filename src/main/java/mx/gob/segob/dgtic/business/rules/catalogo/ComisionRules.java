package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.persistence.repository.ComisionRepository;

@Component
public class ComisionRules {

  @Autowired
  private ComisionRepository comisionRepository;

  public List<ComisionDto> obtenerListaComisiones() {
    return comisionRepository.obtenerListaComisiones();
  }

  public ComisionDto buscaComision(Integer idComision) {
    return comisionRepository.buscaComision(idComision);
  }

  public void modificaComision(ComisionDto comision) {
    comisionRepository.modificaComision(comision);
  }

  public void agregaComision(ComisionDto comisionDto) {
    comisionRepository.agregaComision(comisionDto);
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
  
  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad,String claveUsuario, String nombre,
      String apellidoPaterno, String apellidoMaterno) {
  return comisionRepository.obtenerComisionesPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno, apellidoMaterno);
}
}
