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

import mx.gob.segob.dgtic.business.service.CargaInicialService;
import mx.gob.segob.dgtic.business.service.UsuarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class UsuarioRecurso {

	@Autowired 
	private UsuarioService usuarioService;
	
	@Autowired 
	private CargaInicialService cargaInicialService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cargaInicial")
	@PermitAll
	public Response cargaInicial(){
		return null; //ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, usuarioService.cargaInicial());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneUsuarios")	
	@PermitAll
	public Response obtieneUsuarios() {
			//cargaInicialService.cargaInicial();
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, usuarioService.obtenerListaUsuarios());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaUsuario")	
	@PermitAll
	public Response buscaUsuario(@QueryParam("id") String id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, usuarioService.buscaUsuario(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaUsuario")	
	@PermitAll
	public Response modificaUsuario(@RequestParam String jsonUsuario) {
		JsonObject jsonObject = new JsonParser().parse(jsonUsuario).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		UsuarioDto usuarioDto = gson.fromJson(jsonObject.get("usuario"), UsuarioDto.class);
		
		usuarioService.modificaUsuario(usuarioDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaUsuario")	
	@PermitAll
	public Response eliminaUsuario(@QueryParam("id") String id) {
		
		usuarioService.eliminaUsuario(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("reiniciaContrasenia")	
	@PermitAll
	public Response reiniciaContrasenia(@QueryParam("claveUsuario") String claveUsuario) {
		
		usuarioService.reiniciaContrasenia(claveUsuario);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaUsuarioPorId")	
	@PermitAll
	public Response buscaUsuarioPorId(@QueryParam("id") String id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, usuarioService.buscaUsuarioPorId(Integer.parseInt(id)));
	}
}
