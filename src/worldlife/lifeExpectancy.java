package worldlife;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jogamp.opengl.util.glsl.ShaderCode;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class lifeExpectancy extends PApplet {
	UnfoldingMap map;
	public void setup() {
		size(800,600,OPENGL);
		map=new UnfoldingMap(this, 50, 50, 700, 500, new Microsoft.RoadProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		loadfromCSV("LifeExpectancyWorldBankModule3.csv");
		List<Feature> countries = GeoJSONReader.loadData(this, "LifeExpectancyWorldBankModule3.csv");
		List<Marker> contriesMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarker((Marker) contriesMarkers);
		ShadeCounteries();
		
	}
	
	private void ShadeCounteries() {
		// TODO Auto-generated method stub
		for (Marker mk : contryMarkers)
		{
			String countryId = mk.getId();
			if(lifeExpByCountry.containskey(countryId))
			{
				float lifexp = lifeExpByCountry.get(countryId);
				int colorLevel = (int) map (lifexp,40,90,10,255);
				mk.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else
			{
				mk.setColor(color(150, 150, 150));

			}
		}
	}

	public void draw() {
	map.draw();	
	}
private Map<String, Float> loadfromCSV(String file) {
	    Map<String, Float> lifeMap = new HashMap<String, Float>();
	    String[] rows = loadStrings(file);
	    
	    for (String row : rows) {
	        String[] columns = row.split(",");
	        if (columns != null && columns.length > 5) {  // Make sure there are at least 6 columns
	            float val = Float.parseFloat(columns[5]);
	            lifeMap.put(columns[4], val);
	        }
	    }
	    return lifeMap;
	}

}
