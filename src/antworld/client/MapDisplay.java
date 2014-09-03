package antworld.client;



public class MapDisplay {
  
  /********************************************/
  /* A_Star_Rendering.java                    */
  /*  written by Danny Adams                  */
  /*  Updated August 21, 2014                 */
  /********************************************/
 
  public MapDisplay ()
  {

    
      String file = "/nfs/student/d/dadams39/cs351/AntWorld/AntWorld.png";
      new Picture(file);

      
    }
  public static void main(String args[])
  {
    
    MapDisplay trial = new MapDisplay();
  }

}
