/**
 * @(#)GelembungBubble.java
 *
 * GelembungBubble Applet application
 *
 * @author nObeLLeon
 * @version 1.00 2020/6/1
 *********************************************  Program Successful  ***********************************************************************
 */
 
import java.awt.*;
import java.applet.*;

class GelembungBubble0{
	
	int p, q, diameter, awalDiameter, akhirDiameter;
	boolean tumbuh = true;
	Color warnaRandom;
	Graphics g;
	
	// konstruktor
	public GelembungBubble0(){
		
		int merah = (int)(Math.random()*255);
		int hijau = (int)(Math.random()*255);
		int biru = (int)(Math.random()*255);
		
		// Menghasilkan warna secara acak
		warnaRandom = new Color(merah, hijau, biru);
		
		// Membuat diameter awal antara 20 sampai dengan 30
		awalDiameter = (int)(Math.random()*20) + 10;
		
		// Membuat diameter awal antara 50 sampai dengan 150
		akhirDiameter = (int)(Math.random()*100) + 50;
		
		diameter = awalDiameter;
	}
	
	public void setPosition(int p, int q) {
		
		this.p = p;
	 	this.q = q;
	}

	public void drawBubble(){
		g.setColor(warnaRandom);
		g.fillOval(p-diameter/2, q-diameter/2, 		
			          diameter, diameter);
	}
	
	public void breathe(){
		
		// Gelembung akan bertambah besar sampai awalDiameter tercapai
		if(tumbuh){
			if(diameter < akhirDiameter)
				diameter++;
			else
				tumbuh = false;
		}
		// Gelembung akan bertambah kecil sampai awalDiameter tercapai 
		else{
			if(diameter > awalDiameter)
				diameter--;
			else
				tumbuh = true;
			
		}
	}
	
	public void paint(Graphics gr){
		
		g = gr;
		drawBubble();
	}
}

public class GelembungBubble extends Applet implements Runnable{
	
	Image Buffer;
	Graphics gBuffer;
	GelembungBubble0 bubbleKu[];   // Memanggil konstruktor GelembungBubble0
	Thread lari;
	boolean klik;
	int indeks;
	int MAX = 100;
	
	public void init(){
		
		Buffer = createImage(size().width, size().height);
		gBuffer = Buffer.getGraphics();
		bubbleKu = new GelembungBubble0[MAX];
	}
	
	public void start(){
		
		if(lari == null){
			lari = new Thread(this);
			lari.start();
		}
	}
	
	public void stop(){
		
		if(lari != null){
			lari.stop();
			lari = null;
		}
	}
	
	public void run(){
		
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		while(true){
			try{lari.sleep(15);}
			catch(Exception e){}
			
			// Metode Bubble hanya dapat dijalankan jika kita sudah membuat
			// obyek pada metode mouseDown
			if(klik){
				for(int i=0; i<indeks; i++){
					bubbleKu[i].breathe();                                         ///
					bubbleKu[i].paint(gBuffer); 
				}
			}
			repaint(); 
		}
	}
	
	public boolean mouseDown(Event evt, int p, int q){
		
		if(indeks < MAX){
			bubbleKu[indeks] = new GelembungBubble0();          // Memanggil GelembungBubble0();
			bubbleKu[indeks].setPosition(p, q);
			indeks++;
		}
		klik = true;
		return true;
	}
	
	public void update(Graphics g){
		
		paint(g);
	}
	
	public void paint(Graphics g){
		
		gBuffer.setColor(Color.black);          // warna
		gBuffer.drawString("Klik disini!",20 ,20);
		g.drawImage(Buffer, 0, 0, this);
		
		// Menggambar warna latar belakang berwarna putih
		gBuffer.setColor(Color.white);
		gBuffer.fillRect(0, 0, size().width, size().height);
	}
	
}

