package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

import com.jogamp.opengl.math.geom.Frustum.Location;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 150, 50, 700, 500, new Microsoft.RoadProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
		de.fhpotsdam.unfolding.geo.Location valLoc = new de.fhpotsdam.unfolding.geo.Location(-38.14f, -73.03f); // create new location
		de.fhpotsdam.unfolding.geo.Location AlsakLoc= new de.fhpotsdam.unfolding.geo.Location(61.02f,-147.65f); // create Alaska Eq
		de.fhpotsdam.unfolding.geo.Location WestCoastLoc= new de.fhpotsdam.unfolding.geo.Location(3.3f,95.78f); // create west Eq
		de.fhpotsdam.unfolding.geo.Location EastCoastEq= new de.fhpotsdam.unfolding.geo.Location(38.322f,142.369f); // create east Eq
		de.fhpotsdam.unfolding.geo.Location kamchatka= new de.fhpotsdam.unfolding.geo.Location(52.76f,160.06f); // create Alaska Eq
		
		PointFeature ValEq =new PointFeature(valLoc); // create point feature for the location
		ValEq.addProperty("title", "Valdivia , chile");
		ValEq.addProperty("magnitude", "9.5");
		ValEq.addProperty("date", "May 22,1960");
		ValEq.addProperty("year", "1960");
		
		PointFeature AlaskaEq =new PointFeature(AlsakLoc); // create point feature for the location
		ValEq.addProperty("title", "Alsaka , ");
		ValEq.addProperty("magnitude", "9.2");
		ValEq.addProperty("date", "03 28,1964");
		ValEq.addProperty("year", "1964");
		
		PointFeature sumatraEq =new PointFeature(WestCoastLoc); // create point feature for the location
		ValEq.addProperty("title", "sumatra , ");
		ValEq.addProperty("magnitude", "9.1");
		ValEq.addProperty("date", "12 26,2004");
		ValEq.addProperty("year", "2004");
		
		PointFeature japnaEq =new PointFeature(EastCoastEq); // create point feature for the location
		ValEq.addProperty("title", "Japan , ");
		ValEq.addProperty("magnitude", "9.0");
		ValEq.addProperty("date", "03 28,2011");
		ValEq.addProperty("year", "2011");
		
		PointFeature kamchatkaEq	 =new PointFeature(kamchatka); // create point feature for the location
		ValEq.addProperty("title", "kamchatka , ");
		ValEq.addProperty("magnitude", "9.0");
		ValEq.addProperty("date","12 25,1952");
		ValEq.addProperty("year","1952");
		
		
		List<PointFeature> bigEqs = new ArrayList<PointFeature>();
		bigEqs.add(ValEq);
		bigEqs.add(AlaskaEq);
		bigEqs.add(sumatraEq);
		bigEqs.add(japnaEq);
		bigEqs.add(kamchatkaEq);
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();
	    

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    for(int i=0;i<earthquakes.size();i++)
	    {
	    	markers.add(createMarker(earthquakes.get(i)));
	    	
	    }
	    /* for(PointFeature eq:bigEqs)
	     {
	    	 markers.add(new SimplePointMarker(eq.getLocation(),eq.getProperties()));
	     }*/
	  /*   for (Marker mk: markers)
	     {
	    	 if(  mk.getIntegerProperty("year") == null)
	    	 {
	    		 mk.setColor(yellow);
	    	 }
	    	 else
	    		 mk.setColor(gray);
	     }*/
	    // Add the markers to the map so that they are displayed
	   
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    int red = color(255, 0, 0);
	    int blue=color(67, 53, 167);
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    if(mag < THRESHOLD_LIGHT )
   		 		{
			marker.setColor(blue);
			marker.setRadius(4);

   		 		}
	    else if(mag == THRESHOLD_LIGHT || mag < THRESHOLD_MODERATE)
	    {
			marker.setColor(yellow);
			marker.setRadius(7);

	    }
	    else
			marker.setColor(red);
	    	marker.setRadius(10);
		

	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(200,200,200);
	    map.draw();
	    textSize(32);
	    text("Red", 50, 50);
	    fill(0, 0, 0);
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		
	}
}
