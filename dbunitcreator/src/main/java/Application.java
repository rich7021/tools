import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class Application {
    private static String tableName;
    private static String[] columns;
    private static String[] values;

    public static void main(String[] args) throws IOException, URISyntaxException {
        ClassLoader classLoader = Application.class.getClassLoader();
        File file = new File(classLoader.getResource("raw.txt").getFile());
        File result = new File(classLoader.getResource("result.txt").getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String resultString = "";

        int lnNo = 0;
        String line;
        while ((line = br.readLine()) != null) {
            if (lnNo == 0) {
                getTableName(line);
                lnNo++;
                continue;
            }

            String[] values = line.split("\t");
            if (lnNo == 1) {
                getColumns(values);
                lnNo++;
                continue;
            }
            resultString += getResult(values);

        }
        PrintWriter pr = new PrintWriter(result);
        pr.write(resultString);
        pr.close();
    }

    private static void getTableName(String line) {
        tableName = line.trim().toUpperCase();
    }

    private static void getColumns(String[] values) {
        columns = new String[values.length];
        System.arraycopy(values, 0, columns, 0, values.length);
    }

    private static String getResult(String[] values) {
        String comma;
        StringBuilder sb = new StringBuilder("<");
        sb.append(tableName).append(" ");
        for (int i = 0; i < values.length; i++) {
            comma = (values[i].contains("\"")) ? "\'" : "\"";
            LocalDate ld;
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                        .appendPattern("dd-MMM-yy").toFormatter();
                ld = LocalDate.parse(values[i], formatter);
                values[i] = ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException ex) {

            }
            LocalDateTime ldt;
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                        .appendPattern("dd-MMM-yy hh.mm.ss.SSS000000 a").toFormatter();
                ldt = LocalDateTime.parse(values[i], formatter);
                values[i] = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"));
            } catch (DateTimeParseException ex) {

            }

            sb.append(columns[i]).append("=").append(comma).append(values[i]).append(comma)
                    .append(" ");
        }
        sb.append("/>\n");
        return sb.toString();
    }

}
