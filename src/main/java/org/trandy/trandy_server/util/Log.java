package org.trandy.trandy_server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.logging.Logger;

@Slf4j
public class Log {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final ObjectWriter prettyPrinter = objectMapper.writerWithDefaultPrettyPrinter();

    private static final Logger logger = Logger.getLogger(Log.class.getName());

    private Log() {}

    // 원하는 로그 스타일이 있을 때 마다 추가하여 사용한다.
    public static <T> void objectInfo(T object) {
        try {
            String jsonString = prettyPrinter.writeValueAsString(object);
            log.info("=============================================================");
            log.info("{} as json\n{}", object.getClass().getSimpleName(), jsonString);
            log.info("=============================================================");
        } catch (JsonProcessingException e) {
            log.error("Error converting object to json", e);
        }
    }

    public static void varLog(String key, String value){
        try {
            log.info("=============================================================");
            log.info("{} : {}", key, value);
            log.info("=============================================================");
        } catch (Exception e) {
            log.error("Log 파싱 실패", e);
        }
    }

    public static void createBackupLog(File logDir){
        // /home/user/AI_TASK/001.EMI/result/log/
        String pattern = ".*_backup\\.log";

        try{
            File[] files = logDir.listFiles();

            // for문 먼저 돌려서 로그파일, 백업로그 파일 명 가져올 것.
            if (files != null) {
                // 기존 백업파일 삭제
                for (File file : files) {
                    if (file.isFile() && file.getName().matches(pattern)) {
                        if (file.delete()) {
                            log.info("삭제된 파일 : " + file.getAbsolutePath());
                        } else {
                            log.info("삭제 실패한 파일 : " + file.getAbsolutePath());
                        }
                    }
                }
            }

            if (files != null) {
                // 기존 로그파일 백업파일로 변경
                for(File file : files){
                    if(file.exists()){
                        String oldFileName = file.getName();

                        int dotIndex = oldFileName.lastIndexOf('.');
                        String fileNameWithoutExtension = oldFileName.substring(0, dotIndex);
                        String fileExtension = oldFileName.substring(dotIndex);

                        String newFileName = String.format("%s/%s"
                                ,logDir.getAbsolutePath()
                                ,fileNameWithoutExtension + "_backup" + fileExtension);

                        // 새로운 파일명 생성
                        File newFile = new File(newFileName);

                        if(file.renameTo(newFile)){
                            log.info("변경된 파일명 : " + file.getName());
                        }else {
                            log.info("변경 실패한 파일 : " + file.getAbsolutePath());
                        }
                    } else{
                        log.info("변경할 로그파일이 존재하지 않습니다.");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
