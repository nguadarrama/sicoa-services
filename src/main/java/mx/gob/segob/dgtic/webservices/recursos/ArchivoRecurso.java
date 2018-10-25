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

import mx.gob.segob.dgtic.business.service.ArchivoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class ArchivoRecurso {

	@Autowired
	private ArchivoService archivoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneArchivos")	
	@PermitAll
	public Response obtieneArchivos() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, archivoService.obtenerListaArchivos());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaArchivo")	
	@PermitAll
	public Response buscaArchivo(@QueryParam("id") Integer id) {
		System.out.println("IdArchivo en recurso "+id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, archivoService.buscaArchivo(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaArchivo")	
	@PermitAll
	public Response modificaArchivo(@RequestParam String jsonArchivo) {
		JsonObject jsonObject = new JsonParser().parse(jsonArchivo).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		ArchivoDto archivoDto = gson.fromJson(jsonObject.get("archivo"), ArchivoDto.class);
		
		archivoService.modificaArchivo(archivoDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaArchivo")	
	@PermitAll
	public Response agregaArchivo(@RequestParam String jsonArchivo) {
		JsonObject jsonObject = new JsonParser().parse(jsonArchivo).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		ArchivoDto archivoDto = gson.fromJson(jsonObject.get("archivo"), ArchivoDto.class);
		System.out.println("archivo en recurso "+archivoDto.getArchivo());
		
		Integer idArchivo = archivoService.agregaArhivo(archivoDto);
		
		ArchivoDto archivo = new ArchivoDto();
		archivo.setIdArchivo(idArchivo);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, archivo);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaArchivo")	
	@PermitAll
	public Response eliminaArchivo(@QueryParam("id") Integer id) {
		
		archivoService.eliminaArchivo(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
