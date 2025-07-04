package org.devquality.trukea.routes;

import io.javalin.Javalin;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.repositories.*;
import org.devquality.trukea.persistance.repositories.impl.*;
import org.devquality.trukea.services.*;
import org.devquality.trukea.services.impl.*;
import org.devquality.trukea.web.controllers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Routes {

    private static final Logger logger = LoggerFactory.getLogger(Routes.class);
    private final DatabaseConfig databaseConfig;

    public Routes(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public void routes(Javalin app) {
        // Usuarios
        IUsuarioRepository usuarioRepository = new UsuarioRepositoryImpl(databaseConfig);
        IUserServices userServices = new UserServiceImpl(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(userServices);
        UsuariosRoutes usuariosRoutes = new UsuariosRoutes(usuarioController);
        usuariosRoutes.configure(app);

        // Categorias
        ICategoriaRepository categoriaRepository = new CategoriaRepositoryImpl(databaseConfig);
        ICategoriaService categoriaService = new CategoriaServiceImpl(categoriaRepository);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        CategoriasRoutes categoriasRoutes = new CategoriasRoutes(categoriaController);
        categoriasRoutes.configure(app);

        // Productos
        IProductoRepository productoRepository = new ProductoRepositoryImpl(databaseConfig);
        IProductoService productoService = new ProductoServiceImpl(productoRepository);
        ProductoController productoController = new ProductoController(productoService);
        ProductosRoutes productosRoutes = new ProductosRoutes(productoController);
        productosRoutes.configure(app);

        // Trueques
        ITruequeRepository truequeRepository = new TruequeRepositoryImpl(databaseConfig);
        ITruequeService truequeService = new TruequeServiceImpl(truequeRepository);
        TruequeController truequeController = new TruequeController(truequeService);
        TruequesRoutes truequesRoutes = new TruequesRoutes(truequeController);
        truequesRoutes.configure(app);

        // Calificaciones
        ICalificacionesRepository calificacionesRepository = new CalificacionesRepositoryImpl(databaseConfig);
        ICalificacionesService calificacionesService = new CalificacionesServiceImpl(calificacionesRepository);
        CalificacionesController calificacionesController = new CalificacionesController(calificacionesService);
        CalificacionesRoutes calificacionesRoutes = new CalificacionesRoutes(calificacionesController);
        calificacionesRoutes.configure(app);
    }
}