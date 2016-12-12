package my;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

public class StubTest {

	/*
	 * Declare harnesses that let you test a mapper, a reducer, and a mapper and
	 * a reducer working together.
	 */
	MapDriver<LongWritable, Text, Text, TextArrayWritable> mapDriver;
	ReduceDriver<Text, TextArrayWritable, Text, TextArrayWritable> reduceDriver;
	MapReduceDriver<Object, Text, Text, LongWritable, Text, LongWritable> mapReduceDriver;

	/*
	 * Set up the test. This method will be called before every test.
	 */
	@Before
	public void setUp() {

		/*
		 * Set up the mapper test harness.
		 */
		Mapper<LongWritable, Text, Text, TextArrayWritable> mapper = new StubMapper();
//		mapDriver = new MapDriver<Object, Text, Text, LongWritable>();
		mapDriver = new MapDriver<LongWritable, Text, Text, TextArrayWritable>();
		mapDriver.setMapper(mapper);
		/*
		 * Set up the reducer test harness.
		 */
		Reducer<Text, TextArrayWritable, Text, TextArrayWritable> reducer = new StubReducer();
		reduceDriver = new ReduceDriver<Text, TextArrayWritable, Text, TextArrayWritable>();
		reduceDriver.setReducer(reducer);

		/*
		 * Set up the mapper/reducer test harness.
		 */
		mapReduceDriver = new MapReduceDriver<Object, Text, Text, LongWritable, Text, LongWritable>();
		mapReduceDriver.withMapper(mapper);
		mapReduceDriver.withReducer(reducer);
	}

	/*
	 * Test the mapper.
	 */
	@Test
	public void testMapper() {

		/*
		 * For this test, the mapper's input will be "1 cat cat dog" TODO:
		 * implement
		 */
		mapDriver.withInput("1");
		mapDriver.setInputValue(new Text("this        is test"));
		List<Pair<Text, TextArrayWritable>> x;
		try {
			x = mapDriver.run();
			assertEquals(x.size(), 3);
			assertEquals(x.get(0).getFirst().toString(), "this");
			assertEquals(x.get(1).getFirst().toString(), "is");
			assertEquals(x.get(2).getFirst().toString(), "test");
			
			for(Pair<Text, TextArrayWritable> p:x)
			{
				System.out.println("MAPPER: " + p.getFirst() + ": " + p.getSecond());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * Test the reducer.
	 */
	@Test
	public void testReducer() {

		/*
		 * For this test, the reducer's input will be "cat 1 1". The expected
		 * output is "cat 2". TODO: implement
		 */
		fail("Please implement test.");

	}

	/*
	 * Test the mapper and reducer working together.
	 */
	@Test
	public void testMapReduce() throws IOException {

		mapReduceDriver.addInput(new Pair<Object, Text>("1", new Text(
				"sandeep giri is here")));
		mapReduceDriver.addInput(new Pair<Object, Text>("2", new Text(
				"teach the map and reduce class is fun.")));
		List<Pair<Text, LongWritable>> output = mapReduceDriver.run();
		assertEquals(11, output.size());
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + " - " + p.getSecond());
		}
	}
}
