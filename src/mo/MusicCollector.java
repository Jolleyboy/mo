package mo;

import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;


public class MusicCollector {
    Model model = Model.getInstance();
    public String path;// = "C:\\Users\\Rafael\\Music\\stcb\\";

    /**
     * This method should be called to set the directory from which the
     * user desires to obtain files.
     * @param newPath 
     */
    public void setPath(String newPath) {
        path = newPath;
    }
    
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
            if (listOfFile.isDirectory()) {
                folder = path + listOfFile.getName() + "\\";
                System.out.println(folder);
                searchComp(folder);
            } else {
                folder = path + "\\";
                searchFiles(folder);
                break;
            }
        }
    }
    
    /**
     * This method will obtain the audio files from under a specific
     * directory.
     * @param path 
     */
    private void searchFiles(String path) {
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                files = listOfFile.getName();
                if (files.endsWith(".mp3") || files.endsWith(".wav") || files.endsWith(".flac") || files.endsWith(".m4a") || files.endsWith(".ogg") || files.endsWith(".aac")) {
                    //Change files into audiofiles and add them into a list
                    MusicFile mf = new MusicFile(files);
                    addToList(mf);
                    System.out.println(files);
                }
            }
        }
    }
    
    /**
     * This method will append to the MusicList each new MusicFile
     * that is loaded.
     * @param newFile 
     */
    private void addToList(MusicFile newFile) {
        ObservableList<MusicFile> mfList = model.getList();
        mfList.addAll(newFile);
        model.setList(mfList);
    }
    
}
