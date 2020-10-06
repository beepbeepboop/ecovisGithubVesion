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

	@FXML
	Group gCanopy;
	@FXML
	Group gUnderGrowth;
	@FXML
	Pane p1;
	@FXML
	Label l1;

	Scale s = new Scale();
	final DoubleProperty myScale = new SimpleDoubleProperty(1.0);

	private ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private Species[] species;
	private LinkedList<Plant> undergrowthPlants;
	private LinkedList<Plant> canopyPlants;
	PlantModel plantModel;
	int noSpc;

	ObservableList<Node> canopyNodes;
	ObservableList<Node> underGrowthNodes;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//Read Files into Classes
		FileReader fr = new FileReader("C:\\Users\\jordan\\IdeaProjects\\EcoVis\\data");
		species = fr.getSpecies();
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = fr.getUndergrowthPlants();


		System.out.println("Making PlantModel");
		noSpc = species.length;
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants, species);
		p1.getTransforms().add(s);
		setSpcColour();
		initPlantVis();
	}

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
	}

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
		}
		s.setPivotX((s.getPivotX()+event.getX())/2);
		s.setPivotY((s.getPivotY()+event.getY())/2);
		s.setX(myScale.getValue());
		s.setY(myScale.getValue());

	}
	public void panOnDrag(MouseEvent event)
	{
			s.setPivotX(event.getX());
			s.setPivotY(event.getY());
	}

	public void visStuff(MouseEvent event)
	{
		if(event.isControlDown())
		{
			gUnderGrowth.setVisible(!gUnderGrowth.isVisible());
		}
		else if (event.isAltDown())
		{
			gCanopy.setVisible(!gCanopy.isVisible());
		}
	}




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

	public void filter(ActionEvent event)
	{
		filterSpc(spcFil);
	}

	public void remFilter(ActionEvent event)
	{
		remFilterSpc(spcFil);
	}

	public void filterSpc(int id)
	{
		if(id>species.length-1||id<0){System.out.println("ID out of Bounds");}
		int startC = species[id].getCanopyPos();
		int endC = startC+species[id].getNumCanopyPlants();
		for (int i=startC; i<endC; i++)
		{
			canopyPlants.get(i).setVisible(false);
		}
		int startU = species[id].getUnderPos();
		int endU = startU+species[id].getNumUnderGrowthPlants();
		for(int i=startU; i<endU; i++)
		{
			underGrowthNodes.get(i).setVisible(false);
		}
	}

	public void remFilterSpc(int id)
	{
		if(id>species.length||id<0){System.out.println("ID out of Bounds");}
		int startC = species[id].getCanopyPos();
		int endC = startC+species[id].getNumCanopyPlants();
		for (int i=startC; i<endC; i++)
		{
			canopyPlants.get(i).setVisible(true);
		}
		int startU = species[id].getUnderPos();
		int endU = startU+species[id].getNumUnderGrowthPlants();
		for(int i=startU; i<endU; i++)
		{
			undergrowthPlants.get(i).setVisible(true);
		}
	}


}