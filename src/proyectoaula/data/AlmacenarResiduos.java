
package proyectoaula.data;

import java.util.ArrayList;
import java.util.List;

public class AlmacenarResiduos {
    private static List<Residuos> residuos;

    public AlmacenarResiduos() {
        if(residuos == null){
             residuos = new ArrayList<>();
        }
    }

    public boolean agregarResiduo(Residuos residuo) {
        boolean exito = true;
        for(var r : residuos){
            if (r.getCodigo().equals(residuo.getCodigo())){
               return false;
            }
        }
        if(exito) residuos.add(residuo);
        return exito;
    }

    public void eliminarResiduo(String codigo) {
        residuos.removeIf(residuo -> residuo.getCodigo().equals(codigo));
    }

    public void modificarResiduo(Residuos residuo, String nuevoMaterial,String nuevoObjeto, int nuevoPuntos) {
        int index = residuos.indexOf(residuo);
        if (index != -1) {
           Residuos residuoAModificar = residuos.get(index);

        residuoAModificar.setMaterial(nuevoMaterial);
        residuoAModificar.setObjeto(nuevoObjeto);
        residuoAModificar.setPuntos(nuevoPuntos);
  }
}

    public Residuos buscarResiduo(String codigo) {
        return residuos.stream()
                .filter(residuo -> residuo.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Residuos> obtenerResiduos() {
        return residuos; // Retorna una copia para evitar modificaciones externas
    }
}
