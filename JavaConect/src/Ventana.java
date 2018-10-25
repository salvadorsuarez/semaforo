//Liberias que se ocupan para la conexion con Arduino
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
// libreria para las funciones de botones y cajas de texto
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Duran
 */
public class Ventana extends JFrame {

    private JPanel contentPane;//panel para la vista
    private JTextField textField; //caja de texto para ingresar al serial COM
    JTextField jTextFieldMensaje;//caja de texto para ingresar el mensaje
    JTextField txtcampo = new JTextField("", 20);
    Enumeration puertos_libres = null;//puestos serial libres para su identificacion
    CommPortIdentifier port = null; //identificacion del puerto
    SerialPort puerto_ser = null; //el puerto a utilizar para trabajar
    private OutputStream Output = null; 
    InputStream in = null;
    int temperatura = 10;
    Thread timer;
    JLabel lblNewLabel;
    JButton btnNewButton, btnNewButton_1, btnEnviar;//hacemos los botones

    private void EnviarDatos(String date) {//metodo para enviar el mensaje
        try {
            Output.write(date.getBytes());//envia el mensaje
        } catch (IOException e) {//por si ocurre un error
            System.exit(ERROR);
        }
    }

    public void letras() {
        String cadena = ""; //Se declara la variable que guardará el //mensaje a enviar
        cadena = jTextFieldMensaje.getText(); //Se asigna el //texto del TextField a la variable cadena
    }

    public Ventana() {//metodo para conexion
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//panel del Jframe
        setBounds(100, 100, 636, 365);//posicion del frame
        contentPane = new JPanel();//panel 
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));//posicion a usar
        setContentPane(contentPane);
        contentPane.setLayout(null);//usamos el panel para poner los demas funciones
        btnNewButton = new JButton("Conectar");//boton conectar
        btnNewButton.addActionListener(new ActionListener() {//funcion para avilitar funcion
            public void actionPerformed(ActionEvent e) {//evento del boton conectar a el serial
                puertos_libres = CommPortIdentifier.getPortIdentifiers();//identifica el puerto 
                int aux = 0;//variable aux 
                while (puertos_libres.hasMoreElements()) {//siclo para identificar los puertos
                    port = (CommPortIdentifier) puertos_libres.nextElement();//ve los diferentes puertos
                    int type = port.getPortType();//
                    if (port.getName().equals(textField.getText())) {//si el puerto consode con el del arduino hace la conexion
                        try {
                            puerto_ser = (SerialPort) port.open("puerto serial", 2000);
                            int baudRate = 9600; // 9600bps

                            puerto_ser.setSerialPortParams(//parametros para el puerto
                                    baudRate,
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                            puerto_ser.setDTR(true);
                            Output = puerto_ser.getOutputStream();
                            in = puerto_ser.getInputStream();
                            btnNewButton_1.setEnabled(true);
                            btnNewButton.setEnabled(false);
                        } catch (IOException e1) {//pisobles caso de error
                        } catch (PortInUseException e1) {
                            e1.printStackTrace();
                        } catch (UnsupportedCommOperationException e1) {
                            e1.printStackTrace();
                        }

                        break;
                    }
                }
            }
        });
        btnNewButton.setBounds(38, 63, 89, 23);//posicionar boton
        contentPane.add(btnNewButton);//agregar al panel 

        btnNewButton_1 = new JButton("Desconectar");//boton para desconectar
        btnNewButton_1.setEnabled(false);//el botn no esta avilitado hasta que se ingrese el COM
        btnNewButton_1.addActionListener(new ActionListener() {//metodo para el boton
            public void actionPerformed(ActionEvent arg0) {//evento para el boton funcione
                puerto_ser.close();//sierra el puerto cuando presionamos el boton
                btnNewButton_1.setEnabled(false);//boton desabilitado
                btnNewButton.setEnabled(true);//boton avilitado
            }
        });        
        btnNewButton_1.setBounds(205, 63, 128, 23);//posicion del boton
        contentPane.add(btnNewButton_1);//agregado al panel


        btnEnviar = new JButton("Enviar");//boton enviar
        btnEnviar.addActionListener(new ActionListener() {//funcion del boton
            public void actionPerformed(ActionEvent e) {//evento del boton enviar texto
                EnviarDatos(jTextFieldMensaje.getText());//optiene el texto en la caja de texto
                jTextFieldMensaje.setText("");//texto vacio
                letras();//funcion letra para ver que se optiene
            }
        });
        btnEnviar.setBounds(205, 163, 228, 23);//posicion del boton
        contentPane.add(btnEnviar);//agregado al panel

        textField = new JTextField();//caja de texto
        textField.setBounds(38, 32, 86, 20);//posicion
        contentPane.add(textField);//se agrega al panel para ser visible
        textField.setColumns(10);

        jTextFieldMensaje = new JTextField();//caja de texto
        jTextFieldMensaje.setBounds(138, 32, 186, 20);//posicion
        contentPane.add(jTextFieldMensaje);//se agrega al panel para ser visible
        jTextFieldMensaje.setColumns(10);

        JLabel lblPuertoCom = new JLabel("Puerto COM");//donde se ve para el puerto
        lblPuertoCom.setBounds(37, 11, 90, 14);
        contentPane.add(lblPuertoCom);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {//para correr el programa
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
