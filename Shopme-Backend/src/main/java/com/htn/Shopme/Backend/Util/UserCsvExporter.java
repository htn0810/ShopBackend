package com.htn.Shopme.Backend.Util;

import com.htn.Shopme.Backend.DTO.UserDTO;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCsvExporter {

    public void export(List<UserDTO> users, HttpServletResponse response) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormatter.format(new Date());
        String fileName = "users_" + timestamp + ".csv";
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"UserId", "E-mail", "First Name", "Last Name", "Roles"};
        String[] fieldMapping = {"id", "email", "firstName", "lastName", "roles"};
        csvWriter.writeHeader(csvHeader);
        for (UserDTO user: users) {
            csvWriter.write(user, fieldMapping);
        }
        csvWriter.close();
    }
}
