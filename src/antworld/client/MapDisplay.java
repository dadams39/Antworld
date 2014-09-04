package antworld.client;

public class MapDisplay 
{
  /********************************/
  /* MapDisplay.java              */
  /*  written by Danny Adams      */
  /*  Updated August 21, 2014     */
  /********************************/
  public MapDisplay ()
  {
    String file = "/nfs/student/d/dadams39/cs351/AntWorld/AntWorld.png";
    new Picture(file);
  }
  public static void main(String args[])
  {
    new MapDisplay();
  }

}
