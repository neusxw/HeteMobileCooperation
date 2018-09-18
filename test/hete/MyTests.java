package hete;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import heteMobileCooperation.MobileAgent;
import heteMobileCooperation.MobileAgentBuilder;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.parameter.Parameters;
import repast.simphony.parameter.ParametersParser;
import repast.simphony.scenario.ScenarioUtils;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.collections.IndexedIterable;

public class MyTests {

	public Context context;
	public Schedule schedule;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		/**
		 * 1.建立RunEnvironment运行环境；
		 * 2.建立context。
		 */
		//1.1 获取parameters.xml文件中的信息
		String scenarioDirString = "HeteMobileCooperation.rs";
		ScenarioUtils.setScenarioDir(new File(scenarioDirString));
		File paramsFile = new File(ScenarioUtils.getScenarioDir(),"parameters.xml");
		ParametersParser pp = new ParametersParser(paramsFile );
		Parameters params = pp.getParameters();
		//1.2 建立Schedule
		schedule = new Schedule ();
		//1.3 建立运行环境
		RunEnvironment.init(schedule,null,params,true);
		
		//2  建立context
		context = new DefaultContext();
		RunState.init().setMasterContext(context);
		MobileAgentBuilder builder = new MobileAgentBuilder();
		context = builder.build(context);

	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testAddingPersonToContext () {
		//System.out.println("OK!");
		IndexedIterable<MobileAgent> it;
		it = context.getObjects(MobileAgent.class);
		assertTrue(it.size()>0);
		schedule.execute();
		
		//System.out.println(it.size());
		MobileAgent agent = it.iterator().next();
		Grid<Object> grid = (Grid<Object>)context.getProjection("Grid");
		assertTrue(grid.getLocation(agent)!=null);
		agent.combat();
		agent.findEmptySites().size();
		//System.out.println(agent.findNeighbors().size());
		
		it = context.getObjects(MobileAgent.class);
		while(it.iterator().hasNext()) {
			MobileAgent m = it.iterator().next();
			assertTrue(m.findEmptySites().size()+m.findNeighbors().size()==4);
		}
		
	}

}
