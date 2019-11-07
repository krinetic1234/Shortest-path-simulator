package jrJava.shortestPath_1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class GraphicsBoard extends JPanel {

	private BufferedImage bImage;
	private Graphics bufferedG;
	
	
	public GraphicsBoard(int w, int h){
		JFrame frame = new JFrame("Shortest Path");
		frame.setBounds(100, 0, 0 ,0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(w, h));
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		
		bImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bufferedG = bImage.getGraphics();
		((Graphics2D)bufferedG).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
											     RenderingHints.VALUE_ANTIALIAS_ON);
		
		bufferedG.setColor(Color.WHITE);
		bufferedG.fillRect(0, 0, w, h);
	}
	
	
	public Graphics getCanvas(){ return bufferedG; }
	
	
	public void paintComponent(Graphics g){
		g.drawImage(bImage, 0, 0, null);
	}
	
}






