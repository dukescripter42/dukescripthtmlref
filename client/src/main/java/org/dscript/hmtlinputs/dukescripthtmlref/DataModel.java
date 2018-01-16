package org.dscript.hmtlinputs.dukescripthtmlref;

import java.io.Closeable;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import net.java.html.json.ComputedProperty;
import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;
import org.dscript.hmtlinputs.dukescripthtmlref.js.PlatformServices;
import net.java.html.json.ModelOperation;
import net.java.html.json.Models;
import net.java.html.json.OnPropertyChange;
import org.dscript.hmtlinputs.dukescripthtmlref.js.PlatformServices;
import org.dscript.hmtlinputs.dukescripthtmlref.js.TemplateRegistration;



@Model(className = "Data", targetId = "", properties = {
    @Property(name = "template", type = String.class),
    @Property(name = "contentA", type = String.class),
    @Property(name = "contentB", type = String.class), 
    @Property(name = "contentC", type = String.class),
    @Property(name = "errorMessage", type = String.class), 
    @Property(name = "errorField", type = String.class),
    @Property(name = "theaterReviews", type = TheaterReview.class, array = true),
    @Property(name = "editedTheaterReview", type = TheaterReview.class),
    @Property(name = "synesthesiaValues", type = String.class, array = true),
    @Property(name = "ratingValues", type = int.class, array = true),
    @Property(name = "directories", type = String.class, array = true),
    @Property(name = "directoryValues", type = String.class, array = true),
    @Property(name = "editedfiletitle", type= String.class),
    @Property(name = "editeduploadedfile", type= String.class),
    @Property(name = "uplfiles", type = UplFile.class, array=true),
    @Property(name = "currentMenu", type = int.class),
     @Property(name = "strActionWithDuration", type = String.class),
})

final class DataModel {

    private static Data ui;
    private static Closeable a;
    private static Closeable b;
    private static Closeable f;
    
    
    
    @Model(className="UplFile", properties = {
        @Property(name = "origFileName", type = String.class),
        @Property(name = "targetFileName", type = String.class),
        @Property(name = "userName4File", type = String.class),
    })
    
    public static class UplFileVMD{
        
    }
    
    
    
    @Function
    public static void uploadFile(Data model) {

        String theFile = showFileDialog("Add a file", ".txt .doc .xls .pdf .xml");

        if (null != theFile && theFile.length() > 0) {
            File file = new File(theFile);
            model.setEditeduploadedfile(file.getAbsolutePath());
            System.out.println("Your edited title is: " + model.getEditedfiletitle());
            System.out.println("Selected File is: " + file.getAbsolutePath());
            System.out.println("Any file action could be performed here: copy, read etc.");

            UplFile uplFile = new UplFile();
            uplFile.setOrigFileName(file.getAbsolutePath());
            uplFile.setTargetFileName("thePathWhereItWasCopied2ForExample");
            uplFile.setUserName4File(model.getEditedfiletitle());
            model.getUplfiles().add(uplFile);

        } else {
            System.out.println("No file selected!");
        }

    }
    
    
     public static String showFileDialog(String strTitel, String strAllowedFileExts) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(strTitel);
        String[] split = strAllowedFileExts.split(" ");
        for (int i = 0; i < split.length; i++) {
            split[i] = "*" + split[i];
            System.out.println("Allowed Extensions:  " + split[i]);
        }

        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter(strAllowedFileExts, split);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }    
    
    
    

    public static void init() {
        
        ui = new Data();
        Models.toRaw(ui);
        a = TemplateRegistration.register("a", "a.html");
        b = TemplateRegistration.register("b", "b.html");
        f = TemplateRegistration.register("f", "file.html");
        
        // Variable errorField is used in form: must be available before template ist set.
        ui.setErrorField("nope");
        ui.setTemplate("a");
        //  Highlighting in the mail menue
        ui.setCurrentMenu(1);
        
        ui.setStrActionWithDuration("Noch nicht ausgeführt.");
        
        ui.setContentA("Please be first and rate your last theater visit!");
        ui.setContentB("This is Content for Template B!");
        ui.setContentC("This is Content for Template C!");
        
        ui.getRatingValues().add(1);
        ui.getRatingValues().add(2);
        ui.getRatingValues().add(3);
        ui.getRatingValues().add(4);
        ui.getRatingValues().add(5);
        
        ui.getSynesthesiaValues().add("red");
        ui.getSynesthesiaValues().add("green");
        ui.getSynesthesiaValues().add("blue");
        ui.getSynesthesiaValues().add("orange");
        ui.getSynesthesiaValues().add("yellow");
        
        ui.getDirectoryValues().add("Target A 1");       
        ui.getDirectoryValues().add("Target A 2");
        ui.getDirectoryValues().add("Target B 1");
        ui.getDirectoryValues().add("Target B 2");
    }
    
    /**
     * Called when the page is ready.
     */
    static void onPageLoad() throws Exception {
        init();
        ui.applyBindings();
    }

    @Model(className = "TheaterReview", properties = {
        @Property(name = "performance", type = String.class), 
        @Property(name = "dateOfVisit", type = String.class), 
        @Property(name = "content", type = String.class),   
        @Property(name = "synesthesia", type = String.class, array = true),
        @Property(name = "rating", type = int.class),
    })
    
    public static class TheaterReviewVMD {
        
    }
    


    @Function
    public static void setA(Data model) {
        model.setCurrentMenu(1);
        model.setTemplate("a");
    }

    @Function
    public static void setB(Data model) {
        model.setCurrentMenu(2);
        model.setTemplate("b");
    }
    
    @Function
    public static void setF(Data model) {
        model.setCurrentMenu(3);
        model.setTemplate("f");
    }
    
    @Function
    public static void savePerformanceRatingSetB(Data model) {

        String strDate = model.getEditedTheaterReview().getDateOfVisit();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {

            if (strDate == null) {
                model.setErrorMessage("Format-Error: Desired date format is: dd.mm.yyyy");
                model.setErrorField("dateOfVisit");
                setA(model);
            } else {
                Date test = sdf.parse(strDate);
                model.getTheaterReviews().add(model.getEditedTheaterReview());
                System.out.println("Date OK: " + strDate);
                model.setEditedTheaterReview(new TheaterReview());

                setB(model);
                model.setContentB("Your review was saved successfully.");
            }
        } catch (ParseException pe) {
            System.out.println("Date format invalid! strDate" + strDate);
            model.setErrorMessage("Format-Error: Desired date format is: dd.mm.yyyy");
            setA(model);
        }

    }
    
    @Function
     public static void actionExitNow() {
        String strMethodName1 = DataModel.class.getSimpleName() + ".actionExitNow";

                    String strAppShutDownMessage = "Shutdown application now.";
                    System.out.println(strAppShutDownMessage);
                    System.exit(0);
    }

     
    @Function
    public static void doSomethingWithLongDuration(Data model) {
        System.out.println("Starte lang dauernde Activity");
        try {
            Thread.sleep(5000); //5 sekunden schlafen
            // hier soll auf der Seite ein Fortschrittsbalken  angezeigt werden
            model.setStrActionWithDuration("Die langdauernde Aktion wurde ausgeführt.");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(DataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setB(model);
        System.out.println("Beende dauernde Activity");
    }
     
}
