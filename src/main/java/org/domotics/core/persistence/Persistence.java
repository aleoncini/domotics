package org.domotics.core.persistence;

import org.domotics.core.model.Controller;
import org.domotics.core.model.Terminal;
import org.domotics.core.model.Zone;

import java.util.Collection;

public interface Persistence {
    public String version();

    // Controller section
    public Controller saveController(Controller controller);
    public boolean deleteController(String uuid);
    public Controller getController(String uuid);
    public Collection<Controller> getControllers();

    // Terminal section
    public Terminal saveTerminal(Terminal terminal);
    public boolean deleteTerminal(String uuid);
    public Terminal getTerminal(String uuid);
    public Collection<Terminal> getTerminals();

    // zone section
    public Zone saveZone(Zone zone);
    public boolean deleteZone(String uuid);
    public Zone getZone(String uuid);
    public Collection<Zone> getZones();
    public boolean associateTerminal(String uuid, String t_uuid);
    public boolean removeAssociation(String uuid, String t_uuid);
    public Collection<Terminal> getAssociatedTerminals(String uuid);

}
