package Counters;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Classes.*;
public class Bread extends Counter{
    static String name = "Bread";
    static String ImagePath = "assets/Images/Counters/BreadCrate.png";
    static Holdable holding;
    public void LeftClick(Player plr){
        if (plr.getHolding() == null){
            plr.setHolding(Item.newItem("Bread"));
        }
    }
    public Bread(){
        super(name,ImagePath);
    }
    
}
