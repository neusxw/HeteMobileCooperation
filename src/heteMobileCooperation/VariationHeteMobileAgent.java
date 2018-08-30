package heteMobileCooperation;

import java.util.Random;

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
	@ScheduledMethod(start = 1, interval = 1, priority = -2)
	public void offspring(){
		if (ID == 500) {
			System.out.println("offspring**********");
		}
		super.offspring();
		if (ID == 500) {
			System.out.println("offspring--son");
		}
		Random random =new Random();
		if (random.nextFloat() < variationRate) {
			if (strategy == C) {
				strategy = D;
			}else {
				strategy = C;
			}
		}
		if (random.nextFloat() < variationRate) {
			moveProbability += random.nextInt(2)*0.1 - 0.05;
			if (moveProbability>1) {
				moveProbability = 1;
			}
			
			if (moveProbability<0) {
				moveProbability=0;
			}
		}
		//System.out.println("offspring-------------");
	}
	
}