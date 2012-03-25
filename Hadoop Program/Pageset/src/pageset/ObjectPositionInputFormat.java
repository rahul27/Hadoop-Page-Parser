/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pageset;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;



public class ObjectPositionInputFormat extends
    FileInputFormat<Text, Text> {

  public RecordReader<Text, Text> getRecordReader(
      InputSplit input, JobConf job, Reporter reporter)
      throws IOException {

    reporter.setStatus(input.toString());
    return new ObjPosRecordReader(job, (FileSplit)input);
  }
}

class ObjPosRecordReader implements RecordReader<Text, Text> {

  private LineRecordReader lineReader;
  private LongWritable lineKey;
  private Text lineValue;

  public ObjPosRecordReader(JobConf job, FileSplit split) throws IOException {
    lineReader = new LineRecordReader(job, split);

    lineKey = lineReader.createKey();
    lineValue = lineReader.createValue();
  }

  public boolean next(Text key, Text value) throws IOException {
    // get the next line
    if (!lineReader.next(lineKey, lineValue)) {
      return false;
    }

    // parse the lineValue which is in the format:
    // objName, x, y, z
    String [] pieces = lineValue.toString().split(":");
    if (pieces.length != 4) {
      throw new IOException("Invalid record received" + pieces.length);
    }

    // try to parse floating point components of value
    String fx, fy;
    try {
      fx = pieces[1] + pieces[2];
      //System.out.println(fx);
     // fy = pieces[2];// + .trim());
      //fz = Float.parseFloat(pieces[3].trim());
    } catch (NumberFormatException nfe) {
      throw new IOException("Error parsing floating point value in record");
    }

    // now that we know we'll succeed, overwrite the output objects

    key.set(pieces[0]); // objName is the output key.

    value.set(pieces[1] + ":" + pieces[2] + ":" +pieces[3]);
    //value.y = fy;
    //value.z = fz;

    return true;
  }

  public Text createKey() {
    return new Text("");
  }

  public Text createValue() {
    return new Text("");
  }

  public long getPos() throws IOException {
    return lineReader.getPos();
  }

  public void close() throws IOException {
    lineReader.close();
  }

  public float getProgress() throws IOException {
    return lineReader.getProgress();
  }
}