package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class Functions extends TestBase {

    String screenshotPath = ".\\screenshot";

    /**
     * Determine if the element exists
     * @param element enter the location of element
     * @return true or false
     * @author He, ying
     */

    public boolean doesElementExist(WebElement element){
        TestBase.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try{
            element.isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

        return true;
    }

    /**
     * to capture the current screenshot
     * @param name
     * @param imgName customize the image name
     * @return return the image path
     */

    public void captureScreenshot(TakesScreenshot name, String imgName){
        File file = name.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(file, new File(screenshotPath+"\\" +imgName +".png"));
        }catch (IOException e){
            System.out.println("cannot save the screenshot");
        }
    }


    public static void deleteOldScreenshot(){
        File file = new File(".\\test-output\\errorScreenshot");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = new Date();
        Date fileDate;
        String fileName;
        int distanceDays = 3;

        File[] fileList = file.listFiles();

        if(fileList.length>0){
            for(File f : fileList){
                fileName = (f.getName().split("/."))[0];

                try{
                    fileDate = dateFormat.parse(fileName);
                    if(calculateNumberDay(fileDate,today)>distanceDays){
                        f.delete();
                    }
                } catch (ParseException e) {
                    System.out.println("Delete Failed");
                }
            }
        }
    }

    /**
     * Compare two images, after compared, those files will be removed
     * @param image1Name
     * @param image2Name
     * @return
     */

    public boolean compareTwoImages(String image1Name, String image2Name){
        try{
            File f1 = new File(screenshotPath + "\\" + image1Name + ".png");
            File f2 = new File(screenshotPath + "\\" + image2Name + ".png");
            if(f1.exists() && f2.exists()){

                BufferedImage imgA = ImageIO.read(f1);
                BufferedImage imgB = ImageIO.read(f2);

                if(imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()){
                    for(int x=0; x< imgA.getWidth();x++){
                        for(int y =0; y<imgA.getHeight();y++){
                            if(imgA.getRGB(x, y)!= imgB.getRGB(x,y)){
                                return false;
                            }
                        }

                    }
                }else {
                    return false;
                }
            }else{
                System.out.println("The file not exists!!! Please check the file name and the path");
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("the two images is not accessed");
        }
        return true;
    }

    /**
     * Delete al of files in a directory
     * @param directoryPath
     */

    public void deleteAllfileInDirectory(String directoryPath){
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        for(File file: files){
            file.delete();

            if(!file.delete()){
                System.out.println("Failed to delete " + file);
            }
        }
    }


    public static int calculateNumberDay(Date d1, Date d2){
        return abs((int)(d2.getTime()-d1.getTime()/(1000*60*60*24)));
    }


    /**
     * clear the old extent report, always store 4 reports in the folder.
     */
    public static void clearOldExtentReport(){
        File file = new File(".\\test-output");
        File[] files = file.listFiles();

        if(files.length>3){

            try{

            for (int i = 1; i < files.length-3; i++) {
                files[i].delete();
                i++;
            }

            }catch (Exception e){
                System.out.println("Failed to delete extentreports");
            }
        }
    }

}
