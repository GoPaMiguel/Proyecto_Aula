package proyectoaula.data;

public class Premio {
    private String id;
    private String nombredelpremio;
    private String cantidaddepuntos;
    
    public Premio () {
        
    }
    
    public Premio (String id, String nombredelpremio, String cantidaddepuntos) {
        this.id = id;
        this.nombredelpremio= nombredelpremio;
        this.cantidaddepuntos= cantidaddepuntos;
    }@Override
      
        public String toString() {
            return "Premio{" +
                    "id='" + id + '\'' +
                    ", nombredelpremio='" + nombredelpremio + '\'' +
                    ", cantidaddepuntos='" + cantidaddepuntos + '\''
                    ;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
