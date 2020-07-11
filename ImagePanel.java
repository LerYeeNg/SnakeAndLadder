import javax.swing.*;
import java.awt.*;
/**
 * Image Panel extended from JPanel to enable the use of image as background of a panel
 * reference from https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 * @author Ler Yee Ng 101991752
 * @version 1.0
 */
public class ImagePanel extends JPanel {
private static final long serialVersionUID = 1L;
private Image image = null;
private int iWidth2;
private int iHeight2;

public ImagePanel(Image image)
{
    this.image = image;
}


public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (image != null)
    {
        g.drawImage(image,50,5,this);
    }
}
}
