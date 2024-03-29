package UiClasses;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;
import easingTypes.*;

public class UiTween implements Serializable{
    private JComponent frame;
    private EaseFunc func;
    private EaseType type;
    private float duration = 1;
    public UiTween(JComponent Frame,EaseFunc Func,EaseType Type){
        frame = Frame;func = Func; type = Type;
    }
    public UiTween(JComponent Frame,EaseFunc Func,EaseType Type,double t){
        frame = Frame;func = Func; type = Type;duration = (float) t;
    }
    public Dimension TweenSize(Dimension Start,Dimension goal,double time){
        Tween x = new Tween(func, type, Start.width, goal.width,duration);
        Tween y = new Tween(func, type, Start.height, goal.height,duration);
        Dimension d = new Dimension((int) x.GetValue(time), (int) y.GetValue(time));
        frame.setSize(d);
        return d;
    }   
    public Dimension TweenSizeFromCenter(Dimension Start,Dimension goal,double time){
        Tween x = new Tween(func, type, Start.width, goal.width,duration);
        Tween y = new Tween(func, type, Start.height, goal.height,duration);
        Point center = ((UiElement)frame).GetCenter();
        Dimension d = new Dimension((int) x.GetValue(time), (int) y.GetValue(time));
        frame.setSize(d);
        ((UiElement)frame).SetCenter(center);
        return d;
    }    
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    public Color TweenBackgroundColor(Color Start,Color goal,double time){
        Tween r = new Tween(func, type, Start.getRed(), goal.getRed(),duration);
        Tween g = new Tween(func, type, Start.getGreen(), goal.getGreen(),duration);
        Tween b = new Tween(func, type, Start.getBlue(), goal.getBlue(),duration);
        int red = (int) clamp(r.GetValue(time) , 0, 255);
        int green = (int) clamp(g.GetValue(time), 0, 255);
        int blue = (int) clamp(b.GetValue(time) , 0, 255);
        Color color = new Color(red, green, blue);
        frame.setBackground(color);
        return color;
    }

    public Point TweenLocation(Point Start,Point goal,double time){
        if (time >1) time = 1;
        Tween x = new Tween(func, type, Start.x, goal.x,duration);
        Tween y = new Tween(func, type, Start.y, goal.y,duration);
        frame.setLocation((int) x.GetValue(time), (int) y.GetValue(time));
        //.out.println(y.GetValue(time)+"|"+time);
        return frame.getLocation();
    }
    public void SetDuration(double x){
        duration = (float) x;
    }
    public void Play(TweenProperty x,Object Start, Object End,double time){
        duration =(float) 1;
        Thread thread = new Thread(() -> {
            long start = System.nanoTime();
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            final double[] i = {0};
            long interval = 10;
            executor.scheduleAtFixedRate(() -> {
                i[0] += 0.01/time;
                switch (x){
                    case TweenLocation:
                    TweenLocation((Point) Start, (Point) End, i[0]);
                    break;
                case TweenBackgroundColor:
                    TweenBackgroundColor((Color) Start, (Color) End, i[0]);
                    break;
                case TweenSizeFromCenter:
                    TweenSizeFromCenter((Dimension) Start, (Dimension) End, i[0]);
                    break;
                case TweenSize:
                    TweenSize((Dimension) Start, (Dimension) End, i[0]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid tween property: " + x);
                }
            }, 
            0, interval, TimeUnit.MILLISECONDS);
            try {
                executor.awaitTermination((long)(time*1000+100), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {}
            executor.shutdown();
        });
        thread.start();
        
    }

}