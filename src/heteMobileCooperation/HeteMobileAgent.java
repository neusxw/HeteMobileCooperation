package heteMobileCooperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.util.ContextUtils;

/** 
 * @author Shen Xiaowei
 * 异质性移动Agent。
 * 此处的异质性主要表现为个体的初始一定概率不同。
 */

public class  HeteMobileAgent extends MobileAgent{


	public HeteMobileAgent(int ID){
		super(ID);
		moveProbability = Math.random();
	}	 

	
	/**
	 * @Overriding
	 * reproduce.
	 * 更新策略
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void offspring(){
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> net = (Network<Object>)context.getProjection("study network");

		List<MobileAgent> neighbors = findNeighbors();
		neighbors.add(this);
		List<MobileAgent> electors = new ArrayList<MobileAgent>();
		double maxPayoff = 0;

		for (MobileAgent another : neighbors) {
			if(another.getPayoff() > maxPayoff) {
				maxPayoff = another.getPayoff();
			}
		}

		for (MobileAgent another : neighbors) {
			if(Math.abs(another.getPayoff() - maxPayoff) < Math.pow(10, -6)) {
				electors.add(another);
			}
		}

		Collections.shuffle(electors);
		if (electors.size() > 0) {		
			//SimUtilities.shuffle(electors, RandomHelper.getUniform());
			MobileAgent elector = electors.get(0);
			strategy = elector.strategy;
			moveProbability = elector.moveProbability;
		}
	}
}