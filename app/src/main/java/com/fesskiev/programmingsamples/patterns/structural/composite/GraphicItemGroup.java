package com.fesskiev.programmingsamples.patterns.structural.composite;


import java.util.ArrayList;
import java.util.List;

public class GraphicItemGroup {

    private List<Line> lines = new ArrayList<>();

    private List<Rectangle> rectangles = new ArrayList<>();

    public List<Line> getLines() {
        return lines;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public void draw(){
        for (Line line : lines) {
            line.draw();
        }

        for (Rectangle rectangle : rectangles) {
            rectangle.draw();
        }
    }
}
