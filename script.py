import os

# Define el directorio base donde se encuentra el código fuente Java
# Asegúrate de que esta ruta sea correcta con respecto a donde ejecutas el script.
# Si ejecutas el script desde la raíz de tu proyecto Gradle, esta es la ruta correcta.
BASE_DIR = 'src/main/java/org/example'

# Define las entidades que tendrán operaciones CRUD completas (crear, leer, actualizar, eliminar)
CRUD_ENTITIES = ['User', 'Reporte']

# Define las entidades que tendrán solo operaciones de lectura (obtener todos, obtener por ID)
READ_ONLY_ENTITIES = ['Calle', 'Seccion', 'NivelUrgencia', 'TipoReporte', 'EstadoReporte', 'MensajeAdmin']

# Lista combinada de todas las entidades para iterar
ALL_ENTITIES = CRUD_ENTITIES + READ_ONLY_ENTITIES

def get_plural_name(entity_name):
    """
    Función auxiliar para obtener el nombre plural de la entidad.
    Se puede mejorar para reglas de pluralización más complejas.
    """
    if entity_name.endswith('e'):
        return entity_name + "s"
    elif entity_name.endswith('s'):
        return entity_name + "es"
    elif entity_name == "User": # Caso especial para User -> Users
        return "Users"
    elif entity_name == "Reporte": # Caso especial para Reporte -> Reportes
        return "Reportes"
    else:
        return entity_name + "s"

def get_api_path_base(entity_name):
    """
    Define la ruta base de la API para cada entidad.
    """
    if entity_name == "User":
        return "/users"
    elif entity_name == "Reporte":
        return "/reports"
    elif entity_name == "Calle":
        return "/calles"
    elif entity_name == "Seccion":
        return "/secciones"
    elif entity_name == "NivelUrgencia":
        return "/niveles-urgencia"
    elif entity_name == "TipoReporte":
        return "/tipos-reporte"
    elif entity_name == "EstadoReporte":
        return "/estados-reporte"
    elif entity_name == "MensajeAdmin":
        return "/admin-messages"
    else:
        return f"/{entity_name.lower()}s" # Fallback genérico

def get_id_setter_name(entity_name):
    """
    Devuelve el nombre correcto del método setter para el ID de la entidad,
    basado en la convención de nombres de los modelos.
    """
    if entity_name == "User":
        return "setIdUsuario"
    elif entity_name == "NivelUrgencia":
        return "setIdNivel"
    elif entity_name == "TipoReporte":
        return "setIdTipo"
    elif entity_name == "EstadoReporte":
        return "setIdEstado"
    elif entity_name == "MensajeAdmin":
        return "setIdMensaje"
    elif entity_name == "Calle":
        return "setIdCalle"
    elif entity_name == "Seccion":
        return "setIdSeccion"
    elif entity_name == "Reporte":
        return "setIdReporte"
    else:
        # Fallback genérico, aunque las entidades principales ya están cubiertas
        return f"setId{entity_name}"

def generate_service_code(entity_name, is_crud):
    """Genera el código Java para una clase de servicio."""
    lower_entity_name = entity_name[0].lower() + entity_name[1:] # Convierte a camelCase (ej. "User" -> "user")
    plural_entity_name = get_plural_name(entity_name)

    code = f"""// src/main/java/org/example/service/{entity_name}Service.java
package org.example.service;

import org.example.dao.{entity_name}Dao;
import org.example.model.{entity_name};
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los {plural_entity_name.lower()}.
 * Utiliza {entity_name}Dao para interactuar con la base de datos.
 */
public class {entity_name}Service {{
    private final {entity_name}Dao {lower_entity_name}Dao;

    public {entity_name}Service({entity_name}Dao {lower_entity_name}Dao) {{
        this.{lower_entity_name}Dao = {lower_entity_name}Dao;
    }}

"""
    if is_crud:
        code += f"""    public Optional<{entity_name}> create{entity_name}({entity_name} {lower_entity_name}) {{
        // Aquí podrías añadir validaciones de negocio antes de insertar
        return {lower_entity_name}Dao.insert({lower_entity_name});
    }}

"""
    code += f"""    public Optional<{entity_name}> get{entity_name}ById(int id) {{
        return {lower_entity_name}Dao.selectById(id);
    }}

    public List<{entity_name}> getAll{plural_entity_name}() {{
        return {lower_entity_name}Dao.selectAll();
    }}

"""
    if is_crud:
        code += f"""    public boolean update{entity_name}({entity_name} {lower_entity_name}) {{
        // Aquí podrías añadir validaciones de negocio antes de actualizar
        return {lower_entity_name}Dao.update({lower_entity_name});
    }}

    public boolean delete{entity_name}(int id) {{
        // Aquí podrías añadir lógica para verificar dependencias antes de eliminar
        return {lower_entity_name}Dao.delete(id);
    }}
"""
    code += "}\n"
    return code

