package Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import User.frmMain;
import java.awt.HeadlessException;

public class DownloadService implements Runnable {

    public ServerSocket server;
    public Socket socket;
    public int port;
    public String saveTo = "";
    public InputStream in;
    public FileOutputStream out;
    public frmMain frmMain;

    public DownloadService(String saveTo, frmMain frmMain) {
        try {
            server = new ServerSocket(6000);
            port = server.getLocalPort();
            this.saveTo = saveTo;
            this.frmMain = frmMain;
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        try {
            socket = server.accept();
            System.out.println("Download: " + socket.getRemoteSocketAddress());

            in = socket.getInputStream();
            out = new FileOutputStream(saveTo);

            byte[] buffer = new byte[1024];
            int count;

            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
            }

            out.flush();

            JOptionPane.showMessageDialog(frmMain, "Download file hoàn tất", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (HeadlessException | IOException e) {
            System.out.println("Download file thất bại");
        }
    }
}
