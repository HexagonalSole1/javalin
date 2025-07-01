package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.repositories.IUsuarioRepository;
import org.devquality.trukea.persistance.repositories.impl.UsuarioRepositoryImpl;
import org.devquality.trukea.services.IUserServices;
import org.devquality.trukea.services.impl.UserServiceImpl;
import org.devquality.trukea.web.controllers.UsuarioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Routes {

    private static final Logger logger = LoggerFactory.getLogger(Routes.class);
    private final DatabaseConfig databaseConfig;

    public Routes(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

   public void routes(Javalin app) {
       IUsuarioRepository usuarioRepository = new UsuarioRepositoryImpl(databaseConfig);
       IUserServices userServices = new UserServiceImpl(usuarioRepository);
       UsuarioController controller = new UsuarioController(userServices);

       UsuariosRoutes usuariosRoutes = new UsuariosRoutes(controller);
       usuariosRoutes.configure(app);
   }

}
