package modules.gateways;

import modules.entities.Event;

import java.util.ArrayList;

public class EventGateway {
    private EventStrategy strategy;

    public EventGateway() {
        this.strategy = new EventGatewayDB();
    }

    public ArrayList<Event> readData() {
        return this.strategy.readData();
    }

    public void writeData(ArrayList<Event> writeEvents) throws ClassNotFoundException {
        this.strategy.writeData(writeEvents);
    }

    public void setStrategy(EventStrategy newStrat) {
        this.strategy = newStrat;
    }

    public void setFilename(String newFilename) {
        this.strategy.setFilename(newFilename);
    }
}
