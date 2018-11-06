package mx.gob.segob.dgtic.persistence.repository;

import java.util.Date;
import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;

public interface ComisionRepository {

  public List<ComisionDto> obtenerListaComisiones();

  public ComisionDto buscaComision(Integer idComision);

  public ComisionDto modificaComision(ComisionDto comisionDto);
  
  public ComisionDto modificaComisionEstatusArchivo(ComisionDto comisionDto);

  public ComisionDto agregaComision(ComisionDto comisionDto);

  public void eliminaComision(Integer idComision);

  public List<ComisionDto> obtenerListaComisionPorFiltros(String claveUsuario, String fechaInicio,
      String fechaFin, String idEstatus);

  public List<ComisionDto> obtenerListaComisionPorFiltrosEmpleados(String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno, String idEstatus,
      String idUnidad);
  
  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad, String claveUsuario, String nombre,
      String apellidoPaterno, String apellidoMaterno);
}
