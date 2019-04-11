package utils;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import org.openqa.selenium.WebElement;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ReusableFunctions extends TestBase {


    /**
     * Get the length of numbers
     * @param length
     * @return
     */
    public String getNumbers(int length){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timeNow = String.valueOf(timestamp.getTime());
        String str = timeNow.substring(timeNow.length()-length);
        return str;
    }


    /**
     * Locate a element by text from a list
     * @param elementList
     * @param specificText
     * @return return the index of element
     * @author He, ying
     */
    public int locateElementfromListByText(List<WebElement> elementList, String specificText){
        for(int i=0; i<elementList.size(); i++){
            if(elementList.get(i).getText().trim().equals(specificText.trim())){
                return i;
            }
        }
        return -1;
    }


    /**
     * When we open a new tab, we need to judge the current window for locating elements
     * @author He, ying
     */
    public  void judgeCurrentWindow(){
        String currentWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> it = handles.iterator();

        while(it.hasNext()){
            String handle = it.next();
            if(currentWindow.equals(handle))
                continue;
            driver.switchTo().window(handle);
        }
    }





}
