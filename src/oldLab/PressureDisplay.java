package oldLab;

/**
 * Created by wrightjt on 12/3/2015.
 */
public class PressureDisplay implements IDisplay {

    @Override
    public void dataChanged(IWeatherData data) {
        System.out.format("Pressure: %f%n: ", data.getPressure());
    }
}
