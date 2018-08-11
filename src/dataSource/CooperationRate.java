/**
 * 
 */
package dataSource;

import java.util.Iterator;

import heteMobileCooperatiom.MobileAgent;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

/**
 * @author nick
 *
 */
public class CooperationRate implements AggregateDataSource {
	
	@Override
	public String getId() { 
		return "Cooperation Rate";
	}

	@Override
	public Class<?> getDataType() {
		return double.class;
	}

	@Override
	public Class<?> getSourceType() {
		return Context.class;
	}

	@Override
	public Object get(Iterable<?> objs, int size) {
		
		//Context<Object> context = MobileAgentBuilder.context;

		
		Context<Object> context = (Context<Object>) objs.iterator().next();
		Grid grid = (Grid)context.getProjection("Grid");
		
		context.size();

		int cooAgent = 0;
		int allAgent = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			allAgent++;
			if (agent.getStrategy() == 'C')
				cooAgent++;
		}

		return ((double)cooAgent / allAgent) * 100;
	}

	@Override
	public void reset() {

	}

}
