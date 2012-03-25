import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import pageset.ObjectPositionInputFormat;

public class Pageset{
	public static class Map extends MapReduceBase implements Mapper<Text, Text, Text, Text>{
		private final static IntWritable one =  new IntWritable(1);
		private Text word = new Text();
		public void map(Text key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
			

                output.collect(key, value);

	
		}
	}
	public static class Reduce extends MapReduceBase implements Reducer<Text, Text , Text, Text>{
		public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException{
                                ArrayList<Page> objArray = new ArrayList<Page>();
                             
				while (values.hasNext()){
                                   String [] pieces = values.next().toString().split(":");
                                   Page objPage = new Page();
                                   objPage.setPageDetails(pieces[0], pieces[1], pieces[2]);
                                   objArray.add(objPage);

				}
                                Text outstr = new Text();
                                Pager objPg = new Pager();
                                Integer[] array;
                                Page MinPg;
                                MinPg = objPg.extractMinWorkingSet(objArray);
                                String stringout = null;
                                array = MinPg.getarrPageset();
                                stringout = "Min Page: TimeStamp: " + Double.toString(MinPg.timeStamp)
                                        + " PageCount: " + Integer.toString(MinPg.PageCount) + " PageSet:";


                                for (int i=0;i<array.length;i++)
                                {
                                  stringout = stringout + " " + array[i].toString();
                                }
                                     
                                            
                                outstr.set(stringout);

				output.collect(key, outstr);

                                outstr.set(objPg.extractRunningWorkingSet(objArray));

                                output.collect(key, outstr);

                                outstr.set(objPg.extractPageFaultCount(objArray));

                                output.collect(key, outstr);

                                outstr.set("Page Priority: \n" + objPg.getPagePriority(objArray));

                                output.collect(key, outstr);
		}
	}
	public static void main(String[]args) throws IOException{
		JobConf conf = new JobConf(Pageset.class);
		conf.setJobName("wordcount");
		conf.setOutputKeyClass(Text.class);
                conf.setOutputValueClass(Text.class);
		conf.setMapperClass(Map.class);
		conf.setReducerClass(Reduce.class);
		conf.setInputFormat(ObjectPositionInputFormat.class);
                conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

                try{
                    JobClient.runJob(conf);
                }catch(IOException e){
                    System.err.println(e.getMessage());
                }
	}
}