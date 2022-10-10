package com.rea.app.common;

import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;
import java.util.Date;

public class FileUtils {

    public static final String[] IMAGE_ACCEPTED_EXTENSION = new String[] {"jpg", "jpeg", "png"};

    public static String getCustomFileName(String fileName, String initialFileName){
        if (!fileName.isEmpty()){
            return initialFileName + "-" + new Date().getTime() + "." + FilenameUtils.getExtension(fileName);
        }
        return null;
    }

    public static boolean checkFileExtensionFormat(String fileName, String[] fileExtensions){
        if (!fileName.isEmpty()){
            return !Arrays.asList(fileExtensions).contains(FilenameUtils.getExtension(fileName).toLowerCase());
        }
        return true;
    }
}
