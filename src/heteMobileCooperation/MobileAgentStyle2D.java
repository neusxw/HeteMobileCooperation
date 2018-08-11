package heteMobileCooperation;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;

public class MobileAgentStyle2D extends DefaultStyleOGL2D{

	private ShapeFactory2D shapeFactory;
	public final static Color CC = new Color(255,0,0);
	public final static Color DC = new Color(255,255,0);
	public final static Color CD = new Color(0,255,0);
	public final static Color DD = new Color(0,0,255);
	@Override
	public void init(ShapeFactory2D factory) {
		this.shapeFactory = factory;
	}

	@Override
	public Color getColor(Object o) {
		MobileAgent agent = (MobileAgent)o;
		
		if (agent.getStrategy() == 'C' && agent.getOldStrategy() == 'C')
			return CC; 
		if (agent.getStrategy() == 'C' && agent.getOldStrategy() == 'D')
			return DC; 
		if (agent.getStrategy() == 'D' && agent.getOldStrategy() == 'C')
			return CD; 
		if (agent.getStrategy() == 'D' && agent.getOldStrategy() == 'D')
			return DD; 
		
		return null;
	}

	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		if (spatial == null) {			
		}
		
		if (((MobileAgent)agent).getStrategy() == 'C') {
			spatial = shapeFactory.createRectangle(10, 10);
		}else {
			//spatial = shapeFactory.createRectangle(14, 14);
			spatial = shapeFactory.createCircle(5, 20);
		}
		
		return spatial;
	}
	
	@Override
	  public String getLabel(Object object) {
		
		MobileAgent agent = (MobileAgent)object;
		//int neighborsNum = agent.findNeighbors().size();
		//int ID = agent.ID;
		double payoff = agent.getPayoff();
		DecimalFormat df = new DecimalFormat("##.0");
		//return df.format(payoff);
		return("");

	  }

	@Override
	  public Font getLabelFont(Object object) {
	    return new Font("ËÎÌו",Font.BOLD,20);
	  }

	@Override
	  public Position getLabelPosition(Object object) {
	    return Position.CENTER;
	  }

	@Override
	  public float getLabelXOffset(Object object) {
	    return 0;
	  }

	@Override
	  public float getLabelYOffset(Object object) {
	    return 0;
	  }

	@Override
	  public Color getLabelColor(Object object) {
	    return Color.RED;
	  }
}
