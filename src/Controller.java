//package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

	//FXML Variables for UI objects
	@FXML Group gCanopy;
	@FXML Group gUnderGrowth;
	@FXML Pane p1;
	@FXML Label l1;
	@FXML Slider sRadLower;
	@FXML Slider sRadHigher;
	@FXML ImageView ivBackground;

	//Vars for Scaling and Zooming
	Scale s = new Scale();
	final DoubleProperty myScale = new SimpleDoubleProperty(1.0);


	//Data structures to hold file data
	private ElevationGrid elevationGrid;
	private Species[] species;
	private Plant[] undergrowthPlants;
	private Plant[] canopyPlants;

	//Data structures used for FireModel and Filtering
	private Filter filter;
	private PlantModel plantModel;
	private FireModel fireModel;

	//TODO
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//Read Files into Classes
		FileReader fr = new FileReader("C:\\Users\\jordan\\IdeaProjects\\EcoVis\\data");
		species = fr.getSpecies();
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = fr.getUndergrowthPlants();

		//Initialize Classes for FireModel and Filtering
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants, species);
		filter = new Filter(canopyPlants, undergrowthPlants, species);

		//Initialize Parent Pane and Background
		p1.setPrefSize(elevationGrid.getDimX()*elevationGrid.getGridSpacing(),elevationGrid.getDimY()*elevationGrid.getGridSpacing());
		p1.getTransforms().add(s);
		ivBackground.setImage(elevationGrid.getBackground());
		ivBackground.fitHeightProperty().bind(p1.heightProperty());
		ivBackground.fitWidthProperty().bind(p1.widthProperty());
		//Set Default Colours for Species and Draw all the Plants
		setSpcColour(species.length);
		initPlantVis();

		//put this into UI calls
		fireModel = new FireModel(plantModel);
		LinkedList<Coordinate> fireStart = new LinkedList<Coordinate>();
		fireStart.add(new Coordinate(180,60));
		fireStart.add(new Coordinate(200, 100));
		fireStart.add(new Coordinate(600, 600));
		fireStart.add(new Coordinate(800, 200));
		System.out.println("Computing Firemodel");
		fireModel.computeSpread(400, fireStart, 3);

	}

	//ERROR with storing plant ID it seems
	/*
	private void initPlantVis()
	{
		for(Plant plant: undergrowthPlants)
		{
			Circle circle = new Circle();
			circle.setCenterX(plant.getX());
			circle.setCenterY(plant.getY());
			circle.setFill(Paint.valueOf(species[plant.getID()].getColour()));
			circle.setRadius(plant.getCanopyRadius());

			gUnderGrowth.getChildren().add(circle);
			plant.setCircle(circle);
		}
		for(Plant plant: canopyPlants)
		{
			Circle circle = new Circle();
			circle.setCenterX(plant.getX());
			circle.setCenterY(plant.getY());
			circle.setFill(Paint.valueOf(species[plant.getID()].getColour()));
			circle.setRadius(plant.getCanopyRadius());

			gCanopy.getChildren().add(circle);
			plant.setCircle(circle);
		}

		canopyNodes = gCanopy.getChildren();
		underGrowthNodes = gUnderGrowth.getChildren();
	}*/


	//Initial creation and display of Circles to represent Plants
	private void initPlantVis()
	{
		Plant plant;
		for(int i=0;i<species.length;i++)
		{
			for(int j=species[i].getCanopyPos();j<species[i].getCanopyPos()+species[i].getNumCanopyPlants();j++)
			{
				plant = canopyPlants[j];
				Circle circle = new Circle();
				circle.setCenterX(plant.getX());
				circle.setCenterY(plant.getY());
				circle.setFill(Paint.valueOf(species[i].getColour()));
				circle.setRadius(plant.getCanopyRadius());

				gCanopy.getChildren().add(circle);
				plant.setCircle(circle);
			}
			for(int j=species[i].getUnderPos();j<species[i].getUnderPos()+species[i].getNumUnderGrowthPlants();j++)
			{
				plant = undergrowthPlants[j];
				Circle circle = new Circle();
				circle.setCenterX(plant.getX());
				circle.setCenterY(plant.getY());
				circle.setFill(Paint.valueOf(species[i].getColour()));
				circle.setRadius(plant.getCanopyRadius());

				gUnderGrowth.getChildren().add(circle);
				plant.setCircle(circle);
			}
		}
	}

	//TODO Update Colours, maybe spectral
	private void setSpcColour()
	{
		String co;
		for(int i=0;i< 16;i++)
		{
			switch (i)
			{
				case 0:
					co = "#14467532";
					break;
				case 1:
					co = "#c2828232";
					break;
				case 2:
					co = "#d0000032";
					break;
				case 3:
					co = "#6ae07732";
					break;
				case 4:
					co = "#006b0a32";
					break;
				case 5:
					co = "#ccff0032";
					break;
				case 10:
					co = "#20405532";
					break;
				case 11:
					co = "#a282a232";
					break;
				case 12:
					co = "#d0700b32";
					break;
				case 13:
					co = "#2ae03732";
					break;
				case 14:
					co = "#a06b0c32";
					break;
				case 15:
					co = "#acc0a262";
					break;
				default:
					co = "#ff008f32";

			}
			species[i].setColour(co);
		}
	}

	//Attempt at dynamically allocating colours
	public void setSpcColour(int numSpc)
	{
		int inc = 255/(numSpc/2);
		String[] colours = new String[numSpc];
		for(int i=0; i<numSpc; i++){colours[i]="#00";}
		String temp;
		for(int i=0; i<numSpc; i+=2)
		{
			colours[i]+="ff";
			temp=Integer.toHexString(inc*(i/2));
			if(temp.length()==1){temp="0"+temp;}
			colours[i]+=temp;
		}
		for(int i=1; i<numSpc; i+=2)
		{
			temp=Integer.toHexString(inc*(i/2));
			if(temp.length()==1){temp="0"+temp;}
			colours[i]+=temp;
			colours[i]+="ff";
		}
		for(int i=0; i<numSpc; i++){colours[i]+="32";species[i].setColour(colours[i]);}
	}

	//Code to enable scrolling
	public void setOnScroll(ScrollEvent event)
	{
		if(event.getDeltaY()>0)
		{
			myScale.set(myScale.getValue() * 1.2);
			//myScale.set(myScale.getValue()+0.15);
		}
		else
		{
			if (myScale.getValue() > 1.0)
			{
				myScale.set(myScale.getValue() / 1.2);
				//myScale.set(myScale.getValue()-0.15);
			}
			else{myScale.set(1.0);}
		}
		s.setPivotX((s.getPivotX()+event.getX())/2);
		s.setPivotY((s.getPivotY()+event.getY())/2);
		s.setX(myScale.getValue());
		s.setY(myScale.getValue());

	}

	//Code for Panning
	//TODO Make this work

	double mX=0.0, mY=0.0;
	public void panOnDrag(MouseEvent event)
	{
		s.setPivotX(s.getPivotX()+(mX-event.getX()));
		s.setPivotY(s.getPivotY()+(mY-event.getY()));
	}
	/*
	public void panOnDrag(MouseEvent event)
	{
		s.setPivotX(event.getX());
		s.setPivotY(event.getY());
	}*/
	public void mouseHand(MouseEvent event){p1.setCursor(Cursor.CLOSED_HAND);mX=event.getX();mY=event.getY();}
	public void mousePointer(MouseEvent event){p1.setCursor(Cursor.DEFAULT);}


	//Filter by Radius using two sliders
	public void updateRadFilter(MouseEvent event)
	{
		filter.filterByRadius((float)sRadLower.getValue(), (float)sRadHigher.getValue());
	}

	//Filter by species using Buttons
	public void filterBySpecies(ActionEvent event){filter.filterSpc(spcFil);}
	public void remFilterBySpecies(ActionEvent event){filter.remFilterSpc(spcFil);}

	boolean[] layerFilter = {false, false, false};
	public void filterBackGround(ActionEvent event){ivBackground.setVisible(layerFilter[0]);layerFilter[0]=!layerFilter[0];}
	public void filterUnderGrowth(ActionEvent event){gUnderGrowth.setVisible(layerFilter[1]);layerFilter[1]=!layerFilter[1];}
	public void filterCanopy(ActionEvent event){gCanopy.setVisible(layerFilter[2]);layerFilter[2]=!layerFilter[2];}

	//Yeet these methods
	int spcFil = 0;
	public void filterInc(ActionEvent event)
	{
		if(spcFil<15){spcFil++;}
		else{spcFil=0;}
		l1.setText(String.valueOf(spcFil));
	}

	public void filterDec(ActionEvent event)
	{
		if(spcFil>0){spcFil--;}
		else{spcFil = 15;}
		l1.setText(String.valueOf(spcFil));
	}

	boolean proxFilterBool = true;
	public void filterProx(ActionEvent event){if(proxFilterBool){filter.filterByProxy((float)75, (float)75, (float)25);proxFilterBool=false;}else{filter.remFilterByProxy();proxFilterBool=true;}}


	int snapNo = 0;
	public void f0(ActionEvent event){fireColour(0);}
	public void f2(ActionEvent event){snapNo+= 10; fireColour(snapNo);}
	public void f3(ActionEvent event){snapNo+= 50; fireColour(snapNo);}
	public void fireColour(int snapNUM)
	{
		FireSnapshot fs = fireModel.getFireSnapShot(snapNUM);
		LinkedList<Plant> workingList;
		for(Coordinate c: fs.getBurned())
		{
			for(Plant plant : (LinkedList<Plant>)plantModel.getGrid()[c.getX()][c.getY()])
			{
				plant.setColour("#00000052");
			}
		}

		for(Coordinate c: fs.getBurning())
		{
			for(Plant plant : (LinkedList<Plant>)plantModel.getGrid()[c.getX()][c.getY()])
			{
				plant.setColour("#ff6c0399");
			}
		}
	}
}