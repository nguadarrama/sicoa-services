package mx.gob.segob.dgtic.webservices.recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.business.service.JustificacionService;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class JustificacionRecurso {
	
	@Autowired 
	private JustificacionService justificacionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneJustificaciones")
	public Response obtieneJustificacion(){
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, justificacionService.obtenerListaJustificaciones());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneLista")
	public Response obtieneLista(){
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, justificacionService.obtenerLista());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaJustificacion")	
	public Response buscaJustificacion(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, justificacionService.buscaJustificacion(id));
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaJustificacion")	
	public Response eliminaJustificacion(@QueryParam("id") Integer id) {
		justificacionService.eliminaJustificacion(id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
