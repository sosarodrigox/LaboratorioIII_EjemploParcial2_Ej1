import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Socio extends Persona{
	
	private Integer nroSocio;
	private LocalDate fechaIns;
	private Boolean socioActual;
	DateTimeFormatter dForma = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Socio(String nombre, String apellido, Long dni, Integer nroSocio, LocalDate fechaIns) {
		super();
		super.nombre=nombre;
		super.apellido=apellido;
		super.dni=dni;
		this.nroSocio = nroSocio;
		this.fechaIns = fechaIns;
		this.socioActual = true;
	}
	
	public Socio(String nombre, String apellido, Long dni, Integer nroSocio, LocalDate fechaIns, Boolean socioActual) {
		super();
		super.nombre=nombre;
		super.apellido=apellido;
		super.dni=dni;
		this.nroSocio = nroSocio;
		this.fechaIns = fechaIns;
		this.socioActual = socioActual;
	}
	
	public Integer getNroSocio() {
		return nroSocio;
	}
	public void setNroSocio(Integer nroSocio) {
		this.nroSocio = nroSocio;
	}
	public LocalDate getFechaIns() {
		return fechaIns;
	}
	public void setFechaIns(LocalDate fechaIns) {
		this.fechaIns = fechaIns;
	}
	public Boolean getSocioActual() {
		return socioActual;
	}
	public void setSocioActual(Boolean socioActual) {
		this.socioActual = socioActual;
	}

	@Override
	public String toString() {
		return nombre + ";" + apellido + ";" + dni + ";" + nroSocio+ ";" + fechaIns.format(dForma) + ";" + socioActual;
	}
	
	
	

}
