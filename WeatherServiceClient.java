// WeatherServiceClient.java 

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.*;   
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class WeatherServiceClient extends JFrame
{

   public WeatherServiceClient( String server ) 
   {
      super( "RMI WeatherService Client" ); 

      try {

	 Registry registry = LocateRegistry.getRegistry(server);
	 WeatherService stub = (WeatherService) registry.lookup("WeatherService");

         List<WeatherBean> weatherInformation = new ArrayList( 
            stub.getWeatherInformation() );   

         List<WeatherBean> weatherBeans = new ArrayList();

         
       ListModel weatherListModel =  new WeatherListModel( weatherInformation );

         JList weatherJList = new JList( weatherListModel );
         weatherJList.setCellRenderer( new WeatherCellRenderer() );
         getContentPane().add( new JScrollPane( weatherJList ) );

         weatherJList.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "  City                 Yesterday            PCPN           Desc         Today         Desc        Tommorow", TitledBorder.LEFT, TitledBorder.TOP));

      }
      
      catch ( ConnectException connectionException ) {
         System.err.println( "Connection to server failed. " +
            "Server may be temporarily unavailable." );
         
         connectionException.printStackTrace();
      }
      
      catch ( Exception exception ) {
         exception.printStackTrace();
      }
      
   } 

   public static void main( String args[] )
   {

      WeatherServiceClient client = null;
      System.setSecurityManager (new SecurityManager());

      if ( args.length == 0 )
         client = new WeatherServiceClient( "localhost" );
      else
         client = new WeatherServiceClient( args[ 0 ] );

      client.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      client.pack();
      client.setResizable( true ); 
      client.setVisible( true );  
   }
}
