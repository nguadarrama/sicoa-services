package mx.gob.segob.dgtic.webservices.recursos;

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

import mx.gob.segob.dgtic.business.service.EstatusService;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class EstatusRecurso {
	
	@Autowired
	private EstatusService estatusService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneEstatus")	
	public Response obtieneEstatus() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, estatusService.obtenerListaEstatus());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaEstatus")	
	public Response buscaEstatus(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, estatusService.buscaEstatus(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaEstatus")	
	public Response modificaEstatus(@RequestParam String jsonEstatus) {
		JsonObject jsonObject = new JsonParser().parse(jsonEstatus).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		EstatusDto estatusDto = gson.fromJson(jsonObject.get("estatus"), EstatusDto.class);
		
		estatusService.modificaEstatus(estatusDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaEstatus")	
	public Response agregaEstatus(@RequestParam String jsonEstatus) {
		JsonObject jsonObject = new JsonParser().parse(jsonEstatus).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		EstatusDto estatusDto = gson.fromJson(jsonObject.get("estatus"), EstatusDto.class);
		
		estatusService.agregaEstatus(estatusDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaEstatus")	
	public Response eliminaEstatus(@QueryParam("id") Integer id) {
		
		estatusService.eliminaEstatus(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneListaCompletaEstatus")	
	public Response obtieneListaCompletaEstatus() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, estatusService.obtenerListaCompletaEstatus());
	}
}
