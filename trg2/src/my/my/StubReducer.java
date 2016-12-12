package my;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;


//public class StubReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
//
//  @Override
//  public void reduce(Text key, Iterable<LongWritable> values, Context context)
//      throws IOException, InterruptedException {
//	  
//	  long sum = 0;
//	  for(LongWritable iw:values)
//	  {
//		  sum += iw.get();
//	  }
//	  context.write(key, new LongWritable(sum));
//  }
//}

public class StubReducer extends Reducer<Text, TextArrayWritable, Text, TextArrayWritable> {
	public void reduce(Text key, Iterator<TextArrayWritable> values, Context context) 
			throws IOException, InterruptedException {
		List<Writable> list = new ArrayList<Writable>();
		while (values.hasNext()) {
			Writable[] writables = values.next().get();
			for (Writable t: writables) {list.add(t);}
		}
		Writable[] writables = list.toArray(new Writable[0]);
		TextArrayWritable writable = new TextArrayWritable();
		writable.set(writables);
		context.write(key, writable);
	}
}