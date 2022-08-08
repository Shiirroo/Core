package de.shiro.actionregister.pos;

import de.shiro.Core;
import de.shiro.api.blocks.WorldPos;
import de.shiro.api.types.Visibility;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Log;
import de.shiro.utlits.Utlits;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static de.shiro.utlits.Utlits.checkFolderIsExistsOrCreate;
import static de.shiro.utlits.Utlits.mapper;
import static java.util.Comparator.comparing;

public class PosManager {


    private final UUID sessionID;
    @Getter
    public final File SaveFolderAsFile;

    @Getter
    public final File playerFolderFile;


    public PosManager(ISession iSession) {
        String savesFolder = "plugins//" + Core.getInstance().getName() + "//pos";
        String playerFolder = savesFolder + "//" + iSession.getExecutorID().toString();
        checkFolderIsExistsOrCreate(savesFolder);
        checkFolderIsExistsOrCreate(playerFolder);
        SaveFolderAsFile = new File(savesFolder);
        playerFolderFile = new File(playerFolder);
        this.sessionID = iSession.getExecutorID();
    }


    public List<WorldPos> getWorldPosList() {
        List<WorldPos> worldPosList = new ArrayList<>();
        File[] folders = SaveFolderAsFile.listFiles();
        if (folders == null) return worldPosList;
        for (File dir : folders) {
            if (dir.isDirectory()) {
                worldPosList.addAll(getUserPosList(dir));
            }

        }
        worldPosList.sort(comparing(WorldPos::getPosName));
        return worldPosList;
    }

    public static List<String> getPosSaveName(List<WorldPos> list){
        List<String> worldNameList = new ArrayList<>();
        for(WorldPos worldPos : list){
            worldNameList.add(worldPos.getPosName());
        }
        worldNameList.sort(String::compareTo);
        return worldNameList;
    }

    public HashMap<File, HashMap<File, WorldPos>> getWorldPosHashMap() {
        HashMap<File, HashMap<File, WorldPos>> worldPosHashMap = new HashMap<>();
        File[] folders = SaveFolderAsFile.listFiles();
        if (folders == null) return worldPosHashMap;
        for (File dir : folders) {
            if (dir.isDirectory()) {
                List<WorldPos> userPosList = getUserPosList(dir);
                if (!userPosList.isEmpty()) {
                    worldPosHashMap.put(dir, getUserPosHashMap(dir));
                }
            }

        }

        return worldPosHashMap;
    }


    private List<WorldPos> getUserPosList(File userDir) {
        List<WorldPos> worldPosList = new ArrayList<>();
        File[] files = userDir.listFiles();
        if (files == null) return worldPosList;
        for (File file : files) {
            if (!file.isDirectory()) {
                WorldPos worldPos = getWorldPos(file);
                if (worldPos != null) worldPosList.add(worldPos);
            }
        }
        worldPosList.sort(comparing(WorldPos::getPosName));
        return worldPosList;
    }

    private HashMap<File, WorldPos> getUserPosHashMap(File userDir) {
        HashMap<File, WorldPos> worldPosHashMap = new HashMap<>();
        File[] files = userDir.listFiles();
        if (files == null) return worldPosHashMap;
        for (File file : files) {
            if (!file.isDirectory()) {
                WorldPos worldPos = getWorldPos(file);
                if (worldPos != null) worldPosHashMap.put(file, worldPos);
            }
        }
        return worldPosHashMap;
    }


    private WorldPos getWorldPos(File file) {
        try {
            WorldPos pos = (mapper.readValue(file, WorldPos.class));
            if (Utlits.checkVisibilityState(pos, UUID.fromString(file.getParentFile().getName()), sessionID)) {
                return pos;
            }
        } catch (IOException e) {
            Log.error("Error while reading file: " + file.getName());
        }
        return null;
    }


    public List<WorldPos> getWorldPosListByInput(String input) {
        List<WorldPos> worldPosList = new ArrayList<>();
        for (WorldPos pos : getWorldPosList()) {
            if (input.equalsIgnoreCase("*") || pos.getPosName().toLowerCase().contains(input.toLowerCase())) {
                worldPosList.add(pos);
            }
        }
        return worldPosList;
    }

    public WorldPos getWorldPosByName(String posName) {
        return getWorldPosList().stream().filter(pos -> pos.getPosName().equalsIgnoreCase(posName) || posName.equalsIgnoreCase("*")).findFirst().orElse(null);
    }


    public Boolean isPosExists(String posName) {
        return getWorldPosList().stream().anyMatch(pos -> pos.getPosName().equalsIgnoreCase(posName) || posName.equalsIgnoreCase("*"));
    }

    public List<WorldPos> getWorldPosListByVisibility(Visibility visibility) {
        List<WorldPos> worldPosList = new ArrayList<>();
        for (WorldPos pos : getWorldPosList()) {
            if (pos.getVisibility().equals(visibility)) {
                worldPosList.add(pos);
            }
        }
        worldPosList.sort(comparing(WorldPos::getPosName));
        return worldPosList;
    }


    public Pair<File, WorldPos> hasManagePerm(String posName) {
        if(posName.equalsIgnoreCase("*")) return null;
        for (File dir : getWorldPosHashMap().keySet()) {
            HashMap<File, WorldPos> userPosHashMap = getWorldPosHashMap().get(dir);
            for (File file : userPosHashMap.keySet()) {
                WorldPos userPos = userPosHashMap.get(file);
                if (userPos.getPosName().equalsIgnoreCase(posName)) {
                    if (userPos.getVisibility().equals(Visibility.PUBLIC) || (userPos.getVisibility().equals(Visibility.PROTECTED) || userPos.getVisibility().equals(Visibility.PRIVATE)) && UUID.fromString(dir.getName()).equals(sessionID)) {
                        return Pair.of(file, userPos);
                    } else {
                        return Pair.of(null, null);
                    }
                }
            }
        }
        return null;
    }

    public List<String> hasManagePerm() {
        List<String> worldPosList = new ArrayList<>();
        for (File dir : getWorldPosHashMap().keySet()) {
            HashMap<File, WorldPos> userPosHashMap = getWorldPosHashMap().get(dir);
            for (File file : userPosHashMap.keySet()) {
                WorldPos userPos = userPosHashMap.get(file);
                if (userPos.getVisibility().equals(Visibility.PUBLIC) || (userPos.getVisibility().equals(Visibility.PROTECTED) || userPos.getVisibility().equals(Visibility.PRIVATE)) && UUID.fromString(dir.getName()).equals(sessionID)) {
                    worldPosList.add(userPos.getPosName());
                }
            }
        }
        worldPosList.sort(String::compareTo);
        return worldPosList;
    }

}
