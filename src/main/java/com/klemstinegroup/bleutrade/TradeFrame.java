package com.klemstinegroup.bleutrade;// Imports

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.event.*;

class TradeFrame
        extends JFrame {
    // Instance attributes used in this example
    private JPanel topPanel;
    protected JList listbox;
    protected Vector<String> listData;
    protected JScrollPane scrollPane;


    // Constructor of main frame
    public TradeFrame() {
        // Set the frame characteristics
        setTitle("BleuTrader");
        setSize(300, 100);
        setBackground(Color.gray);

        // Create a panel to hold all other components
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create the data model for this example
        listData = new Vector();

        // Create a new listbox control
        listbox = new JList(listData);
//		listbox.addListSelectionListener(this);

        // Add the listbox to a scrolling pane
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(listbox);
        topPanel.add(scrollPane, BorderLayout.CENTER);

    }


//	// Handler for list selection changes
// 	public void valueChanged( ListSelectionEvent event )
// 	{
// 		// See if this is a listbox selection and the
// 		// event stream has settled
//		if( event.getSource() == listbox
//						&& !event.getValueIsAdjusting() )
//		{
//			// Get the current selection and place it in the
//			// edit field
//			String stringValue = (String)listbox.getSelectedValue();
//			if( stringValue != null )
//				dataField.setText( stringValue );
//		}
// 	}

//	// Handler for button presses
//	public void actionPerformed( ActionEvent event )
//	{
//		if( event.getSource() == addButton )
//		{
//			// Get the text field value
//			String stringValue = dataField.getText();
//			dataField.setText( "" );
//
//			// Add this item to the list and refresh
//			if( stringValue != null )
//			{
//				listData.addElement( stringValue );
//				listbox.setListData( listData );
//				scrollPane.revalidate();
//				scrollPane.repaint();
//			}
//		}
//
//		if( event.getSource() == removeButton )
//		{
//			// Get the current selection
//			int selection = listbox.getSelectedIndex();
//			if( selection >= 0 )
//			{
//				// Add this item to the list and refresh
//				listData.removeElementAt( selection );
//				listbox.setListData( listData );
//				scrollPane.revalidate();
//				scrollPane.repaint();
//
//				// As a nice touch, select the next item
//				if( selection >= listData.size() )
//					selection = listData.size() - 1;
//				listbox.setSelectedIndex( selection );
//			}
//		}
//	}

    public void change(ArrayList<String> s) {
        boolean changed = false;
        for (int i = listData.size() - 1; i > -1; i--) {
            String v = listData.get(i);
            if (!s.contains(v)) {
                changed = true;
                listData.removeElementAt(i);
                ;
            }
        }
        for (String v : s) {
            if (!listData.contains(v)) {
                changed = true;
                listData.addElement(v);
                ;
                ;
            }
        }
        if (changed) {
            Collections.sort(listData);
            Collections.reverse(listData);
            listbox.setListData(listData);
            scrollPane.revalidate();
            scrollPane.repaint();
            try {
                SoundUtils.tone(1000,100);
                SoundUtils.tone(2000,100);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    // Main entry point for this example
    public static void main(String args[]) {
        // Create an instance of the test application
        TradeFrame mainFrame = new TradeFrame();
        mainFrame.setVisible(true);
    }
}