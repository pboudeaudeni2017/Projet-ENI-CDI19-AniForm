package fr.eni.papeterie.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelButtons extends JPanel {

	public interface IPanelButtonsObverser {
		void onNext();
		void onPrevious();
		void onCreate();
		void onSave();
		void onDelete();
	}
	
	private static final int ACTION_NEXT = 0;
	private static final int ACTION_PREVIOUS = 1;
	private static final int ACTION_SAVE = 2;
	private static final int ACTION_CREATE = 3;
	private static final int ACTION_DELETE = 4;
	
	private JButton bttPrevious;
	private JButton bttNew;
	private JButton bttSave;
	private JButton bttDelete;
	private JButton bttNext;
	
	private List<IPanelButtonsObverser> observers = new ArrayList<>();
	
	public PanelButtons() {
		add(getBttPrevious());
		add(getBttNew());
		add(getBttSave());
		add(getBttDelete());
		add(getBttNext());
	}
	
	private JButton getBttPrevious() {
		if(bttPrevious == null) {
			bttPrevious = new JButton(new ImageIcon(getClass().getResource("resources/Back24.gif")));
			bttPrevious.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					notifyObservers(ACTION_PREVIOUS);
				}
			});
		}
		return bttPrevious;
	}
	private JButton getBttNew() {
		if(bttNew == null) {
			bttNew = new JButton(new ImageIcon(getClass().getResource("resources/New24.gif")));
			bttNew.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					notifyObservers(ACTION_CREATE);
				}
			});
		}
		return bttNew;
	}
	private JButton getBttSave() {
		if(bttSave == null) {
			bttSave = new JButton(new ImageIcon(getClass().getResource("resources/Save24.gif")));
			bttSave.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					notifyObservers(ACTION_SAVE);
				}
			});
		}
		return bttSave;
	}
	private JButton getBttDelete() {
		if(bttDelete == null) {
			bttDelete = new JButton(new ImageIcon(getClass().getResource("resources/Delete24.gif")));
			bttDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					notifyObservers(ACTION_DELETE);
				}
			});
		}
		return bttDelete;
	}
	private JButton getBttNext() {
		if(bttNext == null) {
			bttNext = new JButton(new ImageIcon(getClass().getResource("resources/Forward24.gif")));
			bttNext.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					notifyObservers(ACTION_NEXT);
				}
			});
		}
		return bttNext;
	}
	
	public void register(IPanelButtonsObverser observer) {
		observers.add(observer);
	}
	
	public void unregister(IPanelButtonsObverser observer) {
		observers.remove(observer);
	}
	
	private void notifyObservers(int action) {
		for(IPanelButtonsObverser observer : observers) {
			switch(action) {
			case ACTION_NEXT:
				observer.onNext();
				break;
			case ACTION_PREVIOUS:
				observer.onPrevious();
				break;
			case ACTION_CREATE:
				observer.onCreate();
				break;
			case ACTION_SAVE:
				observer.onSave();
				break;
			case ACTION_DELETE:
				observer.onDelete();
				break;
			}
		}
	}
	
}
