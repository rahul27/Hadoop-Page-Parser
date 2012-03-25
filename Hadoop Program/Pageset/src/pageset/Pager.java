import java.io.BufferedReader;
import java.util.Map.Entry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
//import java.security.KeyStore.Entry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import sun.org.mozilla.javascript.internal.ObjArray;


public class Pager {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String line,nextline;
		String time;
		String page_count;
		String page_set;
		int page_len;
		int time_end;
		int count_end;
		ArrayList<Page> objArray = new ArrayList<Page>();
		
		
		File file = new File("/home/rahul/files/mapout");
		BufferedReader bufRdr  = new BufferedReader(new FileReader(file));
		
		
		while((line = bufRdr.readLine())!=null)
		{
		//line = bufRdr.readLine();
		
			page_len = line.length();
			
			time_end = line.indexOf(':');
			
			time = line.substring(0, time_end - 1);
			
			count_end = line.substring(time_end + 1).indexOf(":");
			
			page_count = line.substring(time_end + 1, time_end + count_end);
			page_set = line.substring(time_end + count_end + 2);
			
			//System.out.print(time + " " + page_count + " " + page_set + "\n");
	
			Page objPage = new Page();
			objPage.setPageDetails(time, page_count, page_set);
			//objPage.printPageDetails();
			
			//objArray = new ArrayList<Page>();
			objArray.add(objPage);
		}
		
		/*Iterator<Page> it = objArray.iterator();
		while(it.hasNext())
		{
			it.next().printPageDetails();
		}*/
		
		//extractMinWorkingSet(objArray);
		//getPagePriority(objArray);
		//extractPageFaultCount(objArray);
		
