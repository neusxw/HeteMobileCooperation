package heteMobileCooperation;

import dataSource.Observer;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.util.ContextUtils;

/**
 * �����ƶ��ԶԺ�����Ϊ��Ӱ��
 * �ο����ף�Mendeli H Vainstein��Ana TC Silva��Jeferson J Arenzon. 
 * Does mobility decrease cooperation?[J]. 
 * Journal of Theoretical Biology, 2007, 244(4): 722-728.
 * 
 * @author Shen Xiaowei
 *
 */
public class MobileAgentBuilder implements ContextBuilder<Object> {

	@Override
	public Context<Object> build(Context<Object> context) {

		Parameters p = RunEnvironment.getInstance().getParameters();

		int gridWidth = (Integer)p.getValue("gridWidth");
		int gridHeight = (Integer)p.getValue("gridHeight");
		double density = (double)p.getValue("agentDensity");
		int numAgents = (int) Math.round(density*gridWidth*gridHeight);

		GridFactoryFinder.createGridFactory(null).createGrid("Grid",
				context, GridBuilderParameters.singleOccupancy2D(new RandomGridAdder(),
						new WrapAroundBorders(), gridWidth, gridHeight));

		createAgent("HeteMobileAgent",numAgents, context);
		addObeserver(context);
		//RunEnvironment.getInstance().pauseAt(100);

		return context;
	}

	private void createAgent(String type, int m, Context<Object> context) {
		for (int i=0; i < m; i++){
			MobileAgent agent = null;
			switch (type)
			{
			case "MobileAgent":
				agent = new MobileAgent(i+1);
				break;
			case "HeteMobileAgent":
				agent = new HeteMobileAgent(i+1);
				break;
			case "VariationHeteMobileAgent":
				agent = new VariationHeteMobileAgent(i+1);
				break;	
			default:
				agent = new MobileAgent(i+1);
				break;

			}

			if (i < Math.round(m / 2)) {
				agent.setStrategy('C');
			}else {
				agent.setStrategy('D');
			}
			agent.setOldStrategy(agent.getStrategy());
			context.add(agent);
		}
	}


	public void addObeserver(Context<Object> context) {
		Grid grid = (Grid)context.getProjection("Grid");
		grid.setAdder(new SimpleGridAdder());
		Observer ob = new Observer(true);
		context.add(ob);
		grid.setAdder(new RandomGridAdder());
	}
}