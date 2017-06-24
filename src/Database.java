import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Database {
	
	private JSONArray userEventJSON;
	private int colLength = 2;
	
	public Database() {
		userEventJSON = new JSONArray();
		getRunnerList();
	}
	
	public void getRunnerList() {
		String ip = "http://192.168.86.128:7777/name/select";
		URL url;
		try {
			url = new URL(ip);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String inputLine;
			while ((inputLine = rd.readLine()) != null)
				response.append(inputLine);
			rd.close();
			con.disconnect();
			userEventJSON = new JSONArray(response.toString());
		} catch (MalformedURLException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		} catch (JSONException e) {
//			e.printStackTrace();
		}
	}
	
	public void addTagToDatabase(String id, String tag) {
		try {
			JSONObject jo = new JSONObject();
			jo.put(id, tag);
			String ip = "http://localhost:7777";
			URL url = new URL(ip);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestMethod("POST");
			con.connect();
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jo.toString());
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
				System.out.println(line);
			rd.close();
			wr.close();
			con.disconnect();
		} catch (MalformedURLException e) {
//			e.printStackTrace();
		} catch (ProtocolException e) {
//			e.printStackTrace();
		} catch (JSONException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	public String[][] getTable() {
		String[][] table = null;
		try {
			if (userEventJSON.length() > 0) {
				table = new String[userEventJSON.length()][colLength];
				for (int i = 0; i < table.length; i++) {
					JSONObject obj = new JSONObject(userEventJSON.get(i).toString());
					String[] indexList = {"user_event_id", "user_name"};
					for (int j = 0; j < indexList.length; j++) {
						table[i][j] = obj.get(indexList[j]).toString();
					}
				}
			} else
				table = new String[1][colLength];
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return table;
	}
	
}
