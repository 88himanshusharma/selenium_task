package TestCase;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class ReadData {
    @Test(dataProvider = "data")
    public void Test(Map<Object, Object> map) {
        System.out.println("USERNAME=" + map.get("UserName"));
        System.out.println("PASSWORD=" + map.get("Password"));
        System.out.println("DOB=" +map.get("DoB"));
        System.out.println("Address=" +map.get("Address"));
        System.out.println(" ");
    }
    @DataProvider(name = "data")
    public Object[][] dataSupplier() {
        FileInputStream fis = null;
        XSSFSheet ws;
        XSSFWorkbook wb = null;
        String filePath = "C://Users//abc//Desktop//TestData.xlsx";
        File xls = new File(filePath);
        try {
            fis = new FileInputStream(xls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb = new XSSFWorkbook(fis);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ws = wb.getSheet("Sheet1");
        int rowcount = ws.getLastRowNum();
        int colcount = ws.getRow(0).getLastCellNum();
        Object[][] data = new Object[rowcount][1];
        for (int r = 0; r < rowcount; r++) {
            HashMap<Object, Object> h = new HashMap<>();
            for (int c = 0; c < colcount; c++) {
                h.put(ws.getRow(0).getCell(c).toString(), ws.getRow(r + 1).getCell(c).toString());
            }
            data[r][0] = h;
        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
