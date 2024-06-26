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
        if (cedula.getText().length() == 8 || cedula.getText().length() == 10) {
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
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese Cedula Valida, con 8 o 10 caracteres", "Validar", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "No se elimino correctamente, error: Selcione un usuario");

        }
    }

    public boolean validarLogin(String usuario, String password) {
        CConexion cx = new CConexion();
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos vacios, por favor llene todos los campos");
            return false;
        } else {
            String sql = "SELECT cedula, id, contraseña, puntos FROM USUARIOS;";
            Statement st = null;
            try {
                st = (Statement) cx.conecarDB().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    if (usuario.equals(rs.getString("cedula")) && password.equals(rs.getString("contraseña"))) {
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
            JOptionPane.showMessageDialog(null, "No se modifico, error: " + e.toString());
        }
    }

    public void cargarPerfil(int id, JTextField nombre, JTextField apellido, JTextField cedula, JTextField carrera, JComboBox genero, JPasswordField contraseña, JTextField puntos) {

        CConexion conexion = new CConexion();
        String sql = "select * from usuarios where Usuarios.id=" + id + ";";
        Statement st = null;
        try {
            st = (Statement) conexion.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombre.setText(rs.getString("nombre"));
                apellido.setText(rs.getString("apellido"));
                carrera.setText(rs.getString("carrera"));
                contraseña.setText(rs.getString("contraseña"));
                cedula.setText(rs.getString("cedula"));
                int punto = rs.getInt("puntos");
                puntos.setText(String.valueOf(punto));
                genero.setSelectedItem(rs.getString("genero"));
                setPuntos(punto);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede acceder correctamente, error: " + e.toString());
        }
    }

    public void selecionarTable(JTable tabla, JTextField puntos) {
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                puntos.setText(tabla.getValueAt(fila, 4).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no selecionada");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Eror de seleccion, error: " + e.toString());
        }
    }

    public void Calcular(JTextField peso, JTextField puntos, JTextField puntosCal) {
        if (ValidorPuntos(peso, puntos)) {
            int total = 0;
            try {
                total = Math.round((Integer.parseInt(peso.getText()) * Integer.parseInt(puntos.getText())));
                if (total <= 0) {
                    JOptionPane.showMessageDialog(null, "Solo acepta numero mayores a 0");
                } else {
                    puntosCal.setText(String.valueOf(total));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Solo acepta numero mayores a 0");
            }
        }
    }

    public boolean ValidorPuntos(JTextField peso, JTextField puntos) {

        if (peso.getText().isEmpty() || puntos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione un Peso (KG) y selecione una tabla");
            return false;
        }
        return true;
    }

    public void reciclar(int id, int puntos, int puntoExi) {
        CConexion cx = new CConexion();
        String sql = "update Usuarios set usuarios.puntos = ? where Usuarios.id=" + id + ";";
        try {
            CallableStatement cs = cx.conecarDB().prepareCall(sql);
            cs.setInt(1, (puntos + puntoExi));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se Agrego correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se Agrego los puntos, error: " + e.toString());
        }
    }

    public void cambiarPuntos(int id) {
        CConexion cx = new CConexion();
        String sql = "update Usuarios set usuarios.puntos = ? where Usuarios.id=" + id + ";";
        try {
            CallableStatement cs = cx.conecarDB().prepareCall(sql);
            cs.setInt(1, (getPuntos()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se Consiguio correctamente el premio");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se Agrego los puntos, error: " + e.toString());
        }
    }

    public void obtenerPuntosDB(int id) {
        CConexion cx = new CConexion();
        String sql = "select puntos from usuarios where Usuarios.id=" + id + ";";
        Statement st = null;

        try {
            st = (Statement) cx.conecarDB().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int punto = rs.getInt("puntos");
                setPuntos(punto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede acceder correctamente, error: " + e.toString());
        }
    }

    public void calcularPuntosrestantes(JTextField puntosActu, JTextField puntoNec) {
        int tusPuntos = Integer.parseInt(puntosActu.getText());
        int puntosNecesarios = Integer.parseInt(puntoNec.getText());

        if (tusPuntos == puntosNecesarios) {
            setPuntos(0);
        } else if (tusPuntos > puntosNecesarios) {
            int total = (tusPuntos - puntosNecesarios);
            setPuntos(total);
        } else if (tusPuntos < puntosNecesarios) {
            JOptionPane.showMessageDialog(null, "No te tienes suficientes puntos");
        }
    }

}
