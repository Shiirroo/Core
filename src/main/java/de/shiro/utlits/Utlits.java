package de.shiro.utlits;

import de.shiro.api.types.Visibility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Period;
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


    public static boolean checkFolderIsExistsOrCreate(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }


    public static String convertTimeSingle(long timeInSeconds) {
        long seconds = timeInSeconds % 60;
        long minutes = (timeInSeconds / 60) % 60;
        long hours = (timeInSeconds / 3600) % 24;
        long days = (timeInSeconds / (3600 * 24)) % 7;
        long weeks = timeInSeconds / (3600 * 24 * 7);
        long months = timeInSeconds / (3600 * 24 * 30);

        if (months > 0) {
            return months + (months <= 1 ? " month" : " months");
        } else if (weeks > 0) {
            return weeks + (weeks <= 1 ? " week" : " weeks");
        } else if (days > 0) {
            return days + (days <= 1 ? " day" : " days");
        } else if (hours > 0) {
            return hours + (hours <= 1 ? " hr" : " hrs");
        } else if (minutes > 0) {
            return minutes + (minutes <= 1 ? " min" : " mins");
        } else {
            return seconds + (" sec");
        }
    }

    public static String convertTime(long timeInSeconds) {
        long seconds = timeInSeconds % 60;
        long minutes = (timeInSeconds / 60) % 60;
        long hours = (timeInSeconds / 3600) % 24;
        long days = (timeInSeconds / (3600 * 24)) % 7;
        long weeks = timeInSeconds / (3600 * 24 * 7);
        long months = timeInSeconds / (3600 * 24 * 30);

        StringBuilder result = new StringBuilder();

        if (months > 0) {
            result.append(months).append(months == 1 ? " month " : " months ");
        }
        if (weeks > 0) {
            result.append(weeks).append(weeks == 1 ? " week " : " weeks ");
        }
        if (days > 0) {
            result.append(days).append(days == 1 ? " day " : " days ");
        }
        if (hours > 0) {
            result.append(hours).append(hours == 1 ? " hour " : " hours ");
        }
        if (minutes > 0) {
            result.append(minutes).append(minutes == 1 ? " minute " : " minutes ");
        }
        if (seconds > 0) {
            result.append(seconds).append(seconds == 1 ? " second " : " seconds ");
        }

        return result.toString().trim();
    }

    public static String byteArrayToHexString(byte[] data) {
        char[] toDigits =  {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return new String(out);
    }

    public static String calcSHA1String( File file ) throws IOException, NoSuchAlgorithmException {
        return bytesToHexString( calcSHA1( file ) );
    }

    public static byte[] calcSHA1( File file ) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream( file );
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        while (digestInputStream.read(bytes) > 0);

        byte[] resultByteArry = digest.digest();
        digestInputStream.close();
        return resultByteArry;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(value).toUpperCase());
        }
        return sb.toString();
    }

    public static long zeroLastNDigits(long value, long zeros) {
        long tenToTheN =  (long) Math.pow(10, zeros);
        return (value / tenToTheN) * tenToTheN;
    }

    public static long zeroLastNDigitsAndAddValue(long value, long zeros, long addedValue) {
        long tenToTheN =  (long) Math.pow(10, zeros);
        return ((value / tenToTheN) + addedValue) * tenToTheN;
    }
    private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d+)([wdhms])");


    public static long parseDuration(String duration) {
        long totalDurationInMillis = 0;
        Matcher matcher = DURATION_PATTERN.matcher(duration);
        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            char unit = matcher.group(2).charAt(0);
            switch (unit) {
                case 'w' -> totalDurationInMillis += value * 7 * 24 * 60 * 60 * 1000; // Convert weeks to milliseconds
                case 'd' -> totalDurationInMillis += value * 24 * 60 * 60 * 1000; // Convert days to milliseconds
                case 'h' -> totalDurationInMillis += value * 60 * 60 * 1000; // Convert hours to milliseconds
                case 'm' -> totalDurationInMillis += value * 60 * 1000; // Convert minutes to milliseconds
                case 's' -> totalDurationInMillis += value * 1000; // Convert seconds to milliseconds
            }
        }

        return totalDurationInMillis;
    }

    public static String formatDuration(long durationInMillis) {
        if(durationInMillis < 1000) return "0s";

        long weeks = durationInMillis / (7 * 24 * 60 * 60 * 1000);
        long days = (durationInMillis % (7 * 24 * 60 * 60 * 1000)) / (24 * 60 * 60 * 1000);
        long hours = (durationInMillis % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long minutes = (durationInMillis % (60 * 60 * 1000)) / (60 * 1000);
        long seconds = (durationInMillis % (60 * 1000)) / 1000;

        StringBuilder sb = new StringBuilder();
        if (weeks > 0) {
            sb.append(weeks).append("w ");
        }
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s");
        }


        return sb.toString().trim();
    }


    public static long removeLastNDigits(long x, long n) {
        return (long) (x / Math.pow(10, n));
    }

    public static String getFormatKey(String key){
        return key.replace("block.minecraft.", "minecraft:").replace("item.minecraft.", "minecraft:").toLowerCase();
    }

}
