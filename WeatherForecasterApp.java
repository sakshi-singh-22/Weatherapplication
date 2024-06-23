import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecasterApp {

    private static JFrame frame;
    private static JTextField LocationFeild;
    private static JTextArea WeatherDisplay;
    private static JButton fetchButton;
    private static String apiKey = "21d4f6395517271e2669c9230dcde939"; //Replace your own generate key
    private static  String fetchWeatherData(String city){
       try {
           URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");

           BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String response = " ";
String line;

while ((line = reader.readLine()) != null){
    response += line;
}
reader.close();
           JSONObject jsonObject = (JSONObject) JSONValue.parse(response.toString());
           JSONObject mainobj = (JSONObject)  jsonObject.get("main"); // main = key where all data is stored

           double temperatureKelvin = (double)mainobj.get("temp");
           long humidity = (long)mainobj.get("humidity");
           //temp to celsius

           double temperatureCelcius = temperatureKelvin - 273.15;

           //reterive data
           JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
           JSONObject weather = (JSONObject) weatherArray.get(0);
 String description = (String) weather.get("description");

 return "Description: " + description + "\ntemperature: "
+ temperatureCelcius + " Celsius\nHumidity: " + humidity + "%";
       } catch (Exception e){
           return"Failed to fetch weather data . please check your city and api key ...";
       }

    }
    public static void main(String[] args){
         frame = new JFrame("Weather Forecast App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLayout(new FlowLayout());

         LocationFeild = new JTextField(15);
         fetchButton = new JButton("Fetch weather");
         WeatherDisplay = new JTextArea(10,30);
        WeatherDisplay.setEditable(false);
        frame.add(new JLabel("Enter City Name"));
frame.add(LocationFeild);
frame.add(fetchButton);
frame.add(WeatherDisplay);

fetchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String city = LocationFeild.getText();
        String weatherInfo = fetchWeatherData(city);
        WeatherDisplay.setText(weatherInfo);
    }
});
        frame.setVisible(true);
    }

}
