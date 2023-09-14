package project;

import javax.swing.*;
      import java.applet.AudioClip;
        import java.io.*;
        import java.applet.Applet;
import java.net.MalformedURLException;
        import java.net.URI;
        import java.net.URL;
public class JavaClip extends JFrame {
    URL url;
    public static int ctl;
    public static AudioClip aau;
    public JavaClip(){
        super();
        try {
            URI cb;
            File f = new File("D:\\Java work\\untitled\\src\\music\\bgm.wav"); //引号里面的是音乐文件所在的绝对鹿筋
            cb = f.toURI();
            url = cb.toURL();

            aau = Applet.newAudioClip(url);
//aau.play();

//循环播放 aau.play() 单曲 aau.stop()停止播放

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static void MusicOn(){
        aau.loop();
    }
    public static void MusicOff(){
        aau.stop();
    }
}
