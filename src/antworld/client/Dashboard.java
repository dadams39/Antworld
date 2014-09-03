package antworld.client;

/************************************************/
/* Dashboard <Class>                            */
/* @ author: Danny Adams                        */
/* @ modified by CS351 Team.                    */
/*   Created September 1, 2014                  */
/*   Updated September 2, 2014                  */
/*   package: antworld                          */
/*   Allows for the visualization of ants       */
/*   coordinates within the visual world.       */
/************************************************/ 

import java.awt.*;
import javax.swing.*;
import antworld.data.AntData;
import antworld.data.CommData;

@SuppressWarnings("serial")
public class Dashboard  extends JFrame
{
  // Instance attributes used in this example
  private JPanel topPanel;
  private JTable table;
  private JScrollPane scrollPane;

  public Dashboard()
  {
    // Set the frame characteristics

    this.setTitle( "Simple Table Application" );
    this.setSize( 300, 200 );
    this.setBackground( Color.gray );

    // Create a panel to hold all other components
    topPanel = new JPanel();
    topPanel.setLayout( new BorderLayout() );
    this.getContentPane().add( topPanel );

    // Create columns names
    String columnNames[] = { "Column 1", "Column 2", "Column 3" };

    // Create some data
    String dataValues[][] =
      {
        { "12", "234", "67" },
        { "-123", "43", "853" },
        { "93", "89.2", "109" },
        { "279", "9033", "3092" }
      };

    // Create a new table instance
    table = new JTable( dataValues, columnNames );

    // Add the table to a scrolling pane
    scrollPane = new JScrollPane( table );
    topPanel.add( scrollPane, BorderLayout.CENTER );

  }

  public Dashboard(CommData commData)
  {
    // Set the frame characteristics

    this.setTitle( "Ant_Locator" );
    this.setSize( 400, 400 );
    this.setBackground( Color.cyan );

    // Create a panel to hold all other components
    topPanel = new JPanel();
    topPanel.setLayout( new BorderLayout() );
    this.getContentPane().add( topPanel );

    // Create columns names
    String columnNames[] = { "Ant_Name", "X Coord.", "Y Coord." };

    int column = commData.myAntList.size();
    String ant_Values[][] = new String [column][3];
    int index = 0;
    for(AntData ant : commData.myAntList)
    {
      ant_Values[index][0] = "Ant_" + Integer.toString(index);
      ant_Values[index][1] = Integer.toString(ant.gridX);
      ant_Values[index][2] = Integer.toString(ant.gridY);
      index ++;
    }
   
    // Create a new table instance
    table = new JTable( ant_Values, columnNames );

    // Add the table to a scrolling pane
    scrollPane = new JScrollPane( table );
    topPanel.add( scrollPane, BorderLayout.CENTER );

  }
  
  public void updateTable(CommData commData)
  
  {
    this.topPanel = new JPanel();
    this.topPanel.setLayout( new BorderLayout());
    this.getContentPane().add( topPanel );
  
    String columnNames[] = { "Ant_Name", "X Coord.", "Y Coord." };

    int column = commData.myAntList.size();
    String ant_Values[][] = new String [column][3];
    int index = 0;
    for(AntData ant : commData.myAntList)
    {
      ant_Values[index][0] = "Ant_" + Integer.toString(index);
      ant_Values[index][1] = Integer.toString(ant.gridX);
      ant_Values[index][2] = Integer.toString(ant.gridY);
      index ++;
    }
   
    // Create a new table instance
    table = new JTable( ant_Values, columnNames );

    // Add the table to a scrolling pane
    scrollPane = new JScrollPane( table );
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
    topPanel.add( scrollPane, BorderLayout.CENTER );
    
  }
//Simple Unit Test
  public static void main( String args[] )
  {
    // Create an instance of the test application
    Dashboard mainFrame  = new Dashboard();
    mainFrame.setVisible( true );
  }

}