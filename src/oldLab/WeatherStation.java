package oldLab;

/**
 * Created by wrightjt on 12/3/2015.
 */
public class WeatherStation {
    public static void main(String[] args) {
        IWeatherReporter reporter = new WeatherReporter();

        IDisplay tempDisplay = new TemperatureDisplay();
        IDisplay humDisplay = new HumidityDisplay();
        IDisplay pressureDisplay = new PressureDisplay();

        reporter.registerDisplay(pressureDisplay);
        reporter.registerDisplay(tempDisplay);
        reporter.registerDisplay(humDisplay);

        IWeatherData data = new WeatherData(10, 10, 10);
        reporter.setWeatherData(data);

        data = new WeatherData(15, 15, 15);
        reporter.setWeatherData(data);
    }
}
