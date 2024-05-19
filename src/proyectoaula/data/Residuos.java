package proyectoaula.data;

import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import proyectoaula.database.CConexion;

public class Residuos {

    private int id;

    private String codigo;
    private String material;
    private String objeto;
    private int puntos;

    public Residuos() {

    }

    public Residuos(String codigo, String material, String objeto, int puntos) {
        this.codigo = codigo;
        this.material = material;
        this.objeto = objeto;
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Residuo{"
                + "codigo='" + codigo + '\''
                + ", material='" + material + '\''
                + ", objeto='" + objeto + '\''
                + ", puntos=" + puntos
                + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void crearResiduo(JTextField codigo, JTextField material, JTextField objeto, JTextField puntos) {
        CConexion conexion = new CConexion();
        String sql = "insert into Residuos(codigo, material, objeto, puntos) values (?,?,?,?);";
        
        //Cargar las variables
        setCodigo(codigo.getText());
        setMaterial(material.getText());
        setObjeto(objeto.getText());
        setPuntos(Integer.parseInt(puntos.getText()));
                
          try {
            CallableStatement cs = conexion.conecarDB().prepareCall(sql);
            cs.setString(1, getCodigo());
            cs.setString(2, getMaterial());
            cs.setString(3, getObjeto());
            cs.setInt(4, getPuntos());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente, error: " + e);
        }
    }

}
