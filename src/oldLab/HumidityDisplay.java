package oldLab;

/**
 * Created by wrightjt on 12/3/2015.
 */
public class HumidityDisplay implements IDisplay {

    @Override
    public void dataChanged(IWeatherData data) {
        System.out.format("Humidity: %f%n: ", data.getHumidity());
    }
}
