package demos;

import processing.core.*;

public class app extends PApplet {
    private String URL = "C:\\Users\\Assor\\Downloads\\100386361_2984173511667791_6024501989889540096_n.jpg"; // replace with a valid image URL or local path
    private PImage bgImg;

    // Setup method that runs once at the beginning
    public void setup() {
        size(200, 200);
        // Load the image from the URL or local path
        bgImg = loadImage(URL); 
    }

    // Draw method that runs continuously
    public void draw() {
       
            image(bgImg, 0, 0, width, height); // Draw the image to the canvas
            fill(255,209,0);
            ellipse(width/4, height/5, width/5, height/5);
    }

   
}
