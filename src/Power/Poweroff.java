/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Power;

import DesktopController.DesktopController;

/**
 *
 * @author abc
 */
public class Poweroff {
    String os;
    Runtime runtime;
    public Poweroff() {
        os = System.getProperty("os.name");
        System.out.println(os);
        runtime = Runtime.getRuntime();
       
    }
    
    public void shutdown() {     
        try {
            if ("Windows 7".equals(os)||"Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)) {
                runtime.exec("shutdown -s");
            } else {
                System.out.println("Unsupported operating system");
                DesktopController.msg.setText("Unsupported operating system");
            }
        } catch(Exception e) {
            System.out.println("shutdown error");
            DesktopController.msg.setText("shutdown error");
            e.printStackTrace();
        }
        
    }
    
    public void restart() {     
        try {
            if ("Windows 7".equals(os)||"Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)) {
                runtime.exec("shutdown -r");
            } else {                            
                System.out.println("Unsupported operating system");
                 DesktopController.msg.setText("Unsupported operating system");
            }
        } catch(Exception e) {
            System.out.println("restart error");
            DesktopController.msg.setText("restart error");

            e.printStackTrace();
        }
        
    }
    
    public void sleep() {     
        try {
            if ("Windows 7".equals(os)||"Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)) {
                runtime.exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
            } else {
                System.out.println("Unsupported operating system");
                 DesktopController.msg.setText("Unsupported operating system");
            }
        } catch(Exception e) {
            System.out.println("sleep error");
            DesktopController.msg.setText("sleep error");

            e.printStackTrace();
        }   
    }
    
     public void lock() {     
        try {
            if ("Windows 7".equals(os)||"Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)) {
                runtime.exec("Rundll32.exe user32.dll,LockWorkStation");
            } else {
                System.out.println("Unsupported operating system");
                DesktopController.msg.setText("Unsupported operating system");
//                
            }
        } catch(Exception e) {
            System.out.println("lock error");
            DesktopController.msg.setText("lock error");

            e.printStackTrace();
        }
        
    }
    
//    public static void main(String args[]) {
//        Poweroff poweroff = new Poweroff();
//        poweroff.sleep();
//    }
}
