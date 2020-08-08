
import java.awt.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WeatherItem extends JPanel { 
   
   private static final long serialVersionUID = 1;
   private WeatherBean weatherBean;  // weather information

   private static ImageIcon backgroundImage;
   
   static {
      
      URL url = WeatherItem.class.getResource( "images/back.jpg" );
      backgroundImage = new ImageIcon( url );
   
 }

   public WeatherItem( WeatherBean bean )
   {
      weatherBean = bean;
   }

   public void paintComponent( Graphics g )
   {
      super.paintComponent( g );
      
      backgroundImage.paintIcon( this, g, 0, 0 );
            
      Font font = new Font( "SansSerif", Font.BOLD, 12 );
      g.setFont( font );
      g.setColor( Color.white );
      g.drawString( weatherBean.getCityName(), 10, 19 );
      g.drawString( weatherBean.getTemperature(), 130, 19 );
      g.drawString( weatherBean.getPcpn(), 190, 19 );
      //g.drawString( weatherBean.getDesc1(), 250, 19 );
      weatherBean.getImage().paintIcon( this, g, 250, 0 );
      g.drawString( weatherBean.getForecast_yest(), 310, 19 );
      weatherBean.getImage().paintIcon( this, g, 370, 0 );
      //g.drawString( weatherBean.getDesc2(), 370, 19 );
      g.drawString( weatherBean.getForecast_tomm(), 420, 19 );

      //weatherBean.getImage().paintIcon( this, g, 470, 1 );



   } // end method paintComponent

   public Dimension getPreferredSize()
   {
     
      return new Dimension( backgroundImage.getIconWidth() + 100,
         backgroundImage.getIconHeight() );
   }
}

