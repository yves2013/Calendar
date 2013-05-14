package com.connect2mysql;

import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.generator.DefaultSQLGenerator;
/**
 * Main application class.
 */
public class Connect2mysqlApplication extends Application {
	private SQLContainer phonebook;
	private Window mainWindow;
	private Table table;
    @Override
    public void init() {
    	Mysql mysql=new Mysql("jdbc:mysql://localhost:3306/STCOURSE","root","");
    	// mapping with the table VAADIN_EMPLOYEE of the Oracle database
    	phonebook = mysql.queryTable("T");

    	// creation of a table in the GUI
    	table = new Table("PHONEBOOK", phonebook);
    	table.setPageLength(20); // the number of rows per page
    	table.setSizeFull(); // the table will fill the window
    	table.setImmediate(true); // the server is notify each time I select a row or modify values 
    	table.setSelectable(true); // the user is allowed to select rows
    	table.setMultiSelect(false); // the user is not allowed to select more than one row
    	table.setEditable(true); // the user is allowed to modify values in the selected row

    	// building the main window
    	mainWindow = new Window("Vaadin_phonebook");
    	setMainWindow(mainWindow); 

    	// layout of the main window
    	VerticalLayout vl = new VerticalLayout();
    	vl.setSpacing(true);
    	vl.setMargin(true);
    	mainWindow.setContent(vl);

    	// panel of action buttons
    	HorizontalLayout hl = new HorizontalLayout();
    	hl.setSpacing(true);
    	hl.setMargin(true);
    	Button addButton = new Button("Add", phonebook, "addItem"); // adds an empty row into the container and thus the table
    	hl.addComponent(addButton);
    	Button validerButton = new Button("Validate", this, "validate");
    	hl.addComponent(validerButton);
    	Button deleteButton = new Button("Delete", this, "delete");
    	hl.addComponent(deleteButton);

    	// placement of the buttons' panel and the table into the main window
    	vl.addComponent(hl);
    	vl.addComponent(table); 
    	}

    	// validate an update of the container into the database
    	public void validate()
    	{
    	try {
    	phonebook.commit();
    	mainWindow.showNotification("SUCCESS", "Transaction successfull", Window.Notification.TYPE_HUMANIZED_MESSAGE);
    	} catch (Exception e) {
    	mainWindow.showNotification("ERROR", e.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
    	} 
    	}
    	// delete a record
    	public void delete()
    	{ 
    	RowId itemID = (RowId) table.getValue(); // retrieves the id of the record 
    	if (itemID != null) {
    	phonebook.removeItem(itemID); // remove the record from the container
    	validate(); // perform the update into the database
    	}
    	}
       
    }



