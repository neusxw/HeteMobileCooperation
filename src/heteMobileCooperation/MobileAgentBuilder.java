package heteMobileCooperation;

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
 * 考察移动性对合作行为的影响
 * 参考文献：Mendeli H Vainstein，Ana TC Silva，Jeferson J Arenzon. 
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
		
		createHeteMobileAgent(numAgents, context);
		addObeserver(context);
		//RunEnvironment.getInstance().pauseAt(100);
		
		return context;
	}
	
	private void createMobileAgent(int m, Context<Object> context) {
		for (int i=0; i < m; i++){
			MobileAgent agent = new MobileAgent(i+1);
			if (i < Math.round(m / 2)) {
				agent.setStrategy('C');
			}else {
				agent.setStrategy('D');
			}
			agent.setOldStrategy(agent.getStrategy());
			context.add(agent);
		}
	}
	
	private void createHeteMobileAgent(int m, Context<Object> context) {
		for (int i=0; i < m; i++){
			HeteMobileAgent agent = new HeteMobileAgent(i+1);
			if (i < Math.round(m / 2)) {
				agent.setStrategy('C');
			}else {
				agent.setStrategy('D');
			}
			agent.setOldStrategy(agent.getStrategy());
			context.add(agent);
		}
	}
	
	private void createVariationHeteMobileAgent(int m, Context<Object> context) {
		for (int i=0; i < m; i++){
			VariationHeteMobileAgent agent = new VariationHeteMobileAgent(i+1);
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
		Observer ob = new Observer();
		context.add(ob);
		grid.setAdder(new RandomGridAdder());
	}
}
