package proyectoaula.data;

import java.util.ArrayList;
import java.util.List;

public class Almacenarpremio {
    private static List<Premio> premios;
    
    
    public Almacenarpremio() {
        if (premios == null) {
            premios = new ArrayList<>();
        }   
    }
    
    public boolean insertarPremio ( Premio Premio) {
        boolean exito = true;
        for (var p : premios){
            if (p.getId().equals(Premio.getId())){
                return false;
            } else {
            }
        }
        if (exito) premios.add(Premio);
        return exito;
    }
    
    public void eliminarPremio(String codigo) {
        premios.removeIf(premio -> premio.getId().equals(codigo));
    }
    
    public void modificarPremio(Premio premio, String nuevonombre, String nuevospuntos){
        int index = premios.indexOf(premio);
        if (index != -1) {
            Premio premioAModificar = premios.get(index);
            premioAModificar.setNombredelpremio(nuevonombre);
            premioAModificar.setCantidaddepuntos(nuevospuntos);
            
        }
        }
        
    public Premio buscarPremio(String id){
        return premios.stream()
                .filter(residuo -> residuo.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Premio> obtenerResiduos() {
        return premios; // Retorna una copia para evitar modificaciones externas
    }
    }