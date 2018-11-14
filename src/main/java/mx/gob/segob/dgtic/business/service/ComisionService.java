package mx.gob.segob.dgtic.business.service;

import java.util.List;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GenerarReporteArchivoComision;
import mx.gob.segob.dgtic.comun.sicoa.dto.Reporte;

public interface ComisionService {

  public List<ComisionDto> obtenerListacomisiones();

  public ComisionDto buscaComision(Integer idComision);

  public ComisionDto modificaComision(ComisionAux comision);
  
  public ComisionDto modificaComisionEstatusArchivo(ComisionAux comisionAuxDto);

  public ComisionDto agregaComision(ComisionAux comisionDto);

  public void eliminaComision(Integer idComision);

  public List<ComisionDto> obtenerListaComisionPorFiltros(String claveUsuario, String fechaInicio,
      String fechaFin, String idEstatus);

  public List<ComisionDto> obtenerListaComisionEmpleados(String claveUsuario, String nombre,
      String apellidoPaterno, String apellidoMaterno, String idEstatus, String idUnidad);

  public List<ComisionDto> obtenerComisionesPorUnidad(String idUnidad, String claveUsuario,
      String nombre, String apellidoPaterno, String apellidoMaterno);
  
  public Reporte generaReporteComisiones(GenerarReporteArchivoComision generarReporteArchivo);
}
