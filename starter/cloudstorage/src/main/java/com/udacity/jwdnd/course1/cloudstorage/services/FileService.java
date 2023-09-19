package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File handler service.
 *
 * @// TODO : 10-Sep-23 receive files, store them, retrieve them by id, ensure their size isn't past limit
 */
@Service
public class FileService {

    private List<File> fileList;
    private final FileMapper fileMapper;


    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFileById(int fileId) {
        // @// TODO optimization, this uses two queries
        File file = fileMapper.getOneFileInfo(fileId);
        file.setFileData(fileMapper.getFileData(fileId).getFileData());
        return file;
    }


    public List<File> getFileList(int userId){
        //if file list is empty (this means the server just started)
        // then just set a new one and return it.
        if (fileList == null || fileList.isEmpty()) {
            setFileList(userId);
            return fileList;
        }
        //if there is a file list already, ensure it is for the same user
        AtomicInteger uId = new AtomicInteger();
        this.fileList.stream().peek(f -> uId.set(f.getUserId()));
        if (uId.get() == userId) {
            return fileList;
        }
        else {
            setFileList(userId);
        }
        return fileList;
    }

    public void setFileList(int userId) {
        fileList = fileMapper.getAllFilesFromUserId(userId);
    }

    public void uploadFile(MultipartFile file, int userId) throws SQLException, IOException {
        //call file factory to get type File instead of multiPartFile.
        File fileHolder = getFileFromMultipartFile(file,userId);
        //Insert the file into DB, the fileMapper insert will return the newly created fileId.
        int fileId = fileMapper.insert(fileHolder);
        //Use fileId to upload the file separately,
        // this is done in a separate query in case we want to handle special logic for file uploading here.
        fileMapper.updateFileData(fileHolder.getFileData(),fileId);
    }

    public File getFileFromMultipartFile(MultipartFile file, int userId) throws IOException, SQLException {
        //This function is needed because fileData needs to be separated out of the constructor due to mybatis mapper error with Blob.
        // cast the file into a Blob type.
        Blob blob = new SerialBlob(file.getBytes());
        //create a file object, the first null is the fileId which will be generated by ORM later
        File file1 = new File(null, file.getOriginalFilename(), file.getContentType(),file.getSize(),userId);
        //Attach file data, for now this is the only way,
        // but we might have different factories for different file types.
        file1.setFileData(blob);
        return file1;
    }
}
