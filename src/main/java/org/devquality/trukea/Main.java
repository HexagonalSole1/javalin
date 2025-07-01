package org.devquality.trukea;

import io.javalin.Javalin;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.routes.Routes;

public class Main {

    public static void main(String[] args) {

        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

        Javalin app = Javalin.create();

        Routes routes = new Routes(databaseConfig);
        routes.routes(app);

        int port = 8082;
        app.start(port);
    }
}
