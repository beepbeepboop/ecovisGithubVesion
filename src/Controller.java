//package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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

	Scale s = new Scale();
	final DoubleProperty myScale = new SimpleDoubleProperty(1.0);

	private ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private Species[] species;
	private LinkedList<Plant> undergrowthPlants;
	private LinkedList<Plant> canopyPlants;
	PlantModel plantModel;
	int noSpc;

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
		/*
		for(int i=0; i<plantModel.getDimX(); i++)
		{
			for (int j = 0; j < plantModel.getDimY(); j++)
			{
				Circle circle = new Circle();
				circle.setCenterX(i);
				circle.setCenterY(j);
				plantModel.getNode(i,j).calcVis();
				circle.setFill(Paint.valueOf(plantModel.getNode(i, j).getColour()));
				circle.setRadius(plantModel.getNode(i, j).getRadius());
				gCanopy.getChildren().add(circle);
			}
		}

		/*
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants);
		p1.getTransforms().add(s);
		int idForColour = 0;
		String colourForCirc = "";
		int plantCounter;
		for(int i=0; i<plantModel.getDimX(); i++)
		{
			for(int j=0; j<plantModel.getDimY();j++)
			{
				plantCounter = 0;
				while (plantModel.getNode(i,j).getPlants().size()>plantCounter)
				{
					Circle circle = new Circle();
					circle.setCenterX(plantModel.getNode(i,j).getPlants().get(plantCounter).getX());
					circle.setCenterY(plantModel.getNode(i,j).getPlants().get(plantCounter).getY());

					idForColour = plantModel.getNode(i,j).getPlants().get(plantCounter).getID();
					switch (idForColour)
					{
						case 0:
							colourForCirc = "#14467532";
							break;
						case 1:
							colourForCirc = "#c2828232";
							break;
						case 2:
							colourForCirc = "#d0000032";
							break;
						case 3:
							colourForCirc = "#6ae07732";
							break;
						case 4:
							colourForCirc = "#006b0a32";
							break;
						case 5:
							colourForCirc = "#ccff0032";
							break;
						case 10:
							colourForCirc = "#20405532";
							break;
						case 11:
							colourForCirc = "#a282a232";
							break;
						case 12:
							colourForCirc = "#d0700b32";
							break;
						case 13:
							colourForCirc = "#2ae03732";
							break;
						case 14:
							colourForCirc = "#a06b0c32";
							break;
						case 15:
							colourForCirc = "#ace00062";
							break;
						default:
							colourForCirc = "#ff008f32";

					}
					circle.setFill(Paint.valueOf(colourForCirc));
					circle.setRadius(plantModel.getNode(i,j).getPlants().get(plantCounter).getCanopyRadius());
					gCanopy.getChildren().add(circle);
					plantCounter++;
				}
			}
		}
		/*
		LinkedList<Plant> underGrowth = fr.getUndergrowthPlants();
		for(int i=0; i<underGrowth.size(); i++)
		{
			Circle circle = new Circle();
			circle.setCenterX(underGrowth.get(i).getX());
			circle.setCenterY(underGrowth.get(i).getY());
			circle.setFill(Paint.valueOf("#bfd60c34"));
			circle.setRadius(underGrowth.get(i).getCanopyRadius());

			gUnderGrowth.getChildren().add(circle);
		}
		gUnderGrowth.setVisible(false);
		System.out.println(fr.toString());
		*/

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
		}
		for(Plant plant: canopyPlants)
		{
			Circle circle = new Circle();
			circle.setCenterX(plant.getX());
			circle.setCenterY(plant.getY());
			circle.setFill(Paint.valueOf(species[plant.getID()].getColour()));
			circle.setRadius(plant.getCanopyRadius());

			gCanopy.getChildren().add(circle);
		}
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
					co = "#ace00062";
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
			//myScale.set(myScale.getValue() * 1.2);
			myScale.set(myScale.getValue()+0.15);
		}
		else
		{
			if (myScale.getValue() > 1.0)
			{
				//myScale.set(myScale.getValue() / 1.2);
				myScale.set(myScale.getValue()-0.15);
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

}