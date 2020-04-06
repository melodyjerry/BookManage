package com.demo.controller.function;

import com.demo.controller.MainController;
import com.demo.service.MainService;
import com.demo.utils.ResourcesConfig;
import com.demo.utils.ServiceFactory;
import javafx.event.ActionEvent;

public class SystemManageController {
    private MainService mainService = ServiceFactory.getMainServiceInstance();

    /**
     * 操作日志信息
     * @param actionEvent
     * @throws Exception
     */
    public void listOperation(ActionEvent actionEvent) throws Exception {
        mainService.switchView(ResourcesConfig.OPERATION_FXML, MainController.mainToOtherContainer);
    }
}
