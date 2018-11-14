package mx.gob.segob.dgtic.comun.sicoa.dto;

public class VacacionesAux {

		
	private Integer idDetalle;
	private UsuarioDto idUsuario;
	private VacacionPeriodoDto idVacacion;
	private Integer idResponsable;
	private ArchivoDto idArchivo;
	private EstatusDto idEstatus;
	private String fechaInicio;
	private String fechaFin;
	private Integer dias;
	private String fechaRegistro;
    private String name;
    
    public VacacionesAux(){
    	
    }
    public VacacionesAux(Integer idUsuario, Integer idVacacion, Integer idResponsable, Integer idEstatus, String fechaInicio, String fechaFin, 
    	Integer dias){
    	
    }
	public Integer getIdDetalle() {
		return idDetalle;
	}
	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}
	public UsuarioDto getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(UsuarioDto idUsuario) {
		this.idUsuario = idUsuario;
	}
	public VacacionPeriodoDto getIdVacacion() {
		return idVacacion;
	}
	public void setIdVacacion(VacacionPeriodoDto idVacacion) {
		this.idVacacion = idVacacion;
	}
	public Integer getIdResponsable() {
		return idResponsable;
	}
	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}
	public ArchivoDto getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(ArchivoDto idArchivo) {
		this.idArchivo = idArchivo;
	}
	public EstatusDto getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(EstatusDto idEstatus) {
		this.idEstatus = idEstatus;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
