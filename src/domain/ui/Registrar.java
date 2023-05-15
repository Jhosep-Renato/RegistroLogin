package domain.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Registrar {
    private JPanel panel;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnLogin;

    public Registrar(){
        btnLogin.addActionListener(ActionListener -> {
            Login login = new Login();
        });

        btnRegistrar.addActionListener(ActionListener -> {
            registrar();
        });

        btnLimpiar.addActionListener(ActionListener -> {
            limpiar();
        });
    }
    public JPanel getPanel(){
        return panel;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login", "root", "truelove06");
    }

    public void registrar(){

        String sql = "INSERT INTO Registro (usuario, password) VALUES (?, ?)";

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, txtUsuario.getText());
            statement.setString(2, String.valueOf(txtPassword.getPassword()));

            if(validar()){
                JOptionPane.showMessageDialog(this.panel, "Existe un usuario con el mismo nombre");
            }
            else{
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this.panel, "Registro exitoso");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this.panel, "Se produjo un error");
        }
    }

    public boolean validar() throws SQLException {

        String sql = "SELECT * FROM Registro WHERE usuario = ?";

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, txtUsuario.getText());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public void initComponent(){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();

            this.getPanel().setPreferredSize(new Dimension(500, 300));
            frame.setContentPane(this.getPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
        });
    }

    public void limpiar(){
        txtUsuario.setText("");
        txtPassword.setText("");
    }
}