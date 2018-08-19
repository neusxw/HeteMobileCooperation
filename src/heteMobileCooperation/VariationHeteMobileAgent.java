package heteMobileCooperation;

import repast.simphony.engine.schedule.ScheduledMethod;

/*
 * �ƶ�Agent
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
	 * ���²���
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