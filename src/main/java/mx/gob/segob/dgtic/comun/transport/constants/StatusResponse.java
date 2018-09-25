/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.constants;

import java.io.Serializable;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.apache.http.annotation.Immutable;


/**
 * Constantes que enumeran los estatus de respuesta para una peticion HTTP
 * <p>
 * Contiene los estatus definidos en RFC1945(HTTP/1.0) y RFC2616 (HTTP/1.1)
 * 
 */

@Immutable
public final class StatusResponse implements Serializable, StatusType {
	
    // --- 1xx Informational ---	
    /** The Constant serialVersionUID. */ 
	private static final long serialVersionUID = 1L;
	
	/** <tt>100 Continue</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse CONTINUE = create(100, Family.INFORMATIONAL, "100 Continue");
    /** <tt>101 Switching Protocols</tt> (HTTP/1.1 - RFC 2616)*/
    public static final StatusResponse SWITCHING_PROTOCOLS = create(101, Family.INFORMATIONAL, "101 Switching Protocols");
    
	
    // --- 2xx Success ---

    /** <tt>200 OK</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse OK = create(200, Family.SUCCESSFUL, "200 OK");
    /** <tt>201 Created</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse CREATED = create(201, Family.SUCCESSFUL, "201 Created");   
    /** <tt>202 Accepted</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse ACCEPTED = create(202, Family.SUCCESSFUL, "202 Accepted");
    /** <tt>203 Non Authoritative Information</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse NON_AUTHORITATIVE_INFORMATION = create(203, Family.SUCCESSFUL, "203 Non Authoritative Information");
    /** <tt>204 No Content</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse NO_CONTENT = create(204, Family.SUCCESSFUL, "204 No Content");
    /** <tt>205 Reset Content</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse RESET_CONTENT = create(205, Family.SUCCESSFUL, "205 Reset Content");
    /** <tt>206 Partial Content</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse PARTIAL_CONTENT = create(206, Family.SUCCESSFUL, "206 Partial Content");
    
    // --- 3xx Redirection ---

    /** <tt>300 Mutliple Choices</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse MULTIPLE_CHOICES = create(300, Family.REDIRECTION, "300 Mutliple Choices");
    /** <tt>301 Moved Permanently</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse MOVED_PERMANENTLY = create(301, Family.REDIRECTION, "301 Moved Permanently");
    /** <tt>302 Moved Temporarily</tt> (Sometimes <tt>Found</tt>) (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse MOVED_TEMPORARILY = create(302, Family.REDIRECTION, "302 Moved Temporarily");
    /** <tt>303 See Other</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse SEE_OTHER = create(303, Family.REDIRECTION, "303 See Other");
    /** <tt>304 Not Modified</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse NOT_MODIFIED = create(304, Family.REDIRECTION, "304 Not Modified");
    /** <tt>305 Use Proxy</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse USE_PROXY = create(305, Family.REDIRECTION, "305 Use Proxy");
    /** <tt>307 Temporary Redirect</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse TEMPORARY_REDIRECT = create(307, Family.REDIRECTION, "307 Temporary Redirect");
    

    // --- 4xx Client Error ---

    /** <tt>400 Bad Request</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse BAD_REQUEST = create(400, Family.CLIENT_ERROR, "400 Bad Request");
    /** <tt>401 Unauthorized</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse UNAUTHORIZED = create(401, Family.CLIENT_ERROR, "401 Unauthorized");
    /** <tt>402 Payment Required</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse PAYMENT_REQUIRED = create(402, Family.CLIENT_ERROR, "402 Payment Required");
    /** <tt>403 Forbidden</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse FORBIDDEN = create(403, Family.CLIENT_ERROR, "403 Forbidden");
    /** <tt>404 Not Found</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse NOT_FOUND = create(404, Family.CLIENT_ERROR, "404 Not Found");
    /** <tt>405 Method Not Allowed</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse METHOD_NOT_ALLOWED = create(405, Family.CLIENT_ERROR, "405 Method Not Allowed");
    /** <tt>406 Not Acceptable</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse NOT_ACCEPTABLE = create(406, Family.CLIENT_ERROR, "406 Not Acceptable");
    /** <tt>407 Proxy Authentication Required</tt> (HTTP/1.1 - RFC 2616)*/
    public static final StatusResponse PROXY_AUTHENTICATION_REQUIRED = create(407, Family.CLIENT_ERROR, "407 Proxy Authentication Required");
    /** <tt>408 Request Timeout</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse REQUEST_TIMEOUT = create(408, Family.CLIENT_ERROR, "408 Request Timeout");
    /** <tt>409 Conflict</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse CONFLICT = create(409, Family.CLIENT_ERROR, "409 Conflict");
    /** <tt>410 Gone</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse GONE = create(410, Family.CLIENT_ERROR, "410 Gone");
    /** <tt>411 Length Required</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse LENGTH_REQUIRED = create(411, Family.CLIENT_ERROR, "411 Length Required");
    /** <tt>412 Precondition Failed</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse PRECONDITION_FAILED = create(412, Family.CLIENT_ERROR, "412 Precondition Failed");
    /** <tt>413 Request Entity Too Large</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse REQUEST_TOO_LONG = create(413, Family.CLIENT_ERROR, "413 Request Entity Too Large");
    /** <tt>414 Request-URI Too Long</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse REQUEST_URI_TOO_LONG = create(414, Family.CLIENT_ERROR, "414 Request-URI Too Long");
    /** <tt>415 Unsupported Media Type</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse UNSUPPORTED_MEDIA_TYPE = create(415, Family.CLIENT_ERROR, "415 Unsupported Media Type");
    /** <tt>416 Requested Range Not Satisfiable</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse REQUESTED_RANGE_NOT_SATISFIABLE = create(416, Family.CLIENT_ERROR, "416 Requested Range Not Satisfiable");
    /** <tt>417 Expectation Failed</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse EXPECTATION_FAILED = create(417, Family.CLIENT_ERROR, "417 Expectation Failed");
    
   
    // --- 5xx Server Error ---

    /** <tt>500 Server Error</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse INTERNAL_SERVER_ERROR = create(500, Family.SERVER_ERROR, "500 Server Error");    
    /** <tt>501 Not Implemented</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse NOT_IMPLEMENTED = create(501, Family.SERVER_ERROR, "501 Not Implemented");
    /** <tt>502 Bad Gateway</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse BAD_GATEWAY = create(502, Family.SERVER_ERROR, "502 Bad Gateway");
    /** <tt>503 Service Unavailable</tt> (HTTP/1.0 - RFC 1945) */
    public static final StatusResponse SERVICE_UNAVAILABLE = create(503, Family.SERVER_ERROR, "503 Service Unavailable");
    /** <tt>504 Gateway Timeout</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse GATEWAY_TIMEOUT = create(504, Family.SERVER_ERROR, "504 Gateway Timeout");
    /** <tt>505 HTTP Version Not Supported</tt> (HTTP/1.1 - RFC 2616) */
    public static final StatusResponse HTTP_VERSION_NOT_SUPPORTED = create(505, Family.SERVER_ERROR, "505 HTTP Version Not Supported");
    
	
	


