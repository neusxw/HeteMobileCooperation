package heteMobileCooperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

/*
 * 移动Agent
 * @author Shen Xiaowei
 */

public class MobileAgent {

	protected final int ID;

	/**
	 * 囚徒博弈，博弈参数设定
	 **/
	protected final char C = 'C';
	protected final char D = 'D';

	protected final double R = 1.0;	//R
	protected final double S = 0.0;	//S
	protected final double T = 1.4;	//T
	protected final double P = 0.0;	//P

	/**
	 * Agent的参数
	 */
	protected char strategy;					//当前策略
	protected char oldStrategy;					//前一轮的策略
	protected double payoff;					//支付
	protected double moveProbability;			//移动概率

	public MobileAgent(int ID){
		this.ID = ID;
		Parameters p = RunEnvironment.getInstance().getParameters();
		moveProbability = (double)p.getValue("moveProbability");
	}	 

	/*
	 * 博弈
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 3)
	public void combat() {
		oldStrategy = strategy;
		List<MobileAgent> neighbors = findNeighbors(); 
		payoff = 0;

		for (MobileAgent another : neighbors) {
			if (strategy == C && another.strategy == C) {
				payoff += R;
			}else if(strategy == C && another.strategy == D) {
				payoff += S;
			}else if(strategy == D && another.strategy == C) {
				payoff += T;
			}else if(strategy == D && another.strategy == D) {
				payoff += P;
			}
		}
	}


	/**
	 * reproduce.
	 * 更新策略
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void offspring(){

		Context<Object> context = ContextUtils.getContext(this);
		List<MobileAgent> neighbors = findNeighbors();
		//neighbors.add(this);
		List<MobileAgent> electors = new ArrayList<MobileAgent>();
		double maxPayoff = 0;

		for (MobileAgent another : neighbors) {
			if(another.getPayoff() > maxPayoff) {
				maxPayoff = another.getPayoff();
			}
		}

		for (MobileAgent another : neighbors) {
			if(Math.abs(another.getPayoff() - maxPayoff) < Math.pow(10, -6) && another.getPayoff() > this.payoff) {
				electors.add(another);
			}
		}

		Collections.shuffle(electors);
		if (electors.size() > 0) {		
			//SimUtilities.shuffle(electors, RandomHelper.getUniform());
			MobileAgent elector = electors.get(0);
			copyProperty(elector);
		}
	}

	/**
	 * 随机移动
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void move(){

		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid<Object>)context.getProjection("Grid");

		List<GridPoint> emptySites = findEmptySites();

		if (emptySites.size() > 0 &&  moveProbability > RandomHelper.nextDouble()) 
			grid.moveTo(this, emptySites.get(0).getX(), emptySites.get(0).getY());
	}

	/**
	 * Provides a list of adjacent (unoccupied) sites in the cell's VN 
	 * neighborhood.  The list of sites is shuffled.
	 * @return the list of adjacent sites.
	 * 
	 */
	//TODO 已测试
	public List<GridPoint> findEmptySites(){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		Context context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");
		GridPoint pt = grid.getLocation(this);

		// Find Empty VN neighbors
		if (!grid.getObjectsAt(pt.getX()+1,pt.getY()).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()));
		if (!grid.getObjectsAt(pt.getX(),pt.getY()+1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX(),pt.getY()+1));
		if (!grid.getObjectsAt(pt.getX()-1,pt.getY()).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()));
		if (!grid.getObjectsAt(pt.getX(),pt.getY()-1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX(),pt.getY()-1));

		Collections.shuffle(emptySites);
		return emptySites;
	}

	//TODO 已测试
	public List<MobileAgent> findNeighbors(){
		Context context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");
		GridPoint pt = grid.getLocation(this);

		List<MobileAgent> neighbors = new ArrayList<MobileAgent>();
		Iterator<MobileAgent> it;
		it = grid.getObjectsAt(pt.getX()+1,pt.getY()).iterator();
		while (it.hasNext()) {
			neighbors.add(it.next());
		}

		it = grid.getObjectsAt(pt.getX(),pt.getY()+1).iterator();
		while (it.hasNext()) {
			neighbors.add(it.next());
		}
		it = grid.getObjectsAt(pt.getX()-1,pt.getY()).iterator();
		while (it.hasNext()) {
			neighbors.add(it.next());
		}
		it = grid.getObjectsAt(pt.getX(),pt.getY()-1).iterator();
		while (it.hasNext()) {
			neighbors.add(it.next());
		}

		Collections.shuffle(neighbors);
		return neighbors;
	}

	private void copyProperty(MobileAgent a) {
		this.strategy = a.strategy;
		this.moveProbability = a.moveProbability;
	}
	
	

	public char getStrategy() {
		return strategy;
	}
	public void setStrategy(char strategy) {
		this.strategy = strategy;
	}
	public char getOldStrategy() {
		return oldStrategy;
	}
	public void setOldStrategy(char strategy) {
		this.oldStrategy = strategy;

	}	
	public double getPayoff() {
		return payoff;
	}
	public int getID() {
		return ID;
	}
	public double getMoveProbability() {
		return moveProbability;
	}
	public String toString() {
		return "ID = " + Integer.toString(ID);
	}


}
