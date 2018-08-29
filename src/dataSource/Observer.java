package dataSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import heteMobileCooperation.*;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;


public class Observer {
	boolean dataOutputState;
	public File file;
	
	public Observer(boolean dataOutputState) {
		this.dataOutputState = dataOutputState;
		this.file = new File("repastOutput.txt");;
	}

	//@ScheduledMethod(start = 1, interval = 1, priority = 10)
	public void BeforeEveryTick() {

		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> net = (Network<Object>)context.getProjection("study network");
		Grid<Object> grid = (Grid<Object>)context.getProjection("Grid");

		net.removeEdges();

		for(Object agent:grid.getObjects()) {
			if(agent instanceof MobileAgent)  {
				//System.out.print(((MobileAgent)agent).getStrategy());
			}
		}
		//System.out.println(context.size());
	}

	public double getCooperationRate() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		int cooAgent = 0;
		int allAgent = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			allAgent++;
			if (agent.getStrategy() == 'C') {
				cooAgent++;
			}
		}

		return ((double)cooAgent / allAgent) * 100;

	}
	
	public double getAverageMoveProbability() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double sumMoveProbability = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			count++;
			sumMoveProbability += agent.getMoveProbability();
		}

		return (double)sumMoveProbability / count;

	}
	
	public double getAMPofDefector() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double sumAMPofDefector = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			if (agent.getStrategy() == 'D') {
			count++;
			sumAMPofDefector += agent.getMoveProbability();
			}
		}
		
		if (count > 0) {
		return (double)sumAMPofDefector / count;
		}else {
			return 0;
		}

	}
	
	public double getPayoffOfDefector() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double sumPayoff = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			if (agent.getStrategy() == 'D') {
			count++;
			sumPayoff += agent.getPayoff();
			}
		}
		
		if (count > 0) {
		return (double)sumPayoff / count;
		}else {
			return 0;
		}

	}
	
	public double getAMPofCooperator() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double sumAMP = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			if (agent.getStrategy() == 'C') {
			count++;
			sumAMP += agent.getMoveProbability();
			}
		}
		
		if (count > 0) {
		return (double)sumAMP / count;
		}else {
			return 0;
		}

	}
	
	public double getPayoffOfCooperator() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double sumPayoff = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			if (agent.getStrategy() == 'C') {
			count++;
			sumPayoff += agent.getPayoff();
			}
		}
		
		if (count > 0) {
		return (double)sumPayoff / count;
		}else {
			return 0;
		}
	}
	
	public double getChangeC2D() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double countC2D = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			count++;
			if (agent.getOldStrategy() == 'C' && agent.getStrategy() == 'D') {
			countC2D++;
			}
		}
		
		if (count > 0) {
		return (double)countC2D / count;
		}else {
			return 0;
		}
	}
	
	public double getChangeD2C() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double countD2C = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			count++;
			if (agent.getOldStrategy() == 'D' && agent.getStrategy() == 'C') {
			countD2C++;
			}
		}
		
		if (count > 0) {
		return (double)countD2C / count;
		}else {
			return 0;
		}
	}

/*	MATLAB绘图：
 	clc;
	clear all;
	close all;
	a=load('D:\GitHub\Reast\HeteMobileCooperation\repastOutput.txt');
	figure;
	plot(a(:,1),a(:,2)/100,'-b');
	legend('合作率')
	figure;
	plot(a(:,1),a(:,3),'-g',a(:,1),a(:,4),'-r',a(:,1),a(:,5),'-y');
	legend('平均移动概率','背叛者平均移动概率','合作者平均移动概率')
	figure;
	plot(a(:,1),a(:,6),'-b',a(:,1),a(:,7),'-r');
	legend('背叛者平均支付','合作者平均支付')
	*/
	@ScheduledMethod(start = 0, interval = 1, priority = ScheduleParameters.LAST_PRIORITY)
	public void dataOutput() {
		double tick = RunEnvironment.getInstance().getCurrentSchedule().getTickCount();

		if (tick < Math.pow(10, -6)) {
			file.delete();
		}

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file,true); 
			String str = tick + " " + getCooperationRate() + " " + getAverageMoveProbability() + " " +
			getAMPofDefector() + " "  + getAMPofCooperator() + " " + getPayoffOfDefector() + " " + getPayoffOfCooperator()+
			 " " + getChangeD2C() + " " + getChangeC2D();
			writer.write(str + "\r\n");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/*	@ScheduledMethod(start = 1, interval = 1,priority = -1)
	public void renewGUI() {
		MyPanel.theTicks.setText(RunEnvironment.getInstance().getCurrentSchedule().getTickCount()+"");

		MyPanel.cooperationRate.setText(this.getCooperationRate()+"");
	}*/

}
