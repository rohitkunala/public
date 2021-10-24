/*
		DNS
        l2,l3   tf         ta      b,b1,b2
        Reverse DNS
		l9,l10      tf5            b9
		CHAT
		l8      tf2        a1      b5,b6
		
		PING TEST 
		l4,l5   tf1                b3,b31
		PING SWEEPER l6
				tf3        ta1     b7,b71
		PORT SCANNER
		l7      tf4        ta2     b8,b81
*/		
		
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
public class LANClie extends JFrame implements ActionListener
{
    JTextField tf, tf1, tf2, tf3, tf4, tf5;
    JTextArea ta ,ta1, ta2;
    JLabel l,l1,l2,l3, l4,l5, l6, l7, l8, l9,l10;
    JButton b,b1,b2, b5,b6,  b3,b31, b7,b71, b81,b8, b9; 
    JRadioButton r1=new JRadioButton("IP");
    JRadioButton r2=new JRadioButton("Host Name");
    ButtonGroup rg=new ButtonGroup();

    static JTextArea a1;
    
    static Socket s;
    static ServerSocket skt;
    static DataInputStream din;
    static DataOutputStream dout;
    
    public LANClie()
    {
        super("LAN Utility,Client ");
        setSize(800,950);
        
        rg.add(r1);
        rg.add(r2);
        add(r1);
        add(r2);
        r1.setBounds(510,100,45,20);
        r2.setBounds(570,100,105,20);

        l1=new JLabel("<html><font color='blue' style='font-size:22px'>NetworKING(client)</font></html>");
        l1.setBounds(255,10,350,50);
        add(l1);
        
        l2=new JLabel("<html><font color='red' style='font-size:12px'>DNS</font><br>Enter Fully Qualified Domain Name:</html>" ); 
        l2.setBounds(50,75,250,50);
        add(l2);

        tf=new JTextField();
        tf.setBounds(50,125,200,30);
        add(tf);

        l3=new JLabel("Press Find To See IP's:");
        l3.setBounds(50,155,300,35);
        add(l3);

        ta=new JTextArea();
        ta.setBounds(50,185,330,60); 
        add(ta);
    
        b=new JButton("Find IP");
        b.setBounds(50,250,100,30);
        add(b);
        b.addActionListener(this);

        b1=new JButton("Reset");
        b1.setBounds(155,250,95,30);
        add(b1);
        b1.addActionListener(this);
        
        b2=new JButton("Filter for IP");
        b2.setBounds(100,280,120,30);
        add(b2);
        b2.addActionListener(this);

        l8=new JLabel("<html><font color='red' style='font-size:12px'>CHAT(Client)</html>");
        l8.setBounds(50, 340, 160, 35);
        add(l8);

        a1=new JTextArea();
        a1.setBounds(50,370,300,250);
        add(a1);

        tf2=new JTextField();
        tf2.setBounds(50,625,170,30);
        tf2.setBackground(new Color(200,55,77));
        tf2.setForeground(new Color(255,255,255));
        add(tf2);

        b5=new JButton("send");
        b5.setBounds(220,625,70,30);
        b5.setBackground(new Color(30,200,30));
        b5.setForeground(new Color(255,255,255));
        add(b5);
        b5.addActionListener(this);
        
        l4=new JLabel("<html><font color='red' style='font-size:12px'>PING TEST</font><br>Want To Ping ?,</html>");
        l4.setBounds(400,75,200,50);
        add(l4);

        tf1=new JTextField();
        tf1.setBounds(400,125,200,30);
        add(tf1);
        
        l5=new JLabel("Check here after tapping ping");
        l5.setBounds(400,155,250,25);
        add(l5);

        b3=new JButton("PING");
        b3.setBounds(400,180,100,30);
        add(b3);
        b3.addActionListener(this);

        b31=new JButton("Reset");
        b31.setBounds(510,180,100,30);
        add(b31);
        b31.addActionListener(this);

        l6=new JLabel("<html><font color='red' style='font-size:12px'>PING SWEEPER</font><br>Enter NetworkID and min and max</html>");
        l6.setBounds(400,230,250,55);
        //l6.setForeground(new Color(255,153,153));
        add(l6);

        tf3=new JTextField();//NID min max 
        tf3.setBounds(400,280,200,30);
        add(tf3);
        
        b7=new JButton("PING SWEEP");
        b7.setBounds(610,280,120,30);
        add(b7);
        b7.addActionListener(this);

        b71=new JButton("Reset");
        b71.setBounds(630,315,80,30);
        add(b71);
        b71.addActionListener(this);

        ta1=new JTextArea();//ping sweep area
        ta1.setBounds(400,320,200,110);
        add(ta1);

        l7=new JLabel("<html><font color='red' style='font-size:12px'>PORT SCANNER</font><br>Enter IP, min, max port</html>");
        l7.setBounds(400,450,250,55);
        add(l7);

        tf4=new JTextField();
        tf4.setBounds(400,505,200,30);
        add(tf4);

        b8=new JButton("SCAN");
        b8.setBounds(610,505,80,30);
        add(b8);
        b8.addActionListener(this);

        b81=new JButton("Reset");
        b81.setBounds(695,505,80,30);
        add(b81);
        b81.addActionListener(this);

        ta2=new JTextArea();
        ta2.setBounds(400,540,250,120);
        add(ta2);

        l9=new JLabel("<html><font color='red' style='font-size:12px'>Reverse DNS</font><br>Enter IP</html>");
        l9.setBounds(50,670,150,50);
        add(l9);

        tf5=new JTextField();
        tf5.setBounds(50,720,250,30);
        add(tf5);

        l10=new JLabel("Check here");
        l10.setBounds(50,735,350,50);
        add(l10);

        b9=new JButton("Find Host");
        b9.setBounds(80,780,110,35);
        add(b9);
        b9.addActionListener(this);        

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
        String host=tf.getText();
        InetAddress[] ip=InetAddress.getAllByName(host);
        if(ae.getSource()==b)//DNS FIND IP
        {
        for(InetAddress i:ip)
        ta.append(i+"\n");
        }
        else if(ae.getSource()==b1)//DNS RESET
        {
            tf.setText("");
            ta.setText("");
        }
        else if(ae.getSource()==b2)//DNS FILTER FOR IP
        {
            ta.setText("");
            for(InetAddress i:ip)
            ta.append(i.getHostAddress()+"\n");
        }
        else if(ae.getSource()==b3)//PING TEST
        {
            l5.setText("checking...");
            String s=tf1.getText();
            try
            {
                InetAddress inet=InetAddress.getByName(s);
                boolean a;
                if(r1.isSelected())
                a=inet.getHostAddress().equals(s);
                else
                a=inet.getHostName().equals(s);
                if(a){	
                if(inet.isReachable(10000))
                l5.setText("<html><font color='green' style='font-size:13px'>PING SUCCEEDED:</html>");
                else
                l5.setText("<html><font color='red' style='font-size:11px'>PING NOT SUCCEEDED:</html>");
            }
            else
            {
                l5.setText("<html><font color='red' style='font-size:12px'>Invalid URL:</html>");
            }
            }catch(Exception e){l5.setText("<html><font color='red' style='font-size:12px'>Invalid URL:</html>");}	
        }
        else if(ae.getSource()==b31)
        {
            tf1.setText("");
            l5.setText("");
            l5.setText("Check here after tapping ping");
        }
        else if(ae.getSource()==b5)
        {
            String out = tf2.getText();
            a1.setText(a1.getText()+"\n\t\t"+out);
            dout.writeUTF(out);
            tf2.setText("");
        }
        else if(ae.getSource()==b7)
        {
            String a[]=tf3.getText().split(" ",3);
            ta1.setText("sweep started for "+a[0]+"0\n");
            for(int i=Integer.parseInt(a[1]);i<=Integer.parseInt(a[2]);i++)
        {
            String st=a[0]+Integer.toString(i);
            if(InetAddress.getByName(st).isReachable(300))
            ta1.append(st+'\n');
        }
        ta1.append("sweep ended\n");
        }
        else if(ae.getSource()==b71)
        {
            tf3.setText("");
            ta1.setText("");
        }
        else if(ae.getSource()==b8)
        {
            String a[]=tf4.getText().split(" ",3);
            ta2.setText("Scan started for"+a[0]+"\n ");
            for(int port=Integer.parseInt(a[1]);port<=Integer.parseInt(a[2]);port++)
            {
                try{
                    Socket s1=new Socket(a[0],port);//new InetAddress(InetAddress.getByName(a[0]))
                    ta2.append(Integer.toString(port)+'\n');
                    s1.close();
                }catch(Exception e){}
            }
            ta2.append("ended");
        }
        else if(ae.getSource()==b81)
        {
            tf4.setText("");
            ta2.setText("");
        }
        else if(ae.getSource()==b9)//Reverse DNS
        {
            try
            {
                l10.setText(InetAddress.getByName(tf5.getText()).getCanonicalHostName());
            }catch(Exception e){
                l10.setText("Invalid or No Host Name Matched");
            }
        }        
    }
        catch(Exception e)
        {		
        }		
    }

    public static void main(String args[])throws Exception
    {
        new LANClie();
        try{
            try
            {
                s = new Socket(args[0], Integer.parseInt(args[1]));
            }
            catch(Exception e){}
            din  = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true)
            {
                String msginput = din.readUTF();
                a1.setText(a1.getText()+"\n"+msginput);
            }  
        }catch(Exception e){}
    }
}


