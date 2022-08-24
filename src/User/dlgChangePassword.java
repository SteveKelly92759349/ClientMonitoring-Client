package User;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Entity.User;
import Service.UserService;
import Util.StringUtil;

@SuppressWarnings("serial")
public class dlgChangePassword extends JDialog {

    private JPanel contentPane;
    private JPasswordField txtNewPassword;
    private JPasswordField txtRetype;
    private JPasswordField txtCurrentPassword;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Create the frame.
     */
    public dlgChangePassword() {
        setTitle("App chat");
        setModal(true);
        setResizable(false);
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                }
                break;
            }
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 408, 336);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblChangePassword = new JLabel("Thay đổi mật khẩu");
        lblChangePassword.setForeground(Color.BLUE);
        lblChangePassword.setFont(new Font("Tahoma", Font.BOLD, 18));

        JLabel lblCurrentPassword = new JLabel("Mật khẩu hiện tại:");

        JLabel lblNewPassword = new JLabel("Mật khẩu mới:");

        JLabel lblRetype = new JLabel("Xác nhận mật khẩu:");

        txtNewPassword = new JPasswordField();
        txtNewPassword.setEchoChar('*');
        txtNewPassword.setColumns(10);

        txtRetype = new JPasswordField();
        txtRetype.setEchoChar('*');
        txtRetype.setColumns(10);

        InnerClassActionListener actionListener = new InnerClassActionListener();
        JButton btnUpdate = new JButton("Cập nhật");
        getRootPane().setDefaultButton(btnUpdate);
        btnUpdate.setActionCommand("Update");
        btnUpdate.addActionListener(actionListener);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(actionListener);

        txtCurrentPassword = new JPasswordField();
        txtCurrentPassword.setEchoChar('*');
        txtCurrentPassword.setColumns(10);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(24)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewPassword, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                        .addComponent(lblCurrentPassword)
                                        .addComponent(lblRetype, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnUpdate)
                                                .addGap(18)
                                                .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtCurrentPassword, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(txtNewPassword, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(txtRetype, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                .addGap(56))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(95)
                                .addComponent(lblChangePassword)
                                .addContainerGap(105, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(21)
                                .addComponent(lblChangePassword, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(txtCurrentPassword, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                        .addComponent(lblCurrentPassword, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(txtNewPassword, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                        .addComponent(lblNewPassword))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(txtRetype, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                        .addComponent(lblRetype, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnCancel)
                                        .addComponent(btnUpdate))
                                .addGap(33))
        );
        contentPane.setLayout(gl_contentPane);
    }

    /**
     * This is inner class listener for action listener
     */
    private class InnerClassActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Component component = (Component) ae.getSource();
            JDialog dlgChangePassword = (JDialog) SwingUtilities.getRoot(component);
            String strActionCommand = ae.getActionCommand();
            if (strActionCommand.equals("Update")) {
                if (txtCurrentPassword.getPassword().length != 0 && txtNewPassword.getPassword().length != 0 && txtRetype.getPassword().length != 0) {
                    String currentPassword = UserService.getUserInfo(user.getUserId()).getPassword();
                    String strInput = String.valueOf(txtCurrentPassword.getPassword());
                    if (!StringUtil.checkPassword(strInput, currentPassword)) {
                        JOptionPane.showMessageDialog(dlgChangePassword, "Mật khẩu hiện tại không chính xác!", "Thông báo", JOptionPane.OK_OPTION);
                        return;
                    }
                    if (!String.valueOf(txtNewPassword.getPassword()).equals(String.valueOf(txtRetype.getPassword()))) {
                        JOptionPane.showMessageDialog(dlgChangePassword, "Mật khẩu mới và mật khẩu xác nhận không trùng khớp!", "Thông báo", JOptionPane.OK_OPTION);
                        return;
                    }
                    if (String.valueOf(txtNewPassword.getPassword()).equals(String.valueOf(txtCurrentPassword.getPassword()))) {
                        JOptionPane.showMessageDialog(dlgChangePassword, "Mật khẩu mới không được trùng với mật khẩu hiện tại!", "Thông báo", JOptionPane.OK_OPTION);
                        return;
                    }
                    changePassword();
                    dlgChangePassword.dispose();
                } else {
                    JOptionPane.showMessageDialog(dlgChangePassword, "Vui lòng nhập đầy đủ các trường thông tin!", "Thông báo", JOptionPane.OK_OPTION);
                    return;
                }
            }
            if (strActionCommand.equals("Cancel")) {
                dlgChangePassword.dispose();
            }
        }
    }

    private void changePassword() {
        int status;
        if (user.getStatus() == 2) {
            status = 0;
        } else {
            status = 1;
        }
        String newPassword = String.valueOf(txtNewPassword.getPassword());
        // hashing password
        String HashPassword = StringUtil.hashPassword(newPassword);
        int result = UserService.changePassword(user.getUserId(), HashPassword, status);
        if (result < 1) {
            JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thất bại!", "Thông báo", JOptionPane.OK_OPTION);
            return;
        }
        JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
}
