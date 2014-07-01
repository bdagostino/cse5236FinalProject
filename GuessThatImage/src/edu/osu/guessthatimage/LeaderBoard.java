package edu.osu.guessthatimage;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LeaderBoard extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        ListView list = (ListView) findViewById(R.id.leader_board);

        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<30;i++)
        {
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("ItemTitle", "This is Title.....");
        	map.put("ItemText", "This is text.....");
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
