package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * MyBatis mapper for credentials.
 */
@Mapper
public interface CredentialMapper {
    /**
     * Gets credentials.
     *
     * @param credintialid The ID for the specific credential to retrieve.
     * @return credential object tied with provided ID.
     */
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credintialid}")
    Credential getCredentials(Integer credintialid);

    /**
     * Gets all credentials.
     *
     * @param userid ID of the user to get credentials for.
     * @return all credentials tied with provided user.
     */
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getAllCredentials(Integer userid);

    /**
     * Insert credentials int.
     *
     * @param credential credential object to insert into DB.
     * @return The newly created credential ID.
     */
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid}, #{notedescription}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredentials(Credential credential);

}