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
//import proyectoaula.igu.PanelEstudiantes.MenuEstudiante;

/**
 *
 * @author migopa
 */
public class User {

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
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
            JOptionPane.showMessageDialog(null, "No se inserto correctamente, error: " + e.toString());
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
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                id.setText((String) tabla.getValueAt(fila, 0));
                cedula.setText((String) tabla.getValueAt(fila, 3));
                nombre.setText((String) tabla.getValueAt(fila, 1));
                apellido.setText((String) tabla.getValueAt(fila, 2));
                carrera.setText((String) tabla.getValueAt(fila, 4));
                genero.setSelectedItem(tabla.getValueAt(fila, 5));
            } else {
                JOptionPane.showMessageDialog(null, "Fila no selecionada");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Eror de seleccion, error: " + e.toString());
        }
    }

    public boolean validador(JTextField cedula, JTextField nombre, JTextField apellido, JTextField carrera, JComboBox genero) {

        if (cedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Cedula no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (apellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Apellido no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (carrera.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo Carrera no puede estar vacio", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (genero.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(null, "Seleciona un genero", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean validarCrear(JTextField cedula) {
        String sql = "SELECT cedula FROM USUARIOS";
        CConexion conexion = new CConexion();

        Statement st = null;
        try {
            st = (Statement) conexion.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int n = 1;
                if (rs.getString(n).equalsIgnoreCase(cedula.getText())) {
                    JOptionPane.showMessageDialog(null, "Cedula ya existente", "Validar", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                n++;
            }
            return true;
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro en la base de datos", "Validar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void modificarUsuario(int id, JTextField nombre, JTextField apellido, JTextField carrera, JComboBox genero) {
        setNombre(nombre.getText());
        setApellido(apellido.getText());
        setCarrera(carrera.getText());
        setGenero(genero.getSelectedItem().toString());
        setId(id);

        CConexion conexion = new CConexion();
        String sql = "update Usuarios set usuarios.nombre = ?, usuarios.apellido = ?, usuarios.carrera = ?, usuarios.genero = ? where Usuarios.id=?;";

        try {
            CallableStatement cs = conexion.conecarDB().prepareCall(sql);
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getCarrera());
            cs.setString(4, getGenero());
            cs.setInt(5, getId());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modifico correctamente, error: " + e.toString());
        }
    }

    public void limpiarcampos(JTextField id, JTextField cedula, JTextField nombre, JTextField apellido, JTextField carrera, JComboBox genero) {
        id.setText("");
        cedula.setText("");
        nombre.setText("");
        apellido.setText("");
        carrera.setText("");
        genero.setSelectedIndex(0);
    }

    public void eliminar(JTextField id) {
        int codigo = Integer.parseInt(id.getText());
        if (!id.getText().isEmpty()) {
            setId(codigo);
            String sql = "delete from Usuarios where usuarios.id=?;";
            CConexion conexion = new CConexion();
            try {
                CallableStatement cs = conexion.conecarDB().prepareCall(sql);
                cs.setInt(1, getId());
                cs.execute();
                JOptionPane.showMessageDialog(null, "Se elimino correctamente");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "No se elimino correctamente, error: " + e.toString());

            }
        } else {
        }
    }

    public boolean validarUsuario(JTextField usuario) {
        CConexion cx = new CConexion();
        if (usuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos vacios, por favor llene todos los campos");
            return false;
        } else {
            String sql = "SELECT cedula, id FROM USUARIOS;";
            Statement st = null;
            try {
                st = (Statement) cx.conecarDB().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    rs.getString("cedula");
                    if (rs.getString("cedula").equals(usuario.getText())) {
                        setId(rs.getInt("id"));
                        return true;
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Usuario incorrecto");
                return false;
            }
        }
        return false;
    }

    public boolean validarcontraseña(JTextField contraseña) {
        CConexion cx = new CConexion();
        if (contraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese una contraseña");
            return false;
        } else {
            String sql = "SELECT cedula FROM USUARIOS";
            Statement st = null;
            try {
                st = (Statement) cx.conecarDB().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    int n = 1;
                    if (rs.getString(n).equals(contraseña.getText())) {
                        System.out.println("SII");
                        return true;
                    } else {
                        n++;
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Cntraseña incorrecta");
                return false;
            }
        }
        return false;
    }

    public void modificarPerfil(int id, JTextField nombre, JTextField apellido, JTextField carrera, JComboBox genero, JPasswordField contraseña) {
        setNombre(nombre.getText());
        setApellido(apellido.getText());
        setCarrera(carrera.getText());
        setGenero(genero.getSelectedItem().toString());
        setContraseña(contraseña.getText());
        setId(id);

        CConexion conexion = new CConexion();
        String sql = "update Usuarios set usuarios.nombre = ?, usuarios.apellido = ?, usuarios.carrera = ?, usuarios.genero = ?, usuarios.contraseña = ? where Usuarios.id=?;";

        try {
            CallableStatement cs = conexion.conecarDB().prepareCall(sql);
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getCarrera());
            cs.setString(4, getGenero());
            cs.setString(5, getContraseña());
            cs.setInt(6, getId());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modifico correctamente, error: " + e.toString());
        }               
    }
    
    public void cargarPerfil(int id, JTextField nombre, JTextField apellido, JTextField carrera, JComboBox genero, JPasswordField contraseña){
        
        CConexion conexion = new CConexion();
        String sql = "select * from usuarios where Usuarios.id=?;";
        Statement st = null;
 
        try {
            st = (Statement) conexion.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            setNombre(rs.getString("nombre"));
            setApellido(rs.getString("apellido"));
            setCarrera(rs.getString("carrera"));
            setGenero(rs.getString("genero"));
            setContraseña(rs.getString("contraseña"));            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede acceder correctamente, error: " + e.toString());
        }               
        
        nombre.setText(getNombre());
        apellido.setText(getApellido());
        carrera.setText(getCarrera());
        contraseña.setText(getContraseña());
        genero.setSelectedItem(getGenero());
    }

}
