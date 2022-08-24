package User;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Common.Message;
import Entity.User;
import Service.UserService;

@SuppressWarnings("serial")
public class frmLogin extends JFrame {

    private final JPanel contentPane;
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private UserService userService;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frmLogin frame = new frmLogin();
                frame.setVisible(true);
            } catch (Exception e) {
            }
        });
    }

    /**
     * Create the frame.
     */
    public frmLogin() {
        setTitle("Ứng dụng chat");
        setResizable(false);
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                }
                break;
            }
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 454, 265);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblLogin = new JLabel("ĐĂNG NHẬP");
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblUsername = new JLabel("Tài khoản:");

        JLabel lblPassword = new JLabel("Mật khẩu:");

        txtUsername = new JTextField();
        txtUsername.setColumns(10);

        txtPassword = new JPasswordField(60);
        txtPassword.setEchoChar('*');
        txtPassword.setColumns(10);

        InnerClassActionListener actionListener = new InnerClassActionListener();
        JButton btnLogin = new JButton("Đăng nhập");
        getRootPane().setDefaultButton(btnLogin);
        btnLogin.setActionCommand("Login");
        btnLogin.addActionListener(actionListener);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setActionCommand("Register");
        btnRegister.addActionListener(actionListener);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(166, Short.MAX_VALUE)
                                .addComponent(lblLogin)
                                .addGap(163))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(74)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(lblPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblUsername, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(btnRegister, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                                        .addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                        .addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                                .addGap(106))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(18)
                                .addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblUsername)
                                        .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblPassword)
                                        .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(24)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(btnRegister, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }

    /**
     * This is inner class listener for action listener
     */
    private class InnerClassActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String strActionCommand = ae.getActionCommand();
            Component component = (Component) ae.getSource();
            JFrame frame = (JFrame) SwingUtilities.getRoot(component);
            if (strActionCommand.equals("Login")) {
                if (txtUsername.getText().equals("") && txtPassword.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập tên tài khoản và mật khẩu!", "Thông báo", JOptionPane.OK_OPTION);
                    return;
                }
                if (txtUsername.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập tên tài khoản!", "Thông báo", JOptionPane.OK_OPTION);
                    return;
                }
                if (txtPassword.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập mật khẩu!", "Thông báo", JOptionPane.OK_OPTION);
                    return;
                }

                loginToSystem();
            }
            if (strActionCommand.equals("Register")) {
                dlgRegister dlgRegister = new dlgRegister();
                dlgRegister.setModal(true);
                dlgRegister.setLocationRelativeTo(frame);
                dlgRegister.setVisible(true);
            }
        }
    }

    private void loginToSystem() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        int userId = UserService.checkAuthenticate(username, password);
        if (userId == -1) {
            JOptionPane.showMessageDialog(this, "Sai tên tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.OK_OPTION);
            return;
        }

        // get server info
        User userInfo = UserService.getUserInfo(userId);

        // check this user has already login (connected) or not
        int status = userInfo.getStatus();
        // status is "Connected"
        if (status == 1) {
            JOptionPane.showMessageDialog(this, "User này đã đăng nhập vào hệ thống. Vui lòng kiểm tra lại!", "Lỗi", JOptionPane.OK_OPTION);
            return;
        }

        // connect to server
        userService = new UserService();

        String hostAddress = userInfo.getIp();
        int port = userInfo.getPort();
        boolean isSuccess = userService.connectToServer(hostAddress, port);
        if (!isSuccess) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!", "Lỗi", JOptionPane.OK_OPTION);
        } else {
            // if this is the first time user login to system
            if (status == 2) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                dlgChangePassword dlgChangePassword = new dlgChangePassword();
                dlgChangePassword.setModal(true);
                dlgChangePassword.setUser(userInfo);
                dlgChangePassword.setLocationRelativeTo(this);
                dlgChangePassword.setVisible(true);
            } else {
                // update the status of user
                int online = 1;
                int result = UserService.updateUserStatus(userId, online);
                if (result < 1) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thất bại!", "Thông báo", JOptionPane.OK_OPTION);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // send user info to server
                Message message = new Message("ONLINE", userInfo.getUsername(), null, null);
                userService.sendToServer(message);

                // listen reponse from server
                userService.start();

                this.setVisible(false);
                frmMain frmMain = new frmMain();
                frmMain.setUser(userInfo);
                frmMain.setUserService(userService);
                frmMain.setVisible(true);
            }
        }
    }
}
