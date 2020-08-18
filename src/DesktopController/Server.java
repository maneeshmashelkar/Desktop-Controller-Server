package DesktopController;

import static DesktopController.DesktopController.*;
import MouseKeyboard.MouseKeyboard;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import javax.swing.JButton;
import javax.swing.JLabel;
import Power.Poweroff;

/**
 *
 * @author abc
 */
public class Server extends Thread {

    JLabel status;
    JLabel msg;
    MouseKeyboard mouseKeyboard = new MouseKeyboard();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();//get screen width
    int screenHeight = (int) screenSize.getHeight();//get screen height
    private final JButton startServer;
    private final JButton reset;

    public Server(JLabel status, JLabel msg, JButton startServer, JButton reset) {
        this.status = status;
        this.msg = msg;
        this.startServer = startServer;
        this.reset = reset;
        start();//start the thread
    }

    private void connectionClosed() {
        try {
            if (serversocket != null) {
                serversocket.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            //listen fot the client
            DesktopController.serversocket = new ServerSocket(DesktopController.port);
            DesktopController.socket = DesktopController.serversocket.accept();
            status.setText("connected");
            status.paintImmediately(status.getVisibleRect());
            DesktopController.inputStream = DesktopController.socket.getInputStream();
            DesktopController.objectInputStream = new ObjectInputStream(DesktopController.inputStream);

            String message;
            Poweroff poweroff = new Poweroff();
            while (true) {
                try {
                    message = (String) DesktopController.objectInputStream.readObject();//get msg from client
                    System.out.println(message);
                    DesktopController.msg.setText("");
                    int keycode;
                    if (message != null) {
                        switch (message) {
                            case "LEFT_CLICK":
                                mouseKeyboard.leftClick();
                                break;
                            case "RIGHT_CLICK":
                                mouseKeyboard.rightClick();
                                break;
                            case "DOUBLE_CLICK":
                                mouseKeyboard.doubleClick();
                                break;
                            case "MOUSE_WHEEL":
                                int scrollAmount
                                        = (int) DesktopController.objectInputStream.readObject();
                                mouseKeyboard.mouseWheel(scrollAmount);
                                Thread.sleep(25);
                                System.out.println(scrollAmount);

                                break;
                            case "MOUSE_MOVE":
                                int x = (int) DesktopController.objectInputStream.readObject();
                                int y = (int) DesktopController.objectInputStream.readObject();
                                Point point = MouseInfo.getPointerInfo().getLocation();//get mouse pointer current location
                                int nowx = point.x;//get mouse X value
                                int nowy = point.y;//get mouse Y value
                                if (x <= screenWidth && x != 0 && y != 0 && y <= screenHeight) {
                                    mouseKeyboard.mouseMove(nowx, nowy, nowx + x, nowy + y, 3, 6);//mouseMove(init-X,init-Y,destination-X,destination-Y,sleeptime,no. of loop)
//                                     Thread.sleep(500);, 14, 525
//                                    System.out.println(x + "," + y);
//                                    System.out.println((nowx + x) + "," + (nowy + y));

                                }
                                break;
                            case "KEY_PRESS":
                                keycode = (int) DesktopController.objectInputStream.readObject();
                                mouseKeyboard.keyPressMethod(keycode);
                                break;
                            case "KEY_RELEASE":
                                keycode = (int) DesktopController.objectInputStream.readObject();
                                mouseKeyboard.keyReleaseMethod(keycode);
                                break;
                            case "TYPE_CHARACTER":
                                char ch = ((String) DesktopController.objectInputStream.readObject()).charAt(0);
                                mouseKeyboard.typeCharacter(ch);
                                System.out.println(ch);
                                break;
                            case "TYPE_KEY":
                                keycode = (int) DesktopController.objectInputStream.readObject();
                                mouseKeyboard.typeCharacter(keycode);
                                break;
                            case "LEFT_KEY":
                                mouseKeyboard.pressLeftKey();
                                break;
                            case "DOWN_KEY":
                                mouseKeyboard.pressDownKey();
                                break;
                            case "RIGHT_KEY":
                                mouseKeyboard.pressRightKey();
                                break;
                            case "UP_KEY":
                                mouseKeyboard.pressUpKey();
                                break;
                            case "PRESENTATION_MAX_KEY":
                                mouseKeyboard.pressPresentationMaxKey();
                                break;
                            case "PRESENTATION_MIN_KEY":
                                mouseKeyboard.pressPresentationMinKey();
                                break;
                            case "SHUTDOWN":
                                poweroff.shutdown();
                                break;
                            case "RESTART":
                                poweroff.restart();
                                break;
                            case "SLEEP":
                                poweroff.sleep();
                                break;
                            case "LOCK":
                                poweroff.lock();
                                break;
                        }
                    } else {
                        status.setText("Disconnected");
                        status.paintImmediately(status.getVisibleRect());
                        System.out.println("disconnect");
                        connectionClosed();
                        break;
                    };
                } catch (Exception ex) {
                    ex.printStackTrace();
                    status.setText("Disconnected");
                    status.paintImmediately(status.getVisibleRect());
                    System.out.println("disconnect");
                    connectionClosed();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
