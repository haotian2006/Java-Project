package UiClasses;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class ImageLabel extends JLabel  implements UiElement{
    public Point GetCenterRelativeToFrame(){//deprecated 
        Dimension size = getSize();
        return new Point(size.width/2, size.height/2);
    }
    public void LerpXSize(int x,double t){
        x = (int) Math.ceil(x*t);
        setSize(x,getSize().height);
    }
    public void LerpYSize(int y,double t){
        y = (int) Math.ceil(y*t);
        setSize(getSize().width,y);
    }
    public void LerpImageXSize(int x,double t){
        x = (int) Math.ceil(x*t);
        SetImageSize(x,this.sizey);
    }
    public void LerpImageYSize(int y,double t){
        y = (int) Math.ceil(y*t);
        SetImageSize(this.sizex,y);
    }
    private ImageIcon image;
    private int sizex,sizey;
    public ImageLabel(String image){
       SetImage(image);
    }
    public ImageLabel(){// makes it so sending an image is optional 
    }
    public void SetImage(String imagePath) {
        if (imagePath.equals("") || imagePath == null){return;}
        try {
            File path = new File(imagePath);
            FileInputStream fis = new FileInputStream(path);  
            BufferedImage image = ImageIO.read(fis);
            ImageIcon icon = new ImageIcon(image);
            this.image = icon;
            setIcon(icon);
            if (sizex != 0 && sizey != 0) SetImageSize(sizex, sizey);
        } catch (IOException e) {
            System.out.println("Failed to load image: " + e.getMessage());
        }
    }
    public void SetImageSize(int x,int y){
        sizex = x;
        sizey = y;
        if (this.image == null) return;
        Image scaledImage = image.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        image = scaledIcon;
        setIcon(scaledIcon);
    }
    public void SetImageSize(Dimension x){
        SetImageSize(x.width, x.height);
    }
    public void SetCenter(Point p){
        Dimension size = getSize();
        int[] half = new int[]{size.width/2, size.height/2};
        setLocation(p.x-half[0], p.y-half[1]);
        
    }
    public Point GetCenter(){
        Dimension size = getSize();
        Point loc = getLocation();
        return new Point(size.width/2+loc.x, size.height/2+loc.y);
    }
    public void LerpPosition(int x,int y,double t){
        x = (int) (x*t);
        y = (int) (y*t);
        setLocation(x,y);
    }
    public ImageLabel Clone(){// creates a new imagelable with similar properties 
        ImageLabel newI = new ImageLabel();
        newI.setBackground(getBackground());
        newI.setBounds(getBounds());
        newI.setIcon(image);
        newI.image = image;
        newI.SetImageSize(sizex, sizey);
        return newI;
    }
}
