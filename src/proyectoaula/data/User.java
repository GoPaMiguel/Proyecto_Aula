package proyectoaula.data;

import java.awt.HeadlessException;
import java.beans.Statement.*;
import java.sql.*;
import java.sql.Statement.*;
import java.util.*;
import javax.swing.JOptionPane;
import proyectoaula.database.CConexion;
import java.sql.CallableStatement;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author migopa
 */
public class User {

    public int id;
    public String nombre;//
    public String carrera; //       
    public String numeroIdentificacion;//
    public String apellido;//
    public String genero;
    public String contraseña;
    public int puntos = 0;
    public static HashMap<String, User> usuarios;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void crearUsuario(String nombre, String apellido, String cedula, String genero, String carrera) throws SQLException {
        setNombre(nombre);
        setApellido(apellido);
        setNumeroIdentificacion(cedula);
        setCarrera(carrera);
        setGenero(genero);
        setContraseña(cedula);

        CConexion conexion = new CConexion();
        String consulta = "insert into Usuarios(nombre, apellido, cedula, carrera, genero, contraseña, puntos) values (?,?,?,?,?,?,?);";
        try {
            CallableStatement cs = conexion.conecarDB().prepareCall(consulta);
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getNumeroIdentificacion());
            cs.setString(4, getCarrera());
            cs.setString(5, getGenero());
            cs.setString(6, getContraseña());
            cs.setString(7, String.valueOf(getPuntos()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente, error: " + e);
        }
    }

    public void listarUsuarios(JTable tabla) {
        CConexion conexion = new CConexion();

        //Estructura de la tabla
        DefaultTableModel model = new DefaultTableModel();
        TableRowSorter<TableModel> ordenarAlfabeto = new TableRowSorter<TableModel>(model);
        tabla.setRowSorter(ordenarAlfabeto);

        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Cedula");
        model.addColumn("Carrera");
        model.addColumn("Genero");
        model.addColumn("Puntos");
        tabla.setModel(model);

        //consulta DB
        String sql = "SELECT * FROM USUARIOS";

        String[] datos = new String[7];
        Statement st = null;

        try {
            st = (Statement) conexion.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(8);

                model.addRow(datos);
            }
            tabla.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pueden mostrar correctamente, error: " + e.toString());
        }

    }

    public void secionarUsuario(JTable tabla, JTextField id, JTextField cedula, JTextField nombre, JTextField apellido, JTextField carrera, JComboBox genero) {

        CConexion conexion = new CConexion();

        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                id.setText((String) tabla.getValueAt(fila, 0));
                cedula.setText((String) tabla.getValueAt(fila, 1));
                nombre.setText((String) tabla.getValueAt(fila, 2));
                apellido.setText((String) tabla.getValueAt(fila, 3));
                carrera.setText((String) tabla.getValueAt(fila, 4));
                genero.setSelectedItem(tabla.getValueAt(fila, 5));
            } else {
                JOptionPane.showMessageDialog(null, "Fila no selecionada");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Eror de seleccion, error: " + e.toString());            
        }

    }

}
