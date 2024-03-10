/* TRABAJO REALIZADO POR:
CARLOS MAURICIO MÉNDEZ HERNÁNDEZ (21TE0144)
* 10-02-24 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class EchoCliente extends JFrame {
    /* DECLARACIÓN DE LOS COMPONENETES DEL FRAME */
    private JTextField txtCliente;
    private JButton btnEnviar;
    private JTextPane txtpServidor;
    private JPanel mainPanel;
    private Socket socket;
    private BufferedReader sockInput;
    private PrintWriter sockOutput;

    /* INICIALIZACIÓN DE LOS COMPONENTES DEL FRAME  */
    public EchoCliente() {
        setContentPane(mainPanel);
        setTitle("Echo Cliente");
        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        /* CONEXIÓN A ECHO SERVER */
        byte[] byteIP = new byte[]{52, 43, 121, 77};
        InetAddress ip = null;
        int port = 9001;

        try {
            ip = InetAddress.getByAddress(byteIP);
            socket = new Socket(ip, port);
            sockInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sockOutput = new PrintWriter(socket.getOutputStream(),true);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* ACCIÓN DEL BOTÓN ENVIAR */
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
                txtCliente.setText("");
            }
        });
        /* ACCIÓN DEL  TXTCLIENTE DANDO ENTER */
        txtCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
                txtCliente.setText("");
            }
        });

        /* BUCLE WHILE PARA LEER CONTINUAMENTE DEL SERVIDOR */
        while (true) {
            try {
                String respuesta = sockInput.readLine();
                txtpServidor.setText(txtpServidor.getText() + "\nServidor: " + respuesta);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /* MÉTODO PARA ENVIAR MENSAJE */
    private void enviarMensaje() {
        String lectura = txtCliente.getText();
        sockOutput.println(lectura);
        txtpServidor.setText(txtpServidor.getText() + "\nCliente: " + lectura);
    }

    public static void main(String[] args) {
        new EchoCliente();
    }
}
