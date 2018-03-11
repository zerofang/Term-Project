package huarongroad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class HuaRongRoad extends JFrame implements MouseListener,ActionListener{
	
	Person person[] = new Person[10]; 
	JButton below,above,left,right;
	JButton restart = new JButton("ÖØÐÂ¿ªÊ¼");
	JLabel label = new JLabel("³É¹¦³öÌÓ£¡",JLabel.CENTER);
	
	public HuaRongRoad(String title)
	{
		super(title);
		setLayout(null);
		setSize(520,700);
		setVisible(true);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		restart.setBounds(190,590,120,40);
		restart.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,12));
		restart.setBackground(Color.white);
		restart.setFocusPainted(false);
		restart.addActionListener(this);
		add(restart);
		
		label.setBounds(10000,10000,120,40);
		label.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,15));
		label.setBackground(Color.white);
		add(label);
		   
		String name[] = {"²Ü²Ù","¹ØÓð","Âí³¬","ÕÅ·É","ÕÔÔÆ","»ÆÖÒ","±ø","±ø","±ø","±ø"};
		for(int i = 0; i < 10; i++) {
			person[i] = new Person(i,name[i]);
			person[i].addMouseListener(this);
			add(person[i]);
		}
		person[0].setBounds(154,54,200,200);
		person[1].setBounds(154,254,200,100);
		person[2].setBounds(54,254,100,200);
		person[3].setBounds(354,254,100,200);
		person[4].setBounds(354,54,100,200);
		person[5].setBounds(54,54,100,200);
		person[6].setBounds(54,454,100,100);
		person[7].setBounds(354,454,100,100);
		person[8].setBounds(154,354,100,100);
		person[9].setBounds(254,354,100,100);
		person[9].requestFocusInWindow();
		below = new JButton();
		above = new JButton();
		left = new JButton();
		right = new JButton();
		below.setEnabled(false);
		above.setEnabled(false);
		left.setEnabled(false);
		right.setEnabled(false);
		add(below);
		add(above);
		add(left);
		add(right);
		below.setBounds(49,554,410,5);
		above.setBounds(49,49,410,5);	
		left.setBounds(49,49,5,510);
		right.setBounds(454,49,5,510);	
		validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		dispose();
		new HuaRongRoad("»ªÈÝµÀ");		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Person target = (Person)e.getSource();
		float x = e.getX();
		float y = e.getY();
		float width = target.getBounds().width;
		float height = target.getBounds().height;
		if(y > (height - (height/width)*x) && y > (height/width)*x) {
			go(target,below);
		}else if(y < (height - (height/width)*x) && y < (height/width)*x) {
			go(target,above);
		}else if(y < (height - (height/width)*x) && y > (height/width)*x) {
			go(target,left);
		}else {
			go(target,right);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void go(Person target,JButton direction) {
		boolean move=true;
	    Rectangle manRect=target.getBounds();
	    int x=target.getBounds().x;
	    int y=target.getBounds().y;
	    
	    if(direction==below)
	    	y=y+100;
	    else if(direction==above)
	    	y=y-100;
	    else if(direction==left)
	    	x=x-100;
	    else if(direction==right)
	    	x=x+100;
	    
	    manRect.setLocation(x,y);
	    Rectangle directionRect=direction.getBounds();
	    for(int k=0;k<10;k++) {
	    	Rectangle personRect=person[k].getBounds();
	    	if((manRect.intersects(personRect))&&(target.no!=k)) {
	    		move=false;
	    	}
	    }
	    if(manRect.intersects(directionRect)) {
	    	move=false;
	    }
	    if(move==true) {
	    	target.setLocation(x,y);
	    }
	    
	    if(target.no == 0 && x == 154 && y == 354) {
	    	label.setLocation(190,5);
	   }
	}
}
