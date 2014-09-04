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
  // Instance attributes 
  private JPanel topPanel;
  private JTable table;
  private JScrollPane scrollPane;

  public Dashboard()
  /************************************************/
  /* Dashboard()                                  */
  /* @ author: Danny Adams                        */
  /* @ modified by CS351 Team.                    */
  /*   Basic Constructor.                         */
  /*   Used for initiation of JTable in program   */
  /************************************************/
  {
    //Frame settings
    this.setTitle( "JTable Set-up" );
    this.setSize( 300, 200 );
    this.setBackground( Color.gray );

    // Create a panel to hold all other components
    topPanel = new JPanel();
    topPanel.setLayout( new BorderLayout() );
    this.getContentPane().add( topPanel );

    // Create columns names
    String columnNames[] = { "Name", "Age", "State" };

    // Corresponding Data Entries
    String dataValues[][] =
      {
        { "Danny", "33", "New Mexico" },
        { "Bob", "43", "Delaware" },
        { "Trinity", "19", "Utah" },
        { "Brandon", "20", "Florida" }
      };

    // JTable instance
    table = new JTable( dataValues, columnNames );

    // Adding a scrolling pane
    scrollPane = new JScrollPane( table );
    topPanel.add( scrollPane, BorderLayout.CENTER );

  }

  public Dashboard(CommData commData)
  /************************************************/
  /* Dashboard(CommData commData)                 */
  /* @ author: Danny Adams                        */
  /* @ modified by CS351 Team.                    */
  /*   Constructor utilized in Ant_Project        */
  /*   Used for tracking of ants in  JTable       */
  /************************************************/
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
    String columnNames[] = { "Ant_Name", "X Coord.", "Y Coord.", "Units" };

    int column = commData.myAntList.size();
    String ant_Values[][] = new String [column][4];
    int index = 0;
    for(AntData ant : commData.myAntList)
    {
      ant_Values[index][0] = "Ant_" + Integer.toString(index);
      ant_Values[index][1] = Integer.toString(ant.gridX);
      ant_Values[index][2] = Integer.toString(ant.gridY);
      ant_Values[index][3] = Integer.toString(ant.carryUnits);
      index ++;
    }

    // Create a new table instance
    table = new JTable( ant_Values, columnNames );

    // Add the table to a scrolling pane
    scrollPane = new JScrollPane( table );
    topPanel.add( scrollPane, BorderLayout.CENTER );

  }

  public void updateTable(CommData commData)
  /************************************************/
  /* updateTable(CommData commData)               */
  /* @ param: CommData object[Antworld project]   */
  /* @ Returns: N/A                               */
  /*   Updates location of ants within tables.    */
  /*   Used for tracking of ants in  JTable       */
  /************************************************/
  {
    this.topPanel = new JPanel();
    this.topPanel.setLayout( new BorderLayout());
    this.getContentPane().add( topPanel );

    String columnNames[] = { "Ant_Name", "X Coord.", "Y Coord.", "Units" };

    int column = commData.myAntList.size();
    String ant_Values[][] = new String [column][4];
    int index = 0;
    for(AntData ant : commData.myAntList)
    {
      ant_Values[index][0] = "Ant_" + Integer.toString(index);
      ant_Values[index][1] = Integer.toString(ant.gridX);
      ant_Values[index][2] = Integer.toString(ant.gridY);
      ant_Values[index][3] = Integer.toString(ant.carryUnits);
      
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
