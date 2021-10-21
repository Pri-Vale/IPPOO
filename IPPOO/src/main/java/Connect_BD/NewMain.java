package Connect_BD;

/**
 * Ejecuta la conexión a la base de datos
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.0
 * @since 1.0
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Conexion conexion1= new Conexion();
        
        conexion1.Conectar_a_base();
    }
    
}
