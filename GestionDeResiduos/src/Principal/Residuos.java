
package Principal;

public class Residuos {
    private String codigo;
    private String material;
    private String objeto;
    private int puntos;
    
    public Residuos(){
        
    }

    public Residuos(String codigo, String material, String objeto, int puntos) {
        this.codigo = codigo;
        this.material = material;
        this.objeto = objeto;
        this.puntos = puntos;
}@Override
    public String toString() {
        return "Residuo{" +
                "codigo='" + codigo + '\'' +
                ", material='" + material + '\'' +
                ", objeto='" + objeto + '\'' +
                ", puntos=" + puntos +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    
}
