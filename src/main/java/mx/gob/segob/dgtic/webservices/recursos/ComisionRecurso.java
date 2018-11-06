package mx.gob.segob.dgtic.webservices.recursos;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.segob.dgtic.business.service.ComisionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.GenerarReporteArchivoComision;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("comisiones")
@Component
public class ComisionRecurso {

  @Autowired
  private ComisionService comisionService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("obtieneComisiones")
  @PermitAll
  public Response obtieneComisiones() {

    return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,
        comisionService.obtenerListacomisiones());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("buscaComision")
  @PermitAll
  public Response buscaComision(@QueryParam("id") Integer id) {

    return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,
        comisionService.buscaComision(id));
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("modificaComision")
  @PermitAll
  public Response modificaComision(@RequestParam String jsonComision) {
    JsonObject jsonObject = new JsonParser().parse(jsonComision).getAsJsonObject();

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    ComisionAux comisionDto = gson.fromJson(jsonObject.get("comision"), ComisionAux.class);

    return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,
        comisionService.modificaComision(comisionDto));
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("agregaComision")
  @PermitAll
  public Response agregaComision(@RequestParam String jsonComision) {
    JsonObject jsonObject = new JsonParser().parse(jsonComision).getAsJsonObject();

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    ComisionAux comisionAux = gson.fromJson(jsonObject.get("comision"), ComisionAux.class);
    System.out.println("Datos para comision en recurso " + " fechaInicio "+comisionAux.getFechaInicio()+
        " fechaFin "+comisionAux.getFechaFin());
    

    return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, comisionService.agregaComision(comisionAux));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("eliminaComision")
  @PermitAll
  public Response eliminaComision(@QueryParam("id") Integer id) {

    comisionService.eliminaComision(id);

    return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("obtieneListaComisionPorFiltros")
  @PermitAll
  public Response obtieneListaComisionPorFiltros(@QueryParam("claveUsuario") String claveUsuario,
      @QueryParam("idEstatus") String idEstatus, @QueryParam("fechaInicio") String fechaInicio,
      @QueryParam("fechaFin") String fechaFin) {

    return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, comisionService
        .obtenerListaComisionPorFiltros(claveUsuario, fechaInicio, fechaFin, idEstatus));
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("obtieneListaComisionPorFiltrosEmpleados")    
  @PermitAll
  public Response obtieneListaComisionEmpleados(@QueryParam("claveUsuario") String claveUsuario,@QueryParam("idEstatus") String idEstatus, 
          @QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno,
          @QueryParam("idUnidad") String idUnidad) {

      return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, comisionService.obtenerListaComisionEmpleados(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idEstatus, idUnidad));
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("obtieneComisionesPorUnidad")  
  @PermitAll
  public Response obtieneComisionesPorUnidad(@QueryParam("idUnidad") String idUnidad, @QueryParam("claveUsuario") String claveUsuario,
          @QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno")String apellidoMaterno) {

      return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, comisionService.obtenerComisionesPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno, apellidoMaterno));
  }
  
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("generarReporte")  
  @PermitAll
  public Response generarReporte(@RequestParam String jsonDetalleVacacion) {
      JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      GenerarReporteArchivoComision generaReporteArchivo = gson.fromJson(jsonObject.get("generaReporteArchivo"), GenerarReporteArchivoComision.class);
      return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,comisionService.generaReporteComisiones(generaReporteArchivo));
  }
  
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("modificaComisionEstatusArchivo") 
  @PermitAll
  public Response modificaComisionEstatusArchivo(@RequestParam String jsonLicenciaMedica) {
      JsonObject jsonObject = new JsonParser().parse(jsonLicenciaMedica).getAsJsonObject();
      
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      ComisionAux comisionDto = gson.fromJson(jsonObject.get("comision"), ComisionAux.class);
      
      return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,comisionService.modificaComisionEstatusArchivo(comisionDto));
  }
}
