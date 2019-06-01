package com.alex.tools.itemsDefsEditor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.alex.loaders.items.ObjectDefinitions;

@SuppressWarnings("serial")
public class ObjectEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ObjectDefinitions defs;
	private Application application;
	private JTextField modelIDField;
	private JTextField nameField;
	private JTextField modelSizeX;
	private JTextField modelSizeY;
	private JTextField clipType;
	private JTextField groundOptionsField;
	private JTextField inventoryOptionsField;
	private JTextField femaleModelId2Field;
	private JTextField configId;
	private JTextField configfileId;
	private JTextField maleModelId3Field;
	private JTextField femaleModelId1Field;
	private JTextField femaleModelId3Field;
	private JTextField modelOffset1Field;
	private JTextField modelOffset2Field;
	private JTextField teamIdField;
	private JTextField notedItemIdField;
	private JTextField switchNotedItemField;
	private JTextField lendedItemIdField;
	private JTextField switchLendedItemField;
	private JTextField changedModelColorsField;
	private JTextField changedTextureColorsField;
	private JCheckBox membersOnlyCheck;
	private JTextField price;
	private JTextField objectsAnimation;
	private JTextField equipSlotField;
	
	//saves it, do not use not done
	public void save() {
	
		defs.name = nameField.getText(); 
	//	defs.setModelSizeX(Integer.valueOf(modelSizeX.getText()));
		/*defs.setModelSizeY(Integer.valueOf(modelSizeY.getText()));
		defs.clipType =  Integer.valueOf(clipType.getText());
		String[] options = inventoryOptionsField.getText().split(";");
		for(int i = 0; i < defs.getOptions().length; i++)
			defs.getOptions()[i] = options[i].equals("null") ? null : options[i];
		defs.objectAnimation = Integer.valueOf(objectsAnimation.getText());
		defs.configId = Integer.valueOf(configId.getText());
		defs.configFileId = Integer.valueOf(configfileId.getText());
		defs.modelIds = defs.modelIds;*/
	//	defs.modelIds = defs.getModels();
		defs.write(Application.STORE);
		application.updateObjectDefs(defs);
	}
	
	/**
	 * Create the dialog.
	 */
	public ObjectEditor(Application application, ObjectDefinitions defs) {
		super(application.getFrame(), "Zaria Object Editor", true);
		this.defs = defs;
		this.application = application;
		setBounds(100, 100, 912, 420);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Model ID:");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel.setBounds(6, 43, 81, 21);
		contentPanel.add(lblNewLabel);
		{
			modelIDField = new JTextField();
			modelIDField.setBounds(139, 40, 122, 28);
			contentPanel.add(modelIDField);
			modelIDField.setColumns(10);
			modelIDField.setText(""+defs.modelIds.toString());
			String text = "null";
			for(int[] option : defs.getModels())
				text += (option == null ? "null" : option)+";";
			modelIDField.setText(""+Arrays.deepToString(defs.modelIds).toLowerCase().toString());
		}
		{
			JLabel label = new JLabel("Name:");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 76, 81, 21);
			contentPanel.add(label);
		}
		{
			nameField = new JTextField();
			nameField.setBounds(139, 73, 122, 28);
			contentPanel.add(nameField);
			nameField.setColumns(10);
			nameField.setText(defs.getName());
		}
		{
			JLabel label = new JLabel("Model Size X:");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 109, 95, 21);
			contentPanel.add(label);
		}
		{
			modelSizeX = new JTextField();
			modelSizeX.setBounds(139, 106, 122, 28);
			contentPanel.add(modelSizeX);
			modelSizeX.setColumns(10);
			modelSizeX.setText(""+defs.getSizeX());
		}
		{
			JLabel label = new JLabel("Model size y:");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 142, 139, 21);
			contentPanel.add(label);
		}
		{
			modelSizeY = new JTextField();
			modelSizeY.setBounds(139, 139, 122, 28);
			contentPanel.add(modelSizeY);
			modelSizeY.setColumns(10);
			modelSizeY.setText(""+defs.getSizeY());
		}
		{
			JLabel label = new JLabel("Clip type");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 175, 139, 21);
			contentPanel.add(label);
		}
		{
			clipType = new JTextField();
			clipType.setBounds(139, 172, 122, 28);
			contentPanel.add(clipType);
			clipType.setColumns(10);
		clipType.setText(""+defs.getClipType());
		}
		{
			JLabel label = new JLabel("Brightness");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 208, 108, 21);
			contentPanel.add(label);
		}
		{
			groundOptionsField = new JTextField();
			groundOptionsField.setBounds(139, 205, 122, 28);
			contentPanel.add(groundOptionsField);
			groundOptionsField.setColumns(10);
			groundOptionsField.setText(""+defs.getBrightness());
		}
		{
			JLabel label = new JLabel("Options:");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 241, 139, 21);
			contentPanel.add(label);
		}
		{
			inventoryOptionsField = new JTextField();
			inventoryOptionsField.setBounds(139, 238, 122, 28);
			contentPanel.add(inventoryOptionsField);
			inventoryOptionsField.setColumns(10);
			String text = "";
			for(String option : defs.getOptions())
				text += (option == null ? "null" : option)+";";
					inventoryOptionsField.setText(text);
			//inventoryOptionsField.setText(defs.getThirdOption());
		}
		{
			JLabel label = new JLabel("Constract");
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setBounds(6, 270, 139, 21);
			contentPanel.add(label);
		}
		{
			inventoryOptionsField = new JTextField();
			inventoryOptionsField.setBounds(139, 267, 122, 28);
			contentPanel.add(inventoryOptionsField);
			inventoryOptionsField.setColumns(10);
			inventoryOptionsField.setText(""+defs.getContrast());
		}
		objectsAnimation = new JTextField();
		objectsAnimation.setText("");
		objectsAnimation.setColumns(10);
		objectsAnimation.setBounds(139, 300, 122, 28);
		contentPanel.add(objectsAnimation);
		
		JLabel lblEquipType = new JLabel("Object animation");
		lblEquipType.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblEquipType.setBounds(6, 300, 139, 21);
		contentPanel.add(lblEquipType);
		objectsAnimation.setText(""+defs.objectAnimation);
		
		JLabel label_2 = new JLabel("Config id");
		label_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		label_2.setBounds(273, 43, 131, 21);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("ConfigFileId:");
		label_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		label_3.setBounds(273, 76, 131, 21);
		contentPanel.add(label_3);
		
		configId = new JTextField();
		configId.setBounds(411, 40, 122, 28);
		contentPanel.add(configId);
		configId.setColumns(10);
		configId.setText(""+defs.getConfigId());
		{
			configfileId = new JTextField();
			configfileId.setBounds(411, 73, 122, 28);
			contentPanel.add(configfileId);
			configfileId.setColumns(10);
			configfileId.setText(""+defs.configFileId);
		}
		{
			JButton saveButton = new JButton("Save");
			saveButton.setBounds(6, 328, 55, 28);
			contentPanel.add(saveButton);
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					save();
					dispose();
				}
			});
			getRootPane().setDefaultButton(saveButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setBounds(73, 328, 67, 28);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	
}
