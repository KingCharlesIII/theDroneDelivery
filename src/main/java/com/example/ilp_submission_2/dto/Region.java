package com.example.ilp_submission_2.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Region {
    private String name;
    private List<Position> vertices;

    @JsonCreator
    public Region( @JsonProperty("vertices") List<Position> vertices, @JsonProperty("name") String name) {
        this.vertices = vertices;
        this.name = name;
    }

    public List<Position> getVertices() {
        return this.vertices;
    }

    /*
    Checks if the first and last vertices are the same, which
    would mean the region is closed, and a valid shape
     */
    public boolean firstEqualLast(){
        int lastValue = this.vertices.size() - 1;
        if(this.vertices.get(lastValue).getLat() != this.vertices.get(0).getLat() || this.vertices.get(lastValue).getLng() != this.vertices.get(0).getLng()){
            return false;
        }
        return true;
    }

    /*
    Checks if a position lies in an enclosed region
     */
    public int boundary(List<Position> vertices, Position pos) {
        int size = vertices.size();
        int count = 0;
        for(int j = 0; j < size; j++){
            if( j != size - 1){
                if((pos.getLat() >= Math.min(vertices.get(j).getLat(), vertices.get(j+1).getLat())) && (pos.getLat() <= Math.max(vertices.get(j).getLat(), vertices.get(j+1).getLat()))
                        && (pos.getLng() >= Math.min(vertices.get(j).getLng(), vertices.get(j+1).getLng())) && (pos.getLng() <= Math.max(vertices.get(j).getLng(), vertices.get(j+1).getLng()))){
                    return 1;
                }
            }
            if(j == size - 1){
                if((pos.getLat() >= Math.min(vertices.get(j).getLat(), vertices.get(0).getLat())) && (pos.getLat() <= Math.max(vertices.get(j).getLat(), vertices.get(0).getLat()))
                        && (pos.getLng() >= Math.min(vertices.get(j).getLng(), vertices.get(0).getLng())) && (pos.getLng() <= Math.max(vertices.get(j).getLng(), vertices.get(0).getLng()))){
                    return 1;
                }
                break;
            }
        }
        for(int i = 0; i < size; i++){
            if(i != size -1){
                if((pos.getLat() < vertices.get(i).getLat()) != ((pos.getLat() < vertices.get(i+1).getLat()))){
                    double xIntersect = vertices.get(i).getLng()
                            + (pos.getLat() - vertices.get(i).getLat())
                            * (vertices.get(i+1).getLng() - vertices.get(i).getLng())
                            / (vertices.get(i+1).getLat() - vertices.get(i).getLat());
                    if(xIntersect > pos.getLng()){
                        count = count + 1;
                    }
                }

            }
            if( i == size - 1){
                if((pos.getLat() < vertices.get(0).getLat()) != ((pos.getLat() < vertices.get(i).getLat()))){
                    double xIntersect = vertices.get(0).getLng()
                            + (pos.getLat() - vertices.get(0).getLat())
                            * (vertices.get(i).getLng() - vertices.get(0).getLng())
                            / (vertices.get(i).getLat() - vertices.get(0).getLat());
                    if(xIntersect > pos.getLng()){
                        count = count + 1;
                    }
                }
            }
        }
        return count;
    }
}