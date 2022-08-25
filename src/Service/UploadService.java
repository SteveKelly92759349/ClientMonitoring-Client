package Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import User.frmMain;
import java.io.IOException;

public class UploadService implements Runnable {

    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream in;
    public OutputStream out;
    public File file;
    public frmMain frmMain;

    public UploadService(String addr, int port, File filepath, frmMain frmMain) {
        super();
        try {
            this.file = filepath;
            this.frmMain = frmMain;
            this.socket = new Socket(InetAddress.getByName(addr), port);
            this.out = socket.getOutputStream();
            this.in = new FileInputStream(filepath);
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int count;

            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
            }
            out.flush();

            JOptionPane.showMessageDialog(frmMain, "Upload file hoàn tất", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Upload file không thành công");
            e.printStackTrace();
        }
    }
}
