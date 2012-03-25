public class Page {
	double timeStamp;
	int PageCount;
	Integer[] arrPageset;
	
	public void setPageDetails(String time, String pageCount, String strPageSet)
	{
		String[] arrTime = time.split(" ");
		String seconds = arrTime[0].trim();
		String microSeconds = arrTime[1].trim();
		timeStamp = Double.parseDouble(seconds) * 1000000 + Double.parseDouble(microSeconds);
		PageCount = Integer.parseInt(pageCount.trim());
		arrPageset = new Integer[PageCount];
		String strPageDetails[] = strPageSet.split(" ");
		int i=0;
		int j=0;
		while(i<this.PageCount)
		{
			if(!strPageDetails[j].trim().equals(""))
			{
				//System.out.println(Integer.parseInt(strPageDetails[j].trim()));
				arrPageset[i] = Integer.parseInt(strPageDetails[j].trim());
				i++;
			}
			j++;
		}
	}
	
	public void printPageDetails()
	{
		System.out.println("Timestamp: " + timeStamp);
		System.out.println("PageCount: " + PageCount);
		System.out.println("PageSet: ");
		for(int i=0;i<PageCount;i++)
		{
			System.out.print(arrPageset[i]+ " ");
		}
		
	}
	
	public int getPageCount()
	{
		return PageCount;
	}
	
	public Integer[] getarrPageset()
	{
		return arrPageset;
	}
	
	public double gettimeStamp()
	{
		return timeStamp;
	}
}

