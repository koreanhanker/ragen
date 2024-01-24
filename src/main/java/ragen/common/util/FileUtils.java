package ragen.common.util;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtils {
    static final String[] PERMISSION_FILE_MIME_TYPE = {
            "image/gif"
            , "image/jpeg"
            , "image/png"
            , "image/bmp"
            , "application/pdf"
            , "video/mp4","text/plain"
            , "text/csv"
            , "application/x-tika-msoffice"
            , "application/vnd.ms-excel"
            , "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            , "application/vnd.openxmlformats-officedocument.presentationml.slide"
            , "application/vnd.openxmlformats-officedocument.presentationml.slideshow"
            , "application/vnd.openxmlformats-officedocument.presentationml.template"
            , "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            , "application/vnd.openxmlformats-officedocument.spreadsheetml.template"
            , "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            , "application/vnd.openxmlformats-officedocument.wordprocessingml.template"
            , "application/vnd.ms-powerpoint"
            , "application/vnd.ms-powerpoint.addin.macroenabled.12"
            , "application/vnd.ms-powerpoint.slide.macroenabled.12"
            , "application/vnd.ms-powerpoint.presentation.macroenabled.12"
            , "application/vnd.ms-powerpoint.slideshow.macroenabled.12"
            , "application/vnd.ms-powerpoint.template.macroenabled.12"
            , "application/msword"
            , "application/x-tika-ooxml"
    };

    /**
     * Is permision file mime type boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isPermisionFileMimeType(MultipartFile file){

        if(file == null || file.isEmpty()){
            return true;
        }

        boolean isPermisionFileMimeType = false;

        try {

            byte[] fileBytes = file.getBytes();
            isPermisionFileMimeType = isPermisionFileMimeType(fileBytes);
            if (!isPermisionFileMimeType) {
                isPermisionFileMimeType = false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return isPermisionFileMimeType;
    }

    /**
     * Is permision file mime type boolean.
     *
     * @param files the files
     * @return the boolean
     */
    public static boolean isPermisionFileMimeType(List<MultipartFile> files){

        if(files == null || files.isEmpty()){
            return true;
        }

        boolean isPermisionFileMimeType = false;

        try {
            for (MultipartFile file : files) {
                byte[] fileBytes = file.getBytes();
                isPermisionFileMimeType = isPermisionFileMimeType(fileBytes);
                if (!isPermisionFileMimeType) {
                    isPermisionFileMimeType = false;
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return isPermisionFileMimeType;
    }

    /**
     * Is permision file mime type boolean.
     *
     * @param fileBytes the file bytes
     * @return the boolean
     */
    public static boolean isPermisionFileMimeType(byte[] fileBytes){

        if(fileBytes.length <= 0){
            return true;
        }

        boolean isPermisionFileMimeType = false;

        try {

            String mimeType = new Tika().detect(fileBytes);
            for (int i = 0; i < PERMISSION_FILE_MIME_TYPE.length; i++) {
                if (PERMISSION_FILE_MIME_TYPE[i].equals(mimeType)) {
                    isPermisionFileMimeType = true;
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return isPermisionFileMimeType;
    }
}
