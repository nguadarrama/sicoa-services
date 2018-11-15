package mx.gob.segob.dgtic.webservices.recursos;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionesAux;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;
import net.sf.jasperreports.engine.JRException;

@Path("catalogo")
@Component
public class DetalleVacacionRecurso extends ServiceBase{

	@Autowired
	private DetalleVacacionService detalleVacacionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneDetalleVacaciones")	
	public Response obtieneDetalleVacaciones() {
		logger.info("Peticion de vacacionesRecurso");
		List<DetalleVacacionDto> lista;
		lista= detalleVacacionService.obtenerListaDetalleVacaciones();
		
		for (DetalleVacacionDto detalleVacacion : lista) {
			
			logger.info("FechaInicio: {} ",detalleVacacion.getFechaInicio());
		}
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, lista);
		}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaDetalleVacacion")	
	public Response buscaDetalleVacacion(@QueryParam("id") Integer id) {
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.buscaDetalleVacacion(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaDetalleVacacion")	
	public Response modificaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		jsonDetalleVacacion = this.cambiaCaracter(jsonDetalleVacacion);
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();	
		JsonObject detalle = jsonObject.getAsJsonObject("detalleVacacion");
		JsonElement idDetalle  =  detalle.get("idDetalle");
		JsonObject archivo = detalle.getAsJsonObject("idArchivo");
		JsonElement idArchivo  =  archivo.get("idArchivo");
		idArchivo.getAsInt();
		DetalleVacacionDto detalleVacacionDto = new DetalleVacacionDto();
		ArchivoDto archivo1= new ArchivoDto();
		archivo1.setIdArchivo(idArchivo.getAsInt());
		detalleVacacionDto.setIdDetalle(idDetalle.getAsInt());
		detalleVacacionDto.setIdArchivo(archivo1);
		logger.info("Datos: {} ", detalleVacacionDto.getIdArchivo().getIdArchivo());
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.modificaDetalleVacacion(detalleVacacionDto));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaDetalleVacacion")	
	public Response agregaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		jsonDetalleVacacion = this.cambiaCaracter(jsonDetalleVacacion);
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		VacacionesAux detalleVacacionDto = gson.fromJson(jsonObject.get(ServiceConstants.DETALLE_VACACION), VacacionesAux.class);
		logger.info("Datos para idVacacion en recurso "+detalleVacacionDto.getIdVacacion()+" fechaInicio "+detalleVacacionDto.getFechaInicio()+
				" fechaFin "+detalleVacacionDto.getFechaFin());
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.agregaDetalleVacacion(detalleVacacionDto));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaDetalleVacacion")	
	public Response eliminaDetalleVacacion(@QueryParam("id") Integer id) {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.eliminaDetalleVacacion(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("aceptaORechazaDetalleVacacion")	
	public Response aceptaORechazaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		jsonDetalleVacacion = this.cambiaCaracter(jsonDetalleVacacion);
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DetalleVacacionDto detalleVacacionDto = gson.fromJson(jsonObject.get("detalleVacacion"), DetalleVacacionDto.class);
		logger.info("Valor de idUsuario: {} ",detalleVacacionDto.getIdUsuario().getIdUsuario());
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.aceptaORechazaDetalleVacacion(detalleVacacionDto));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("consultaVacacionesPropiasPorFiltros")	
	public Response consultaVacacionesPropiasPorFiltros(@RequestParam String jsonBusqueda) {
		jsonBusqueda = this.cambiaCaracter(jsonBusqueda);
		JsonObject jsonObject = new JsonParser().parse(jsonBusqueda).getAsJsonObject();	
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		BusquedaDto busquedaDto = gson.fromJson(jsonObject.get("busqueda"), BusquedaDto.class);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.consultaVacacionesPropiasPorFiltros(busquedaDto));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("obtenerVacacionesPorFiltros")	
	public Response obtenerVacacionesPorFiltros(@RequestParam String jsonBusqueda) {
		jsonBusqueda = this.cambiaCaracter(jsonBusqueda);
		JsonObject jsonObject = new JsonParser().parse(jsonBusqueda).getAsJsonObject();	
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		BusquedaDto busquedaDto = gson.fromJson(jsonObject.get("busqueda"), BusquedaDto.class);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.obtenerVacacionesPorFiltros(busquedaDto));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("generaReporte")	
	public Response generaReporte(@RequestParam String jsonDetalleVacacion) throws FileNotFoundException, ParseException, JRException {
		jsonDetalleVacacion = this.cambiaCaracter(jsonDetalleVacacion);
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		GeneraReporteArchivo generaReporteArchivo = gson.fromJson(jsonObject.get(ServiceConstants.GENERA_REPORTE_ARCHIVO), GeneraReporteArchivo.class);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,detalleVacacionService.generaReporteVacaciones(generaReporteArchivo));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cancelaVacaciones")	
	public Response cancelaVacaciones(@QueryParam("id") Integer id) {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.cancelaVacaciones(id));
	}
}
