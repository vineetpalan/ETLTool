/** * @(#)ETL.java
 *
 * ETL application
 *
 * @vineet palan
 * @version 1.00 2012/4/20
 */
 
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.filechooser.*;

class MyFrame extends JFrame implements ActionListener
{
	String databasePath="",filePath="";
	boolean databaseSelected=false,fileSelected=false;
	
	String currentTable="";
	//----------------------
	JButton b1,b2,b3,b4;
	JPanel top,bottom;
	JPanel p1;
	//---------------------
	JPanel extraction,extraction1,extraction2;
	JButton pb1=new JButton();
	JButton selectDB=new JButton();
    JComboBox tableName=new JComboBox();
    
	//---------------------
	JPanel transformation;
	JCheckBox cb1,cb2,cb3;
	JButton transfrom=new JButton("Transform");
	//---------------------
	JPanel loading,loading1,loading2;
	JButton load1=new JButton();
	JButton load2=new JButton();
	JButton l_selectDB=new JButton();
    JComboBox l_tableName=new JComboBox();
    JButton savefileSelect=new JButton(" . . ");
    
	//---------------------
	JTable table;
	JScrollPane pane;
	//---------------------
	Statement st;
    Connection con;
    
    int fileColumns=0,fileRows=0;
    
	JButton pb2=new JButton();
	JButton fileSelect=new JButton(" .. ");
    JFileChooser fileChooser=new JFileChooser();
    	
