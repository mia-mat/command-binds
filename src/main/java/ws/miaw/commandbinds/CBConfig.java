package ws.miaw.commandbinds;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CBConfig implements Serializable {

    private static final long serialVersionUID = 2L;

    private static final File SAVE_FILE = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "\\miaw\\commandbinds");

    private Map<Set<Integer>, String> binds;

    // whether we consider keys apart from the bind in determining whether to execute it
    private boolean bindExclusivity;

    private CBConfig() {
        this.binds = new HashMap<>();
        bindExclusivity = true;

        save();
    }

    public boolean areBindingsExclusive() {
        return bindExclusivity;
    }

    public void setBindExclusivity(boolean newValue) {
        this.bindExclusivity = newValue;
        save();
    }

    public @Nullable String getBind(Set<Integer> keys) {
        return binds.get(keys);
    }

    /**
     * Considers bind exclusivity
     */
    public List<String> getBindsForHandling(final Set<Integer> keys) {
        if(bindExclusivity) {
            if(getBind(keys) == null) return new ArrayList<>();
            return Collections.singletonList(getBind(keys));
        }

        // if a key of our binds map is a subset of `keys`, include it.
        return binds.entrySet().stream()
                .filter(entry -> keys.containsAll(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public boolean hasBind(Set<Integer> keys) {
        return binds.containsKey(keys);
    }

    public boolean hasBind(String command) {
        for(Set<Integer> key : binds.keySet()) {
            if(binds.get(key).equalsIgnoreCase(command)) {
                return true;
            }
        }

        return false;
    }

    public void addBind(Set<Integer> keys, String command) {
        binds.put(keys, command);
        save();
    }

    public void removeBind(Set<Integer> keys) {
        binds.remove(keys);
        save();
    }

    /**
     * @return the number of binds removed
     */
    public int removeBind(String command) {
        int removed = 0;
        HashMap<Set<Integer>, String> newBinds = new HashMap<Set<Integer>, String>(binds);

        for(Set<Integer> key : binds.keySet()) {
            if(binds.get(key).equalsIgnoreCase(command)) {
                newBinds.remove(key);
                removed++;
            }
        }
        binds = newBinds;
        save();
        return removed;
    }

    public Map<Set<Integer>, String> getBindMap() {
        return Collections.unmodifiableMap(binds);
    }

    protected void save() {
        if(!SAVE_FILE.getParentFile().exists()) SAVE_FILE.getParentFile().mkdirs();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE));
            oos.writeObject(this);
            oos.close(); // flushes
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected static CBConfig create() {
        if (SAVE_FILE.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(SAVE_FILE));
                return (CBConfig) ois.readObject();
            } catch (Exception e) {
                if(ois != null) {
                    try {
                        ois.close(); // make sure stream is closed before attempting to rename

                        // rename old file to save and create a new one; the old one is either of an old version or corrupted.
                        CommandBindsMod.getLogger().warn("Could not load binds configuration, attempting to create a new one.");
                        e.printStackTrace();

                        File oldBinds = new File(SAVE_FILE.getParent(), SAVE_FILE.getName() + "-old");

                        if(oldBinds.exists()) oldBinds.delete();

                        if(SAVE_FILE.renameTo(oldBinds)) {
                            return create();
                        }

                        CommandBindsMod.getLogger().error("Failed to create a new configuration.");
                        return null; // ?
                    } catch (IOException ioException) {
                        // this should never be called
                        ioException.printStackTrace();
                    }
                }

            }
        }

        return new CBConfig();
    }
}
