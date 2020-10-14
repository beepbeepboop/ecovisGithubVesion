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

	@FXML
	Group gCanopy;
	@FXML
	Group gUnderGrowth;
	@FXML
	Pane p1;
	@FXML
	Label l1;
	@FXML
	Slider sRadLower;
	@FXML
	Slider sRadHigher;
	@FXML
	ImageView ivBackground;

	Scale s = new Scale();
	final DoubleProperty myScale = new SimpleDoubleProperty(1.0);
	private Filter filter;

	private ElevationGrid elevationGrid; //Will basically just need this data to produce a background image, then it can be yeeted
	private Species[] species;

	private Plant[] undergrowthPlants;
	private Plant[] canopyPlants;
	PlantModel plantModel;
	int noSpc;

	ObservableList<Node> canopyNodes;
	ObservableList<Node> underGrowthNodes;

	FireModel fireModel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		//Read Files into Classes
		FileReader fr = new FileReader("C:\\Users\\jordan\\IdeaProjects\\EcoVis\\data");
		species = fr.getSpecies();
		elevationGrid = fr.getElevation();
		canopyPlants = fr.getCanopyPlants();
		undergrowthPlants = fr.getUndergrowthPlants();

		p1.setPrefSize(elevationGrid.getDimX()*elevationGrid.getGridSpacing(),elevationGrid.getDimY()*elevationGrid.getGridSpacing());
		//System.out.println("Making PlantModel");
		noSpc = species.length;
		plantModel = new PlantModel(elevationGrid, undergrowthPlants, canopyPlants, species);
		ivBackground.setImage(elevationGrid.getBackground());
		ivBackground.fitHeightProperty().bind(p1.heightProperty());
		ivBackground.fitWidthProperty().bind(p1.widthProperty());

		filter = new Filter(canopyPlants, undergrowthPlants, species);
		p1.getTransforms().add(s);
		setSpcColour(noSpc);
		initPlantVis();
		/*
		System.out.println("Canopy start and amounts:\n");
		for(int i=0;i<species.length;i++)
		{
			System.out.println(i+": "+species[i].getCanopyPos()+" "+species[i].getNumCanopyPlants());
		}
		System.out.println("UnderGrowth start and amounts:\n");
		for(int i=0;i<species.length;i++)
		{
			System.out.println(i+": "+species[i].getUnderPos()+" "+species[i].getNumUnderGrowthPlants());
		}
		//put this into UI calls
		*/
		fireModel = new FireModel(plantModel);
		LinkedList<Coordinate> fireStart = new LinkedList<Coordinate>();
		fireStart.add(new Coordinate(20,10));
		System.out.println("Computing Firemodel");
		fireModel.computeSpread(10, fireStart, 3);
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

	public void updateRadFilter(MouseEvent event)
	{
		filter.filterByRadius((float)sRadLower.getValue(), (float)sRadHigher.getValue());
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

	public void filter(ActionEvent event){filter.filterSpc(spcFil);}
	public void remFilter(ActionEvent event){filter.remFilterSpc(spcFil);}
	public void filterRad(float min, float max){filter.filterByRadius(min,max);}

	boolean[] layerFilter = {false, false, false};
	public void filterBackGround(ActionEvent event){ivBackground.setVisible(layerFilter[0]);layerFilter[0]=!layerFilter[0];}
	public void filterUnderGrowth(ActionEvent event){gUnderGrowth.setVisible(layerFilter[1]);layerFilter[1]=!layerFilter[1];}
	public void filterCanopy(ActionEvent event){gCanopy.setVisible(layerFilter[2]);layerFilter[2]=!layerFilter[2];}

	//Yeet these methods
	boolean proxFilterBool = true;
	public void filterProx(ActionEvent event){if(proxFilterBool){filter.filterByProxy((float)75, (float)75, (float)25);proxFilterBool=false;}else{filter.remFilterByProxy((float)75, (float)75, (float)25);proxFilterBool=true;}}


	public void f0(ActionEvent event){fireColour(1);}
	public void f2(ActionEvent event){fireColour(2);}
	public void f3(ActionEvent event){fireColour(3);}
	public void fireColour(int snapNUM)
	{
		FireSnapshot fs = fireModel.getFireSnapShot(snapNUM);
		LinkedList<Plant> workingList;
		for(Coordinate c: fs.getBurned())
		{
			for(Plant plant : (LinkedList<Plant>)plantModel.getGrid()[c.getX()][c.getY()])
			{
				plant.setColour("#00000032");
			}
		}

		for(Coordinate c: fs.getBurning())
		{
			for(Plant plant : (LinkedList<Plant>)plantModel.getGrid()[c.getX()][c.getY()])
			{
				plant.setColour("#ff6c0332");
			}
		}
	}
}