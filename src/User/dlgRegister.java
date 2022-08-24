package User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Entity.User;
import Service.UserService;
import Util.StringUtil;

@SuppressWarnings("serial")
public class dlgRegister extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField txtRetype;
	private JPasswordField txtPassword;
	private JLabel lblPassword;
	private JLabel lblRetype;
	private JTextField txtFullName;
	private JTextField txtUsername;
	private JLabel lblngKyTai;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dlgRegister dialog = new dlgRegister();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public dlgRegister() {
		setTitle("Ứng dụng chat");
		setBounds(100, 100, 321, 322);
		setResizable(false);
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
                break;
            }
        }
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			txtRetype = new JPasswordField();
			txtRetype.setEchoChar('*');
			txtRetype.setColumns(10);
		}
		{
			txtPassword = new JPasswordField();
			txtPassword.setEchoChar('*');
			txtPassword.setColumns(10);
		}
		{
			lblPassword = new JLabel("Mật khẩu:");
		}
		{
			lblRetype = new JLabel("Xác nhận mật khẩu:");
		}
		
		JLabel lblUsername = new JLabel("Tên đăng nhập:");
		
		JLabel lblFullName = new JLabel("Họ tên:");
		
		txtFullName = new JTextField();
		txtFullName.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		lblngKyTai = new JLabel("THÔNG TIN TÀI KHOẢN");
		lblngKyTai.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblngKyTai.setForeground(Color.BLUE);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRetype)
						.addComponent(lblFullName, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
						.addComponent(txtFullName, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
						.addComponent(txtPassword)
						.addComponent(txtRetype))
					.addContainerGap(117, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addGap(51)
					.addComponent(lblngKyTai, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(40))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(lblngKyTai)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFullName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFullName))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRetype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRetype, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
					.addGap(47))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			InnerClassActionListener actionListener = new InnerClassActionListener();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegister = new JButton("Đăng ký");
				btnRegister.setActionCommand("register");
				btnRegister.addActionListener(actionListener);
				buttonPane.add(btnRegister);
				getRootPane().setDefaultButton(btnRegister);
			}
			{
				JButton btnCancel = new JButton("Hủy");
				btnCancel.setActionCommand("cancel");
				btnCancel.addActionListener(actionListener);
				buttonPane.add(btnCancel);
			}
		}
	}
	
	/*
	 * This is inner class listener for action listener
     */
	private class InnerClassActionListener implements ActionListener {		 
        @Override
        public void actionPerformed(ActionEvent ae) {
        	String strActionCommand = ae.getActionCommand();
	        Component component = (Component) ae.getSource();
	        JDialog dlgRegister = (JDialog) SwingUtilities.getRoot(component);
    		if (strActionCommand.equals("register")) {
    			if (txtUsername.getText().isEmpty() || txtFullName.getText().isEmpty() || txtPassword.getPassword().length == 0 || txtRetype.getPassword().length == 0) {
                	JOptionPane.showMessageDialog(dlgRegister, "Vui lòng nhập đầy đủ các trường thông tin!", "Thông báo", JOptionPane.OK_OPTION);
					return;
    			}
				if(!String.valueOf(txtPassword.getPassword()).equals(String.valueOf(txtRetype.getPassword()))) {
					JOptionPane.showMessageDialog(dlgRegister, "Mật khẩu và mật khẩu xác nhận không trùng khớp!", "Thông báo", JOptionPane.OK_OPTION);
					return;
				}
    			addNewUser();
    		}
    		if (strActionCommand.equals("cancel")) {
    			dlgRegister.dispose();
    		}
        }
    }
	
	/*
	 * Add new user
	 */
	private void addNewUser() {
		User newUser = new User();
		// Current only have 1 server
		int serverId = 1;
		// status is "offline"
		int status = 0;
		newUser.setUsername(txtUsername.getText());
		// hashing password
		String PasswordHash = StringUtil.hashPassword(String.valueOf(txtPassword.getPassword()));
		newUser.setPassword(PasswordHash);
		newUser.setFullName(txtFullName.getText());
		newUser.setStatus(status);

		int isAdded = UserService.addNewUser(newUser, serverId);
		if(isAdded < 1) {
			JOptionPane.showMessageDialog(this, "Đăng ký thất bại!", "Thông báo", JOptionPane.OK_OPTION);
			return;
		}
		JOptionPane.showMessageDialog(this, "Đăng ký thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	}
}
