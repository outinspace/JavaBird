package com.wilsongateway.gameObjects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.wilsongateway.framework.Board;
import com.wilsongateway.framework.Game;
import com.wilsongateway.framework.GameObject;
import com.wilsongateway.framework.Board.Stage;

/**
 * Name	 	: Nicholas Lane Wilson
 * Class 	    : 1620 - 002
 * Program # 	: 7
 * Due Date  	: 12/7/2016
 *
 * Honor Pledge:  On my honor as a student of the University
 *                of Nebraska at Omaha, I have neither given nor received
 *                unauthorized help on this homework assignment.
 *
 * NAME: Nicholas Lane Wilson
 * NUID: 350
 * EMAIL: nlwilson@unomaha.edu
 * 
 * Partners:   NONE
 *	
 * Description: Represents a sub class of Tile. Contains the behaviors of the platform game object.
 * 				Handles the moving and rendering of the platform.
 */
public class Platform extends Tile {

	protected static ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	/**
	 * 
	 * Method Name   : [Constructor]
	 * Parameters    : position : int
	 * Description   : Adds this to platforms.
	 */
	public Platform(int position) {
		super(position);
		platforms.add(this);
	}

	/**
	 * 
	 * Method Name   : refreshTiles
	 * Parameters    : none
	 * Return Values : void
	 * Description   : Repositions old tiles or creates new ones to fill the screen.
	 */
	public static void refreshTiles() {
		//Check if it is a new board
		if(platforms.size() == 0){
			//Creating all new Tiles, adds width + tileWidth for overlap
			for(int position = 0; position < Game.board.getWidth() + tileWidth; position += tileWidth){
				new Platform(position);
			}
		}else{
			//Re-assign the old Tiles
			int i = 0;
			for(int position = 0; position < Game.board.getWidth() + tileWidth; position += tileWidth){
				if(i < platforms.size()){
					platforms.get(i).setPosition(position);
				}else{
					//Create extra tiles if there isn't enough
					new Platform(position);
				}
				i++;
			}
			//Remove any extra tiles
			for(;i < platforms.size(); i++){
				platforms.remove(i);
			}
		}
	}

	/**
	 * 
	 * Method Name   : paintTile
	 * Parameters    : g2d : Graphics2D
	 * Return Values : none
	 * Description   : Renders the current platform and updates the positon.
	 */
	@Override
	public void paint(Graphics2D g2d){
		g2d.drawImage(Game.getPlatform(), Board.roundMid(position), Game.board.getHeight()-platformHeight, null);
		
		//Dev mode outline
		if(Game.board.devMode){
			g2d.drawLine(Board.roundMid(position), Game.board.getHeight()-platformHeight, Board.roundMid(position), Game.board.getHeight());
		}
	}
	
	@Override
	public void move(){
		if(position + tileWidth < 0){
			position = (platforms.size()-1) * tileWidth;
		}
		
		//Controls movement of game object with respect to the 'current' Stage enum.
		if(Game.board.current == Stage.PLAYING || Game.board.current == Stage.MAINMENU){
			position -= Game.heightRatio()*Game.board.speedScaler*Game.tpsRatio();
		}
	}
	
	//Boilerplate
	public static ArrayList<Platform> getPlatforms(){return platforms;}
}
