package ec.gob.inec.conexion.geojson;

import org.postgis.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ec.gob.inec.conexion.geojson.deserializers.GeometryDeserializer;
import ec.gob.inec.conexion.geojson.serializers.GeometrySerializer;

/**
 * Module for loading serializers/deserializers.
 * 
 * @author Maycon Viana Bordin mayconbordin@gmail.com
 */
public class PostGISModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public PostGISModule() {
        super("PostGISModule");

        addSerializer(Geometry.class, new GeometrySerializer());
        addDeserializer(Geometry.class, new GeometryDeserializer());
    }
}
