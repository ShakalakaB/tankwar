module my.tankwar {
    requires javafx.media;
    requires java.desktop;
    requires fastjson;
    requires org.apache.commons.io;
    requires java.sql;
    requires static lombok;
    exports com.aldora.tankwar;
    opens com.aldora.tankwar to fastjson;
}