		Pager objRed = new Pager();
		//objRed.extractRunningWorkingSet(objArray);
		//objRed.extractPageFaultCount(objArray);//(objArray);
		
		

	}
	
	public Page extractMinWorkingSet(ArrayList<Page> objArray)
	{
		Iterator<Page> it = objArray.iterator();
		int min = 0;
		int value;
		int index=0;
		int min_index=0;
		
		while(it.hasNext())
		{
			if (min == 0)
			{
				min = it.next().getPageCount();
				min_index = index;
			}
			else 
			{
				value = it.next().getPageCount();
				//System.out.print(value + " ");
				if (min > value)
				{
					min = value;
					min_index = index;
				}
					
			}
			index = index + 1;
			//it.next().printPageDetails();
			
		}
		
		//objArray.get(min_index).printPageDetails();
                return objArray.get(min_index);
		//System.out.print(min);
		
	}
	
	public String extractRunningWorkingSet(ArrayList<Page> ObjArray)
	{
		
		HashMap<List<Integer[]>,Integer> myHash = new HashMap<List<Integer[]>,Integer>();
		int length = ObjArray.size();
		for (int i=0; i<length; i++){
				List<Integer[]> newList = new ArrayList<Integer[]>();
				newList.add(ObjArray.get(i).arrPageset);
				if (!myHash.containsKey(newList)){
					newList = new ArrayList<Integer[]>();
					newList.add(ObjArray.get(i).arrPageset);
					myHash.put(newList,new Integer(1));
				}
				else {
					Integer presentCount = myHash.get(ObjArray.get(i).arrPageset);
					newList = new ArrayList<Integer[]>();
					newList.add(ObjArray.get(i).arrPageset);
					myHash.remove(newList);
					newList = new ArrayList<Integer[]>();
					newList.add(ObjArray.get(i).arrPageset);
					myHash.put(newList, presentCount + 1);
				}
		}		
		Set<Entry<List<Integer[]>, Integer>> mySet = myHash.entrySet();
		Iterator it = mySet.iterator();
		Integer maxValue = new Integer(0);
		List<List<Integer[]>> maxList = new ArrayList<List<Integer[]>>();
		
		List<Integer[]> newTempList = new ArrayList<Integer[]>();
		newTempList.add(new Integer[]{0,1,2});			
		maxList.add(newTempList);
		newTempList=null;
		
		while (it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();			
			if((Integer)(entry.getValue())>maxValue)
			{
				maxValue = (Integer)entry.getValue();
				if(maxList.size()>0)
				{
					maxList.clear();
					
					//tempList = new ArrayList<Integer[]>();
					//tempList.add((Integer[])entry.getKey());
					maxList.add((List<Integer[]>)entry.getKey());
					//maxList.add(tempList);
				}
			}
		}
		
		Iterator<List<Integer[]>> itnew = maxList.iterator();
                String result;
		//System.out.println("Running working set: " );
                result = "Running working set: ";
		while(itnew.hasNext())
		{
			List<Integer[]> tempList =  (List<Integer[]>)itnew.next();			
			//tempList = itnew.next();		
			for(int j=0;j<tempList.size();j++)
			{
				for(int i=0;i<tempList.get(j).length;i++)
                                    result = result + tempList.get(j)[i] + " ";
				//	System.out.print(tempList.get(j)[i] + " ");
			}

                        result = result + "\n";
			//System.out.println(" ");
		}
	
	
	return result;
		
	}
	
	public String extractPageFaultCount(ArrayList<Page> ObjArray) throws IOException
	{
		//FileWriter fstream = new FileWriter("/home/rahul/files/pagefault");
		//BufferedWriter out = new BufferedWriter(fstream);
                String result = "";
                int length = ObjArray.size();
                int iPageFault=0;
                for (int i=0; i<length; i++){
		if(i==0)
		{
//			System.out.print("Time stamp: " + ObjArray.get(i).timeStamp + " ");
//			out.write("Time stamp: " + ObjArray.get(i).timeStamp + " ");
//			System.out.print("PageFaults" + ": " + iPageFault);
//			out.write("PageFaults" + ": " + iPageFault);
                        result = result + "Time stamp: " + ObjArray.get(i).timeStamp + " " +
                                "PageFaults" + ": " + iPageFault;

		}
		else
		{
			//System.out.println("");
			//out.write("\n");
                        result = result + "\n";
			//System.out.print("Time stamp: " + ObjArray.get(i).timeStamp + " ");
			//out.write("Time stamp: " + ObjArray.get(i).timeStamp + " ");
                        result = result + "Time stamp: " + ObjArray.get(i).timeStamp + " ";
			List<Integer> elem = new ArrayList<Integer>();
			List<Integer> list1 = new ArrayList<Integer>();
			List<Integer> list2 = new ArrayList<Integer>();
			//String strVal="";
			for(int j=0;j<ObjArray.get(i).arrPageset.length;j++)
			{
				elem.add(ObjArray.get(i).arrPageset[j]);
				list1.add(ObjArray.get(i).arrPageset[j]);
				//strVal = strVal + ObjArray.get(i).arrPageset[j] + " ";
			}
			for(int j=0;j<ObjArray.get(i-1).arrPageset.length;j++)
			{
				list2.add(ObjArray.get(i-1).arrPageset[j]);
				if(!elem.contains(ObjArray.get(i-1).arrPageset[j]))
				{
					elem.add(ObjArray.get(i-1).arrPageset[j]);						
				}
			}
			
			for(int j=0;j<elem.size();j++)
			{
				if((list1.contains(elem.get(j)))&&(list2.contains(elem.get(j))))
				{
				}
				else
					iPageFault++;
			}
			//System.out.print("PageFaults" + ": " + iPageFault);
			//out.write("PageFaults" + ": " + iPageFault);
                        result = result + "PageFaults" + ": " + iPageFault;
			iPageFault = 0;
		}	
		
		}			
	//out.close();
	return result;
		
		
	}
	
	
	public String getPagePriority(ArrayList<Page> objArray) throws IOException
	{
	int index = 0;
	double ts;
        String result = "";
	Integer arrPageset[];
	Iterator<Page> it = objArray.iterator();
	HashMap<Integer,List<Double>> pageDict = new HashMap<Integer,List<Double>>();
	List<Double> timeset = new ArrayList<Double>();
	
	//FileWriter fstream = new FileWriter("/home/rahul/files/pri");
	//BufferedWriter out = new BufferedWriter(fstream);
	
	while(it.hasNext())
	{
		Page p = it.next();
		arrPageset= p.getarrPageset();
		ts = p.gettimeStamp();
		if (!timeset.contains(ts))
			timeset.add(ts);
		
		//DecimalFormat formatter = new DecimalFormat("##0.00######");
		//System.out.println(formatter.format(ts));

		//System.out.print(ts);
		for(int i=0;i<arrPageset.length;i++)
		{
			if (!pageDict.containsKey(arrPageset[i]))
			{
				List<Double> value = new ArrayList<Double>();
				value.add(ts);
				pageDict.put(arrPageset[i], value);
			}
			else
			{
				//List<Integer> value;
				List<Double> value = new ArrayList<Double>();
				value = pageDict.get(arrPageset[i]);
				value.add(ts);
				pageDict.put(arrPageset[i], value);
				
			}
			//System.out.print(arrPageset[i]+ " ");
		}
		
		index++;
		//System.out.print("\n");
	}
	
	Set<Integer> pageNo = pageDict.keySet();
	Iterator<Integer> pageIt = pageNo.iterator();
	
	//System.out.print(timeset.size());
	
	
	
	while(pageIt.hasNext())
	{		
		Integer key = pageIt.next();
		List<Double> value;
		//System.out.print( key + " ");
		//System.out.print(pageDict.get(key) + "\n");
		//out.write( key + " " + pageDict.get(key) + "\n");
		value = pageDict.get(key);
		Collections.sort(value);
		Collections.sort(timeset);
		
		int pri = 0;
		
		
		
		int i;
		for(i = 0; i < timeset.size(); i++)
		{
			if (value.contains(timeset.get(i)))
				pri++; 					
			else
				pri--;			
		}
		
		
		
		
		//System.out.print( key + " " );
		//System.out.print(pri + "\n");
		
		//out.write(key + " " + pri + "\n");
                result = result + key + " " + pri + "\n";
	}
        return result;
		
	}
	

}
