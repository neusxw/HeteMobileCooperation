package heteMobileCooperation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javafx.scene.layout.Priority;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;


public class Observer {

	boolean dataOutputState = true;
	public File file = new File("C://repastOutput.txt");
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
	
	public double getAMPofCooperator() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid grid = (Grid)context.getProjection("Grid");

		double count = 0;
		double sumAMPofDefector = 0;
		Iterator it = context.getObjects(MobileAgent.class).iterator();
		while(it.hasNext()) {
			MobileAgent agent = (MobileAgent)it.next();
			if (agent.getStrategy() == 'C') {
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

	
	//MATLAB £ºplot(a(:,1),a(:,2)/100,'-b',a(:,1),a(:,3),'-g',a(:,1),a(:,4),'-r',a(:,1),a(:,5),'-r')
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
			String str = tick + " " + getCooperationRate() + " " + getAverageMoveProbability() + " " + getAMPofDefector() + " " + getAMPofCooperator();
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
