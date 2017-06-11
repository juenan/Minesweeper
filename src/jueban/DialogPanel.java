package jueban;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DialogPanel extends JPanel{
	private JButton cancel;
	private JButton okbutton;
	private boolean ok = false;
	private JDialog dialog;
	public DialogPanel(JPanel panel){
		JPanel buttonpanel = new JPanel();
		cancel = new JButton("取消");
		okbutton = new JButton("确定");
		buttonpanel.add(okbutton);
		buttonpanel.add(cancel);
		setLayout(new BorderLayout());
		
		
		okbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ok = true;
				dialog.setVisible(false);
			}
			
	});
		
	
	
	
	cancel.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
			
		}
		
	});
		
	
		add(panel,BorderLayout.CENTER);
		add(buttonpanel,BorderLayout.SOUTH);
	}
	
	
	public boolean showDialog(Component parent,String title){
		ok = false;
		Frame owner = null;
		if(parent instanceof Frame) owner = (Frame) parent;
		else owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,parent);
		
		if(dialog == null || dialog.getOwner() != owner){
			dialog = new JDialog(owner,true);
			dialog.add(this);
			dialog.getRootPane().setDefaultButton(okbutton);
			dialog.pack();
		}
		Point p = owner.getLocation();
		dialog.setLocation((int)p.getX()+owner.getWidth()/2-(dialog.getWidth()/2),(int)p.getY()+owner.getHeight()/2-(dialog.getHeight()/2));
		dialog.setTitle(title);
		dialog.setVisible(true);
		return ok;
		
	}
}
