import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Login extends JFrame implements ActionListener{
	JLabel l1,l2,l3;
	JTextField t;
	JPasswordField p;
	JButton b;
	Login(){
		setTitle("Login form");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200,1200);
		setLayout(null);
		l1=new JLabel("UserName: ");
		l1.setBounds(100,100,200,75);
		l2=new JLabel("Password: ");
		l2.setBounds(100,250,200,75);
		l3=new JLabel("");
		l3.setBounds(200,550,500,75);
		l3.setForeground(new Color(45,96,23));
		t=new JTextField();
		t.setBounds(450,100,200,75);
		p=new JPasswordField();
		p.setBounds(450,250,200,75);
		b=new JButton("Login");
		b.setBounds(250,400,100,75);
		add(l1);add(l2);add(t);add(p);add(b);add(l3);
		b.addActionListener(this);
		setVisible(true);	
	}
	public void actionPerformed(ActionEvent ae){
		String username=t.getText();
		String password=new String(p.getPassword());
		if(!username.isEmpty() && !password.isEmpty())
			l3.setText("login succesfull...."+username);
		else
			l3.setText("enter username and password..");
		repaint();
	}
	public static void main(String args[]){
		new Login();
	}
}
		