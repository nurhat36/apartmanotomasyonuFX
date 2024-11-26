module com.mycompany.apartmanotomasyonufxml {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.apartmanotomasyonufxml to javafx.fxml;
    exports com.mycompany.apartmanotomasyonufxml;
    requires mssql.jdbc;
}
