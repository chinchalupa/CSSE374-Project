package oldLab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wrightjt on 12/3/2015.
 */
public class WeatherReporter implements IWeatherReporter {

    private List<IDisplay> displays;
    private IWeatherData data;

    public WeatherReporter() {
        this.data = null;
        this.displays = Collections.synchronizedList(new ArrayList<IDisplay>());
    }

    @Override
    public void registerDisplay(IDisplay display) {
        this.displays.add(display);
    }

    @Override
    public void removeDisplay(IDisplay display) {
        this.displays.remove(display);
    }

    @Override
    public void setWeatherData(IWeatherData newData) {
        synchronized (this.displays) {
            if(this.data != null && this.data.equals(newData))
                return;
            this.data = newData;
            this.notifyDisplays();
        }
    }

    protected void notifyDisplays() {
        synchronized (this.displays) {
            for (IDisplay display: this.displays) {
                display.dataChanged(this.data);
            }
        }
    }
}
