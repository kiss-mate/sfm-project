package handlers.interfaces;

import models.MainViewDto;

public interface ActionHandlerBase {
    String handleAction(MainViewDto dto);
}
