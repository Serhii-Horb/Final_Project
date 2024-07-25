package com.example.final_project.aspect;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtils {

    /**
     * Generates a formatted string for logging the result of a DAO operation.
     * This method provides information on the size of the result if it's a list and
     * includes the result itself if the log level is set to DEBUG or if the result is not a list.
     *
     * @param log    the logger used to check the log level
     * @param result the result object to be logged
     * @return a formatted string containing information about the result
     */
    public static String getDaoResultLogInfo(final Logger log, final Object result) {
        StringBuilder resultInfo = new StringBuilder();

        // Check if the result is a List and append its size to the resultInfo
        if (result instanceof List) {
            resultInfo.append("RESULT_SIZE=").append(((List<?>) result).size());
        }

        // Include the result in the log if the DEBUG level is enabled or if the result is not a List
        if (log.isDebugEnabled() || !(result instanceof List)) {
            if (!resultInfo.isEmpty()) {
                resultInfo.append(" ");
            }
            resultInfo.append(result);
        }

        return resultInfo.toString();
    }
}