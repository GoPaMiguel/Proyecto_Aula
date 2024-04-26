
package Principal;

import java.util.ArrayList;
import java.util.List;

public class AlmacenarResiduos {
    private List<Residuos> residuos;

    public AlmacenarResiduos() {
        this.residuos = new ArrayList<>();
    }

    public void agregarResiduo(Residuos residuo) {
        residuos.add(residuo);
    }

    public void eliminarResiduo(String codigo) {
        residuos.removeIf(residuo -> residuo.getCodigo().equals(codigo));
    }

    public void modificarResiduo(Residuos residuo, String nuevoCodigo, String nuevoMaterial,String nuevoObjeto, int nuevoPuntos) {
        int index = residuos.indexOf(residuo);
        if (index != -1) {
           Residuos residuoAModificar = residuos.get(index);

        residuoAModificar.setCodigo(nuevoCodigo);
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
        return new ArrayList<>(residuos); // Retorna una copia para evitar modificaciones externas
    }
}
