package heteMobileCooperation;

import java.util.Random;

import repast.simphony.engine.schedule.ScheduledMethod;

/*
 * 1.���Ժ��ƶ��Կɱ���
 * 2.������
 * 3���ƶ���
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
	 * ���²���
	 * �����������˸���ķ�������@ScheduledMethod������Ϊ׼
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