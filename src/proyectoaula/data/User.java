package proyectoaula.data;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
import proyectoaula.database.CConexion;
import java.sql.CallableStatement;

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
    
    public void crearUsuario(String nombre, String apellido, String cedula, String genero, String carrera) throws SQLException{
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
        cs.setString(5,  getGenero());
        cs.setString(6, getContraseña());
        cs.setString(7, String.valueOf(getPuntos()));
        cs.execute(); 
            JOptionPane.showMessageDialog(null, "Se inserto correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente, error: "+e);
        }                
    }
    
}
