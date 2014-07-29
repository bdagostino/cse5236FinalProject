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

	private databaseHelperHighScores dhScore;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        ListView list = (ListView) findViewById(R.id.leader_board);
        this.dhScore = new databaseHelperHighScores(this);

        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        String time = Settings.getTime(getApplicationContext());
        String difficulty = Settings.getNumber(getApplicationContext());
       
    	List<String> testing = dhScore.selectAll(difficulty,time);
        while(testing.size()>0){
	     	HashMap<String, String> map = new HashMap<String, String>();
	     	map.put("ItemTitle", testing.remove(0));
	     	map.put("ItemText", testing.remove(0));
	     	mylist.add(map);
        }   
        
        SimpleAdapter mSchedule = new SimpleAdapter(this,
        		                                    mylist,
        		                                    R.layout.list_item,
        		                                     
        		                                    new String[] {"ItemTitle", "ItemText"}, 
        		                                    
        		                                    new int[] {R.id.leader_board_score,R.id.leader_board_username});
        list.setAdapter(mSchedule);
    }
}
