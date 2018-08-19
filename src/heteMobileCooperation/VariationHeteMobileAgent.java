package heteMobileCooperation;

import repast.simphony.engine.schedule.ScheduledMethod;

/*
 * 移动Agent
 * @author Shen Xiaowei
 */

public class  VariationHeteMobileAgent extends HeteMobileAgent{

	double variationRate = 0.01;
	
	public VariationHeteMobileAgent(int ID){
		super(ID);
	}	 

	
	/**
	 * @Overriding
	 * reproduce.
	 * 更新策略
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void offspring(){
		super.offspring();
		if (Math.random() < 0.01) {
			if (strategy == C) {
				strategy = D;
			}else {
				strategy = C;
			}
		}
	}
}