package com.lemelo.util;

public class BibliotecaString {
    public String urlBanco() {
        //Como conectar no banco.
        //java -classpath hsqldb.jar org.hsqldb.util.DatabaseManager
        //Standalone
        //jdbc:hsqldb:file:E:/Leoci/Projetos Intellij/local-controle-hsqldb-v2/database/db
        //jdbc:hsqldb:file:D:/ProjetosIntellij/local-controle-hsqldb-v2/database/db
        return "jdbc:hsqldb:file:database_v1/db";
    }

    public String createReferenciaTable() {
        return "create table if not exists referencia (id integer identity primary key, referencia varchar(100))";
    }
}