	MyFrame()
    {
    setLayout(new GridLayout(2,1));	
    	
    //---------------------------------------------

    top = new JPanel(new GridLayout(1, 1));
    table=new JTable(15,10);
    pane = new JScrollPane(table);
  	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setAutoscrolls(true);
    top.add(pane);

    
    //---------------Panel Initialization----------------------
    
    bottom = new JPanel(new GridLayout(2, 1));
    p1=new JPanel();
    extraction=new JPanel(new GridLayout(1,2));
    transformation=new JPanel();
    loading=new JPanel(new GridLayout(1,2));
    
    //---------------------------------------------------------
    
   	
    b1 = new JButton("Extraction");
    b2 = new JButton("Transformation");
    b3 = new JButton("Loading");
    b4 = new JButton("Exit");
   

	b1.addActionListener(this);
	b2.addActionListener(this);
	b3.addActionListener(this);
	b4.addActionListener(this);
	

   	p1.add(b1);
    p1.add(b2);
    p1.add(b3);
    p1.add(b4);
    
    
    bottom.add(p1);
    bottom.add(extraction);


    
    //----------------------------------------------
    add(top);
    add(bottom);
    setSize(1000,1000);
    //setUndecorated(true);
  	//getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
  	setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    show(true);		
    }
    
    
    public void actionPerformed(ActionEvent ae)
    {
    
    	//System.out.println(ae);
    	
    	
    	if (ae.getSource()==b1)
    	{
    		//Extraction
    		
    		bottom.remove(1);
    		bottom.add(extraction);
    		bottom.revalidate();
    		bottom.repaint();
    		
    		
    		createConnection();
    		removeAllComponents(extraction);
    		
    		extraction1=new JPanel();
			extraction2=new JPanel();
			
			//--------------extract from database------------
			
			pb1 =new JButton("Extract");
			selectDB = new JButton("Select Database");
    		tableName=new JComboBox(getTableNames());
    		JLabel label1=new JLabel("Select Table");
    		
			pb1.addActionListener(this);
			selectDB.addActionListener(this);
			
			extraction1.setBorder(BorderFactory.createTitledBorder("Extract From DataBase"));
			extraction1.add(label1);
			extraction1.add(tableName);
			extraction1.add(pb1);
			extraction1.add(selectDB);
			
			//-----------------------------------------------
			
			
			//--------------extract from file----------------

			pb2 =new JButton("Extract ");
    		JLabel label2=new JLabel("Select File");
			
			pb2.addActionListener(this);
			fileSelect.addActionListener(this);
			
			extraction2.setBorder(BorderFactory.createTitledBorder("Extract From File"));
			extraction2.add(label2);
			extraction2.add(fileSelect);
			extraction2.add(pb2);
			
			//-----------------------------------------------
			
    		extraction.add(extraction1);
    		extraction.add(extraction2);
  
			
    		
    		top.revalidate();
    		extraction.revalidate();
    		
    	}
    	if (ae.getSource()==b2)
    	{
    		//Transformation

    		bottom.remove(1);
    		bottom.add(transformation);
       		bottom.revalidate();
    		bottom.repaint();
    		
    		
    		removeAllComponents(transformation);
    		
    		//null check--phone no standardization-----1/0 to yes/no------
    		
    		cb1=new JCheckBox("Null Check");
    		cb2=new JCheckBox("Phone No. Standardization");
    		cb3=new JCheckBox("1/0  to Yes/No");
    		
    		
    		transformation.add(cb1);
    		transformation.add(cb2);
    		transformation.add(cb3);
    		transformation.add(transfrom);
    		
    		transfrom.addActionListener(this);
    		
    		transformation.revalidate();
    		
    	}
    	else if (ae.getSource()==b3)
    	{
    		//Loading....
    		bottom.remove(1);
    		bottom.add(loading);
    		bottom.revalidate();
    		bottom.repaint();
    		
    		
    		removeAllComponents(loading);
    		
    		
    		loading1=new JPanel();
			loading2=new JPanel();
			
			//-------------load to database--------------
			
			l_selectDB = new JButton("Select Database ");
     		
			load1.addActionListener(this);
			l_selectDB.addActionListener(this);
			
			loading1.setBorder(BorderFactory.createTitledBorder("Extract To DataBase"));
			loading1.add(l_selectDB);
			
			//--------------------load to file---------------------------
			load2 =new JButton("Load ");
    		JLabel label2=new JLabel("Select File");
			
			load2.addActionListener(this);
			savefileSelect.addActionListener(this);
			
			loading2.setBorder(BorderFactory.createTitledBorder("Extract to File"));
			loading2.add(label2);
			loading2.add(savefileSelect);
			loading2.add(load2);
			
			//-------------------------------------------------------------------------
			
			
			
			loading.add(loading1);
    		loading.add(loading2);
  
			
       		loading.revalidate();
    		
			
    		
    			
    		
    	}
    	if (ae.getActionCommand().equals("Select Database "))
    	{
    		fileChooser=new JFileChooser();
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Access DataBase Files", new String[]{"mdb","accdb"});
            fileChooser.setFileFilter(filter);
    		int ans=fileChooser.showOpenDialog(this);
    		String newdatabasePath="";
    		
            if (ans == JFileChooser.APPROVE_OPTION) 
            {
                    File f = fileChooser.getSelectedFile();
                    newdatabasePath=f.getPath();
                    Connection con1;
                    Statement st1;
                    newdatabasePath = newdatabasePath.replace("\\", "\\" + "\\");
        			try
        			{
            		con1 = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};Dbq=" + newdatabasePath);
            		st1 = con1.createStatement();
            		System.out.println(newdatabasePath);
        		
            		int columnCount=table.getColumnCount();
		    		int rowCount=table.getRowCount();
		    		
		    		
		    		 
		    		int t = 0;
		    		String str = "INSERT INTO " + currentTable + " values (";
		    	
		    		while(t<columnCount)
		    		{
		    			str = str + "?,";
		    			t++;
		    		}
		    		t = str.lastIndexOf(",");
		    		str = str.substring(0,t);
		    		str = str+")";
		    		
		    	
		    		PreparedStatement pstm=con1.prepareStatement(str);
		    		
		    		for(int i=0;i<rowCount;i++)
		    		{
			    		for(int j=0;j<columnCount;j++)
			    		{
						pstm.setString((j+1),table.getValueAt(i,j).toString());
			    		}
			    		pstm.executeUpdate();
			    		pstm.clearParameters();
		    		}
		    		pstm.close();
		    		JOptionPane.showMessageDialog(this,"Done!!");    		
		    		System.out.println(str);
		    		}
		    		catch(Exception e)
		    		{
		    			JOptionPane.showMessageDialog(this,e.toString());
		    		}
    		
            		
                    
            }
           
    	}
    	else if (ae.getSource()==b4)
    	{
    		//Exit
    		System.exit(0);
    	}
    	
