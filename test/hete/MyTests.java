package hete;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
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

	//所有测试开始之前运行。
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeClass");
	}

	//所有测试结果之后运行。
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AfterClass");
	}

	//每一个测试方法之前云行。
	@Before
	public void setUp() throws Exception {
		System.out.println("Before");
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
		//1.2 建立Schedule进度表
		schedule = new Schedule ();
		//1.3 建立运行环境
		RunEnvironment.init(schedule,null,params,true);

		//2  建立context
		context = new DefaultContext();
		RunState.init().setMasterContext(context);
		MobileAgentBuilder builder = new MobileAgentBuilder();
		context = builder.build(context);

	}

	//每一个测试方法之后运行。
	@After
	public void tearDown() throws Exception {
		System.out.println("After");
	}

	@Test
	public void testAddingPersonToContext () {
		System.out.println("testAddingPersonToContext ()");
		IndexedIterable<MobileAgent> it;
		it = context.getObjects(MobileAgent.class);
		assertTrue(it.size()>0);
		schedule.execute();
		System.out.println(it.size());
		MobileAgent agent = it.iterator().next();
		Grid<Object> grid = (Grid<Object>)context.getProjection("Grid");
		assertTrue(grid.getLocation(agent)!=null);
		agent.combat();
		agent.findEmptySites().size();
		it = context.getObjects(MobileAgent.class);
		for (MobileAgent m:it) {
			if(m.getID() % 1000 == 0) {
				System.out.println(m.getID() + ":" + m.findNeighbors().size());
				assertTrue(m.findEmptySites().size()+m.findNeighbors().size()==4);
			}
		}
	}

	@Test
	public void testTest () {
		System.out.println("test!");
	}

}
