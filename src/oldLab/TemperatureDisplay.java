package oldLab;

/**
 * Created by wrightjt on 12/3/2015.
 */
public class TemperatureDisplay implements IDisplay {

    @Override
    public void dataChanged(IWeatherData data) {
        System.out.format("Temperature: %f%n: ", data.getTemperature());
    }
}
