public class Filter
{
	private float maxHeight, minHeight;
	private float maxRadius, minRadius;
	private Plant[] canopy;
	private Plant[] underGrowth;
	private Species[] species;
		//TODO set max and min
		//TODO add filter by spc
		//TODO add filter by proxy
	public Filter(Plant[] c, Plant[] u, Species[] s){canopy = c; underGrowth = u;species = s;minRadius = (float)0.0; maxRadius = (float)16.0;}

	//Filter functions designed to keep Plant Visibility count accurate
	public void filterByRadius(float minR, float maxR)
	{
		if(minR<minRadius){unfilterRLower(minR,minRadius);}
		else if(minR>minRadius){filterRLower(minRadius, minR);}
		minRadius=minR;
		if(maxR>maxRadius){unfilterRHigher(maxRadius,maxR);}
		else if(maxR<maxRadius){filterRHigher(maxR, maxRadius);}
		maxRadius=maxR;
	}
	public void filterByHeight(float minH, float maxH)
	{
		if(minH<minHeight){unfilterHLower(minH,minHeight);}
		else if(minH>minHeight){filterHLower(minHeight, minH);}
		minHeight=minH;
		if(maxH>maxHeight){unfilterHHigher(maxHeight,maxH);}
		else if(maxH<maxHeight){filterHHigher(maxH, maxHeight);}
		maxHeight=maxH;
	}

	public void filterByProxy(float x, float y, float prox)
	{
		float distance;
		for(Plant plant: canopy)
		{
			distance = (float) Math.sqrt(Math.pow(plant.getX()-x,2)+Math.pow(plant.getY()-y,2));
			if(distance>prox){plant.incVis();}
		}
		for(Plant plant: underGrowth)
		{
			distance = (float) Math.sqrt(Math.pow(plant.getX()-x,2)+Math.pow(plant.getY()-y,2));
			if(distance>prox){plant.incVis();}
		}
	}
	public void remFilterByProxy(float x, float y, float prox)
	{
		float distance;
		for(Plant plant: canopy)
		{
			distance = (float) Math.sqrt(Math.pow(plant.getX()-x,2)+Math.pow(plant.getY()-y,2));
			if(distance>prox){plant.decVis();}
		}
		for(Plant plant: underGrowth)
		{
			distance = (float) Math.sqrt(Math.pow(plant.getX()-x,2)+Math.pow(plant.getY()-y,2));
			if(distance>prox){plant.decVis();}
		}
	}

	public void filterSpc(int id)
	{
		if(id>species.length-1||id<0){System.out.println("ID out of Bounds");}
		int startC = species[id].getCanopyPos();
		int endC = startC+species[id].getNumCanopyPlants();
		for (int i=startC; i<endC; i++){canopy[i].incVis();}
		int startU = species[id].getUnderPos();
		int endU = startU+species[id].getNumUnderGrowthPlants();
		for(int i=startU; i<endU; i++){underGrowth[i].incVis();}
	}
	public void remFilterSpc(int id)
	{
		if(id>species.length||id<0){System.out.println("ID out of Bounds");}
		int startC = species[id].getCanopyPos();
		int endC = startC+species[id].getNumCanopyPlants();
		for (int i=startC; i<endC; i++){canopy[i].decVis();}
		int startU = species[id].getUnderPos();
		int endU = startU+species[id].getNumUnderGrowthPlants();
		for(int i=startU; i<endU; i++){underGrowth[i].decVis();}
	}

	private void unfilterRLower(float min,float max)
	{
		for(Plant plant: canopy){if(plant.getCanopyRadius()>=min&&plant.getCanopyRadius()<max){plant.decVis();}}
		for(Plant plant: underGrowth){if(plant.getCanopyRadius()>=min&&plant.getCanopyRadius()<max){plant.decVis();}}
	}
	private void filterRLower(float min, float max)
	{
		for(Plant plant: canopy){if(plant.getCanopyRadius()>=min&&plant.getCanopyRadius()<max){plant.incVis();}}
		for(Plant plant: underGrowth){if(plant.getCanopyRadius()>=min&&plant.getCanopyRadius()<max){plant.incVis();}}
	}
	private void unfilterRHigher(float min,float max)
	{
		for(Plant plant: canopy){if(plant.getCanopyRadius()>min&&plant.getCanopyRadius()<=max){plant.decVis();}}
		for(Plant plant: underGrowth){if(plant.getCanopyRadius()>min&&plant.getCanopyRadius()<=max){plant.decVis();}}
	}
	private void filterRHigher(float min, float max)
	{
		for(Plant plant: canopy){if(plant.getCanopyRadius()>min&&plant.getCanopyRadius()<=max){plant.incVis();}}
		for(Plant plant: underGrowth){if(plant.getCanopyRadius()>min&&plant.getCanopyRadius()<=max){plant.incVis();}}
	}

	private void unfilterHLower(float min,float max)
	{
		for(Plant plant: canopy){if(plant.getHeight()>=min&&plant.getHeight()<max){plant.decVis();}}
		for(Plant plant: underGrowth){if(plant.getHeight()>=min&&plant.getHeight()<max){plant.decVis();}}
	}
	private void filterHLower(float min, float max)
	{
		for(Plant plant: canopy){if(plant.getHeight()>=min&&plant.getHeight()<max){plant.incVis();}}
		for(Plant plant: underGrowth){if(plant.getHeight()>=min&&plant.getHeight()<max){plant.incVis();}}
	}
	private void unfilterHHigher(float min,float max)
	{
		for(Plant plant: canopy){if(plant.getHeight()>min&&plant.getHeight()<=max){plant.decVis();}}
		for(Plant plant: underGrowth){if(plant.getHeight()>min&&plant.getHeight()<=max){plant.decVis();}}
	}
	private void filterHHigher(float min, float max)
	{
		for(Plant plant: canopy){if(plant.getHeight()>min&&plant.getHeight()<=max){plant.incVis();}}
		for(Plant plant: underGrowth){if(plant.getHeight()>min&&plant.getHeight()<=max){plant.incVis();}}
	}
}