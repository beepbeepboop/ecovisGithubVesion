//package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

	//@FXML
	//ScrollPane sp;
	@FXML
	Pane p1;

	Scale s = new Scale();

	final DoubleProperty myScale = new SimpleDoubleProperty(1.0);
	ImageView img = new ImageView();
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
		HashMap<Integer, Species> canopySpecies;
		HashMap<Integer, Species> undergrowthSpecies;
		LinkedList<Plant> undergrowthPlants;
		LinkedList<Plant> canopyPlants;
		//private static FireModel fireModel;
		PlantModel plantModel;
		FileReader fr = new FileReader("C:\\Users\\jordan\\IdeaProjects\\EcoVis\\data");
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = null;//fr.getUndergrowthPlants();
		fr.readSpc();	//Populates species field
		canopySpecies = fr.getCanopySpecies();
		undergrowthSpecies = fr.getUndergrowthSpecies();

		//System.out.println(undergrowthSpecies);
		System.out.println("Making PlantModel");
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants);
		p1.getTransforms().add(s);
		int idForColour = 0;
		String colourForCirc = "";
		for(int i=0; i<plantModel.getDimX(); i++)
		{
			for(int j=0; j<plantModel.getDimY();j++)
			{
				if(plantModel.getPlants(i,j).size()>0)
				{
					Circle circle = new Circle();
					circle.setCenterX(i);
					circle.setCenterY(j);

					idForColour = plantModel.getPlants(i,j).get(0).getID();
					switch(idForColour)
					{
						case 0: colourForCirc = "#14467532";
							break;
						case 1: colourForCirc = "#c2828232";
							break;
						case 2: colourForCirc = "#d0000032";
							break;
						case 3:	colourForCirc = "#6ae07732";
							break;
						case 4:	colourForCirc = "#006b0a32";
							break;
						case 5: colourForCirc = "#ccff0032";
							break;
						case 10: colourForCirc = "#20405532";
							break;
						case 11: colourForCirc = "#a282a232";
							break;
						case 12: colourForCirc = "#d0700b32";
							break;
						case 13: colourForCirc = "#2ae03732";
							break;
						case 14:	colourForCirc = "#a06b0c32";
							break;
						case 15: colourForCirc = "#ace00062";
							break;
						default: colourForCirc = "#ff008f32";

					}
					circle.setFill(Paint.valueOf(colourForCirc));
					circle.setRadius(plantModel.getPlants(i,j).get(0).getCanopyRadius());
					p1.getChildren().add(circle);
				}
			}
		}
		/*
		for(int i=0; i<canopyPlants.size(); i++)
		{
			Circle circle = new Circle();
			circle.setCenterX(canopyPlants.get(i).getX());
			circle.setCenterY(canopyPlants.get(i).getY());
			circle.setFill(Paint.valueOf("#1f93ff64"));
			circle.setRadius(canopyPlants.get(i).getCanopyRadius());

			p1.getChildren().add(circle);
		}



		/*Vector<Plant> underGrowth = fr.getUnderGrowth();
		for(int i=0; i<underGrowth.size(); i++)
		{
			Circle circle = new Circle();
			circle.setCenterX(underGrowth.elementAt(i).getX());
			circle.setCenterY(underGrowth.elementAt(i).getY());
			circle.setFill(Paint.valueOf("#bfd60c34"));
			circle.setRadius(underGrowth.elementAt(i).getCanopyRadius());

			p1.getChildren().add(circle);
		}*/
		System.out.println(fr.toString());


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
		s.setPivotX(event.getX());
		s.setPivotY(event.getY());
		s.setX(myScale.getValue());
		s.setY(myScale.getValue());

	}
	public void panOnDrag(MouseEvent event)
	{
			s.setPivotX(event.getX());
			s.setPivotY(event.getY());

	}

}