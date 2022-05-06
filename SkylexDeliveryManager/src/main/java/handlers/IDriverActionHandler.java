package handlers;

import models.DriverTabDto;

public interface IDriverActionHandler {
    String handleAction(DriverTabDto dto);
}
