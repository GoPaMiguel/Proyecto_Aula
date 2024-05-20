package proyectoaula.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
    
     public void listar(JTable tabla) {
        CConexion conexion = new CConexion();

        //Estructura de la tabla
        DefaultTableModel model = new DefaultTableModel();
        TableRowSorter<TableModel> ordenarAlfabeto = new TableRowSorter<TableModel>(model);
        tabla.setRowSorter(ordenarAlfabeto);

        model.addColumn("Codigo");
        model.addColumn("Material");
        model.addColumn("Objeto");
        model.addColumn("Puntos");
        
        tabla.setModel(model);

        //consulta DB
        String sql = "select * from Residuos;";

        String[] datos = new String[4];
        Statement st = null;

        try {
            st = (Statement) conexion.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);                
                model.addRow(datos);
            }
            tabla.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pueden mostrar correctamente, error: " + e.toString());
        }

    }

}
