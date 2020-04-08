package com.demo.controller.borrow;

import com.demo.entity.TableView.BorrowInfo;
import com.demo.service.BorrowService;
import com.demo.utils.ExcelExport;
import com.demo.utils.enumeration.Operate;
import com.demo.utils.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class BorrowerPersonController implements Initializable {
    @FXML
    private TextField keywordsField;
    @FXML
    private TableView<BorrowInfo> borrowTable;
    private ObservableList<BorrowInfo> borrowData = FXCollections.observableArrayList();
    //添加续借列
    private TableColumn<BorrowInfo, BorrowInfo> renewCol = new TableColumn<>("操作");
    private BorrowService borrowService = ServiceFactory.getBorrowServiceInstance();

    public void search(ActionEvent actionEvent) {
        if (!keywordsField.getText().equals("")) {
            borrowTable.getItems().removeAll(borrowData);
            showUserData(borrowService.selectBorrowByBookNum(keywordsField.getText()));
        }
    }


    public void export(ActionEvent actionEvent) {
        try {
            ExcelExport.exportBorrow(borrowService.getBorrowPersonList());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示信息");
            alert.setHeaderText("成功");
            alert.setContentText("图书数据已导出!请到D盘根目录查看!");
            alert.showAndWait();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示信息");
            alert.setHeaderText("失败");
            alert.setContentText("请到D盘根目录已存在相同文件borrows.xlsx，请删除或重命名，再重新导出");
            alert.showAndWait();
            e.printStackTrace();
        }

    }


    //表格初始化方法
    private void initTable() throws ParseException {
        //水平方向不显示滚动条，表格的列宽会均匀分布
        //borrowTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //将实体集合作为参数，调用显示数据的方法
        showUserData(borrowService.getBorrowPersonList());

        //添加续借按钮
        borrowService.addButtonToTableView("续借","green-theme", renewCol, Operate.PERSONAL_RENEW, borrowData, borrowTable);
        borrowTable.getColumns().add(renewCol);

    }

    private void showUserData(List<BorrowInfo> borrowList) {
        borrowData.addAll(borrowList);
        borrowTable.setItems(borrowData);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }


}
