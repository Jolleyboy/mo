package mo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.io.filefilter.FileFilterUtils.suffixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible of obtaining the files needed to make the
 * program work.
 */
public class MusicCollector {
    private Logger logger = LoggerFactory.getLogger(MusicCollector.class);
    
    Model model = Model.getInstance();
    private int out;
 
    /**
     * This method will go down through the sub-folders in a recursive
     * manner and then calling searchFiles to obtain the files.
     * @param path 
     */
    public void searchComp(String path){
        String folder;
        File parent = new File(path);
        File[] listOfFiles = parent.listFiles();
        
        for (File listOfFile : listOfFiles) {
            out++;
            folder = path + "\\";    
            
            searchFiles(folder);
            if (listOfFile.isFile() || out != 1) {
                break;
            }
        }
    }
    
    /**
     * This method will obtain the audio files from under a specific
     * directory.
     * @param path 
     */
    void searchFiles(String path) {
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                files = listOfFile.getName();
                if (files.endsWith(".mp3") || files.endsWith(".wav") || files.endsWith(".flac") || files.endsWith(".m4a") || files.endsWith(".ogg") || files.endsWith(".aac")) {
                    //Change files into audiofiles and add them into a list
                    MusicFile mf = new MusicFile(path + files);
                    addToList(mf);
                    //System.out.println(path + files);
                }
            }
            else {
                searchComp(listOfFile.getPath());
            }
        }
    }
    
    /**
     * This method will append to the MusicList each new MusicFile
     * that is loaded.
     * @param newFile 
     */
    private void addToList(MusicFile newFile) {
        boolean inList = false;
        ObservableList<MusicFile> mfList = model.getList();
        for (MusicFile check : mfList) {
            System.out.println(check.getPath() + " " + newFile.getPath());
            if (check.getPath().equals(newFile.getPath()))
                inList = true;
        }
        if (inList == false) {
            mfList.addAll(newFile);
            model.setList(mfList);
        }
    }

    
    //Unused method, has same function as searchComp
    public void findAndAddFiles(File path) {
        Collection<File> files = new ArrayList<File>();
        ObservableList ol = model.getList();
        String[] suffixes = {".mp3", ".wav", ".flac",".m4a", ".ogg", ".aac"};
        for (String suffix : suffixes) {
            files.addAll(FileUtils.listFiles(path,suffixFileFilter(suffix),TrueFileFilter.INSTANCE));
        }
        for (File file : files) {
            ol.add(new MusicFile(file));
        }
        model.setList(ol);
    }
}
