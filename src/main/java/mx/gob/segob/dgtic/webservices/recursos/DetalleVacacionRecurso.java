package mx.gob.segob.dgtic.webservices.recursos;

import java.util.ArrayList;
import java.util.List;
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

import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionesAux;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class DetalleVacacionRecurso {

	@Autowired
	private DetalleVacacionService detalleVacacionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneDetalleVacaciones")	
	@PermitAll
	public Response obtieneDetalleVacaciones() {
		System.out.println("Peticion de vacacionesRecurso");
		List<DetalleVacacionDto> lista = new ArrayList<>();
		lista= detalleVacacionService.obtenerListaDetalleVacaciones();
		
		for (DetalleVacacionDto detalleVacacion : lista) {
			
			System.out.println("FechaInicio "+detalleVacacion.getFechaInicio());
		}
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, lista);
		}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaDetalleVacacion")	
	@PermitAll
	public Response buscaDetalleVacacion(@QueryParam("id") Integer id) {
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.buscaDetalleVacacion(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaDetalleVacacion")	
	@PermitAll
	public Response modificaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DetalleVacacionDto detalleVacacionDto = gson.fromJson(jsonObject.get("detalleVacacion"), DetalleVacacionDto.class);
		
		

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.modificaDetalleVacacion(detalleVacacionDto));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaDetalleVacacion")	
	@PermitAll
	public Response agregaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		VacacionesAux detalleVacacionDto = gson.fromJson(jsonObject.get("detalleVacacion"), VacacionesAux.class);
		System.out.println("Datos para idVacacion en recurso "+detalleVacacionDto.getIdVacacion()+" fechaInicio "+detalleVacacionDto.getFechaInicio()+
				" fechaFin "+detalleVacacionDto.getFechaFin());
		

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.agregaDetalleVacacion(detalleVacacionDto));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaDetalleVacacion")	
	@PermitAll
	public Response eliminaDetalleVacacion(@QueryParam("id") Integer id) {
		
		detalleVacacionService.eliminaDetalleVacacion(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("aceptaORechazaDetalleVacacion")	
	@PermitAll
	public Response aceptaORechazaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DetalleVacacionDto detalleVacacionDto = gson.fromJson(jsonObject.get("detalleVacacion"), DetalleVacacionDto.class);
		System.out.println("Valor de idUsuario "+detalleVacacionDto.getIdUsuario().getIdUsuario());
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.aceptaORechazaDetalleVacacion(detalleVacacionDto));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("consultaVacacionesPropiasPorFiltros")	
	@PermitAll
	public Response consultaVacacionesPropiasPorFiltros(@QueryParam("claveUsuario") String claveUsuario, @QueryParam("idEstatus") String idEstatus, @QueryParam("idPeriodo") String idPeriodo, @QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin) {
//		Integer nuevoIdPeriodo=null;
//		Integer nuevoIdEstatus=null;
//		if(idPeriodo!=null && !idPeriodo.toString().isEmpty()){
//				try{
//					nuevoIdPeriodo=Integer.parseInt(idPeriodo);
//					
//				}catch(Exception e){
//					e.printStackTrace();
//					nuevoIdPeriodo=null;
//					
//				}
//		}
//		if(idEstatus!=null && !idEstatus.toString().isEmpty()){
//			try{
//				
//				nuevoIdEstatus=Integer.parseInt(idEstatus);
//			}catch(Exception e){
//				e.printStackTrace();
//				
//				nuevoIdEstatus=null;
//			}
//		}
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.consultaVacacionesPropiasPorFiltros(claveUsuario, idPeriodo, idEstatus, fechaInicio, fechaFin));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerVacacionesPorFiltros")	
	@PermitAll
	public Response obtenerVacacionesPorFiltros(@QueryParam("claveUsuario") String claveUsuario, @QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno, @QueryParam("idUnidad") String idUnidad, @QueryParam("idEstatus") String idEstatus) {

		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.obtenerVacacionesPorFiltros(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idUnidad, idEstatus));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("generaReporte")	
	@PermitAll
	public Response generaReporte(@RequestParam String jsonDetalleVacacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		GeneraReporteArchivo generaReporteArchivo = gson.fromJson(jsonObject.get("generaReporteArchivo"), GeneraReporteArchivo.class);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,detalleVacacionService.generaReporteVacaciones(generaReporteArchivo));
	}
}
