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

import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("asistencia")
@Component
public class AsistenciaRecurso {
	
	@Autowired
	private AsistenciaService asistenciaService;
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistencias")	
	@PermitAll
	public Response obtieneAsistencia() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.obtenerListaAsistencia());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaAsistencia")	
	@PermitAll
	public Response buscaAsistencia(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistencia(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaAsistencia")	
	@PermitAll
	public Response elimina(@QueryParam("id") Integer id) {
		
//		asistenciaService.eliminaAsistencia(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
}