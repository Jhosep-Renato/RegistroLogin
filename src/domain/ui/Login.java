package domain.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIniciar;
    private Registrar registrar = new Registrar();
    public Login(){
        initComponent();

        btnIniciar.addActionListener(ActionListener -> {
            validar();
        });
    }
    public JPanel getPanel1(){
        return panel1;
    }

    public void initComponent(){

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setContentPane(this.getPanel1());
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setSize(new Dimension(500, 300));
            frame.setVisible(true);
        });
    }

    private void validar(){

        String sql = "SELECT * FROM Registro WHERE usuario = ? AND password = ?";
        try(Connection connection = registrar.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, txtUsuario.getText());
            statement.setString(2, String.valueOf(txtPassword.getPassword()));

            ResultSet result = statement.executeQuery();

            if(result.next()){
                JOptionPane.showMessageDialog(this.panel1, "SESION INICIADA");
            }
            else{
                JOptionPane.showMessageDialog(this.panel1, "LA CUENTA NO EXISTE");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this.panel1, "ERROR EN LA BD");
        }
    }
}