    	if (ae.getActionCommand().equals("Transform"))
    	{
    			//Transform the data
    			if (databaseSelected==true || fileSelected==true)
    			{
    				
	    			if (cb1.isSelected())
	    			{
	    				nullCheck();
	    			}
	    			if (cb2.isSelected())
	    			{
	    				phoneNo();
	    			}
	    			if (cb3.isSelected())
	    			{
	    				yesNo();
	    			}
	    			
    			}
    			else
    			{
    				JOptionPane.showMessageDialog(this,"Please Select Database/File for Extraction!!");
    			}
    	}
      	//----------------------------------------------------------------------------------------
    	if (ae.getActionCommand().equals("Extract"))
    	{
    		//Extract From Selected database
    		String selectedTable=tableName.getSelectedItem().toString();
    		currentTable=selectedTable;
    		
    		System.out.println("selectedTable = "+selectedTable);
    		top.remove(pane);
    		getTableColumnHeader(selectedTable);
    		getTableData(selectedTable);
    		pane = new JScrollPane(table);
    		top.add(pane);
    		top.revalidate();
    	}
    	if (ae.getActionCommand().equals("Select Database"))
    	{
    		fileChooser=new JFileChooser();
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Access DataBase Files", new String[]{"mdb","accdb"});
            fileChooser.setFileFilter(filter);
    		int ans=fileChooser.showOpenDialog(this);
    		
            if (ans == JFileChooser.APPROVE_OPTION) 
            {
                    File f = fileChooser.getSelectedFile();
                    databasePath=f.getPath();
                    databaseSelected=true;
                    b1.doClick();
            }
           
    	}
    	if (ae.getActionCommand().equals("Extract "))
    	{
    		System.out.println("Extract from File");
    		String str=null;

    		try
    		{
				File f=new File(filePath);
				int cols=getFileColumns(f);
    			DefaultTableModel tableModel = new DefaultTableModel(0,cols);
    		
	   			DataInputStream in=new DataInputStream(new FileInputStream(f));
	   			fileSelected=true;
	   			
	   			while( (str=in.readLine()) != null)
	   			{
	   				splitData(str,tableModel);
	   			}
    		}
    		catch(Exception e)
    		{
    			
    		}
    	}
    	if (ae.getActionCommand().equals(" .. "))
    	{
    		//-----------select txt file-------------------------------
    		fileChooser=new JFileChooser();
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files Only", "txt");
            fileChooser.setFileFilter(filter);
    		int ans=fileChooser.showOpenDialog(this);
    		
    		if (ans == JFileChooser.APPROVE_OPTION) 
            {
            	File f = fileChooser.getSelectedFile();
                filePath=f.getPath();
            }
    			
    	}
    	if (ae.getActionCommand().equals(" . . "))
    	{
    		//-----------select txt file-------------------------------
    		fileChooser=new JFileChooser();
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files Only", "txt");
            fileChooser.setFileFilter(filter);
    		int ans=fileChooser.showSaveDialog(this);
    		
    		if (ans == JFileChooser.APPROVE_OPTION) 
            {
            	File f = fileChooser.getSelectedFile();
                filePath=f.getPath();
                filePath+=".txt";
                try
                {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
                for (int i = 0; i < table.getRowCount(); i++) 
    			{
            		for (int j = 0; j < table.getColumnCount(); j++) 
            		{
		                
		                    out.print(table.getValueAt(i, j).toString());
		                    out.print(";");
		            }
		            out.println();
    			}
    			out.close();
    			JOptionPane.showMessageDialog(this,"Data Loaded into File Successfully!!");
                }
                catch(Exception e)
                {
                	
                }
            }
    			
    	}
    	
    	
    	//----------------------------------------------------------------------------------------
    	//----------------------------------------------------------------------------------------
    	
    }
    
    public void nullCheck()
    {
    	for (int i = 0; i < table.getRowCount(); i++) 
    	{
            for (int j = 0; j < table.getColumnCount(); j++) 
            {
                if (table.getValueAt(i, j) == null) 
                {
                    table.setValueAt("null", i, j);
                }
            }
        }
    }
    public void phoneNo()
    {
    	for (int i = 0; i < table.getRowCount(); i++) 
    	{
            for (int j = 0; j < table.getColumnCount(); j++) 
            {	
    			if (table.getValueAt(i, j).toString().length()==10)
                {
                    if (Character.isDigit(table.getValueAt(i, j).toString().charAt(0)) )
                    {
                         String s=table.getValueAt(i, j).toString();
                         table.setValueAt(s.substring(0,3) + "-" +s.substring(3,6) + "-" + s.substring(6) , i, j);
                    }
                }
            }
         }
    }
    public void yesNo()
    {
    	try
    	{
	    	ResultSet rs = st.executeQuery("SELECT * FROM "+ currentTable);
	  		ResultSetMetaData rsmd = rs.getMetaData();
	  		int NumOfCol = rsmd.getColumnCount();
	  		for(int i=1;i<=NumOfCol;i++)
	  		{
	  		
	  			if ((rsmd.getColumnTypeName(i)).equals("BIT"))
	  			{
	  				for(int k=0;k<table.getRowCount();k++)
	  				{
	  					if (table.getValueAt(k,i-1).toString().equals("0"))
		  				{
		  					table.setValueAt("No",k, i-1);
		  				}
		  				else
		  				{
		  					table.setValueAt("Yes",k,i-1);
		  				}
	  				}
	  			}
	  		}
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    
    public int getFileColumns(File ff)
    {
    	int columns=0;
    	String strr=null;
    	try
    	{
	   		DataInputStream inn=new DataInputStream(new FileInputStream(ff));
	   		while( (strr=inn.readLine()) != null)
	   		{
	   			for(int i =0; i < strr.length(); i++)
				{
					if(strr.charAt(i) == ',' || strr.charAt(i)==';')
        			columns++;
				}
				break;
	   		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("getFileColumns = " + e);
    	}
    	
	   	return columns;
    }
    //---------------------------split file Data-----------------------------------------------
    public void splitData(String str,DefaultTableModel tableModel)
    {
		str=str.replace(""+(char)(34),"");
		String temp[];
		
		if (str.contains(";"))
		{
			temp=str.split(";");	
		}
		else 
		{
			temp=str.split(",");
		}	
		
		
        table.setModel(tableModel);
        tableModel.addRow(temp);        
        tableModel.fireTableDataChanged();  	   		
    }
    //---------------------------getTableData-----------------------------------------------
    public void getTableData(String selectedTable)
    {
    	int row=0;
    	int n=getTableColumns(selectedTable);
    	try
    	{
    	ResultSet rss = st.executeQuery("SELECT * FROM " + selectedTable);
    	while (rss.next()) 
    		{
                int j=0;
             	for(int i=1;i<=n;i++)
                {
                	table.setValueAt(rss.getString(i), row, j); 
                	j++; 
                }
                row++;                
            }
            rss.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
     //---------------------------getTableColumnHeader--------------------------------------------
    public void getTableColumnHeader(String selectedTable)
    {
    	try
    	{
  
	    	ResultSet rs = st.executeQuery("SELECT * FROM " + selectedTable);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			Vector temp=new Vector();
			
			for (int i = 1; i < columnCount + 1; i++ ) 
			{
			 temp.addElement(rsmd.getColumnName(i));
			}
			DefaultTableModel model = new DefaultTableModel(temp,getTableRows(selectedTable));
			table.setModel(model);
			rs.close();
			
    	}
    	catch(Exception e)
		{
		}
    }
    //---------------------------getTableRows--------------------------------------------
    public int getTableRows(String selectedTable)
    {
    	int temp=0;
    	
    	
        try 
        {
        	ResultSet rs = st.executeQuery("select count(*) from " + selectedTable);
            
            while(rs.next())
            	temp=rs.getInt(1); 
            
            rs.close();		           
        }
        catch (SQLException ex)
        {
			System.out.println("getTableRows = " + ex);
        }
    	    	
    	return temp;
    	
    }
    //---------------------------getTableColumns---------------------------------------
    public int getTableColumns(String selectedTable)
    {
    	int temp=0;

        try 
        {
            ResultSet rs = st.executeQuery("select * from " + selectedTable);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            temp = rsMetaData.getColumnCount();
            rs.close();       
        }
        catch (SQLException ex)
        {
			System.out.println("getTableColumns = " + ex);
        }
    	
    	return temp;
    }
    //---------------------------getTableNames-----------------------------
    public Vector getTableNames()
    {
    	Vector temp=new Vector();
    	try
    	{
 
    	DatabaseMetaData dbmd = con.getMetaData();
    	String[] types = {"TABLE"};
    	ResultSet rs = dbmd.getTables(null, null, "%", types);
    	while (rs.next())
    	{
    		temp.addElement(rs.getString(3));
    	}
    	}
    	catch(Exception e)
    	{
    		System.out.println("getTableNames=" + e);
    	}
    	
    	return temp;
    }
    //-----------------------------createConnection------------------------------------
    
    public void createConnection()
    {
    	try
        {
        	
        	databasePath = databasePath.replace("\\", "\\" + "\\");
        	System.out.println(databasePath);
            con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};Dbq=" + databasePath);
            st = con.createStatement();
            System.out.println("Connection Establised Successfully");

        }
        catch (SQLException e)
        {
			System.out.println("createConnection=" + e);
        }
    }
    //-----------------------------removeAllComponents------------------------------------
    public void removeAllComponents(JPanel panel)
    {
  		panel.removeAll();
		panel.revalidate(); 
		panel.repaint(); 	
    }
    //=====================================================================================
}



class ETL
{
  public static void main(String arg[])
  {
  MyFrame frame=new MyFrame();
  }
}
