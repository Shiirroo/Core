package de.shiro.utlits;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.shiro.api.blocks.WorldPos;
import de.shiro.api.types.Visibility;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utlits {
    public static String numberToFormat(Integer i) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);
        return decimalFormat.format(i);
    }

    public static String numberToFormat(Double d) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##", decimalFormatSymbols);
        return decimalFormat.format(d);
    }

    public static ObjectMapper mapper = new ObjectMapper();

    public static JsonNode readJsonFile(File file) {
        try {
            return mapper.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkFolderIsExistsOrCreate(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static Boolean checkVisibilityState(WorldPos pos, UUID folderUUID, UUID userUUID ){
        return pos.getVisibility() != Visibility.PRIVATE || folderUUID.equals(userUUID);
    }



}
