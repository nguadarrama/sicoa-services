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
import mx.gob.segob.dgtic.business.service.NivelOrganizacionalService;
import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("nivel")
@Component
public class NivelOrganizacionalRecurso {
	
	@Autowired
	private NivelOrganizacionalService nivelOrganizacionalService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtiene")	
	@PermitAll
	public Response obtenerListaNiveles() {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, nivelOrganizacionalService.obtenerListaNiveles());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneNivelesEmpleado")	
	@PermitAll
	public Response obtenerNivelesEmpleados() {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, nivelOrganizacionalService.nivelesEmpleado());
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agrega")	
	@PermitAll
	public Response agregaPeriodo(@RequestParam String jsonNivelOrganizacional) {
		JsonObject jsonObject = new JsonParser().parse(jsonNivelOrganizacional).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		NivelOrganizacionalDto nivel = gson.fromJson(jsonObject.get("nivel"), NivelOrganizacionalDto.class);
		nivel = nivelOrganizacionalService.creaNivel(nivel);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, nivel);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("busca")	
	@PermitAll
	public Response buscaPeriodo(@QueryParam ("idNivel") Integer idNivel) {
		System.out.println("llego a nivelRecurso: idNivel: "+idNivel);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, nivelOrganizacionalService.buscaNivel(idNivel));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modifica")	
	@PermitAll
	public Response modificaNivel(@RequestParam String jsonNivel) {
		JsonObject jsonObject = new JsonParser().parse(jsonNivel).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		NivelOrganizacionalDto nivel = gson.fromJson(jsonObject.get("nivel"), NivelOrganizacionalDto.class);		
		nivel = nivelOrganizacionalService.modificaNivel(nivel);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,nivel);
	}
}
