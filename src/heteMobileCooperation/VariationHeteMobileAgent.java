package heteMobileCooperation;

import java.util.Random;

import repast.simphony.engine.schedule.ScheduledMethod;

/*
 * 1.策略和移动性可变异
 * 2.异质性
 * 3。移动性
 * @author Shen Xiaowei
 */

public class  VariationHeteMobileAgent extends HeteMobileAgent{

	double variationRate = 0.001;

	public VariationHeteMobileAgent(int ID){
		super(ID);
	}	 


	/**
	 * @Overriding
	 * reproduce.
	 * 更新策略
	 * 子类若覆盖了父类的方法，则@ScheduledMethod以子类为准
	 * 
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void offspring(){
		super.offspring();
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
	}
}