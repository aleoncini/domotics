package org.domotics.core.persistence.mongodb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.domotics.core.model.CloudSetup;
import org.domotics.core.model.Controller;
import org.domotics.core.model.Terminal;
import org.domotics.core.model.Zone;
import org.domotics.core.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Singleton
@Named("mongodbPersistence")
public class MongodbPersistence implements Persistence {
    private Logger logger = LoggerFactory.getLogger(getClass());
    MongoDatabase db;

    public MongodbPersistence(){
        logger.info("==========> [MongodbPersistence] new instance.");
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        db = mongoClient.getDatabase( "domotics" );
        logger.info("==========> [MongodbPersistence] connected to database: " + db.getName());
        this.init();
        logger.info("==========> [MongodbPersistence] init complete.");
    }

    private void init(){
        Collection<Controller> controllers = getControllers();
        if (controllers.isEmpty()){
            logger.info("==========> [MongodbPersistence] Master Controller not found, initialising DB.");
            String ipAddress = "localhost";
            try {
                InetAddress ip = InetAddress.getLocalHost();
                ipAddress = ip.getCanonicalHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            Controller masterController = new Controller().setAddress(ipAddress).setDescription("The Master Controller");
            saveController(masterController);
        }
    }

    public String version() {
        return "MongoDB persistence implementation - ver 1.0-SNAPSHOT";
    }

    @Override
    public Controller saveController(Controller controller) {
        Document document = new Document()
                .append("uuid", controller.getUuid())
                .append("description", controller.getDescription())
                .append("ipAddress", controller.getIpAddress())
                .append("type", controller.getType());
        MongoCollection<Document> collection = db.getCollection("controllers");
        collection.insertOne(document);
        return controller;
    }

    @Override
    public boolean deleteController(String uuid) {
        MongoCollection<Document> collection = db.getCollection("controllers");
        DeleteResult result = collection.deleteOne(eq("uuid", uuid));
        return result.wasAcknowledged();
    }

    @Override
    public Controller getController(String uuid) {
        Controller controller = new Controller();
        MongoCollection<Document> registeredControllers = db.getCollection("controllers");

        registeredControllers.find().forEach((Block<Document>) document -> {
            if ((document.get("uuid")).equals(uuid)){
                controller.setUUID(uuid)
                    .setDescription((String) document.get("description"))
                    .setAddress((String) document.get("ipAddress"))
                    .setType((String) document.get("type"));
            }
        });
        return controller;
    }

    @Override
    public Collection<Controller> getControllers() {
        Collection<Controller> controllers = new ArrayList<Controller>();
        MongoCollection<Document> registeredControllers = db.getCollection("controllers");
        registeredControllers.find().forEach((Block<Document>) document -> {
            Controller controller = new Controller();
            controller.setUUID((String) document.get("uuid"))
                    .setDescription((String) document.get("description"))
                    .setAddress((String) document.get("ipAddress"))
                    .setType((String) document.get("type"));
            controllers.add(controller);
        });
        return controllers;
    }

    @Override
    public Terminal saveTerminal(Terminal terminal) {
        Document document = new Document()
                .append("uuid", terminal.getUuid())
                .append("controllerUuid", terminal.getControllerUuid())
                .append("description", terminal.getDescription())
                .append("pin", terminal.getPin())
                .append("type", terminal.getType());
        MongoCollection<Document> collection = db.getCollection("terminals");
        collection.insertOne(document);
        return terminal;
    }

    @Override
    public boolean deleteTerminal(String uuid) {
        MongoCollection<Document> collection = db.getCollection("terminals");
        DeleteResult result = collection.deleteOne(eq("uuid", uuid));
        if (!result.wasAcknowledged()){
            return false;
        }
        if (!removeAllAssociation(uuid)){
            return false;
        }
        return true;
    }

    @Override
    public Terminal getTerminal(String uuid) {
        Terminal terminal = new Terminal();
        MongoCollection<Document> collection = db.getCollection("terminals");

        Document document = collection.find(eq("uuid", uuid)).first();
        if (document != null){
            terminal.setUuid(uuid)
                    .setControllerUuid(document.getString("controllerUuid"))
                    .setDescription(document.getString("description"))
                    .setPin(document.getInteger("pin"))
                    .setType(document.getInteger("type"));
        }
        return terminal;
    }

    @Override
    public Collection<Terminal> getTerminals() {
        Collection<Terminal> terminals = new ArrayList<Terminal>();
        MongoCollection<Document> collection = db.getCollection("terminals");
        collection.find().forEach((Block<Document>) document -> {
            Terminal terminal = new Terminal();
            terminal.setUuid(document.getString("uuid"))
                    .setControllerUuid(document.getString("controllerUuid"))
                    .setDescription(document.getString("description"))
                    .setPin(document.getInteger("pin"))
                    .setType(document.getInteger("type"));
            terminals.add(terminal);
        });
        return terminals;
    }

    @Override
    public Zone saveZone(Zone zone) {
        Document document = new Document()
                .append("uuid", zone.getUuid())
                .append("description", zone.getDescription())
                .append("terminalUuids", zone.getTerminalUuids());
        MongoCollection<Document> collection = db.getCollection("zones");
        collection.insertOne(document);
        return zone;
    }

    @Override
    public boolean deleteZone(String uuid) {
        MongoCollection<Document> collection = db.getCollection("zones");
        DeleteResult result = collection.deleteOne(eq("uuid", uuid));
        return result.wasAcknowledged();
    }

    @Override
    public Zone getZone(String uuid) {
        Zone zone = new Zone();
        MongoCollection<Document> collection = db.getCollection("zones");

        Document document = collection.find(eq("uuid", uuid)).first();
        if (document != null){
            zone.setUUID(uuid)
                .setDescription(document.getString("description"))
                .setTerminalUuids((List<String>) document.get("terminalUuids"));
        }
        return zone;
    }

    @Override
    public Collection<Zone> getZones() {
        Collection<Zone> zones = new ArrayList<Zone>();
        MongoCollection<Document> collection = db.getCollection("zones");
        collection.find().forEach((Block<Document>) document -> {
            Zone zone = new Zone();
            zone.setUUID(document.getString("uuid"))
                .setDescription(document.getString("description"))
                .setTerminalUuids((List<String>) document.get("terminalUuids"));
            zones.add(zone);
        });
        return zones;
    }

    @Override
    public boolean associateTerminal(String uuid, String t_uuid) {
        try {
            MongoCollection<Document> collection = db.getCollection("zones");
            collection.updateOne(eq("uuid", uuid), new Document("$push", new Document("terminalUuids", t_uuid)));
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAssociation(String uuid, String t_uuid) {
        try {
            MongoCollection<Document> collection = db.getCollection("zones");
            collection.updateOne(eq("uuid", uuid), new Document("$pull", new Document("terminalUuids", t_uuid)));
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAllAssociation(String terminal_uuid) {
        Collection<Zone> zones = getZones();
        for (Zone zone : zones){
            if (!removeAssociation(zone.getUuid(),terminal_uuid)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Collection<Terminal> getAssociatedTerminals(String uuid) {
        ArrayList<Terminal> terminals = new ArrayList<Terminal>();
        Zone zone = this.getZone(uuid);
        List<String> associatedTerminalUuids = zone.getTerminalUuids();
        for(String t_uuid : associatedTerminalUuids){
            terminals.add(this.getTerminal(t_uuid));
        }
        return terminals;
    }

    @Override
    public CloudSetup getCloudSetup() {
        CloudSetup setup = new CloudSetup();
        MongoCollection<Document> collection = db.getCollection("setup");
        Document document = collection.find().first();
        if (document != null){
            setup.setUuid(document.getString("uuid"))
                    .setToken(document.getString("token"));
        }
        return setup;
    }

    @Override
    public CloudSetup saveCloudSetup(CloudSetup setup) {
        Document document = new Document()
                .append("uuid", setup.getUuid())
                .append("token", setup.getToken());
        MongoCollection<Document> collection = db.getCollection("setup");
        collection.insertOne(document);
        return setup;
    }

}