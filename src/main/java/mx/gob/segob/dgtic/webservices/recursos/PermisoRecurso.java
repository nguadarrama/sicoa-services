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

import mx.gob.segob.dgtic.business.service.PermisoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PermisoDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class PermisoRecurso extends RecursoBase{
	
	@Autowired
	private PermisoService permisoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtienePermisos")	
	public Response obtienePermisos() {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, permisoService.obtenerListaPermisos());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaPermiso")	
	public Response buscaPermiso(@QueryParam("id") String id) {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, permisoService.buscaPermiso(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaPermiso")	
	public Response modificaPermiso(@RequestParam String jsonPermiso) {
		jsonPermiso = this.cambiaCaracter(jsonPermiso);
		JsonObject jsonObject = new JsonParser().parse(jsonPermiso).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		PermisoDto permisoDto = gson.fromJson(jsonObject.get("permiso"), PermisoDto.class);		
		permisoService.modificaPermiso(permisoDto);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaPermiso")	
	public Response agregaPermiso(@RequestParam String jsonPermiso) {
		jsonPermiso = this.cambiaCaracter(jsonPermiso);
		JsonObject jsonObject = new JsonParser().parse(jsonPermiso).getAsJsonObject();		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		PermisoDto permisoDto = gson.fromJson(jsonObject.get("permiso"), PermisoDto.class);	
		permisoService.agregaPermiso(permisoDto);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaPermiso")	
	public Response eliminaPermiso(@QueryParam("id") String id) {	
		permisoService.eliminaPermiso(id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
