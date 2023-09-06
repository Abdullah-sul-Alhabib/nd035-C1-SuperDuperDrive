package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoteMapper {
    /**
     *
     * @param userid user ID
     * @return notes tied to the user.
     */
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    Note getAllNotes(Integer userid);

    /**
     *
     * @param note Note object to insert.
     * @return newly generated noteId.
     */
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note note);
}
