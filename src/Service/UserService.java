package Service;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Common.Message;
import DAO.UserDAO;
import Entity.User;
import User.frmMain;
import Util.StringUtil;
import java.awt.HeadlessException;
import java.io.IOException;

public class UserService implements Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Thread thread;

    public JTextArea taContent;
    public frmMain frmMain;
    public User user;

    public static int checkAuthenticate(String username, String password) {
        ArrayList<User> users = new ArrayList<>();
        users = UserDAO.getUsers();
        for (int i = 0; i < users.size(); i++) {
            // Check exist user name
            if (username.equals(users.get(i).getUsername())) {
                // Check that a plain text password matches a previously hashed
                if (StringUtil.checkPassword(password, users.get(i).getPassword())) {
                    return users.get(i).getUserId();
                }
                return -1;
            }
        }
        return -1;
    }

    public static User getUserInfo(int userId) {
        return UserDAO.getUser(userId);
    }

    public static User findUserInfo(String username) {
        ArrayList<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    public static int updateUserStatus(int userId, int status) {
        return UserDAO.updateUserStatus(userId, status);
    }

    public static int changePassword(int userId, String newPassword, int status) {
        return UserDAO.updateUserPassword(userId, newPassword, status);
    }

    public static ArrayList<User> getUsers() {
        return UserDAO.getUsers();
    }

    public static int addNewUser(User newUser, int serverId) {
        return UserDAO.insertNewUser(newUser, serverId);
    }

    public boolean connectToServer(String hostAddress, int port) {
        try {
            socket = new Socket(hostAddress, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void sendToServer(Message message) {
        try {
            this.out.writeObject(message);
            this.out.flush();
        } catch (IOException e) {
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public Message recieveFromServer() {
        try {
            return (Message) this.in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public void run() {
        boolean isRunning = true;
        try {
            Message response;
            String sender, message;
            while (isRunning) {
                response = this.recieveFromServer();
                switch (response.type) {
                    case "CHAT": {
                        sender = response.sender;
                        message = response.content;

                        taContent.append(sender + ": " + message + '\n');

                        break;
                    }
                    case "RECEIVE_FILE": {
                        User senderInfo = findUserInfo(response.sender);

                        taContent.append("[Thông báo] " + senderInfo.getFullName() + " đã gửi file '" + response.content + "'\n");

                        if (JOptionPane.showConfirmDialog(frmMain, ("Bạn có muốn nhận file '" + response.content + "' được gửi bởi '" + senderInfo.getFullName() + "'?")) == 0) {
                            JFileChooser fc = new JFileChooser();
                            fc.setSelectedFile(new File(response.content));
                            int returnVal = fc.showSaveDialog(frmMain);

                            String saveTo = fc.getSelectedFile().getPath();
                            if (saveTo != null && returnVal == JFileChooser.APPROVE_OPTION) {
                                // create thread open server to download file send by client
                                DownloadService dwn = new DownloadService(saveTo, frmMain);
                                Thread t = new Thread(dwn);
                                t.start();

                                // send message reponse with content is port server
                                sendToServer(new Message("RESPONSE", user.getUsername(), ("" + dwn.port), response.sender));
                            } else {
                                sendToServer(new Message("RESPONSE", user.getUsername(), "NO", response.sender));
                            }
                        } else {
                            sendToServer(new Message("RESPONSE", user.getUsername(), "NO", response.sender));
                        }

                        break;
                    }
                    case "UPLOAD_FILE": {
                        System.out.println(response.toString());
                        int port = Integer.parseInt(response.content);
                        String addr = response.sender;

                        UploadService upl = new UploadService(addr, port, frmMain.file, frmMain);
                        Thread t = new Thread(upl);
                        t.start();

                        break;
                    }
                }
            }
        } catch (HeadlessException | NumberFormatException e) {
            isRunning = false;
        }
    }
}
