/*
	name:林廷彥
	student num:F74002159
	decription:
		download the json file from url which is 1st argument
		read the file and set it as a json array
		search all matched object 		
*/

import java.net.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TocHW3 {
	public static boolean laterThan(int year1, int year2) {
		if(year1/100 > year2/100) {
			return true;
		}
		else if(year1/100 == year2/100) {
			if(year1%100 > year2%100)
				return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws FileNotFoundException, JSONException
	{
		
		//long time_begin, time_end;
		//time_begin = System.currentTimeMillis();
		
		/* reading url and turn it into "json.json" */
		//String url="http://www.datagarage.io/api/5365dee31bc6e9d9463a0057";
		String url = args[0];
        
        System.out.println("reading by url ...");
        try {
            URL url_address = new URL( url );
            
            // 讀入網頁(字元串流)            
            BufferedReader br = new BufferedReader(new InputStreamReader(url_address.openStream(), "UTF-8"));
            //BufferedReader br = new BufferedReader(new InputStreamReader(url_address.openStream(), "Big5"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("data.json", false));    
            String oneLine = null ;
            
            while ((oneLine = br.readLine()) != null) {
                bw.write(oneLine);                
            }
            bw.close();
            br.close();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Done");
                   

		/* put the json file into a jsonarray */
		JSONArray jsonRealPrice = new JSONArray(new JSONTokener(new FileReader(new File("data.json"))));
		
		/* search the match data */
		double sum = 0;
		int ctr = 0;
		double avg_price = 0; 
		//String arg1 = "大安區";
		//String arg2 = "復興南路";
		//int arg3 = 103;
		
		Pattern township, road;
		Matcher matcher_ts, matcher_rd = null;
		
		String tested1 = ".*"+args[1]+".*";
		String tested2 = ".*"+args[2]+".*";
		
		for(int i=1; i<jsonRealPrice.length(); i++) {
			//System.out.printf("%d : %s; %s; %d\n", jsonRealPrice.getJSONObject(i).get("總價元"), jsonRealPrice.getJSONObject(i).get("鄉鎮市區"), 
					//jsonRealPrice.getJSONObject(i).get("土地區段位置或建物區門牌"),  jsonRealPrice.getJSONObject(i).get("交易年月"));
			
			township = Pattern.compile(tested1);
			matcher_ts = township.matcher(jsonRealPrice.getJSONObject(i).getString("鄉鎮市區"));
			road = Pattern.compile(tested2);
			matcher_rd = road.matcher(jsonRealPrice.getJSONObject(i).getString("土地區段位置或建物區門牌"));
			
			
			
			if(matcher_ts.find() && matcher_rd.find() && laterThan(jsonRealPrice.getJSONObject(i).getInt("交易年月"), Integer.valueOf(args[3])*100)) {
				//System.out.printf("got one!!! : %s; %s; %d; %d\n", jsonRealPrice.getJSONObject(i).get("鄉鎮市區"), 
				//		jsonRealPrice.getJSONObject(i).getString("土地區段位置或建物區門牌"), jsonRealPrice.getJSONObject(i).getInt("交易年月"), 
				//		jsonRealPrice.getJSONObject(i).getInt("總價元"));
				
				sum = sum + jsonRealPrice.getJSONObject(i).getInt("總價元");
				//System.out.printf("sum: %10.0f\n", sum);
				ctr++;
			}
		}
		
		avg_price = sum/ctr;
		System.out.printf("average: %d\n", (int)avg_price);
		
		//time_end = System.currentTimeMillis();
		//System.out.println("exe time: " + (time_end-time_begin)/1000 + "sec\n");
		
        /* find the match object */
		//Pattern p = Pattern.compile(".*aa.*");
		
		//String testString = "ABCaaabcaac";
		//Matcher matcher = p.matcher(testString);
		/*
		while(matcher.find()) {
			System.out.println("matcher.group():"+matcher.group());
		}
		*/
		
	}
	
}