def generate_controller_code(entity_name, is_crud):
    """Genera el código Java para una clase de controlador."""
    lower_entity_name = entity_name[0].lower() + entity_name[1:] # Convierte a camelCase
    plural_entity_name = get_plural_name(entity_name)
    api_path_base = get_api_path_base(entity_name)
    id_setter_name = get_id_setter_name(entity_name) # Obtener el nombre correcto del setter del ID

    code = f"""// src/main/java/org/example/controller/{entity_name}Controller.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.{entity_name};
import org.example.service.{entity_name}Service;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad '{entity_name}'.
 */
public class {entity_name}Controller {{
    private final {entity_name}Service {lower_entity_name}Service;

    public {entity_name}Controller({entity_name}Service {lower_entity_name}Service) {{
        this.{lower_entity_name}Service = {lower_entity_name}Service;
    }}

"""
    if is_crud:
        code += f"""    /**
     * POST {api_path_base} - Crea un nuevo {lower_entity_name}.
     * Espera un JSON en el cuerpo de la solicitud que se mapeará a un objeto {entity_name}.
     */
    public void create{entity_name}(Context ctx) {{
        try {{
            {entity_name} new{entity_name} = ctx.bodyAsClass({entity_name}.class);
            Optional<{entity_name}> created{entity_name} = {lower_entity_name}Service.create{entity_name}(new{entity_name});
            if (created{entity_name}.isPresent()) {{
                ctx.status(HttpStatus.CREATED).json(created{entity_name}.get()); // 201 Created
            }} else {{
                ctx.status(HttpStatus.BAD_REQUEST).result("No se pudo crear el {lower_entity_name}. Verifique los datos (ej. IDs de FK válidos)."); // 400 Bad Request
            }}
        }} catch (Exception e) {{
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al procesar la solicitud: " + e.getMessage()); // 400 Bad Request
            System.err.println("Error al crear {lower_entity_name}: " + e.getMessage());
        }}
    }}

"""
    code += f"""    /**
     * GET {api_path_base}/{{id}} - Obtiene un {lower_entity_name} por su ID.
     */
    public void get{entity_name}ById(Context ctx) {{
        try {{
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<{entity_name}> {lower_entity_name} = {lower_entity_name}Service.get{entity_name}ById(id);
            if ({lower_entity_name}.isPresent()) {{
                ctx.json({lower_entity_name}.get());
            }} else {{
                ctx.status(HttpStatus.NOT_FOUND).result("{entity_name} no encontrado."); // 404 Not Found
            }}
        }} catch (Exception e) {{
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de {lower_entity_name} inválido."); // 400 Bad Request
            System.err.println("Error al obtener {lower_entity_name} por ID: " + e.getMessage());
        }}
    }}

    /**
     * GET {api_path_base} - Obtiene todos los {plural_entity_name.lower()}.
     */
    public void getAll{plural_entity_name}(Context ctx) {{
        List<{entity_name}> {plural_entity_name.lower()} = {lower_entity_name}Service.getAll{plural_entity_name}();
        ctx.json({plural_entity_name.lower()});
    }}

"""
    if is_crud:
        code += f"""    /**
     * PUT {api_path_base}/{{id}} - Actualiza un {lower_entity_name} existente.
     * Espera un JSON en el cuerpo de la solicitud que se mapeará a un objeto {entity_name}.
     */
    public void update{entity_name}(Context ctx) {{
        try {{
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            {entity_name} {lower_entity_name}ToUpdate = ctx.bodyAsClass({entity_name}.class);
            {lower_entity_name}ToUpdate.{id_setter_name}(id); // Aseguramos que el ID del objeto coincida con el de la URL

            if ({lower_entity_name}Service.update{entity_name}({lower_entity_name}ToUpdate)) {{
                ctx.status(HttpStatus.OK).result("{entity_name} actualizado exitosamente."); // 200 OK
            }} else {{
                ctx.status(HttpStatus.NOT_FOUND).result("{entity_name} no encontrado o no se pudo actualizar."); // 404 Not Found o 304 Not Modified
            }}
        }} catch (Exception e) {{
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al procesar la solicitud de actualización: " + e.getMessage()); // 400 Bad Request
            System.err.println("Error al actualizar {lower_entity_name}: " + e.getMessage());
        }}
    }}

    /**
     * DELETE {api_path_base}/{{id}} - Elimina un {lower_entity_name} por su ID.
     */
    public void delete{entity_name}(Context ctx) {{
        try {{
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            if ({lower_entity_name}Service.delete{entity_name}(id)) {{
                ctx.status(HttpStatus.NO_CONTENT); // 204 No Content (éxito sin cuerpo de respuesta)
            }} else {{
                ctx.status(HttpStatus.NOT_FOUND).result("{entity_name} no encontrado o no se pudo eliminar."); // 404 Not Found
            }}
        }} catch (Exception e) {{
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de {lower_entity_name} inválido."); // 400 Bad Request
            System.err.println("Error al eliminar {lower_entity_name}: " + e.getMessage());
        }}
    }}
"""
    code += "}\n"
    return code

