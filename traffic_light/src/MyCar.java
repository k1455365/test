import java.awt.Color;

public class MyCar extends Car implements Runnable{
	//到十字路口，往哪个方向
	int newDirect;
	Color colorCar;
	public MyCar(int x, int y, int direct,int newDirect, Color colorCar) {
		// TODO Auto-generated constructor stub
		super(x, y, direct);
		this.newDirect=newDirect;
		this.colorCar=colorCar;
	}
	//换方向
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(true){
			if(zanTing){
				switch(direct){  
	            case 0: 
	            	switch(newDirect){
	    			case 1:
	    				if(y>360)
	    					direct=newDirect;
	    				break;
	    			case 3:
	    				if(y>310)
	    					direct=newDirect;
	    				break;
	    			}
	            	////////
	                if(stopCar){ 
	                	y+=speed;   
	                }
	                panDuan(x, y+35, direct);  
	                break;  
	            case 1:  
	            	switch(newDirect){
	    			case 0:
	    				if(x>310)
	    					direct=newDirect;
	    				break;
	    			case 2:
	    				if(x>360)
	    					direct=newDirect;
	    				break;
	    			}
	            	////////
	                if(stopCar){
	                	x+=speed; 
	                }
	                panDuan(x+35, y, direct);  
	                break;  
	            case 2:  
	            	switch(newDirect){
	    			case 1:
	    				if(y<370)
	    					direct=newDirect;
	    				break;
	    			case 3:
	    				if(y<320)
	    					direct=newDirect;
	    				break;
	    			}
	            	////////
	                if(stopCar){
	                	y-=speed;  
	                }
	                panDuan(x, y, direct);  
	                break;  
	            case 3:  
	            	switch(newDirect){
	    			case 0:
	    				if(x<320)
	    					direct=newDirect;
	    				break;
	    			case 2:
	    				if(x<370)
	    					direct=newDirect;
	    				break;
	    			}
	            	////////
	                if(stopCar){
	                	x-=speed;  
	                }
	                panDuan(x, y, direct);  
	                break;  
	            }
			}
			//如果死亡，退出线程
			if(isStop==false){
				break;
			}
			Hufan.hp.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public void panDuan(int x,int y,int direct){
		//保存前面车
	    Car startCar=null;
		// 碰到边界，从arrAcr删除
		if(x<0||x>700||y<0||y>700){   
            Hufan.hp.vtCar.remove(this);
            this.isStop=false;
            this.isLive=false;
        } 
        //车碰车   
        for(int i=0;i<Hufan.hp.vtCar.size();i++){  
            Car car=Hufan.hp.vtCar.get(i);
            if(isLive){
            	if(direct%2==0){  
//                    if(x>=car.x&&x<=car.x+20&&y>=car.y&&y<=car.y+35){
                    if(x>=car.x&&x<=car.x+20&&y>=car.y&&y<=car.y+40){
                    	startCar=car; 
                    	if(startCar.stopCar==false){  
                            stopCar=false;  
                        }
                    	else{
                        	stopCar=true; 
                        }
                    }
                }else{  
                    if(x>=car.x&&x<=car.x+40&&y>=car.y&&y<=car.y+20){ 
                    	startCar=car; 
                    	if(startCar.stopCar==false){  
                            stopCar=false;  
                        }
                    	else{
                        	stopCar=true; 
                        }
                    }
                } 
            }
            if(isLive){
            	if(startCar!=null&&startCar.stopCar==false){  
                    stopCar=false;  
                }else{
                	stopCar=true; 
                }
            }
        }
        //是否碰到灯   
        for(int i=0;i<Hufan.hp.vtLight.size();i++){  
            Light light=Hufan.hp.vtLight.get(i);  
            if(direct%2==0){  
                if(x>=light.x&&x<=light.x+50&&y>=light.y&&y<=light.y+15){
                    if(light.redLight==true&&light.direct==direct){  
                        stopCar=false;  
                    }else{  
                        stopCar=true;  
                    }  
                }  
            }else{  
                if(x>=light.x&&x<=light.x+15&&y>=light.y&&y<=light.y+50){  
                    if(light.redLight==true&&light.direct==direct){  
                        stopCar=false;  
                    }else{  
                        stopCar=true;  
                    }  
                }  
            }  
        }
	}
}
