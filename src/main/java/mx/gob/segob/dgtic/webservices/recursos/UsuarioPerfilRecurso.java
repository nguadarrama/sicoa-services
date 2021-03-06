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

import mx.gob.segob.dgtic.business.service.UsuarioPerfilService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioPerfilDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class UsuarioPerfilRecurso extends RecursoBase{
	
	@Autowired 
	private UsuarioPerfilService usuarioPerfilService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("consultaPerfilesUsuario")	
	public Response obtienePerfilesPorUsuario(@QueryParam("id") String id) {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, usuarioPerfilService.consultaPerfilesPorUsuario(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaEliminaPerfilesUsuario")	
	public Response agregaEliminaPerfilesUsuario(@RequestParam String jsonUsuarioPerfil) {
		jsonUsuarioPerfil = this.cambiaCaracter(jsonUsuarioPerfil);
		JsonObject jsonObject = new JsonParser().parse(jsonUsuarioPerfil).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		UsuarioPerfilDto usuarioPerfilDto = gson.fromJson(jsonObject.get("usuarioPerfil"), UsuarioPerfilDto.class);		
		Integer r = usuarioPerfilService.insertaEliminaUsuarioPerfil(usuarioPerfilDto);
		if (r > 0)
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		else 
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.NOT_MODIFIED, "");
	}

}