def create_file(path, content):
    """Crea un archivo con el contenido dado, asegurando que los directorios existan."""
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"Archivo generado: {path}")

# --- Lógica principal del script ---
def main():
    service_dir = os.path.join(BASE_DIR, 'service')
    controller_dir = os.path.join(BASE_DIR, 'controller')

    # Asegura que los directorios de destino existan
    os.makedirs(service_dir, exist_ok=True)
    os.makedirs(controller_dir, exist_ok=True)

    # Genera archivos para cada entidad
    for entity in ALL_ENTITIES:
        is_crud = entity in CRUD_ENTITIES

        # Generar Servicio
        service_file_path = os.path.join(service_dir, f'{entity}Service.java')
        service_code = generate_service_code(entity, is_crud)
        create_file(service_file_path, service_code)

        # Generar Controlador
        controller_file_path = os.path.join(controller_dir, f'{entity}Controller.java')
        controller_code = generate_controller_code(entity, is_crud)
        create_file(controller_file_path, controller_code)

    print("\nGeneración de archivos completada.")
    print("---------------------------------------------------------------------------------------------------")
    print("¡IMPORTANTE! Pasos siguientes:")
    print("1. Revisa los archivos generados: Aunque el script genera una base, es posible que necesites ajustar")
    print("   los nombres de los campos en los modelos (POJOs) y en los métodos DAO/Service/Controller")
    print("   para que coincidan exactamente con tu esquema de base de datos (ej. 'idUsuario' vs 'id_usuario').")
    print("   Asegúrate de que los setters en los modelos tengan el nombre correcto para Jackson (ej. `setIdUsuario`).")
    print("2. Actualiza tu Main.java: Deberás inicializar los nuevos DAOs, Servicios y Controladores,")
    print("   y luego añadir las rutas correspondientes en el método `main` de tu clase `Main`.")
    print("   El script no modifica Main.java automáticamente para evitar sobreescribir tus cambios.")
    print("3. Revisa las dependencias: Asegúrate de tener 'mysql-connector-java' y 'jackson-databind' en tu build.gradle.")
    print("4. Configura DatabaseConfig.java: Asegúrate de que los detalles de conexión a tu base de datos sean correctos.")
    print("---------------------------------------------------------------------------------------------------")

if __name__ == "__main__":
    main()
