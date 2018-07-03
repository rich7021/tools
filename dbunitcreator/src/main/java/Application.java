import java.io.*;
import java.net.URISyntaxException;

public class Application {
    private static String tableName;
    private static String[] columns;
    private static String[] values;

    public static void main(String[] args) throws IOException, URISyntaxException {
        ClassLoader classLoader = Application.class.getClassLoader();
        File file = new File(classLoader.getResource("new_3.txt").getFile());
        File result = new File("result.txt");
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
        String colun;
        StringBuilder sb = new StringBuilder("<");
        sb.append(tableName).append(" ");
        for (int i = 0; i < values.length; i++) {
            colun = (values[i].contains("\"")) ? "\'" : "\"";
            sb.append(columns[i]).append("=").append(colun).append(values[i]).append(colun)
                    .append(" ");
        }
        sb.append("/>\n");
        return sb.toString();
    }

}
