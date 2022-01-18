
public abstract class Persona {
	
	protected String nombre, apellido;
	protected Long dni;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + nombre + "- Apellido: " + apellido + " - DNI: " + dni + "]";
	}
	
}
