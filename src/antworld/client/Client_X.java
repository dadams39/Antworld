package antworld.client;

/*********************************************/
/* Antworld Battle Project                   */
/* Team Antithesis                           */
/* Contributors Danny Adams &                */
/* William Jennings                          */
/* Updated September 2, 2014                 */
/* Interfacing w/ clientPack_2014_09_02.zip  */
/*********************************************/
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import antworld.data.AntAction;
import antworld.data.AntData;
import antworld.data.CommData;
import antworld.data.Constants;
import antworld.data.Direction;
import antworld.data.NestNameEnum;
import antworld.data.TeamNameEnum;
import antworld.data.AntAction.AntActionType;

public class ClientRandomWalk
/********************************************/
/* Status: Network Connection Established   */
/* Artificial Intelligence: Non-Functional  */
/* Display capabilities:  JTable enabled    */
/* Gui Interfaces: None                     */
/********************************************/
{
  private static final boolean DEBUG = true;
  private static final TeamNameEnum myTeam = TeamNameEnum.Antithesis;
  private static final long password = 662985659947L;
  private ObjectInputStream inputStream = null;
  private ObjectOutputStream outputStream = null;
  private Dashboard mainFrame = null;
  private boolean isConnected = false;
  private NestNameEnum myNestName = null;
  private int refreshCounter, centerX, centerY;
  private int refreshRate = 125;

  private Socket clientSocket;

  private static Random random = Constants.random;

  public ClientRandomWalk(String host, int portNumber)
  /************************************************/
  /* ClientRandomWalk(String host, int portNumber */
  /* @ author: Joel Castellanos                   */
  /* @ param: String host, int port               */
  /*   Basic constructor for Antworld Project     */
  /************************************************/ 
  {
    System.out.println("Starting ClientRandomWalk: " + System.currentTimeMillis());
    isConnected = false;
    refreshCounter = 0;
    while (!isConnected)
    {
      isConnected = openConnection(host, portNumber);
      if (!isConnected) try { Thread.sleep(1000); } catch (InterruptedException e1) {}
    }
    CommData data = chooseNest();
    mainFrame = new Dashboard(data);
    mainGameLoop(data);
    closeAll();
  }

  private boolean openConnection(String host, int portNumber)
  /************************************************/
  /* openConnection(String host, int portNumber   */
  /* @ author: Joel Castellanos                   */
  /* @ param: String host, int port               */
  /* @ returns: boolean                           */
  /*   Starts connection to host server           */
  /************************************************/ 
  {
    try
    {
      clientSocket = new Socket(host, portNumber);
    }
    catch (UnknownHostException e)
    {
      System.err.println("ClientRandomWalk Error: Unknown Host " + host);
      e.printStackTrace();
      return false;
    }
    catch (IOException e)
    {
      System.err.println("ClientRandomWalk Error: Could not open connection to " + host + " on port " + portNumber);
      e.printStackTrace();
      return false;
    }

    try
    {
      outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
      inputStream = new ObjectInputStream(clientSocket.getInputStream());

    }
    catch (IOException e)
    {
      System.err.println("ClientRandomWalk Error: Could not open i/o streams");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void closeAll()
  /************************************************/
  /* closeAll()                                   */
  /* @ author: Joel Castellanos                   */
  /* @ param: N/A                                 */
  /*   Method closes out connection to server/game*/
  /************************************************/ 
  {
    System.out.println("ClientRandomWalk.closeAll()");
    {
      try
      {
        if (outputStream != null) outputStream.close();
        if (inputStream != null) inputStream.close();
        clientSocket.close();
      }
      catch (IOException e)
      {
        System.err.println("ClientRandomWalk Error: Could not close");
        e.printStackTrace();
      }
    }
  }

  public CommData chooseNest()
  /************************************************/
  /* chooseNest()                                 */
  /* @ author: Joel Castellanos                   */
  /* @ param: String host, int port               */
  /*   Assigns team to random nest in world map   */
  /************************************************/ 
  {
    while (myNestName == null)
    {
      try { Thread.sleep(100); } catch (InterruptedException e1) {}

      NestNameEnum requestedNest = NestNameEnum.values()[random.nextInt(NestNameEnum.SIZE)];
      CommData data = new CommData(requestedNest, myTeam);
      data.password = password;

      if( sendCommData(data) )
      {
        try
        {
          if (DEBUG) System.out.println("ClientRandomWalk: listening to socket....");
          CommData recvData = (CommData) inputStream.readObject();
          if (DEBUG) System.out.println("ClientRandomWalk: recived <<<<<<<<<"+inputStream.available()+"<...\n" + recvData);

          if (recvData.errorMsg != null)
          {
            System.err.println("ClientRandomWalk***ERROR***: " + recvData.errorMsg);
            continue;
          }

          if ((myNestName == null) && (recvData.myTeam == myTeam))
          { myNestName = recvData.myNest;
          centerX = recvData.nestData[myNestName.ordinal()].centerX;
          centerY = recvData.nestData[myNestName.ordinal()].centerY;
          System.out.println("ClientRandomWalk: !!!!!Nest Request Accepted!!!! " + myNestName);
          return recvData;
          }
        }
        catch (IOException e)
        {
          System.err.println("ClientRandomWalk***ERROR***: client read failed");
          e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
          System.err.println("ClientRandomWalk***ERROR***: client sent incorect data format");
        }
      }
    }
    return null;
  }

  public void mainGameLoop(CommData data)
  /************************************************/
  /* mainGameLoop(CommData data)                  */
  /* @ author: Joel Castellanos                   */
  /* @ modified by CS351 Team.                    */
  /* @ param: CommData object                     */
  /*   Basic manipulator for Antworld Project     */
  /*   team movements.                            */
  /*   Method may call other methods contained in */
  /*   client package.                            */
  /************************************************/ 
  {
    while (true)
    {
      try
      {
        if (DEBUG) System.out.println("Antithesis: chooseActions: " + myNestName);

        chooseActionsOfAllAnts(data);

        CommData sendData = data.packageForSendToServer();

        //Resets password to 0 for internet security
        sendData.password = 0;
        

        //System.out.println("ClientRandomWalk: Sending>>>>>>>: " + sendData);
        outputStream.writeObject(sendData);
        outputStream.flush();
        outputStream.reset();
        
        //Displays Table for Ant Tracking
        if(refreshCounter % refreshRate == 0)
        {
          mainFrame.updateTable(data);
          mainFrame.setVisible (true);
          refreshCounter = 0;
        }
        
        refreshCounter++;

        if (DEBUG) System.out.println("ClientRandomWalk: listening to socket....");
        CommData recivedData = (CommData) inputStream.readObject();
        if (DEBUG) System.out.println("ClientRandomWalk: received <<<<<<<<<"+inputStream.available()+"<...\n" + recivedData);
        data = recivedData;
        if (DEBUG) System.out.println("refreshCounter:" + Integer.toString(refreshCounter));

        if ((myNestName == null) || (data.myTeam != myTeam))
        {
          System.err.println("ClientRandomWalk: !!!!ERROR!!!! " + myNestName);
        }
      }
      catch (IOException e)
      {
        System.err.println("ClientRandomWalk***ERROR***: client read failed");
        e.printStackTrace();
        try { Thread.sleep(1000); } catch (InterruptedException e1) {}

      }
      catch (ClassNotFoundException e)
      {
        System.err.println("ServerToClientConnection***ERROR***: client sent incorect data format");
        e.printStackTrace();
        try { Thread.sleep(1000); } catch (InterruptedException e1) {}
      }

    }
  }


  private boolean sendCommData(CommData data)
  {

    CommData sendData = data.packageForSendToServer();
    try
    {
      if (DEBUG) System.out.println("ClientRandomWalk.sendCommData(" + sendData +")");
      outputStream.writeObject(sendData);
      outputStream.flush();
      outputStream.reset();
    }
    catch (IOException e)
    {
      System.err.println("ClientRandomWalk***ERROR***: client read failed");
      e.printStackTrace();
      try { Thread.sleep(1000); } catch (InterruptedException e1) {}
      return false;
    }

    return true;

  }

  private void chooseActionsOfAllAnts(CommData commData)
  /************************************************/
  /* chooseActionsOfAllAnts(CommData data)        */
  /* @ author: Joel Castellanos                   */
  /* @ modified by CS351 Team.                    */
  /* @ param: CommData object                     */
  /*   Mutatator for Antworld Project. Generic    */
  /*   ant commands movements. Ants will not      */
  /*   bring any interact with food if this method*/
  /*   is called, but location will change.       */
  /************************************************/ 
  {
    for (AntData ant : commData.myAntList)
    {
      AntAction action = chooseAction(commData, ant);
      ant.myAction = action;
    }
  }

  private AntAction chooseAction(CommData data, AntData ant)
  {
    AntAction action = new AntAction(AntActionType.STASIS);

    if (ant.ticksUntilNextAction > 0) return action;

    if (ant.underground)
    {
      action.type = AntActionType.EXIT_NEST;
      action.x = centerX - Constants.NEST_RADIUS + random.nextInt(2 * Constants.NEST_RADIUS);
      action.y = centerY - Constants.NEST_RADIUS + random.nextInt(2 * Constants.NEST_RADIUS);
      return action;
    }

    action.type = AntActionType.MOVE;
    action.direction = Direction.getRandomDir();

    return action;
  }

  public static void main(String[] args)
  {

    String serverHost = "b146-75";

    if (args.length > 0) serverHost = args[0];

    new ClientRandomWalk(serverHost, Constants.PORT); // Create an instance of the test application



  }

}
