import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class ControlSocios {
	
	static protected Map<Integer,Socio> mapSocios= new TreeMap<Integer,Socio>();
	static int contador=100;
	LocalDate fechaActual;
	
	public Socio buscarSocio(Integer numSocio) {
		Socio buscado=null;
		buscado=mapSocios.get(numSocio);
		return buscado;
	}
	
	public Socio agregarSocio(String nombre, String apellido, Long dni, LocalDate fechaIns, Integer numSocio) {
		Integer cantSocios=mapSocios.size();
		Socio unSocio;
		if(numSocio==null) { //Si no tiene numero de socio es un socio nuevo
			unSocio=new Socio(nombre,apellido,dni,cantSocios+contador, fechaIns);
			mapSocios.put(cantSocios, unSocio);
		}
		else {
			unSocio =buscarSocio(numSocio); //Si encuentra el numero de socio en el Map, actualiza su inscripcion.
			if(unSocio!=null) {
				unSocio.setSocioActual(true);
				fechaActual = LocalDate.now();
				unSocio.setFechaIns(fechaActual);
			}
		}
		return unSocio;
	}
	
	public Socio quitarSocio(Integer numSocio) {
		Socio exSocio=buscarSocio(numSocio);
		if(exSocio!=null) {
			exSocio.setSocioActual(false);
		}
		return exSocio;
	}
	
	public Map<Integer, Socio> getMapSocios() {
		return mapSocios;
	}

	public static void setMapSocios(Map<Integer, Socio> mapSocios) {
		ControlSocios.mapSocios = mapSocios;
	}
}
