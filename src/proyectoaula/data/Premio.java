package proyectoaula.data;

public class Premio {
    int id;
    private String codigo;
    private String nombredelpremio;
    private String cantidaddepuntos;
    
    public Premio () {
        
    }
    
    public Premio (String codigo, String nombredelpremio, String cantidaddepuntos) {
        this.codigo = codigo;
        this.nombredelpremio= nombredelpremio;
        this.cantidaddepuntos= cantidaddepuntos;
    }@Override
      
        public String toString() {
            return "Premio{" +
                    "codigo='" + codigo + '\'' +
                    ", nombredelpremio='" + nombredelpremio + '\'' +
                    ", cantidaddepuntos='" + cantidaddepuntos + '\''
                    ;
        }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String id) {
        this.codigo = id;
    }

    public String getNombredelpremio() {
        return nombredelpremio;
    }

    public void setNombredelpremio(String nombredelpremio) {
        this.nombredelpremio = nombredelpremio;
    }

    public String getCantidaddepuntos() {
        return cantidaddepuntos;
    }

    public void setCantidaddepuntos(String cantidaddepuntos) {
        this.cantidaddepuntos = cantidaddepuntos;
    }
}
