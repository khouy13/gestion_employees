package controller.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane Home_anchorpane;

    @FXML
    private Button add;

    @FXML
    private AnchorPane add_anchorpane;

    @FXML
    private Button add_employer;

    @FXML
    private Button add_salary;

    @FXML
    private Button btn_home;

    @FXML
    private BarChart<?, ?> chartdata;

    @FXML
    private Button clear;

    @FXML
    private Button close;

    @FXML
    private Button delete;

    @FXML
    private Button form_btn;

    @FXML
    private DatePicker form_date;

    @FXML
    private TextField form_firstName;

    @FXML
    private ComboBox<String> form_gender;

    @FXML
    private TextField form_id;

    @FXML
    private TextField form_lastName;

    @FXML
    private TextField form_phone;

    @FXML
    private TextField form_postion;

    @FXML
    private TableColumn<Employee, String> home_col_firstName;

    @FXML
    private TableColumn<Employee, String> home_col_id;

    @FXML
    private TableColumn<Employee, String> home_col_lastName;

    @FXML
    private TableColumn<Employee, String> col_date;

    @FXML
    private ImageView image;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private TableColumn<Employee, String> salary_LastName;

    @FXML
    private AnchorPane salary_anchorpane;

    @FXML
    private TableColumn<Employee, String> salary_firstName;

    @FXML
    private Button salary_form_clear;

    @FXML
    private Label salary_form_firstName;

    @FXML
    private TextField salary_form_id;

    @FXML
    private Label salary_form_lastName;

    @FXML
    private Label salary_form_position;

    @FXML
    private TextField salary_form_salary;

    @FXML
    private Button salary_form_update;

    @FXML
    private TableColumn<Employee, String> salary_id;

    @FXML
    private TableColumn<Employee, String> salary_position;

    @FXML
    private TableColumn<Employee, String> salary_col;

    @FXML
    private TextField search;

    @FXML
    private TableView<Employee> table_salary;

    @FXML
    private Label tatalEemployes;

    @FXML
    private Label totalInactive;

    @FXML

    private ComboBox<String> form_position;

    @FXML
    private Label totalPresent;

    @FXML
    private Button update;
    @FXML
    private TableColumn<Employee, String> col_phone;

    @FXML
    private TableColumn<Employee, String> home_col_poistion;
    @FXML
    private TableColumn<Employee, String> home_gender;

    @FXML
    private Label adminName;

    private ObservableList<Employee> listEmployees;

    @FXML
    private TableView<Employee> table_emplyees;

    private Image imagge;

    private ObservableList<Employee> listSalaries;

    private Connection connection;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public ObservableList<Employee> listEmplyeesData() {
        ObservableList<Employee> listEmplyees = FXCollections.observableArrayList();
        connection = Database.connectDB();
        String sql = "SELECT * FROM employees";
        try {

            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();
            Employee employee;
            while (result.next()) {
                employee = new Employee(result.getInt("employeeId"), result.getString("firstName"),
                        result.getString("lastName"), result.getString("gender"), result.getString("position"),
                        result.getString("phone"), result.getDate("date"), result.getString("image"));
                listEmplyees.add(employee);
            }
            return listEmplyees;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listEmplyees;
    }

    public void remplirTableEmployees() {
        listEmployees = listEmplyeesData();
        home_col_id.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        home_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        home_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        home_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        home_col_poistion.setCellValueFactory(new PropertyValueFactory<>("position"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table_emplyees.setItems(listEmployees);
    }

    public void addEmployerInsetImage(ActionEvent e) {
        FileChooser opChooser = new FileChooser();
        File file = opChooser.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            GetData.path = file.getAbsolutePath();
            imagge = new Image(file.toURI().toString(), 106, 101, false, true);
            image.setImage(imagge);
        }
    }

    public void addEmployerSelect() {
        Employee employee = table_emplyees.getSelectionModel().getSelectedItem();
        int num = table_emplyees.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        form_firstName.setText(employee.getFirstName());
        form_id.setText(String.valueOf(employee.getEmployeeId()));
        form_lastName.setText(employee.getLastName());
        form_phone.setText(employee.getPhone());
        form_gender.setValue(employee.getGender());
        form_position.setValue(employee.getPosition());
        String uri = employee.getImage();
        GetData.path = uri.replace("\\\\", "\\");
        imagge = new Image(uri, 106, 101, false, true);
        image.setImage(imagge);
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void clear(ActionEvent event) {
        form_id.setText("");
        form_firstName.setText("");
        form_lastName.setText("");
        form_gender.getSelectionModel().clearSelection();
        form_position.getSelectionModel().clearSelection();
        form_phone.setText("");
        GetData.path = "";
        image.setImage(null);
    }

    @FXML
    void add(ActionEvent event) {
        connection = Database.connectDB();
        String sql = "INSERT INTO employees(employeeId,firstName,lastName,gender,phone,position,date,image) VALUES (?,?,?,?,?,?,?,?)";
        Date date = new Date();
        java.sql.Date datesqlite = new java.sql.Date(date.getTime());
        try {
            Alert alert;
            if (form_id.getText().isEmpty()
                    || form_firstName.getText().isEmpty()
                    || form_lastName.getText().isEmpty()
                    || form_gender.getSelectionModel().getSelectedItem() == null
                    || form_phone.getText().isEmpty()
                    || form_position.getSelectionModel().getSelectedItem() == null
                    || GetData.path == ""
                    || GetData.path == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Message ERROR");
                alert.setHeaderText(null);
                alert.setContentText("fill all filieds");
                alert.showAndWait();
            } else {
                String check = "SELECT employeeId FROM employees WHERE  employeeId='" + form_id.getText() + "'";
                statement = connection.createStatement();
                result = statement.executeQuery(check);
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("employee id already exit " + form_id.getText());
                    alert.showAndWait();
                } else {
                    prepare = connection.prepareStatement(sql);
                    prepare.setString(1, form_id.getText());
                    prepare.setString(2, form_firstName.getText());
                    prepare.setString(3, form_lastName.getText());
                    prepare.setString(4, (String) form_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, form_phone.getText());
                    prepare.setString(6, (String) form_position.getSelectionModel().getSelectedItem());
                    prepare.setString(7, String.valueOf(datesqlite));
                    String uri = GetData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("add emplyee successfully");
                    alert.showAndWait();
                    remplirTableEmployees();
                    clear(event);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        String sql = "DELETE FROM employees WHERE employeeId ='" + form_id.getText() + "'";
        connection = Database.connectDB();
        try {
            Alert alert;
            if (form_id.getText().isEmpty()
                    || form_firstName.getText().isEmpty()
                    || form_lastName.getText().isEmpty()
                    || form_gender.getSelectionModel().getSelectedItem() == null
                    || form_phone.getText().isEmpty()
                    || form_position.getSelectionModel().getSelectedItem() == null
                    || GetData.path == ""
                    || GetData.path == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Message ERROR");
                alert.setHeaderText(null);
                alert.setContentText("fill all filieds");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("INFORMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("are you sure you want to delete employee " + form_id.getText());
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("delete emplyee successfully");
                    alert.showAndWait();
                    remplirTableEmployees();
                    clear(event);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) {

        Date date = new Date();
        java.sql.Date datesqlite = new java.sql.Date(date.getTime());

        connection = Database.connectDB();
        try {
            Alert alert;
            if (form_id.getText().isEmpty()
                    || form_firstName.getText().isEmpty()
                    || form_lastName.getText().isEmpty()
                    || form_gender.getSelectionModel().getSelectedItem() == null
                    || form_phone.getText().isEmpty()
                    || form_position.getSelectionModel().getSelectedItem() == null
                    || GetData.path == ""
                    || GetData.path == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Message ERROR");
                alert.setHeaderText(null);
                alert.setContentText("fill all filieds");
                alert.showAndWait();
            } else {

                String uri = GetData.path;
                uri = uri.replace("\\", "\\\\");

                String sql = "UPDATE employees SET firstName='" + form_firstName.getText()
                        + "', lastName='" + form_lastName.getText()
                        + "', gender='" + form_gender.getSelectionModel().getSelectedItem()
                        + "', phone='" + form_phone.getText()
                        + "', position='" + form_position.getSelectionModel().getSelectedItem()
                        + "', date='" + datesqlite
                        + "', image='" + uri
                        + "' WHERE employeeId ='" + form_id.getText() + "'";
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("INFORMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("are you sure you want to update employee " + form_id.getText());
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFORMATION MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("update emplyee successfully");
                    alert.showAndWait();
                    remplirTableEmployees();
                    clear(event);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    private double x = 0;
    private double y = 0;

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("are you sure you want to log out?");
        Optional<ButtonType> bOptional = alert.showAndWait();
        if (bOptional.get().equals(ButtonType.OK)) {
            try {

                close.getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("./css/css.css");
                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent e) -> {
                    stage.setX(e.getSceneX() - x);
                    stage.setY(e.getSceneY() - y);
                    stage.setOpacity(.8);
                });
                root.setOnMouseReleased((MouseEvent e) -> {
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert2 = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert2.setTitle("Message Erreur");
                alert2.setHeaderText(null);
                alert2.setContentText(e.getMessage());
            }
            // (10,10)->(200,200) 190,190
        }
    }

    public void swithEvent(ActionEvent event) {

        if (event.getSource() == btn_home) {
            Home_anchorpane.setVisible(true);
            add_anchorpane.setVisible(false);
            salary_anchorpane.setVisible(false);
            btn_home.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#1793ec,#4ab4f6);");
            add_employer.setStyle("-fx-background-color:transparent");
            add_salary.setStyle("-fx-background-color:transparent");
            displayTotalEmployees();
            displayTotalPresentEmployees();
            displayTotalInactiveEmployees();
        }
        if (event.getSource() == add_employer) {
            Home_anchorpane.setVisible(false);
            add_anchorpane.setVisible(true);
            salary_anchorpane.setVisible(false);
            add_employer.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#1793ec,#4ab4f6);");
            btn_home.setStyle("-fx-background-color:transparent");
            add_salary.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == add_salary) {
            remplirTableSalaries();
            Home_anchorpane.setVisible(false);
            add_anchorpane.setVisible(false);
            salary_anchorpane.setVisible(true);
            add_salary.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#1793ec,#4ab4f6);");
            add_employer.setStyle("-fx-background-color:transparent");
            btn_home.setStyle("-fx-background-color:transparent");
        }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    String[] posistions = { "Markter", "web develloper", "mobile devellopper", "web designer" };

    public void addPositionList() {
        ObservableList<String> observable = FXCollections.observableArrayList();
        for (String data : posistions) {
            observable.add(data);
        }
        form_position.setItems(observable);

    }

    String[] genders = { "Male", "famale" };

    public void addGenderList() {
        ObservableList<String> observable = FXCollections.observableArrayList();
        for (String data : genders) {
            observable.add(data);
        }
        form_gender.setItems(observable);
    }

    public void displayAdminName() {
        adminName.setText(GetData.adminName);
    }

    public void search() {
        FilteredList<Employee> filter = new FilteredList<>(listEmployees, e -> true);
        search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predecteDataEmployer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predecteDataEmployer.getEmployeeId().toString().contains(searchKey)) {
                    return true;
                }
                if (predecteDataEmployer.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (predecteDataEmployer.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (predecteDataEmployer.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (predecteDataEmployer.getPhone().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (predecteDataEmployer.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (predecteDataEmployer.getDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<Employee> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(table_emplyees.comparatorProperty());
            table_emplyees.setItems(sortedList);
        });
    }

    public ObservableList<Employee> getListSalaries() {
        ObservableList<Employee> observableList = FXCollections.observableArrayList();
        String sql = "SELECT employeeId,firstName,lastName,position,salary FROM employees";
        connection = Database.connectDB();
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            Employee employee;
            while (result.next()) {
                employee = new Employee(result.getInt("employeeId"), result.getString("firstName"),
                        result.getString("lastName"), result.getString("position"), result.getInt("salary"));
                observableList.add(employee);
            }
            return observableList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return observableList;
    }

    public void remplirTableSalaries() {
        listSalaries = getListSalaries();
        salary_id.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        salary_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salary_LastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salary_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary_col.setCellValueFactory(new PropertyValueFactory<>("salarie"));
        table_salary.setItems(listSalaries);
    }

    public void addEmployerSalary() {
        Employee employee = table_salary.getSelectionModel().getSelectedItem();
        int num = table_salary.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        salary_form_id.setText(String.valueOf(employee.getEmployeeId()));
        salary_form_firstName.setText(employee.getFirstName());
        salary_form_lastName.setText(employee.getLastName());
        salary_form_position.setText(employee.getPosition());
        salary_form_salary.setText(String.valueOf(employee.getSalarie()));
    }

    public void update_salary() {
        String sql = "UPDATE employees SET salary = '" + salary_form_salary.getText() + "' WHERE employeeId = '"
                + salary_form_id.getText() + "'";
        connection = Database.connectDB();
        try {
            Alert alert;
            if (salary_form_id.getText().isEmpty() || salary_form_salary.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Fill all filieds");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("are you sure you want to update employee :" + salary_form_id.getText());
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("update Successfully");
                    alert.showAndWait();
                    remplirTableSalaries();
                    ;
                    clear_salary();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear_salary() {
        salary_form_id.setText("");
        salary_form_firstName.setText("");
        salary_form_lastName.setText("");
        salary_form_salary.setText("");
        salary_form_position.setText("");
    }

    public void displayTotalEmployees() {
        connection = Database.connectDB();
        String sql = "SELECT COUNT(id) FROM employees";
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            int contdata=0;
            while(result.next()){
                 contdata=result.getInt("COUNT(id)");
            }
            tatalEemployes.setText(String.valueOf(contdata));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
        public void displayTotalPresentEmployees() {
        connection = Database.connectDB();
        String sql = "SELECT COUNT(id) FROM employees WHERE salary != 0";
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            int contdata=0;
            while(result.next()){
                 contdata=result.getInt("COUNT(id)");
            }
            totalPresent.setText(String.valueOf(contdata));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
         public void displayTotalInactiveEmployees() {
        connection = Database.connectDB();
        String sql = "SELECT COUNT(id) FROM employees WHERE salary = 0";
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            int contdata=0;
            while(result.next()){
                 contdata=result.getInt("COUNT(id)");
            }
            totalInactive.setText(String.valueOf(contdata));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    public void homeChart(){
        chartdata.getData().clear();
        String sql="";
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        remplirTableEmployees();
        remplirTableSalaries();
        addPositionList();
        addGenderList();
        displayAdminName();
        btn_home.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#1793ec,#4ab4f6);");
        displayTotalEmployees();
        displayTotalPresentEmployees();
        displayTotalInactiveEmployees();
}
}
