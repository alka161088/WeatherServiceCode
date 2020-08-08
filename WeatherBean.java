
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WeatherBean implements Serializable {
   
   private String cityName;         // name of city
   private String temperature;      // city's temperature
   private String description;      // weather description
   private ImageIcon image;         // weather image
   private String pcpn;
   private String desc1;
   private String forecast_yest;
   private String desc2;
   private String forecast_tomm;
   
   private static Properties imageNames; 
   
   static {
      imageNames = new Properties(); // create properties table
      
      try {
         
         URL url = WeatherBean.class.getResource( "imagenames.properties" );
         
         imageNames.load( new FileInputStream( url.getFile() ) );
      }
      catch ( IOException ioException ) {     
         ioException.printStackTrace();
      }

   } // end static block
   
   public WeatherBean( String city, String weatherDescription, 
      String cityTemperature, String pcpn1, String desc01, String forecast01, 
      String desc02, String forecast02 )
   {
      cityName = city;
      temperature = cityTemperature;
      description = weatherDescription.trim();
      pcpn = pcpn1;
      desc1 = desc01;
      forecast_yest = forecast01;
      desc2 = desc02;
      forecast_tomm = forecast02;
      
      System.err.println( "Update weather bean: City=" +city+
                                                "temp="+cityTemperature+
                                                "condition="+weatherDescription+
                                                "pcpn="+pcpn+
                                                "desc 1="+desc1+
                                                "forcast 1="+forecast_yest+
                                                "desc 2="+desc2+
                                                "forcast 2="+forecast_tomm );

      URL url = WeatherBean.class.getResource( "images/" + 
         imageNames.getProperty( description, "noinfo.jpg" ) );      

      image = new ImageIcon( url );
   }

   public String getCityName() 
   { 
      return cityName; 
   }

   public String getTemperature() 
   {
      return temperature; 
   }

   public String getDescription() 
   {
      return description; 
   }
   
   public String getPcpn() 
   {
      return pcpn; 
   }

   public String getForecast_yest() 
   {
      return forecast_yest; 
   }
   
   public String getDesc1() 
   {
      return desc1; 
   }

   public String getForecast_tomm() 
   {
      return forecast_tomm; 
   }

   public String getDesc2() 
   {
      return desc2; 
   }

   public ImageIcon getImage() 
   {      
      return image; 
   }
}

