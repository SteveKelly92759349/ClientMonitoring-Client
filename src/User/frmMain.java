package User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Common.Message;
import Entity.User;
import Service.UserService;

@SuppressWarnings("serial")
public class frmMain extends JFrame {

    private final JPanel contentPane;
    private final JTextArea taContent;
    private final JList<String> lstUsers;
    private UserService userService;
    private User user;
    private final JTextArea taMessage;
    public File file;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        userService.taContent = this.taContent;
        userService.frmMain = this;
        userService.user = this.user;
    }

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frmMain frame = new frmMain();
                frame.setVisible(true);
            } catch (Exception e) {
            }
        });
    }

    /**
     * Create the frame.
     */
    public frmMain() {
        setTitle("Ứng dụng chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                logout();
            }
        });
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                }
                break;
            }
        }
        setBounds(100, 100, 535, 474);

        InnerClassActionListener actionListener = new InnerClassActionListener();
        KeyInput ki = new KeyInput();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mAccount = new JMenu("Tài khoản");
        menuBar.add(mAccount);

        JMenuItem miChangePassword = new JMenuItem("Đổi mật khẩu");
        miChangePassword.setActionCommand("changePassword");
        miChangePassword.addActionListener(actionListener);
        mAccount.add(miChangePassword);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel pTop = new JPanel();
        contentPane.add(pTop, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("PHÒNG CHAT");
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        pTop.add(lblTitle);

        JPanel pBottom = new JPanel();
        contentPane.add(pBottom, BorderLayout.SOUTH);
        pBottom.setLayout(new BorderLayout(0, 0));

        JPanel pMessage = new JPanel();
        pBottom.add(pMessage);

        taMessage = new JTextArea();
        taMessage.setRows(3);
        taMessage.setLineWrap(true);
        taMessage.addKeyListener(ki);
        JScrollPane spMessage = new JScrollPane();
        spMessage.setViewportView(taMessage);

        GroupLayout gl_pMessage = new GroupLayout(pMessage);
        gl_pMessage.setHorizontalGroup(
                gl_pMessage.createParallelGroup(Alignment.LEADING)
                        .addComponent(spMessage, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        gl_pMessage.setVerticalGroup(
                gl_pMessage.createParallelGroup(Alignment.LEADING)
                        .addComponent(spMessage, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );
        pMessage.setLayout(gl_pMessage);

        JPanel pUlti = new JPanel();
        pBottom.add(pUlti, BorderLayout.NORTH);

        JPanel pButton = new JPanel();
        pBottom.add(pButton, BorderLayout.EAST);

        JButton btnSendFile = new JButton("Gửi file");
        btnSendFile.setActionCommand("sendFile");
        btnSendFile.addActionListener(actionListener);

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setActionCommand("logout");
        btnLogout.addActionListener(actionListener);

        GroupLayout gl_pButton = new GroupLayout(pButton);
        gl_pButton.setHorizontalGroup(
                gl_pButton.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_pButton.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_pButton.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(btnSendFile, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnLogout, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_pButton.setVerticalGroup(
                gl_pButton.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_pButton.createSequentialGroup()
                                .addComponent(btnSendFile)
                                .addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(btnLogout))
        );
        pButton.setLayout(gl_pButton);

        JPanel pCenter = new JPanel();
        contentPane.add(pCenter, BorderLayout.CENTER);

        taContent = new JTextArea();
        taContent.setLineWrap(true);
        taContent.setEditable(false);
        JScrollPane spContent = new JScrollPane();
        taContent.setColumns(20);
        taContent.setRows(5);
        spContent.setViewportView(taContent);

        GroupLayout gl_pCenter = new GroupLayout(pCenter);
        gl_pCenter.setHorizontalGroup(
                gl_pCenter.createParallelGroup(Alignment.LEADING)
                        .addComponent(spContent, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
        );
        gl_pCenter.setVerticalGroup(
                gl_pCenter.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_pCenter.createSequentialGroup()
                                .addGap(20)
                                .addComponent(spContent, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pCenter.setLayout(gl_pCenter);

        JPanel pRight = new JPanel();
        contentPane.add(pRight, BorderLayout.EAST);

        JLabel lblCacUserTham = new JLabel("Danh sách thành viên");
        lblCacUserTham.setForeground(Color.BLUE);
        lblCacUserTham.setFont(new Font("Comic Sans MS", Font.BOLD, 13));

        lstUsers = new JList<>();
        lstUsers.setSelectionBackground(Color.LIGHT_GRAY);
        JScrollPane spUserList = new JScrollPane();
        spUserList.setViewportView(lstUsers);

        GroupLayout gl_pRight = new GroupLayout(pRight);
        gl_pRight.setHorizontalGroup(
                gl_pRight.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_pRight.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_pRight.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblCacUserTham, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                        .addComponent(spUserList, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                .addContainerGap())
        );
        gl_pRight.setVerticalGroup(
                gl_pRight.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_pRight.createSequentialGroup()
                                .addComponent(lblCacUserTham, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(spUserList, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pRight.setLayout(gl_pRight);

        // load list of uses in room
        loadUserList();

        // auto refresh data in jtable each 3 seconds
        autoRefresh();
    }

    /**
     * reload the list of users after 3 seconds
     */
    private void autoRefresh() {
        Timer timer = new Timer(0, (ActionEvent e) -> {
            int row = lstUsers.getSelectedIndex();
            loadUserList();
            lstUsers.getSelectionModel().setSelectionInterval(row, row);
        });

        timer.setDelay(3000); // delay for 3 seconds
        timer.start();
    }

    /**
     * This is inner class listener for key listener
     */
    public class KeyInput implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sendMessage();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub		
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub	
        }
    }

    /**
     * This is inner class listener for action listener
     */
    private class InnerClassActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String strActionCommand = ae.getActionCommand();
            if (strActionCommand.equals("sendFile")) {
                sendFile();
            }
            if (strActionCommand.equals("logout")) {
                logout();
            }
            if (strActionCommand.equals("changePassword")) {
                changePassword();
            }
        }
    }

    private void changePassword() {
        dlgChangePassword dlgChangePassword = new dlgChangePassword();
        dlgChangePassword.setModal(true);
        dlgChangePassword.setUser(user);
        dlgChangePassword.setLocationRelativeTo(this);
        dlgChangePassword.setVisible(true);
    }

    private void sendFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this, "Select File");
        file = fileChooser.getSelectedFile();
        long size = file.length();
        if (size < 120 * 1024 * 1024) {
            userService.sendToServer(new Message("UPLOAD", user.getUsername(), file.getName(), "All"));
            taContent.append("[Thông báo] " + user.getFullName() + " đã gửi file '" + file.getName() + "'\n");
        } else {
            JOptionPane.showMessageDialog(this, "Kích thước file quá lớn!", "Thông báo", JOptionPane.OK_OPTION);
        }
    }

    private void sendMessage() {
        Message message = new Message("CHAT", user.getFullName(), taMessage.getText(), null);
        if (!message.content.isEmpty() && !message.content.equals("\n")) {
            // send message to server
            userService.sendToServer(message);

            taMessage.setText("");
            taMessage.setCaretPosition(-1);
        }
    }

    private void logout() {
        // update the status of user
        int offline = 0;
        int result = UserService.updateUserStatus(user.getUserId(), offline);
        if (result < 1) {
            JOptionPane.showMessageDialog(this, "Đăng xuất thất bại!", "Thông báo", JOptionPane.OK_OPTION);
            return;
        }

        // notify logout to server 
        Message message = new Message("LOGOUT", user.getUsername(), null, null);
        userService.sendToServer(message);

        this.dispose();
        frmLogin frmLogin = new frmLogin();
        frmLogin.setVisible(true);
    }

    /*
	 * load user list
     */
    public void loadUserList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<User> users = new ArrayList<>();
        users = (ArrayList<User>) UserService.getUsers();
        for (int i = 0; i < users.size(); i++) {
            model.addElement(users.get(i).toString());
        }
        lstUsers.setModel(model);
    }
}
