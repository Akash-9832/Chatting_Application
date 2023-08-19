package chatapplication;

import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Client implements ActionListener {

    JTextField text;
    static JFrame f = new JFrame();
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    Client() {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        //Insert Back image and property
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/pic3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        //Back close here

        //Insert profile image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/pic2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        //profile image close here

        //Insert video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        //video close here

        //Insert phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel ph = new JLabel(i12);
        ph.setBounds(360, 20, 35, 30);
        p1.add(ph);
        //phone close here

        //Insert more button
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(420, 20, 10, 25);
        p1.add(more);
        //more close here

        //Insert profile name
        JLabel name = new JLabel("Abir");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("sans-serif", Font.BOLD, 24));
        p1.add(name);
        //name close here

        //Online or Offline
        JLabel status = new JLabel("Online");
        status.setBounds(110, 40, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("sans-serif", Font.BOLD, 14));
        p1.add(status);
        //online close here

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        //Insert Textbox
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("sans-serif", Font.PLAIN, 16));
        f.add(text);
        //textbox close here

        //Insert Send button
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("sans-serif", Font.PLAIN, 16));
        f.add(send);
        //send close here


        f.setSize(451, 700);
        f.setLocation(1000, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 20));
        output.setBackground(new Color(30, 201, 192));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sd.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while (true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