    /** El codigo de estatus HTTP. */
    private int statusCode;
    
    /** La familia que pertenece el codigo. */
    private Family family;
    
    /** La descripci&oacute;n del estatus */
    private String reasonPhrase;
    
    
    /**
     * Instancia un nuevo estatus
     *
     * @param statusCode El codigo del estatus
     * @param family La familia que agrupa al estatus
     * @param reasonPhrase La descripci&oacute;n del estatus
     */
    StatusResponse(int statusCode, Family family, String reasonPhrase){
    	this.statusCode = statusCode;
    	this.family = family;
    	this.reasonPhrase = reasonPhrase;
    }


	/**
	 * Devuelve la familia que agrupa el estatus
	 *
	 * @see javax.ws.rs.core.Response.StatusType#getFamily()
	 */
	@Override
	public Family getFamily() {
		return this.family;
	}


	/**
	 * Devuelve la descripci&oacute;n del estatus
	 * 
	 * @see javax.ws.rs.core.Response.StatusType#getReasonPhrase()
	 */
	@Override
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}


	/**
	 * Devuelve el codigo del estatus
	 * 
	 * @see javax.ws.rs.core.Response.StatusType#getStatusCode()
	 */
	@Override
	public int getStatusCode() {
		return this.statusCode;
	}
	
    /**
     * Crea un estatus en base al codigo, la familia que lo agrupa y la descripci&oacute;n indicada
     *
     * @param statusCode el codigo de estatus
     * @param family la familia que lo agrupa
     * @param reasonPhrase la descripci&oacute;n del estatus
     * @return El objeto del estatus de respuesta
     */
    public static StatusResponse create(int statusCode, Family family, String reasonPhrase) {
        return new StatusResponse(statusCode, family, reasonPhrase);
    }
}
