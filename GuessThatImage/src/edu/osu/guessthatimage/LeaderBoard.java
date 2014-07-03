package edu.osu.guessthatimage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class LeaderBoard extends Activity {
	private databaseHelperEasyTwo dh1;
	private databaseHelperEasyFive dh2;
	private databaseHelperEasyTen dh3;
	private databaseHelperMediumTwo dh4;
	private databaseHelperMediumFive dh5;
	private databaseHelperMediumTen dh6;
	private databaseHelperHardTwo dh7;
	private databaseHelperHardFive dh8;
	private databaseHelperHardTen dh9;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        ListView list = (ListView) findViewById(R.id.leader_board);
        
        String easy = "4";
        String medium = "2";
        String hard = "1";
        String two = "2";
        String five = "5";
        String ten = "10";
        this.dh1 = new databaseHelperEasyTwo(this);
        this.dh2 = new databaseHelperEasyFive(this);
        this.dh3 = new databaseHelperEasyTen(this);
        this.dh4 = new databaseHelperMediumTwo(this);
        this.dh5 = new databaseHelperMediumFive(this);
        this.dh6 = new databaseHelperMediumTen(this);
        this.dh7 = new databaseHelperHardTwo(this);
        this.dh8 = new databaseHelperHardFive(this);
        this.dh9 = new databaseHelperHardTen(this);
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        String time = Settings.getTime(getApplicationContext());
        String difficulty = Settings.getNumber(getApplicationContext());
       
        if(time.equals(two) && difficulty.equals(easy)){
        	List<String> testing = dh1.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
         }else if(time.equals(five) && difficulty.equals(easy)){
        	 List<String> testing = dh2.selectAll();
             while(testing.size()>0){
             	HashMap<String, String> map = new HashMap<String, String>();
             	map.put("ItemTitle", testing.remove(0));
             	map.put("ItemText", testing.remove(0));
             	mylist.add(map);
             }
        }else if(time.equals(ten) && difficulty.equals(easy)){
        	List<String> testing = dh3.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else if(time.equals(two) && difficulty.equals(medium)){
        	List<String> testing = dh4.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else if(time.equals(five) && difficulty.equals(medium)){
        	List<String> testing = dh5.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else if(time.equals(ten) && difficulty.equals(medium)){
        	List<String> testing = dh6.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else if(time.equals(two) && difficulty.equals(hard)){
        	List<String> testing = dh7.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else if(time.equals(five) && difficulty.equals(hard)){
        	List<String> testing = dh8.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else if(time.equals(ten) && difficulty.equals(hard)){
        	List<String> testing = dh9.selectAll();
            while(testing.size()>0){
            	HashMap<String, String> map = new HashMap<String, String>();
            	map.put("ItemTitle", testing.remove(0));
            	map.put("ItemText", testing.remove(0));
            	mylist.add(map);
            }
        }else{
                Toast.makeText(getBaseContext(), "Fucking Error",
        Toast.LENGTH_SHORT).show();
        }
       
        SimpleAdapter mSchedule = new SimpleAdapter(this,
        		                                    mylist,
        		                                    R.layout.list_item,
        		                                     
        		                                    new String[] {"ItemTitle", "ItemText"}, 
        		                                    
        		                                    new int[] {R.id.leader_board_score,R.id.leader_board_username});
        list.setAdapter(mSchedule);
    }
}
