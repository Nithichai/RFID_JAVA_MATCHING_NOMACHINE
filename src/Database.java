import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


public class Database {
	
	public void getRunnerList() {
		String ip = "http://localhost:7777";
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addTagToDatabase(String id, String tag) {
		JSONObject jo = new JSONObject();
		try {
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
	
}
