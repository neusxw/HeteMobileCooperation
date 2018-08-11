/**
 * 
 */
package dataSource;

import java.util.Iterator;

import heteMobileCooperation.MobileAgent;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

/**
 * @author Shen Xiaowei
 * 
 * 计算所有合作者的平均移动速度。
 *
 */
public class AMPofCooperator implements AggregateDataSource {
	
	@Override
	public String getId() { 
		return "Average Move Probability of Cooperator";
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

		Context<Object> context = (Context<Object>) objs.iterator().next();
		Grid grid = (Grid)context.getProjection("Grid");
		
		double sum = 0;
		int count = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent) it.next();
			if(agent.getStrategy() == 'C') {
				count++;
				sum += agent.getMoveProbability();
			}
		}
		return (double)sum/count;
	}

	@Override
	public void reset() {

	}

}

