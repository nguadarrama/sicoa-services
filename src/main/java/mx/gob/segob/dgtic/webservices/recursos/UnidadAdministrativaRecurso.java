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

import mx.gob.segob.dgtic.business.service.UnidadAdministrativaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class UnidadAdministrativaRecurso extends RecursoBase{
	
	@Autowired
	private UnidadAdministrativaService unidadAdministrativaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneUnidadesAdministrativas")	
	public Response obtieneUnidadesAdministrativas() {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, unidadAdministrativaService.obtenerListaUnidadAdministrativa());
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("consultaRegistraUsuarioUnidadAdministrativa")	
	public Response consultaRegistraUsuarioUnidadAdministrativa(@RequestParam String jsonUsuarioUnidadAdministrativa) {
		jsonUsuarioUnidadAdministrativa = this.cambiaCaracter(jsonUsuarioUnidadAdministrativa);
		JsonObject jsonObject = new JsonParser().parse(jsonUsuarioUnidadAdministrativa).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto = gson.fromJson(jsonObject.get("UsuarioUnidadAdministrativa"), UsuarioUnidadAdministrativaDto.class);
		Integer r = unidadAdministrativaService.consultaRegistraUsuarioUnidadAdministrativa(usuarioUnidadAdministrativaDto);
		if (r> 0)
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		else 
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.NOT_MODIFIED, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("consultaResponsable")	
	public Response consultaResponsable(@QueryParam("id") String id) {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, unidadAdministrativaService.consultaResponsable(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneUnidadesAdministrativasCompletas")	
	public Response obtieneUnidadesAdministrativasCompletas() {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, unidadAdministrativaService.obtenerUnidadesAdministrativas());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneUnidades")	
	public Response obtieneUnidades() {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, unidadAdministrativaService.obtieneUnidades());
	}
}
