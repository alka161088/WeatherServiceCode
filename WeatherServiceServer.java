// WeatherServiceImpl.java

import java.io.*;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import java.util.concurrent.TimeUnit;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WeatherServiceServer implements WeatherService {
                                   
   public WeatherServiceServer ()
   {
      super();
      updateWeatherConditions();
   };

   private List weatherInformation;  // WeatherBean objects

   private void updateWeatherConditions()
   {
      try {
         System.err.println( "Update weather information..." );

   
         URL url = new URL(
	      "https://forecast.weather.gov/product.php?site=CRH&product=SCS&issuedby=01");
         
        
         BufferedReader in = new BufferedReader(
            new InputStreamReader( url.openStream() ) );

         System.err.println( "Update weather information...1" );

         String separator = "TEMPERATURES INDICATE";

	 String line = "";
         for ( line = in.readLine(); !line.startsWith(separator ); line = in.readLine() )
            System.err.println( line );    // do nothing

         separator = "CITY";

         for ( line = in.readLine(); !line.startsWith(separator ); line = in.readLine() )
            System.err.println( line );    // do nothing


         String inputLine = "";

         System.err.println( "Update weather information...2" + inputLine);

         weatherInformation = new ArrayList(); // create List
         

         inputLine = in.readLine();  // skip an empty line
         inputLine = in.readLine();  // first city info line
         System.err.println( "Update weather information...3" + inputLine);

	 String cityName = "";
	 String temperatures = "";
	 String condition = "";
    String pcpn ="";
    String desc1 = "";
    String forecast_yest = "";
    String desc2 = "";
    String forecast_tomm = "";
    
         System.err.println( "Update weather information...4" );

         while ( inputLine.length() > 10 ) {

	   System.err.println(inputLine.substring( 0, 17 )+"--"+inputLine.substring( 33, 38 )+"--"+inputLine.substring( 17, 20 ));

	
	  cityName = inputLine.substring( 0, 16 );
 	  temperatures = inputLine.substring( 16, 23 );
	  condition = inputLine.substring( 32, 38 );
     pcpn = inputLine.substring( 26, 31 );
     desc1 = inputLine.substring( 32, 38 );
     forecast_yest = inputLine.substring( 40, 47 );
     desc2 = inputLine.substring( 48, 56 );
     forecast_tomm = inputLine.substring( 56 );
     

	  cityName = cityName.trim();
	  temperatures = temperatures.trim();
	  condition = condition.trim();
     pcpn = pcpn.trim();
     desc1 = desc1.trim();
     forecast_yest = forecast_yest.trim();
     desc2 = desc2.trim();
     forecast_tomm = forecast_tomm.trim();

         System.err.println( "City:" + cityName + ", condition:" + condition + ", temp:" + temperatures + ", pcpn:" + pcpn+ ", desc1:" +desc1+", forecast_yest:" +forecast_yest+", desc2:" +desc2+", forecast_tomm:" +forecast_tomm);
          WeatherBean weather = new WeatherBean(
               cityName,   
               condition,   
               temperatures,
               pcpn,
               desc1, 
               forecast_yest, 
               desc2,
               forecast_tomm);


          weatherInformation.add( weather ); 
            
            inputLine = in.readLine();  // get next city's info
         }

         System.err.println( "Initializing WeatherService...5" );

         in.close();  // close connection to NWS Web server  
         
         System.err.println( "Weather information updated." );
      }
      

      catch( java.net.ConnectException connectException ) {
         connectException.printStackTrace();
         System.exit( 1 );
      }
      
      catch( Exception exception ) {
         exception.printStackTrace();
         System.exit( 1 );
      }
   }

   public List getWeatherInformation() throws RemoteException 
   {
      return weatherInformation;
   }

   public static void main( String args[] )
   {     
	try {
		System.setSecurityManager (new SecurityManager());

		System.err.println( "Initializing WeatherService..." );

		WeatherServiceServer obj = new WeatherServiceServer();
		WeatherService stub = (WeatherService) UnicastRemoteObject.exportObject(obj, 0);

		while(true)
   {
       Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		registry.rebind("WeatherService", stub);
	       
          TimeUnit.MINUTES.sleep(60);

         System.out.println("temperature updated in 1 hour");
   }

	     // System.err.println( "WeatherService running." );

	} catch (Exception e) {
		System.err.println("Server exception: " + e.toString());
		e.printStackTrace();
	}

   }
}
