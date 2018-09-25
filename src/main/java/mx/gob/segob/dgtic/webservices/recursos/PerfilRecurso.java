package mx.gob.segob.dgtic.webservices.recursos;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import mx.gob.segob.dgtic.business.service.PerfilService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class PerfilRecurso {
	
	@Autowired
	private PerfilService perfilService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtienePerfiles")	
	@PermitAll
	public Response obtienePerfiles() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, perfilService.obtenerListaPerfiles());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaPerfil")	
	@PermitAll
	public Response buscaPerfil(@QueryParam("id") String id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, perfilService.buscaPerfil(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaPerfil")	
	@PermitAll
	public Response modificaPerfil(@RequestParam String jsonPerfil) {
		JsonObject jsonObject = new JsonParser().parse(jsonPerfil).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		PerfilDto perfilDto = gson.fromJson(jsonObject.get("perfil"), PerfilDto.class);
		
		perfilService.modificaPerfil(perfilDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaPerfil")	
	@PermitAll
	public Response agregaPerfil(@RequestParam String jsonPerfil) {
		JsonObject jsonObject = new JsonParser().parse(jsonPerfil).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		PerfilDto perfilDto = gson.fromJson(jsonObject.get("perfil"), PerfilDto.class);
		
		perfilService.agregaPerfil(perfilDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaPerfil")	
	@PermitAll
	public Response eliminaPerfil(@QueryParam("id") String id) {
		
		perfilService.eliminaPerfil(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}

}
