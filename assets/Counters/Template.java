package Counters;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Classes.*;
public class Template extends Counter{
    static String name = "Template";
    static String ImagePath = "Template.png";
    static Holdable holding;
    public void OnInteract(Player plr){
        plr.setHolding(new Item());
    }
    public Template(){
        super();
    }
    
}
}