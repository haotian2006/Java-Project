import Classes.*;
import UiClasses.*;
import Levels.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.Point;
import PremadeElements.*;
import java.io.File;
public class FinalProject {
    public static Point GetGrid(Point loc){
        return new Point(loc.x/Kitchen.TileSize, loc.y/Kitchen.TileSize);
    }
    public static ScheduledExecutorService MainLoop;
    public static ScreenGui screen;
    public static void StartMainLoop(double seconds){
      Memory.player.first = false;
      // this creates a loop that will update every counter every .01 seconds 
      // using this type of loop because sleep has some yielding issue and weird delay
        Thread thread = new Thread(() -> {
          MainLoop = Executors.newScheduledThreadPool(1);
          long interval = 10;
          double[] timeLeft ={seconds};//just doing seconds-=0.01 causes a scope error so we have to make it into a array
          boolean[] Lost = new boolean[1];//we have to use a array here because you can't get outside vars from inside of a new thread
          MainLoop.scheduleAtFixedRate(() -> {
              if (!Memory.player.GetInGame() || false){//if the game ended then stop the loop
                Lost[0] = true;
                MainLoop.shutdown();
              }
              Memory.Kitchen.Update(Memory.player.GetMouse());
              Memory.player.SetTimer(timeLeft[0]);
              timeLeft[0] -= .01;
              Memory.Kitchen.UpdateAll();
              Memory.player.Update();


          }, 0, interval, TimeUnit.MILLISECONDS);
          try {
            MainLoop.awaitTermination((long)(seconds*1000), TimeUnit.MILLISECONDS);
          } catch (InterruptedException e) {
          }
          MainLoop.shutdown();
          Memory.player.SetInGame(false);
          if (Lost[0]){
            LoseScreen ws = new LoseScreen();
            screen.add(ws,0);
            screen.repaint();
          }else{
            WinScreen ws = new WinScreen();
            screen.add(ws,0);
            screen.repaint();
            Player plr = Memory.player;
            //increase stars 
           plr.SetLevelData(Memory.Kitchen.GetLevel().GetRequirements(plr.getDifficulty(), plr.getScore()));
          }
          System.out.println("Ended");
         //end game
      });
      thread.start();
    }
    public static void StartLevel(int difficulty, int levelNum){
        Level level = new Template();
        Memory.Kitchen.LoadLevel(level);
        Frame Clickable = Memory.Kitchen.getClickFrame();
        Player plr = Memory.player;
        plr.setDifficulty(difficulty);
        plr.Clear();//this would reset the players temporary data 
        Clickable.addMouseListener(new MouseAdapter() {
          // on input 
          public void mousePressed(MouseEvent me) { 
            if (!plr.GetInGame())return;
            Point loc = GetGrid(me.getPoint());
            TileElement counterEle = Memory.Kitchen.GetTileAt(loc);
            if (counterEle == null){ return;}
            Counter counter = counterEle.getCounter();
            if (counter == null) {return;}
            if (me.getButton() == MouseEvent.BUTTON1){
                counter.OnInteract(plr,"Left");
                Memory.Kitchen.UpdateHolding(plr);
            }else if(me.getButton() == MouseEvent.BUTTON3){
              plr.SetCounter(counter);
              counter.OnInteract(plr,"Right");
              Memory.Kitchen.UpdateHolding(plr);
            }
          }
          // on input ended
          public void mouseReleased(MouseEvent me) { 
            if (!plr.GetInGame())return;
            if (me.getButton() == MouseEvent.BUTTON1){
            
            }
            else if(me.getButton() == MouseEvent.BUTTON3){
              plr.SetCounter(null);
            }
        } 

        }); 
        Clickable.addMouseMotionListener(new MouseMotionListener() {
          public void mouseDragged(MouseEvent e) {
            if (!plr.GetInGame())return;
            plr.SetMouse(e.getPoint());
            //Memory.Kitchen.Update(e.getPoint());
          }
          public void mouseMoved(MouseEvent e) {
            if (!plr.GetInGame())return;
            plr.SetMouse(e.getPoint());
           //Memory.Kitchen.Update(e.getPoint());
          }
        });
        plr.SetInGame(true);
        StartMainLoop(level.GetTimeLimit());

    }

    
    public static void main(String[] args) {
        RemoveAllDesktop.Destroy();

        Player player = new Player("bob");   
        screen = new ScreenGui("Under Cooked");
        Kitchen kitchen = new Kitchen(screen);
        Memory.SetPlayer(player);
        Memory.SetKitchen(kitchen);
        screen.FullScreen();
        screen.setBackground(new Color(187, 255, 177));
        StartLevel(2,1);
    }
}



//this function is to destroy  desktop.ini files created by google drive so it doesn't break some scripts 
class RemoveAllDesktop {
    public static void Destroy() {
        String currentDir = System.getProperty("user.dir");
        String parentDir = new File(currentDir).getParent();
        deleteDesktopIniFiles(parentDir);
    }

    private static void deleteDesktopIniFiles(String directory) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDesktopIniFiles(file.getAbsolutePath());
                } else if (file.getName().equals("desktop.ini")) {
                    file.delete();
                }
            }
        }
    }
} 