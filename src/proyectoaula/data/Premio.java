package proyectoaula.data;

import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import proyectoaula.database.CConexion;

public class Premio {

    int id;
    private String codigo;
    private String nombredelpremio;
    private String cantidaddepuntos;

    public Premio() {

    }

    public Premio(String codigo, String nombredelpremio, String cantidaddepuntos) {
        this.codigo = codigo;
        this.nombredelpremio = nombredelpremio;
        this.cantidaddepuntos = cantidaddepuntos;
    }

    @Override

    public String toString() {
        return "Premio{"
                + "codigo='" + codigo + '\''
                + ", nombredelpremio='" + nombredelpremio + '\''
                + ", cantidaddepuntos='" + cantidaddepuntos + '\'';
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
    
    public boolean validador(JTextField id, JTextField codigo, JTextField nombre, JTextField puntos) {

        if (codigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Codigo no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (puntos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Puntos no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
       
        String sql = "SELECT codigo FROM Residuos";

        CConexion conexion = new CConexion();

        Statement st = null;
        try {
            st = (Statement) conexion.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int n = 1;
                if (rs.getString(n).equalsIgnoreCase(codigo.getText())) {
                    JOptionPane.showMessageDialog(null, "Codigo ya existente", "Validar", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                n++;
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro en la base de datos", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void crearPremio(JTextField codigo, JTextField nombre, JTextField puntos) {
        CConexion conexion = new CConexion();
        String sql = "insert into Premios(codigo, nombre, puntos) values (?,?,?);";

        //Cargar las variables
        setCodigo(codigo.getText());
        setNombredelpremio(nombre.getText());
        setCantidaddepuntos(puntos.getText());

        try {
            CallableStatement cs = conexion.conecarDB().prepareCall(sql);
            cs.setString(1, getCodigo());
            cs.setString(2, getNombredelpremio());
            cs.setString(3, getCantidaddepuntos());
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

        model.addColumn("ID");
        model.addColumn("Codigo");
        model.addColumn("Nombre");
        model.addColumn("Puntos");

        tabla.setModel(model);

        //consulta DB
        String sql = "select * from Premios;";

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

    public void selecionarPremio(JTable tabla, JTextField id, JTextField codigo, JTextField nombre, JTextField puntos) {
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                id.setText((String) tabla.getValueAt(fila, 0));
                codigo.setText((String) tabla.getValueAt(fila, 1));
                nombre.setText((String) tabla.getValueAt(fila, 2));
                puntos.setText((String) tabla.getValueAt(fila, 3));
            } else {
                JOptionPane.showMessageDialog(null, "Fila no selecionada");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Eror de seleccion, error: " + e.toString());
        }
    }

    public void modificarPremioAdmin(JTextField id, JTextField codigo, JTextField nombre, JTextField puntos) {

        setCodigo(codigo.getText());
        setNombredelpremio(nombre.getText());
        setId(Integer.parseInt(id.getText()));
        setCantidaddepuntos(puntos.getText());

        CConexion conexion = new CConexion();
        String sql = "update Premios set premios.codigo = ?, premios.nombre = ?, premios.puntos = ? where premios.id=?;";

        try {
            CallableStatement cs = conexion.conecarDB().prepareCall(sql);
            cs.setInt(4, getId());
            cs.setString(1, getCodigo());
            cs.setString(2, getNombredelpremio());
            cs.setString(3, getCantidaddepuntos());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modifico correctamente, error: " + e.toString());
        }
    }

    public void eliminar(JTextField id) {

        int codigoId = Integer.parseInt(id.getText());
        if (!id.getText().isEmpty()) {
            setId(codigoId);
            String sql = "delete from Premios where premios.id=?;";
            CConexion conexion = new CConexion();
            try {
                CallableStatement cs = conexion.conecarDB().prepareCall(sql);
                cs.setInt(1, getId());
                cs.execute();
                JOptionPane.showMessageDialog(null, "Se elimino correctamente");
                id.setText("");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "No se elimino correctamente, error: " + e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleciona un resudio para eliminar");
        }
    }

}
