import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;



import java.util.Timer;
import java.util.TimerTask;

public class StopWatch extends JFrame implements ActionListener, Runnable //might need to rename class to Timer
{
	private JLabel label;
	private JButton button;
	private Timer timer;
	
	//constructor StopWatch
	public StopWatch()
	{
	
		//display timer
		label = new JLabel ("00:00");
		label.setFont(new Font (label.getFont().getName(),Font.BOLD, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label, BorderLayout.CENTER);
		
		
		//Start stop button
		button = new JButton("start/stop");
		add(button, BorderLayout.SOUTH);
		button.addActionListener((ActionListener) this);
		
	    timer = null;
	    setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()== button)
		{
			if(timer == null)//start
			{
				timer = new java.util.Timer();
				StopperTask task = new StopperTask();
				timer.scheduleAtFixedRate(task, 0 , 1000);
			}
			else //stop
			{
				timer.cancel();
				timer = null;
			}return;
		}
		
		
	}
	public void addText(String text) 
	{
		this.label.setText(text);
		
	}
	protected class StopperTask extends TimerTask
	{
		
		protected long start = System.currentTimeMillis();

		@Override
		public void run() 
		{
			long time = System.currentTimeMillis() - start ;
			long sec = time / 1000 ;
			long min = sec / 60;
			sec = sec % 60;
			final String text = String.format("%02d:%02d", min , sec );
			addText(text);
		}
		
			}

	public static void main ( String []args)
	{
			
	   StopWatch gui = new StopWatch();
	   gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   gui.setTitle("HighCard game timer");
	   gui.setSize(300,200);
	   gui.setVisible(true);
	   
	   Thread t = new Thread(gui);
	   t.start();
					   
					   
	}//end main

	

	
}

