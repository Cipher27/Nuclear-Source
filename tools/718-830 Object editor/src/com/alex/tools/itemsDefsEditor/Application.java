package com.alex.tools.itemsDefsEditor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.alex.loaders.items.ObjectDefinitions;
import com.alex.store.Store;
import com.alex.utils.Constants;
import com.alex.utils.Utils;

public class Application {

	public static Store STORE;
	private JFrame frmCacheEditorV;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		STORE = new Store("C:/Users/paolo/Dropbox/Zaria 718-830/data/cache/", false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmCacheEditorV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	private void setLook() {
		boolean found = false;
	     for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())  {
	         if(info.getName().equals("Nimbus"))
	             try {
	                 UIManager.setLookAndFeel(info.getClassName());
	                 found = true;
	             }catch(Exception e) {
	                 e.printStackTrace();
	             }
	     }
	     if(!found)
	             try {
	                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
	             }catch(Exception e) {
	                 e.printStackTrace();
	             }
	}
	
	private JList<ObjectDefinitions> objectList;
	private DefaultListModel<ObjectDefinitions> objectModelList;
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setLook();
		frmCacheEditorV = new JFrame();
		frmCacheEditorV.setTitle("Cache Editor 718/830");
		frmCacheEditorV.setBounds(100, 100, 420, 435);
		frmCacheEditorV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmCacheEditorV.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Info", null, panel, null);
		panel.setLayout(null);
		
		
		JLabel lblCreatedByAlexalso = new JLabel("Created By Dragonkk upgraded by Omglolomghi");
		lblCreatedByAlexalso.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 15));
		lblCreatedByAlexalso.setBounds(6, 290, 322, 46);
		panel.add(lblCreatedByAlexalso);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Objects", null, panel_1, null);
		panel_1.setLayout(null);
		objectModelList = new DefaultListModel<ObjectDefinitions>();
		objectList = new JList<ObjectDefinitions>(objectModelList);
		objectList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		objectList.setLayoutOrientation(JList.VERTICAL);
		objectList.setVisibleRowCount(-1);
		JScrollPane itemListscrollPane = new JScrollPane(objectList);
		itemListscrollPane.setBounds(34,49, 250, 254); //(34, 49, 155, 254);
		panel_1.add(itemListscrollPane);
		
		JButton btnEdit = new JButton("Edit");
		final Application app = this;
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectDefinitions defs = objectList.getSelectedValue();
				if(defs == null)
					return;
				new ObjectEditor(app, defs);
			}
		});
		btnEdit.setBounds(301, 48, 90, 28);
		panel_1.add(btnEdit);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ObjectEditor(app, new ObjectDefinitions(STORE, Utils.getObjectDefinitionSize(STORE) , false));
			}
		});
		btnAdd.setBounds(301, 88, 90, 28);
		panel_1.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectDefinitions defs = objectList.getSelectedValue();
				if(defs == null)
					return;
				STORE.getIndexes()[Constants.ITEM_DEFINITIONS_INDEX].removeFile(defs.getArchiveId(), defs.getFileId());
				removeItemDefs(defs);
			}
		});
		btnRemove.setBounds(301, 128, 90, 28);
		panel_1.add(btnRemove);
		
		JLabel label = new JLabel("Objects:");
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label.setBounds(34, 18, 155, 21); //34,18,155,21
		panel_1.add(label);
		
		JButton btnDuplicate = new JButton("Clone");
		btnDuplicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectDefinitions defs = objectList.getSelectedValue();
				if(defs == null)
					return;
				defs = (ObjectDefinitions) defs.clone();
				if(defs == null)
					return;
				defs.id = Utils.getObjectDefinitionSize(STORE);
				new ObjectEditor(app, defs);
			}
		});
		btnDuplicate.setBounds(301, 168, 90, 28);
		panel_1.add(btnDuplicate);
		addAllItems();
	}
	
	public void addAllItems() {
		for(int id = 0; id < Utils.getObjectDefinitionSize(STORE); id++) {
			addItemDefs(ObjectDefinitions.getObjectDefinition(STORE,id));
		}
	}
	
	public void addItemDefs(final ObjectDefinitions defs) {
		EventQueue.invokeLater(new Runnable() { 
            public void run() { 
            	objectModelList.addElement(defs);
            }
        });
	}
	
	public void updateObjectDefs(final ObjectDefinitions defs) {
		EventQueue.invokeLater(new Runnable() { 
            public void run() { 
            	int index = objectModelList.indexOf(defs);
            	if(index == -1)
            		objectModelList.addElement(defs);
            	else
            		objectModelList.setElementAt(defs, index);
            }
		});
	}
	
	public void removeItemDefs(final ObjectDefinitions defs) {
		EventQueue.invokeLater(new Runnable() { 
            public void run() { 
            	objectModelList.removeElement(defs);
            }
        });
	}
	
	public JFrame getFrame() {
		return frmCacheEditorV;
	}
	
}
