import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Hufan extends JFrame implements ActionListener{
	static HuPanel hp;
	JMenuBar jmb;
	JMenu jm1,jm2;
	JMenuItem jmi1,jmi2,jmi3,jmi4;
	StartPanel sp;
	public static void main(String[] args) {
		new Hufan();
	}
	public Hufan(){
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ");
		jmi1=new JMenuItem("��ʼģ��");
		jmi1.addActionListener(this);
		jmi2=new JMenuItem("��ͣ��Ϸ");
		jmi2.addActionListener(this);
		jmi3=new JMenuItem("�˳���Ϸ");
		jmi3.addActionListener(this);
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm2=new JMenu("����");
		jmi4=new JMenuItem("����");
		jmi4.addActionListener(this);
		jm2.add(jmi4);
		jmb.add(jm1);
		jmb.add(jm2);
		this.setJMenuBar(jmb);
		
		sp=new StartPanel();
		new Thread(sp).start();
		this.add(sp);
		this.setTitle("ģ�⽻ͨ��");
		this.setSize(700, 700);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jmi1){
			sp.isLive=false;
			this.remove(sp);
			hp=new HuPanel();
			this.add(hp);
			this.setVisible(true);
		}else if(e.getSource()==jmi2){
			if(jmi2.getText().equals("��ͣ��Ϸ")){
				Car.zanTing=false;
				hp.zanTing=false;
				Light.kaiGuan=false;
				jmi2.setText("������Ϸ");
			}else if(jmi2.getText().equals("������Ϸ")){
				Car.zanTing=true;
				hp.zanTing=true;
				Light.kaiGuan=true;
				jmi2.setText("��ͣ��Ϸ");
			}
		}else if(e.getSource()==jmi3){
			System.exit(0);
		}else if(e.getSource()==jmi4){
			new Set(this,"����",true);
		}
	}
}
class StartPanel extends JPanel implements Runnable{
	int info=0;
	boolean isLive=true;
	public void paint(Graphics g) {
		super.paint(g) ;
		g.fillRect(0, 0, 700, 700);
		g.setColor(Color.red);
		g.setFont(new Font("����",Font.BOLD,30));
		if(info%2==0){
			g.drawString("Javaģ�⽻ͨ", 230,300);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			info++;
			this.repaint();
			if(isLive==false){
				break;
			}
		}
	}
}
class HuPanel extends JPanel implements Runnable{
	static Vector<MyCar> vtCar=new Vector<MyCar>();
	static Vector<Light> vtLight=new Vector<Light>();
	ZhaoShi zs = null;
	Random r=new Random();
	boolean zanTing=true;
	static int carNum=15;
	public HuPanel(){
		init();
		vtLight.add(new Light(306,285,0));
		vtLight.add(new Light(285,365,1));  
		vtLight.add(new Light(365,406,2));  
		vtLight.add(new Light(406,305,3));
		//�������߳�
		Thread t=new Thread(new Light());
		t.start();
		//�������ɶ����߳�
		new Thread(this).start();
	}
	//�������³�
	public void init(){
		switch(r.nextInt(4)){
		case 0:
			zs=new ZhaoShi(315, 0, 0);
			break;
		case 1:
			zs=new ZhaoShi(0, 365, 1);
			break;
		case 2:
			zs=new ZhaoShi(365, 700, 2);
			break;
		case 3:
			zs=new ZhaoShi(700, 315, 3);
			break;
		}
		new Thread(zs).start();
	}
	//JFrame�Զ�����paint��paint��JPanel����ķ�����
	//������super���ø����paint����
	public void paint(Graphics g){
		super.paint(g);
		//��ʮ�ֳ���
		g.setColor(Color.darkGray);  
        g.fillRect(0, 300, 700, 6);  
        g.fillRect(0, 400, 700, 6);  
        g.fillRect(300, 0, 6, 700);  
        g.fillRect(400, 0, 6, 700);  
        g.setColor(Color.gray);  
        g.fillRect(0, 350, 700, 2);  
        g.fillRect(350, 0, 2, 700);  
        g.setColor(Color.blue); 
        //����
        for(int i=0;i<vtLight.size();i++){  
            Light light=vtLight.get(i);  
            g.setColor(Color.black);  
            if(light.direct%2==0){  
                g.fillRect(light.x, light.y, 35, 15);  
            }else{  
                g.fillRect(light.x, light.y, 15, 35);  
            }  
            //�̵�
            g.setColor(Color.green);  
            if(light.greenLight==true){  
                if(light.direct%2==0){  
                    g.fillOval(light.x, light.y+2, 10, 10);  
                }else{  
                    g.fillOval(light.x+2, light.y, 10, 10);  
                }  
            }  
            //���
            g.setColor(Color.red);  
            if(light.redLight==true){  
                if(light.direct%2==0){  
                    g.fillOval(light.x+20, light.y+2, 10, 10);  
                }else{  
                    g.fillOval(light.x+2, light.y+20, 10, 10);  
                }  
            }  
        }
      //����   
        for(int i=0;i<vtCar.size();i++){
        	MyCar mc=vtCar.get(i);
        		g.setColor(mc.colorCar); 
            if(mc.direct%2==0){  
                g.fill3DRect(vtCar.get(i).x, vtCar.get(i).y, 20, 35,true);  
            }else{  
                g.fill3DRect(vtCar.get(i).x, vtCar.get(i).y, 35, 20,true);  
            }  
        }
      //�����³�
        g.setColor(Color.red);  
        if(zs.direct%2==0){  
            g.fill3DRect(zs.x, zs.y, 20, 35,true);  
        }else{  
            g.fill3DRect(zs.x, zs.y, 35, 20,true);  
        }
      //����������
        if(zs.text!=null){
        	g.setFont(new Font("����",Font.BOLD,50));
            g.drawString(zs.text.info, zs.text.x, zs.text.y);
        }
	}
	//�õ���ʮ��·��Ҫת�ķ���
	public int getDirect(int d){
		int i=r.nextInt(4);
		while(true){
			if(i==d){
				i=r.nextInt(4);
			}else{
				break;
			}
		}
		return i;
	}
	@Override
	public synchronized void run() {
		MyCar myCar=null;
		int newDirect;
		// TODO Auto-generated method stub
		while(true){
			if(zanTing){
				if(vtCar.size()<carNum){
					switch(r.nextInt(4)){
					case 0:
						newDirect=getDirect(0);
						myCar=new MyCar(315, 0, 0,newDirect,Color.blue);
						vtCar.add(myCar);
						break;
					case 1:
						newDirect=getDirect(1);
						myCar=new MyCar(0, 365, 1,newDirect,Color.green);
						vtCar.add(myCar);
						break;
					case 2:
						newDirect=getDirect(2);
						myCar=new MyCar(365, 700, 2,newDirect,Color.cyan);
						vtCar.add(myCar);
						break;
					case 3:
						newDirect=getDirect(3);
						myCar=new MyCar(700, 315, 3,newDirect,Color.orange);
						vtCar.add(myCar);
						break;
					}
					new Thread(myCar).start();
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}